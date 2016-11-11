package com.mytask.taskmanager.Adaptes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.lb.recyclerview_fast_scroller.RecyclerViewFastScroller;
import com.mytask.taskmanager.Pojo.Comments;
import com.mytask.taskmanager.Pojo.Task;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.activity.RecordAudioActivity;
import com.mytask.taskmanager.activity.Tasks;
import com.mytask.taskmanager.activity.UserTaskDetailsActivity;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.AppUtil;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by NEWSYSTEM1 on 6/9/2016.
 */
public class TaskDetailsAdapter extends RecyclerView.Adapter<TaskDetailsAdapter.MyViewHolder> implements RestfulListener ,RecyclerViewFastScroller.BubbleTextGetter{

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


    List<Task> billToBillArrayList;
    Context _context;
    String _type;
    Activity a;
    RestfulListener listener;
    LinearLayout mImage, mVideo;
    private int lastPosition = -1;
    String UserRole = "";
    Button pComments;
    RecyclerView Comment;
    String video, audio, images;
    JSONObject obj;

    public TaskDetailsAdapter(Context context, RestfulListener rl, int taskId, List<Task> billToBillArrayList, String type) {
        this.billToBillArrayList = billToBillArrayList;
        _context = context;
        _type = type;
        listener = rl;
        a = (Activity) context;
        UserRole = PreferenceUtil.getInstance().getString(_context, ProjectVariables.USER_ROLE, "4");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return billToBillArrayList.size();
    }

    @Override
    public void getData(String s, String status, int rType) {

    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (billToBillArrayList.get(pos).getTaskHeading().length() > 0) {
            return Character.toString(billToBillArrayList.get(pos).getTaskHeading().charAt(0));
        }
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView task, task_header, mTaskDate, mTaskTime, mAsignBy;
        public CheckBox check;
        public LinearLayout taskrow, deleteTask;
        public View itemView;
        public ImageView icon_entry;
        public ImageButton mImageMenu;

        public MyViewHolder(View convertView) {
            super(convertView);
            itemView = convertView;
            task = (TextView) convertView.findViewById(R.id.taskTitle);
            task_header = (TextView) convertView.findViewById(R.id.txt_taskheader);
            taskrow = (LinearLayout) convertView.findViewById(R.id.taskrow);
            mImageMenu = (ImageButton) convertView.findViewById(R.id.Button_menu);
            mAsignBy = (TextView) convertView.findViewById(R.id.asignBy);
            //check = (CheckBox) convertView.findViewById(R.id.check);
            icon_entry = (ImageView) itemView.findViewById(R.id.icon_entry);
            mTaskDate = (TextView) convertView.findViewById(R.id.taskDate);
            mTaskTime = (TextView) convertView.findViewById(R.id.taskTime);


            taskrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(_context, UserTaskDetailsActivity.class);
                    intent.putExtra(TASK_HEADING, billToBillArrayList.get(getAdapterPosition()).getTaskHeading());
                    intent.putExtra(TASK, billToBillArrayList.get(getAdapterPosition()).getTaskDes());
                    intent.putExtra(ASIGNBY, billToBillArrayList.get(getAdapterPosition()).getTaskFromId());
                    intent.putExtra(START, billToBillArrayList.get(getAdapterPosition()).getExpStartDate());
                    intent.putExtra(END, billToBillArrayList.get(getAdapterPosition()).getExpEndDate());
                    intent.putExtra(STARTTIME, billToBillArrayList.get(getAdapterPosition()).getStartTime());
                    intent.putExtra(ENDTIME, billToBillArrayList.get(getAdapterPosition()).getEndTime());
                    intent.putExtra(UNAME, billToBillArrayList.get(getAdapterPosition()).getUname());
                    intent.putExtra(PROFILE, billToBillArrayList.get(getAdapterPosition()).getProfile());
                    intent.putExtra(STATUS, billToBillArrayList.get(getAdapterPosition()).getTaskStatus());
                    intent.putExtra(TASK_ID, billToBillArrayList.get(getAdapterPosition()).getTaskId());
                    intent.putExtra(TASK_FROMID ,billToBillArrayList.get(getAdapterPosition()).getTaskToId());

                    Activity act = (Activity) _context;
                    act.startActivity(intent);
                    act.overridePendingTransition(R.anim.right_enter, R.anim.left_out);
                }
            });

        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final int pos = position;

        final Task t = billToBillArrayList.get(position);
        viewHolder.task.setText(t.getTaskDes());
        viewHolder.task_header.setText(t.getTaskHeading());
        viewHolder.mTaskDate.setText(t.getActStartDate());
        viewHolder.mTaskTime.setText(t.getStartTime());
        viewHolder.mAsignBy.setText(t.getUname());
        // setAnimation(viewHolder.itemView, position);

      /*  Picasso.with(_context)
                .load(ProjectVariables.IMAGE_PATH + billToBillArrayList.get(position).getProfile())
                .placeholder(R.drawable.profile_sample)   // optional
                .error(R.drawable.profile_sample)      // optional
                .resize(300 ,300)
                .into(viewHolder.icon_entry);*/

       /* if (billToBillArrayList.get(position).getTaskHeading().length() > 0)
            viewHolder.icon_entry.setText("" + billToBillArrayList.get(position).getTaskHeading().charAt(0));*/

        if (billToBillArrayList.get(position).getTaskHeading().length() > 0) {
            // viewHolder.icon_entry.setText("" + billToBillArrayList.get(position).getTaskHeading().charAt(0));
            String firstLetter = String.valueOf(billToBillArrayList.get(position).getTaskHeading().charAt(0));
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // int color = generator.getColor(billToBillArrayList.get(position));
            int color = generator.getRandomColor();
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px
            viewHolder.icon_entry.setImageDrawable(drawable);
        }

        if (UserRole.equalsIgnoreCase("3")) {
            viewHolder.mImageMenu.setVisibility(View.GONE);
        }

        if (position % 2 == 0) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
