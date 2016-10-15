package com.mytask.taskmanager.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.mytask.taskmanager.Adaptes.CommentsAdapter;
import com.mytask.taskmanager.Pojo.Comments;
import com.mytask.taskmanager.Pojo.Task;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.AppUtil;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;
import com.mytask.taskmanager.util.RefreshLisener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserTaskDetailsActivity extends AppCompatActivity implements RestfulListener {

    public static final String TASK_HEADING = "task_heading";
    public static final String TASK = "task";
    public static final String ASIGNBY = "asignby";
    public static final String START = "start";
    public static final String END = "end";
    public static final String STARTTIME = "starttime";
    public static final String ENDTIME = "endtime";
    public static final String UNAME = "uname";
    public static final String PROFILE = "profile";
    public static final String STATUS = "status";
    public static final String TASK_ID = "task_id";
    public static final String TASK_FROMID = "task_fromid";


    TextView mTaskHeading, mTask, mAsignby, mStart, mEnd, mStarttime, mEndtime, mUname, mStatus;
    String taskHeading, task, asignby, start, end, starttime, endtime, uname, profile, status, taskID, taskfromid;
    ImageView mProfile;
    List<Task> billToBillArrayList;
    Context _context;
    String _type;
    Activity a;
    RestfulListener listener;
    LinearLayout mImage, mVideo;
    String UserRole = "";
    CommentsAdapter cAdapter;
    private int lastPosition = -1;
    Task t;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String REFRESH_DELAY = "1";
    RefreshLisener refreshLisener;
    JSONObject jsonObject;
    String video, image, task_comment, audio;
    String ImageName = "";
    int taskIDs;
    ProgressDialog pdForVideoUpload;
    String imageURI = "";
    RecyclerView commentsrecyclerview;

    static final String FTP_HOST = "myaccountsretail.com";
    static final String FTP_USER = "myRetail";
    static final String FTP_PASS = "vKsj30!9";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task_details);

        UserRole = PreferenceUtil.getInstance().getString(UserTaskDetailsActivity.this, ProjectVariables.USER_ROLE, "4");

        listener = UserTaskDetailsActivity.this;
        _context = UserTaskDetailsActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void onStart() {
        super.onStart();
        //initializing view objects
        mTaskHeading = (TextView) findViewById(R.id.task_heading);
        mTask = (TextView) findViewById(R.id.task);
        // mAsignby = (TextView)findViewById(R.id.assign_By);
        mStart = (TextView) findViewById(R.id.start_date);
        mEnd = (TextView) findViewById(R.id.end_date);
        mStarttime = (TextView) findViewById(R.id.start_time);
        mEndtime = (TextView) findViewById(R.id.end_time);
        mUname = (TextView) findViewById(R.id.assign_By);
        mProfile = (ImageView) findViewById(R.id.profileImage);
        mStatus = (TextView) findViewById(R.id.status);
        Intent intent = getIntent();
        if (null != intent) {
            taskHeading = intent.getStringExtra(TASK_HEADING);
            task = intent.getStringExtra(TASK);
            profile = intent.getStringExtra(PROFILE);
            start = intent.getStringExtra(START);
            end = intent.getStringExtra(END);
            starttime = intent.getStringExtra(STARTTIME);
            endtime = intent.getStringExtra(ENDTIME);
            uname = intent.getStringExtra(UNAME);
            status = intent.getStringExtra(STATUS);
            taskIDs = intent.getIntExtra(TASK_ID, 0);
            asignby = intent.getStringExtra(ASIGNBY);
            taskfromid = intent.getStringExtra(TASK_FROMID);
        }
        mTaskHeading.setText(taskHeading);
        mTask.setText(task);
        mStart.setText(start);
        mEnd.setText(end);
        mStarttime.setText(starttime);
        mEndtime.setText(endtime);
        mUname.setText(uname);
        mStatus.setText(status);

        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapser.setExpandedTitleColor(Color.parseColor("#000000"));
        collapser.setTitle(uname);

        Picasso.with(this)
                .load(ProjectVariables.IMAGE_PATH + profile)
                .placeholder(R.drawable.amply_background)
                .error(R.drawable.amply_background)
                .resize(300, 300)
                .into(mProfile);

        JSONObject obj = new JSONObject();
        try {
            obj.accumulate("Cid", taskIDs + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsynHttpPost post = new AsynHttpPost(_context, 0, 325, ProjectVariables.TASK_COMMENTS, listener, obj, "");
        post.execute();
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab);
        assert fab1 != null;
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(_context, TaskCommentActivity.class);
                intent.putExtra(TASK_ID, taskIDs);
                intent.putExtra(ASIGNBY, asignby);
                intent.putExtra(PROFILE,profile);
                startActivity(intent);

                /*final String[] status = {""};
                final Dialog updateDialog = new Dialog(UserTaskDetailsActivity.this);
                updateDialog.setTitle("Task Replay");
                updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                updateDialog.setContentView(R.layout.task_editor);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(updateDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                updateDialog.getWindow().setAttributes(lp);
                updateDialog.show();
                ImageButton edt_close = (ImageButton) updateDialog.findViewById(R.id.edt_close);

                commentsrecyclerview = (RecyclerView) updateDialog.findViewById(R.id.TaskComment);
                ArrayList<Comments> currentPojo = new ArrayList<Comments>();
                currentPojo = AppUtil.getCurrentPojo();
                if (currentPojo.size() > 0) {
                    cAdapter = new CommentsAdapter(currentPojo, _context);
                    commentsrecyclerview.setLayoutManager(new LinearLayoutManager(_context));
                    commentsrecyclerview.setItemAnimator(new DefaultItemAnimator());
                    commentsrecyclerview.setHasFixedSize(true);
                    commentsrecyclerview.setAdapter(cAdapter);

                }

                edt_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDialog.dismiss();
                    }
                });
                final EditText comment = (EditText) updateDialog.findViewById(R.id.taskComments);
                Spinner statusSpinner = (Spinner) updateDialog.findViewById(R.id.taskSpinner);
                Button submit = (Button) updateDialog.findViewById(R.id.task_submit);

                ImageButton record = (ImageButton) updateDialog.findViewById(R.id.record);


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

                            AsynHttpPost post = new AsynHttpPost(_context, 0, 887, ProjectVariables.TASK_UPDATE, listener, obj, "");
                            post.execute();
                            updateDialog.dismiss();
                        } else {
                            Toast.makeText(_context, "Please enter comment about your task", Toast.LENGTH_LONG).show();
                        }

                    }
                });*/
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void getData(String s, String status, int rType) {
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

                Toast.makeText(UserTaskDetailsActivity.this, result, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
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
}
