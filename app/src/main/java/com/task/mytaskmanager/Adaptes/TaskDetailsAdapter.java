package com.task.mytaskmanager.Adaptes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.task.mytaskmanager.Pojo.Comments;
import com.task.mytaskmanager.Pojo.Task;
import com.task.mytaskmanager.Pojo.TaskUser;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.activity.Tasks;
import com.task.mytaskmanager.activity.TasksAdapter;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.PreferenceUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by NEWSYSTEM1 on 6/9/2016.
 */
public class TaskDetailsAdapter extends RecyclerView.Adapter<TaskDetailsAdapter.MyViewHolder> implements RestfulListener {
    List<Task> billToBillArrayList;
    Context _context;
    String _type;
    Activity a;
    RestfulListener listener;
    LinearLayout mImage, mVideo;
    private int lastPosition = -1;
    String UserRole = "";
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView task, task_header ,icon_entry;
        public CheckBox check;
        public LinearLayout taskrow,deleteTask;
        public View itemView;
        public ImageButton mImageMenu;
        public MyViewHolder(View convertView) {
            super(convertView);
            itemView = convertView;
            task = (TextView) convertView.findViewById(R.id.taskTitle);
            task_header = (TextView) convertView.findViewById(R.id.txt_taskheader);
            taskrow = (LinearLayout) convertView.findViewById(R.id.taskrow);
            mImageMenu = (ImageButton) convertView.findViewById(R.id.Button_menu);
            //check = (CheckBox) convertView.findViewById(R.id.check);
            icon_entry = (TextView) itemView.findViewById(R.id.icon_entry);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final int pos = position;

        final Task t = billToBillArrayList.get(position);
        viewHolder.task.setText(t.getTaskDes());
        viewHolder.task_header.setText(t.getTaskHeading());
        setAnimation(viewHolder.itemView, position);
        viewHolder.icon_entry.setText("" + billToBillArrayList.get(position).getTaskHeading().charAt(0));
        if(UserRole.equalsIgnoreCase("3")){
            viewHolder.mImageMenu.setVisibility(View.GONE);
        }

        if (position % 2 == 0) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#AFB42B"));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#AFB42B"));
        }
        viewHolder.taskrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("UserRole",UserRole);
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
                JSONObject obj = new JSONObject();
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

                final TextView task, taskHead, asignBy, start, end, status;
                RecyclerView Comment = null;
                task = (TextView) d.findViewById(R.id.txt_show_desc);
                taskHead = (TextView) d.findViewById(R.id.txt_show_task_head);
                asignBy = (TextView) d.findViewById(R.id.txt_show_assignBy);
                start = (TextView) d.findViewById(R.id.txt_show_start);
                end = (TextView) d.findViewById(R.id.txt_show_endDate);
                status = (TextView) d.findViewById(R.id.TaskStatus);
                Button pComments = (Button) d.findViewById(R.id.previousComments);
                Comment = (RecyclerView) d.findViewById(R.id.TaskComment);
                // Comment.setDivider(new ColorDrawable(Color.parseColor("#000000")));
                final RecyclerView finalComment = Comment;
                pComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Comments> currentPojo = new ArrayList<Comments>();
                        currentPojo = AppUtil.getCurrentPojo();

                        if (currentPojo.size() > 0) {
                            CommentsAdapter cAdapter = new CommentsAdapter(currentPojo, _context);
                            finalComment.setLayoutManager(new LinearLayoutManager(_context));
                            finalComment.setItemAnimator(new DefaultItemAnimator());
                            finalComment.setHasFixedSize(true);
                            finalComment.setAdapter(cAdapter);

                        } else {
                            Toast.makeText(_context, "No comments list", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                task.setText(t.getTaskDes());
                taskHead.setText(t.getTaskHeading());
                asignBy.setText(t.getTaskFromId());
                start.setText(t.getExpStartDate());
                end.setText(t.getExpEndDate());
                status.setText(t.getTaskStatus());
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

                        ImageButton edt_close = (ImageButton) updateDialog.findViewById(R.id.edt_close);

                        edt_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateDialog.dismiss();
                            }
                        });

                        /*Record video in Task Replay  */
                        Button record = (Button) updateDialog.findViewById(R.id.record);

                        /**
                         *Click the Capture Video and Capture Image Using Alear Dialog
                         */
                        record.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog d = new Dialog(_context);
                                d.setContentView(R.layout.image_video);
                                d.setTitle("Select video or Image.....!");
                                d.show();
                                mImage = (LinearLayout) d.findViewById(R.id.getImage);
                                mVideo = (LinearLayout) d.findViewById(R.id.getVideo);
                                Button CAncel = (Button) d.findViewById(R.id.CAncel);
                                CAncel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        d.dismiss();
                                    }
                                });
                                /*Click the AleartDialog video popsition*/
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
                                 /*Click the AleartDialog image popsition*/
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
                                    status[0] = "Compleated";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SharedPreferences sharedPreferences = _context.getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                                String video = sharedPreferences.getString("video", "novideo");
                                SharedPreferences sharedPreferences1 = _context.getSharedPreferences("CurrentImage", Context.MODE_PRIVATE);
                                String images = sharedPreferences1.getString("Image", "noimages");
                                String task_comment = comment.getText().toString();
                                if (!(task_comment.equalsIgnoreCase("") && task_comment.isEmpty()) && !(status[0].equalsIgnoreCase("") && status[0].isEmpty())) {
                                    JSONObject obj = new JSONObject();
                                    try {
                                        obj.accumulate("Cid", t.getTaskId() + "");
                                        obj.accumulate("TaskStatus", status[0]);
                                        obj.accumulate("Comments", task_comment);

                                        if (!video.equalsIgnoreCase(ProjectVariables.NOVIDEO))
                                            obj.accumulate("video", video);
                                        if (!images.equalsIgnoreCase(ProjectVariables.NOIMAGE))
                                            obj.accumulate("Image", images);

                                        obj.accumulate("TaskToId", PreferenceUtil.getInstance().getString(_context, "currentUser", "000"));
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
        });

    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(2000));//to make duration random number between [0,501)
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