/*        viewHolder.taskrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("UserRole", UserRole);
                final Dialog d = new Dialog(_context);
                //d.setTitle("Task Details");
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.show_task_detils_row);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(d.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                d.show();
                d.getWindow().setAttributes(lp);
                obj = new JSONObject();
                try {
                    obj.accumulate("Cid", t.getTaskId() + "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AsynHttpPost post = new AsynHttpPost(_context, 0, 999, ProjectVariables.TASK_COMMENTS, listener, obj, "");
                post.execute();


                String UserRole = PreferenceUtil.getInstance().getString(_context, ProjectVariables.USER_ROLE, "4");
                ImageButton button = (ImageButton) d.findViewById(R.id.show_close);
                Button remind = (Button) d.findViewById(R.id.id_remind);
                Button delete = (Button) d.findViewById(R.id.id_delete);
                Button smsAlert = (Button) d.findViewById(R.id.id_smsalert);
                Button id_score = (Button) d.findViewById(R.id.id_score);

                if (UserRole.equalsIgnoreCase("3")) {
                    remind.setVisibility(View.GONE);
                    delete.setVisibility(View.GONE);
                    smsAlert.setVisibility(View.GONE);
                    id_score.setVisibility(View.GONE);
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                final TextView task, taskHead, asignBy, start, end, status, startTime, endTime ,Uname;
                final ImageView mProfile;
                final RecyclerView Comment;
                task = (TextView) d.findViewById(R.id.txt_show_desc);
                taskHead = (TextView) d.findViewById(R.id.txt_show_task_head);
                asignBy = (TextView) d.findViewById(R.id.txt_show_assignBy);
                start = (TextView) d.findViewById(R.id.txt_show_start);
                end = (TextView) d.findViewById(R.id.txt_show_endDate);
                status = (TextView) d.findViewById(R.id.TaskStatus);
                startTime = (TextView) d.findViewById(R.id.startTime);
                endTime = (TextView) d.findViewById(R.id.endTime);
                Uname = (TextView)d.findViewById(R.id.uname);
                mProfile = (ImageView)d.findViewById(R.id.profile_taskdetails);

                pComments = (Button) d.findViewById(R.id.previousComments);
                Comment = (RecyclerView) d.findViewById(R.id.TaskComment);
                //  Comment.setDivider(new ColorDrawable(Color.parseColor("#000000")));
                // Comment.setText(t.getTaskComment());
                pComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Comments> currentPojo = new ArrayList<Comments>();
                        currentPojo = AppUtil.getCurrentPojo();
                        if (currentPojo.size() > 0) {
                            CommentsAdapter cAdapter = new CommentsAdapter(currentPojo, _context);
                            Comment.setLayoutManager(new LinearLayoutManager(_context));
                            Comment.setItemAnimator(new DefaultItemAnimator());
                            Comment.setHasFixedSize(true);
                            Comment.setAdapter(cAdapter);
                        }
                    }
                });

                task.setText(t.getTaskDes());
                taskHead.setText(t.getTaskHeading());
                asignBy.setText(t.getTaskFromId());
                start.setText(t.getExpStartDate());
                end.setText(t.getExpEndDate());
                status.setText(t.getTaskStatus());
                startTime.setText(t.getStartTime());
                endTime.setText(t.getEndTime());
                Uname.setText(t.getUname());
                Picasso.with(_context)
                        .load(ProjectVariables.IMAGE_PATH + billToBillArrayList.get(position).getProfile())
                        .placeholder(R.drawable.profile_sample)   // optional
                        .error(R.drawable.profile_sample)      // optional
                        .resize(200 ,200)
                        .into(mProfile);

                Button b = (Button) d.findViewById(R.id.showvideo);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog showVideo = new Dialog(_context);
                        showVideo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        showVideo.setContentView(R.layout.showvideo);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(showVideo.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                        showVideo.show();
                        VideoView videoview = (VideoView) showVideo.findViewById(R.id.videoPreview);
                        MediaController mediaController = new MediaController(_context);
                        mediaController.setAnchorView(videoview);
                        String MainUrl = "http://makeindiakart.com/taskfiles/";
                        MainUrl = MainUrl + billToBillArrayList.get(position).getVideo();
                        Uri video = Uri.parse(MainUrl);
                        videoview.setMediaController(mediaController);
                        videoview.setVideoURI(video);
                        videoview.start();

                    }
                });

                Button task_edt = (Button) d.findViewById(R.id.id_task_edt);

                task_edt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        final RecyclerView Comments;
                        final String[] status = {""};
                        final Dialog updateDialog = new Dialog(_context);
                        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        updateDialog.setContentView(R.layout.task_editor);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(updateDialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                        updateDialog.getWindow().setAttributes(lp);
                        updateDialog.show();
                        Comments = (RecyclerView) updateDialog.findViewById(R.id.TaskComment);
                        ImageButton edt_close = (ImageButton) updateDialog.findViewById(R.id.edt_close);

                        ArrayList<Comments> currentPojo = new ArrayList<Comments>();
                        currentPojo = AppUtil.getCurrentPojo();
                        if (currentPojo.size() > 0) {
                            CommentsAdapter cAdapter = new CommentsAdapter(currentPojo, _context);
                            Comments.setLayoutManager(new LinearLayoutManager(_context));
                            Comments.setItemAnimator(new DefaultItemAnimator());
                            Comments.setHasFixedSize(true);
                            Comments.setAdapter(cAdapter);

                        }
                        edt_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateDialog.dismiss();
                            }
                        });
                        *//*Record video in Task Replay  *//*
                        ImageButton record = (ImageButton) updateDialog.findViewById(R.id.record);

                        *//**
         *Click the Capture Video and Capture Image Using Alear Dialog
         *//*
                        record.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog d = new Dialog(_context);
                                d.setContentView(R.layout.image_video);
                                d.setTitle("Select Video,Image & Audio !");
                                d.show();
                                mImage = (LinearLayout) d.findViewById(R.id.getImage);
                                mVideo = (LinearLayout) d.findViewById(R.id.getVideo);
                                Button CAncel = (Button) d.findViewById(R.id.CAncel);
                                LinearLayout recordAudio = (LinearLayout) d.findViewById(R.id.getAudio);
                                CAncel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        d.dismiss();
                                    }
                                });
                                *//*Click the AleartDialog video popsition*//*

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
                                        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                                        //takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().getPath()+"videocapture_example.mp4");
                                        Activity act = (Activity) _context;
                                        act.startActivityForResult(takeVideoIntent, 667);
                                    }
                                });
                                 *//*Click the AleartDialog image popsition*//*
                                mImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        d.dismiss();
                                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        Activity act = (Activity) _context;
                                        act.startActivityForResult(intent, 202);
                                    }
                                });
                            }
                        });

                        final EditText comment = (EditText) updateDialog.findViewById(R.id.taskComments);
                        Spinner statusSpinner = (Spinner) updateDialog.findViewById(R.id.taskSpinner);
                        Button submit = (Button) updateDialog.findViewById(R.id.task_submit);
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
                                images = sharedPreferences1.getString("Image", "noimages");
                                SharedPreferences sharedPreferen = _context.getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
                                audio = sharedPreferen.getString("Audio", "noaudio");
                                String task_comment = comment.getText().toString();
                                if (!(task_comment.equalsIgnoreCase("") && task_comment.isEmpty()) && !(status[0].equalsIgnoreCase("") && status[0].isEmpty())) {
                                    obj = new JSONObject();
                                    try {
                                        obj.accumulate("Cid", billToBillArrayList.get(position).getTaskId() + "");
                                        obj.accumulate("TaskStatus", status[0]);
                                        obj.accumulate("Comments", task_comment);

                                        if (!video.equalsIgnoreCase(ProjectVariables.NOVIDEO))
                                            obj.accumulate("video", video);

                                        if (!images.equalsIgnoreCase(ProjectVariables.NOIMAGE))
                                            obj.accumulate("Image", images);

                                        if (!audio.equalsIgnoreCase(ProjectVariables.NOAUDIO))
                                            obj.accumulate("Audio", audio);

                                        obj.accumulate("TaskToId", t.getTaskFromId());
                                        obj.accumulate("TaskFromId", PreferenceUtil.getInstance().getString(_context, "Uid", "c001"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    AsynHttpPost post = new AsynHttpPost(_context, 0, 777, ProjectVariables.TASK_UPDATE, listener, obj, "");
                                    post.execute();
                                    updateDialog.dismiss();
                                } else {
                                    Toast.makeText(_context, "Please enter comment about your task", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

                d.show();

            }
        });*/

    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(1500));//to make duration random number between [0,501)
            itemView.startAnimation(anim);
            lastPosition = position;
        }
    }

    public ArrayList<Tasks> TaskList() {
        ArrayList<Tasks> taskses = new ArrayList<Tasks>();
        taskses.add(new Tasks("All"));
        taskses.add(new Tasks("Completed"));
        taskses.add(new Tasks("Pending"));
        taskses.add(new Tasks("Progress"));
        return taskses;
    }


}
