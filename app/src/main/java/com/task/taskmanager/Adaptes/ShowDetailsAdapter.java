package com.task.taskmanager.Adaptes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.task.taskmanager.Pojo.Task;
import com.task.taskmanager.R;

import java.util.List;

/**
 * Created by NEWSYSTEM1 on 6/9/2016.
 */
public class ShowDetailsAdapter extends RecyclerView.Adapter<ShowDetailsAdapter.MyViewHolder> {
    List<Task> billToBillArrayList;
    Context context1;
    boolean visile;

    public ShowDetailsAdapter(Context context, int taskId, List<Task> billToBillArrayList, boolean v) {
        this.billToBillArrayList = billToBillArrayList;
        visile = v;
        context1 = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_task_detils_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return billToBillArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView task, taskHead, asignBy, start, end, status;
        public Button edit;

        public MyViewHolder(View convertView) {
            super(convertView);
            task = (TextView) convertView.findViewById(R.id.txt_show_desc);
            taskHead = (TextView) convertView.findViewById(R.id.txt_show_task_head);
            asignBy = (TextView) convertView.findViewById(R.id.txt_show_assignBy);
            start = (TextView) convertView.findViewById(R.id.txt_show_start);
            end = (TextView) convertView.findViewById(R.id.txt_show_endDate);
            status = (TextView) convertView.findViewById(R.id.TaskStatus);
           // edit = (Button) convertView.findViewById(R.id.show_edt);

        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        final int pos = position;
        final Task t = billToBillArrayList.get(position);
        viewHolder.task.setText(t.getTaskDes());
        viewHolder.taskHead.setText(t.getTaskHeading());
        viewHolder.asignBy.setText(t.getTaskFromId());
        viewHolder.start.setText(t.getExpStartDate());
        viewHolder.end.setText(t.getExpEndDate());
        viewHolder.status.setText(t.getTaskStatus());
        if (visile) {
            viewHolder.edit.setVisibility(View.VISIBLE);
        } else {
            viewHolder.edit.setVisibility(View.GONE);
        }

//        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog d = new Dialog(context1);
//                d.setContentView(R.layout.edit_task);
//                d.show();
//
//
//            }
//        });

    }

}
