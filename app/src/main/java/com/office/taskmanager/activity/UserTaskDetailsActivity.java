package com.office.taskmanager.activity;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ZoomControls;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.office.taskmanager.Adaptes.CommentsAdapter;
import com.office.taskmanager.Pojo.Comments;
import com.office.taskmanager.Pojo.Task;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.SharedPreferenceUtil;
import com.office.taskmanager.util.ProjectVariables;
import com.office.taskmanager.util.RefreshLisener;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserTaskDetailsActivity extends AppCompatActivity implements RestfulListener {

    private Animator mCurrentAnimatorEffect;
    private int mShortAnimationDurationEffect;
    TextView mTaskHeading, mTask, mAsignby, mStart, mEnd, mStarttime, mEndtime, mUname, mStatus, head;
    String taskHeading, task, asignby, start, end, starttime, endtime, uname, profile, status, taskID, taskfromid, taskimage ,taskVideo;
    ImageView mProfile, mTaskImage;
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
    String video, task_comment, audio;
    String ImageName = "";
    int taskIDs;
    String imageURI = "";
    private boolean isResources = true;
    static final String FTP_HOST = "myaccountsretail.com";
    static final String FTP_USER = "myRetail";
    static final String FTP_PASS = "vKsj30!9";
    private ZoomControls zoom;
    private ImageView image;
    private VideoView videoPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task_details);
        UserRole = SharedPreferenceUtil.getInstance().getString(UserTaskDetailsActivity.this, ProjectVariables.USER_ROLE, "4");
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
        mTaskImage = (ImageView) findViewById(R.id.taskImage);
        videoPreview = (VideoView) findViewById(R.id.videoPreview);

        Intent intent = getIntent();
        if (null != intent) {

            taskHeading = intent.getStringExtra(ProjectVariables.TASK_HEADING);
            task = intent.getStringExtra(ProjectVariables.TASK);
            profile = intent.getStringExtra(ProjectVariables.PROFILE);
            start = intent.getStringExtra(ProjectVariables.START);
            end = intent.getStringExtra(ProjectVariables.END);
            starttime = intent.getStringExtra(ProjectVariables.STARTTIMES);
            endtime = intent.getStringExtra(ProjectVariables.ENDTIMES);
            uname = intent.getStringExtra(ProjectVariables.UNAME);
            status = intent.getStringExtra(ProjectVariables.STATUSS);
            taskIDs = intent.getIntExtra(ProjectVariables.TASK_ID, 0);
            asignby = intent.getStringExtra(ProjectVariables.ASIGNBY);
            taskfromid = intent.getStringExtra(ProjectVariables.TASK_FROMID);
            taskimage = intent.getStringExtra(ProjectVariables.TASK_IMAGE);
            taskVideo = intent.getStringExtra(ProjectVariables.TASK_VIDEO);

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
        collapser.setExpandedTitleColor(Color.parseColor("#ffffff"));
        collapser.setExpandedTitleMarginEnd(10);
        collapser.setTitle(uname);
        try {
            Picasso.with(this)
                    .load(ProjectVariables.IMAGE_PATH + profile)
                    .placeholder(R.drawable.amply_background)
                    .error(R.drawable.amply_background)
                    .resize(300, 300)
                    .into(mProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glide.with(this).load(ProjectVariables.IMAGE_PATH + taskimage)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mTaskImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaController mediaController = new MediaController(_context);
        mediaController.setAnchorView(videoPreview);
        String MainUrl = ProjectVariables.IMAGE_PATH;
        MainUrl = MainUrl + taskVideo;
        Uri video = Uri.parse(MainUrl);
        videoPreview.setMediaController(mediaController);
        videoPreview.setVideoURI(video);
        videoPreview.start();
        videoPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                videoPreview.start();
                return false;
            }
        });



        mTaskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog showimage = new Dialog(_context);
                showimage.requestWindowFeature(Window.FEATURE_NO_TITLE);
                showimage.setContentView(R.layout.showimage);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(showimage.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                showimage.show();
                image = (ImageView) showimage.findViewById(R.id.showImage);
                zoom = (ZoomControls) showimage.findViewById(R.id.zoomControls1);
                zoom.setOnZoomInClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO Auto-generated method stub
                        float x = image.getScaleX();
                        float y = image.getScaleY();
                        image.setScaleX((float) (x + 1));
                        image.setScaleY((float) (y + 1));
                    }
                });
                zoom.setOnZoomOutClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        float x = image.getScaleX();
                        float y = image.getScaleY();
                        image.setScaleX((float) (x - 1));
                        image.setScaleY((float) (y - 1));
                    }
                });
                try {
                    Glide.with(_context).load(ProjectVariables.IMAGE_PATH + taskimage)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        JSONObject obj = new JSONObject();
        try {
            obj.accumulate("Cid", taskIDs + "");
            AsynHttpPost post = new AsynHttpPost(_context, 0, 325, ProjectVariables.TASK_COMMENTS, listener, obj, "");
            post.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab);
        assert fab1 != null;
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(_context, TaskCommentActivity.class);
                intent.putExtra(ProjectVariables.TASK_ID, taskIDs);
                intent.putExtra(ProjectVariables.ASIGNBY, asignby);
                startActivity(intent);
                overridePendingTransition(R.anim.right_enter, R.anim.left_out);
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
                overridePendingTransition(R.anim.left_enter, R.anim.right_out);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
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
