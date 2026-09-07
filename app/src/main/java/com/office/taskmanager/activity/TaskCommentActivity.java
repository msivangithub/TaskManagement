package com.office.taskmanager.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.office.taskmanager.Adaptes.CommentsAdapter;
import com.office.taskmanager.Pojo.Comments;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.ImageUploadTask;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.PreferenceUtil;
import com.office.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskCommentActivity extends AppCompatActivity implements RestfulListener {

    private RelativeLayout notificationCount1;
    private RecyclerView commentsrecyclerview;
    private CommentsAdapter cAdapter;
    private Context _context;
    private EditText comment;
    private Spinner statusSpinner;
    private Button submit;
    private ImageButton record;
    private LinearLayout mImage, mVideo;
    private final String[] status = {""};
    private String video, image, task_comment, audio;
    private int taskIDs;
    public static final String TASK_ID = "task_id";
    public static final String ASIGNBY = "asignby";
    public static final String PROFILE = "profile";
    String UserRole = "";
    private String taskHeading, task, asignby, start, end, starttime, endtime, uname, profile, taskID, taskfromid;
    private RestfulListener listener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog pdForVideoUpload;
    private String imageURI = "";
    static final String FTP_HOST = "myaccountsretail.com";
    static final String FTP_USER = "myRetail";
    static final String FTP_PASS = "vKsj30!9";
    Toolbar toolbarWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_comment);
        setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Task Reply</font>"));
        UserRole = PreferenceUtil.getInstance().getString(TaskCommentActivity.this, ProjectVariables.USER_ROLE, "4");
        _context = TaskCommentActivity.this;
        listener = TaskCommentActivity.this;

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (null != intent) {
            taskIDs = intent.getIntExtra(TASK_ID, 0);
            asignby = intent.getStringExtra(ASIGNBY);

        }
        comment = (EditText) findViewById(R.id.taskComments);
        statusSpinner = (Spinner) findViewById(R.id.taskSpinner);
        submit = (Button) findViewById(R.id.task_submit);
        record = (ImageButton) findViewById(R.id.record);
        commentsrecyclerview = (RecyclerView) findViewById(R.id.TaskComment);


        ArrayList<Comments> currentPojo = new ArrayList<Comments>();
        currentPojo = AppUtil.getCurrentPojo();
        if (currentPojo.size() > 0) {
            cAdapter = new CommentsAdapter(currentPojo, _context);
            commentsrecyclerview.setLayoutManager(new LinearLayoutManager(_context));
            commentsrecyclerview.setItemAnimator(new DefaultItemAnimator());
            commentsrecyclerview.setHasFixedSize(true);
            commentsrecyclerview.setAdapter(cAdapter);
        }

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(_context);
                d.setContentView(R.layout.image_video);
                d.setTitle("Select Video or Image.....!");
                d.show();
                mImage = (LinearLayout) d.findViewById(R.id.getImage);
                mVideo = (LinearLayout) d.findViewById(R.id.getVideo);
                LinearLayout recordAudio = (LinearLayout) d.findViewById(R.id.getAudio);
                Button CAncel = (Button) d.findViewById(R.id.CAncel);
                CAncel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });

                recordAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        _context.startActivity(new Intent(_context, RecordAudioActivity.class));

                    }
                });

                mVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
                        startActivityForResult(takeVideoIntent, 667);
                    }
                });

                mImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 202);
                    }
                });
            }
        });
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    status[0] = "Pending";
                if (position == 1)
                    status[0] = "Progress";
                if (position == 2)
                    status[0] = "Completed";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskCommentsData();
            }


            private void taskCommentsData() {
                SharedPreferences sharedPreferences = _context.getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                video = sharedPreferences.getString("video", "novideo");
                SharedPreferences sharedPreferences1 = _context.getSharedPreferences("CurrentImage", Context.MODE_PRIVATE);
                image = sharedPreferences1.getString("Image", "noimage");
                SharedPreferences sharedPreferen = _context.getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
                audio = sharedPreferen.getString("Audio", "noaudio");
                task_comment = comment.getText().toString();

                if (!(task_comment.equalsIgnoreCase("") && task_comment.isEmpty()) && !(status[0].equalsIgnoreCase("") && status[0].isEmpty())) {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.accumulate("Cid", taskIDs + "");
                        obj.accumulate("TaskStatus", status[0]);
                        obj.accumulate("Comments", task_comment);

                        if (!video.equalsIgnoreCase(ProjectVariables.NOVIDEO))
                            obj.accumulate("video", video);

                        if (!image.equalsIgnoreCase(ProjectVariables.NOIMAGE))
                            obj.accumulate("Image", image);

                        if (!audio.equalsIgnoreCase(ProjectVariables.NOAUDIO))
                            obj.accumulate("Audio", audio);

                        if (UserRole.equalsIgnoreCase("1")) {

                            obj.accumulate("TaskToId", PreferenceUtil.getInstance().getString(_context, "currentUser", "current"));

                        } else if (UserRole.equalsIgnoreCase("5") || UserRole.equalsIgnoreCase("6") || UserRole.equalsIgnoreCase("4")) {

                            obj.accumulate("TaskToId", asignby);

                        } else {

                            obj.accumulate("TaskToId", asignby);
                        }
                        obj.accumulate("TaskFromId", PreferenceUtil.getInstance().getString(_context, "Uid", "c001"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (AppUtil.isNetworkAvailable(TaskCommentActivity.this)) {
                        AsynHttpPost post = new AsynHttpPost(_context, 0, 887, ProjectVariables.TASK_UPDATE, listener, obj, "");
                        post.execute();
                    } else {
                        Toast.makeText(_context, ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(_context, "Please enter comment about your task", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    @Override
    public void getData(String s, String status, int rType, String temp) {
        if (rType == 887) {
            try {
                JSONArray array = new JSONArray(s);
                JSONObject obj = array.getJSONObject(0);

                String result = obj.getString("Result");
                SharedPreferences sharedPreferences = getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("video", ProjectVariables.NOVIDEO);
                curentEdit.commit();

                SharedPreferences sharedPreferences1 = getSharedPreferences("CurrentImage", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit1 = sharedPreferences1.edit();
                curentEdit1.putString("Image", ProjectVariables.NOIMAGE);
                curentEdit1.commit();


                SharedPreferences sharedPrefer = getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentE = sharedPrefer.edit();
                curentE.putString("Audio", ProjectVariables.NOAUDIO);
                curentE.commit();

                Toast.makeText(TaskCommentActivity.this, result, Toast.LENGTH_LONG).show();
                comment.setText("");
            } catch (JSONException e) {
                e.printStackTrace();

            }
        } else if (rType == 325) {

            ArrayList<Comments> current = new ArrayList<>();
            try {
                String comm, userroles, videoPlay, image, audio, profile;

                JSONArray array = new JSONArray(s);
                for (int c = 0; c < array.length(); c++) {
                    Comments c1 = new Comments();
                    JSONObject obj = array.getJSONObject(c);
                    comm = obj.getString("Comments");
                    userroles = obj.getString("UserRole");
                    videoPlay = obj.getString("video");
                    image = obj.getString("image");
                    audio = obj.getString("Audio");
                    profile = obj.getString("UImage");
                    c1.setComments(comm);
                    c1.setUserRole(userroles);
                    c1.setVideo(videoPlay);
                    c1.setImage(image);
                    c1.setAudio(audio);
                    c1.setProfile(profile);
                    current.add(c1);

                }
                //if (array.length() > 0)
                AppUtil.setCurrentPojo(current);

            } catch (JSONException e) {
                e.printStackTrace();
                AppUtil.setCurrentPojo(current);
                // AppUtil.setCurrentComments(new ArrayList<String>());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.left_enter, R.anim.right_out);
                return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(getApplicationContext(), "Main activity result", Toast.LENGTH_LONG).show();
        if (resultCode == Activity.RESULT_OK) {
            //  Toast.makeText(getApplicationContext(), "Main activity result1"+requestCode, Toast.LENGTH_LONG).show();
            if (requestCode == 667) {
                Uri selectedimg = data.getData();
                String origanImage = getAbsolutePath(selectedimg);
                String[] imageArray = origanImage.split("/");

                int length = imageArray.length;

                String convertedImage = imageArray[length - 1];

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentVideo", MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("video", convertedImage);
                curentEdit.commit();

                Toast.makeText(getApplicationContext(), "Video Recorded " + convertedImage, Toast.LENGTH_LONG).show();
                //UploadTask u = null;
                try {
                    ImageUploadTask uploadTask = new ImageUploadTask(_context, getContentResolver().openInputStream(selectedimg), convertedImage);
                    uploadTask.execute();
                    // u = new UploadTask(getContentResolver().openInputStream(selectedimg), convertedImage);
                    // u.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 202) {

                Uri selectedimg = data.getData();
                imageURI = getAbsolutePath(selectedimg);

                String[] imageArray = imageURI.split("/");

                int length = imageArray.length;
                String convertedImage = imageArray[length - 1];

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentImage", MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("Image", convertedImage);
                curentEdit.commit();
                // UploadTask u = null;
                try {
                    ImageUploadTask uploadTask = new ImageUploadTask(_context, getContentResolver().openInputStream(selectedimg), convertedImage);
                    uploadTask.execute();
                    //u.execute();
                    //u = new UploadTask(getContentResolver().openInputStream(selectedimg), convertedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
