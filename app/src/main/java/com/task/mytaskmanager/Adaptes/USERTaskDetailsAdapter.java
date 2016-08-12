package com.task.mytaskmanager.Adaptes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.task.mytaskmanager.Pojo.Task;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.activity.Tasks;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
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
//                final AlertDialog d = new AlertDialog.Builder(_context)
//                        .setTitle("Enter An Administrative Password")
//                        .setView(R.layout.show_task_detils_row)
//                        .create();
                final TextView task, taskHead, asignBy, start, end, status,Comment;
                task = (TextView) d.findViewById(R.id.txt_show_desc);
                taskHead = (TextView) d.findViewById(R.id.txt_show_task_head);
                asignBy = (TextView) d.findViewById(R.id.txt_show_assignBy);
                start = (TextView) d.findViewById(R.id.txt_show_start);
                end = (TextView) d.findViewById(R.id.txt_show_endDate);
                status = (TextView) d.findViewById(R.id.TaskStatus);


                Comment = (TextView) d.findViewById(R.id.TaskComment);
                Comment.setText(t.getTaskComment());
                task.setText(t.getTaskDes());
                taskHead.setText(t.getTaskHeading());
                asignBy.setText(t.getTaskFromId());
                start.setText(t.getExpStartDate());
                end.setText(t.getExpEndDate());
                status.setText(t.getTaskStatus());


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
                                String task_comment = comment.getText().toString();
                                if (!(task_comment.equalsIgnoreCase("") && task_comment.isEmpty()) && !(status[0].equalsIgnoreCase("") && status[0].isEmpty())) {
                                    JSONObject obj = new JSONObject();
                                    try {
                                        obj.accumulate("Cid", t.getTaskId() + "");
                                        obj.accumulate("TaskStatus", status[0]);
                                        obj.accumulate("Comments", task_comment);
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
