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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.task.mytaskmanager.Pojo.UserRoles;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.activity.Tasks;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEWSYSTEM1 on 6/9/2016.
 */
public class USERTaskDetailsAdapter extends RecyclerView.Adapter<USERTaskDetailsAdapter.MyViewHolder> implements RestfulListener {
    List<Task> billToBillArrayList;
    Context _context;
    String _type;
    Activity a;
    RestfulListener listener;

    public USERTaskDetailsAdapter(Context context, RestfulListener rl, int taskId, List<Task> billToBillArrayList, String type) {
        this.billToBillArrayList = billToBillArrayList;
        _context = context;
        _type = type;
        listener = rl;
        a = (Activity) context;

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
        public TextView task, task_header;
        public CheckBox check;
        public LinearLayout taskrow;

        public MyViewHolder(View convertView) {
            super(convertView);
            task = (TextView) convertView.findViewById(R.id.taskTitle);
            task_header = (TextView) convertView.findViewById(R.id.txt_taskheader);
            taskrow = (LinearLayout) convertView.findViewById(R.id.taskrow);
            //check = (CheckBox) convertView.findViewById(R.id.check);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final int pos = position;
        final Task t = billToBillArrayList.get(position);
        viewHolder.task.setText(t.getTaskDes());
        viewHolder.task_header.setText(t.getTaskHeading());
        viewHolder.taskrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                final AlertDialog d = new AlertDialog.Builder(_context)
//                        .setTitle("Enter An Administrative Password")
//                        .setView(R.layout.show_task_detils_row)
//                        .create();
                final TextView task, taskHead, asignBy, start, end, status;
                final RecyclerView Comment;
                task = (TextView) d.findViewById(R.id.txt_show_desc);
                taskHead = (TextView) d.findViewById(R.id.txt_show_task_head);
                asignBy = (TextView) d.findViewById(R.id.txt_show_assignBy);
                start = (TextView) d.findViewById(R.id.txt_show_start);
                end = (TextView) d.findViewById(R.id.txt_show_endDate);
                status = (TextView) d.findViewById(R.id.TaskStatus);

                Button pComments = (Button) d.findViewById(R.id.previousComments);
                Comment = (RecyclerView) d.findViewById(R.id.TaskComment);
                //  Comment.setDivider(new ColorDrawable(Color.parseColor("#000000")));
                // Comment.setText(t.getTaskComment());
                pComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Comments> currentPojo = new ArrayList<Comments>();
                        currentPojo= AppUtil.getCurrentPojo();

                        if (currentPojo.size() > 0) {
                            CommentsAdapter cAdapter = new CommentsAdapter(currentPojo,_context);
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

                Button mShowvideo = (Button) d.findViewById(R.id.showvideo);
                mShowvideo.setOnClickListener(new View.OnClickListener() {
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
                ImageButton button = (ImageButton) d.findViewById(R.id.show_close);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                Button task_edt = (Button) d.findViewById(R.id.id_task_edt);

                task_edt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        final String[] status = {""};
                        final Dialog updateDialog = new Dialog(_context);
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

                        edt_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateDialog.dismiss();
                            }
                        });
                        final EditText comment = (EditText) updateDialog.findViewById(R.id.taskComments);
                        Spinner statusSpinner = (Spinner) updateDialog.findViewById(R.id.taskSpinner);
                        Button submit = (Button) updateDialog.findViewById(R.id.task_submit);


                        Button record = (Button) updateDialog.findViewById(R.id.record);


                        record.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);
                                // takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().getPath()+"videocapture_example.mp4");
                                Activity act = (Activity) _context;
                                act.startActivityForResult(takeVideoIntent, 667);


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
                                String task_comment = comment.getText().toString();
                                if (!(task_comment.equalsIgnoreCase("") && task_comment.isEmpty()) && !(status[0].equalsIgnoreCase("") && status[0].isEmpty())) {
                                    JSONObject obj = new JSONObject();
                                    try {
                                        obj.accumulate("Cid", t.getTaskId() + "");
                                        obj.accumulate("TaskStatus", status[0]);
                                        obj.accumulate("Comments", task_comment);
                                        obj.accumulate("video", video);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    AsynHttpPost post = new AsynHttpPost(_context, 0, 888, ProjectVariables.TASK_UPDATE, listener, obj, "");
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

    public ArrayList<Tasks> TaskList() {
        ArrayList<Tasks> taskses = new ArrayList<Tasks>();
        taskses.add(new Tasks("All"));
        taskses.add(new Tasks("Completed"));
        taskses.add(new Tasks("Pending"));
        taskses.add(new Tasks("Progress"));
        return taskses;
    }


}
