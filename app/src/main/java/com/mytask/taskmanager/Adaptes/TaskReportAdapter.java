package com.mytask.taskmanager.Adaptes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytask.taskmanager.Pojo.TaskReport;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.fragment.ReportsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEWSYSTEM1 on 6/9/2016.
 */
public class TaskReportAdapter extends RecyclerView.Adapter<TaskReportAdapter.MyViewHolder> {

    List<TaskReport> taskReportList;
    Context _context;
    String _type;
    Activity a;
    LinearLayout mImage, mVideo;
    String UserRole = "";
    private int lastPosition = -1;
    TaskReport t;

    public TaskReportAdapter(Context context, ReportsFragment taskId, int taskReportList, ArrayList<TaskReport> taskReports) {
         this.taskReportList = taskReports;
        _context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_reports, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return taskReportList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ActEndDate, ActStartDate, TaskHeading, TaskStatus, cid;
        public View itemView;


        public MyViewHolder(View convertView) {
            super(convertView);
            itemView = convertView;

            ActEndDate = (TextView) convertView.findViewById(R.id.actenddate);
            ActStartDate = (TextView) convertView.findViewById(R.id.actstartdate);
            TaskHeading = (TextView) convertView.findViewById(R.id.taskheading);
            TaskStatus = (TextView) convertView.findViewById(R.id.taskstatus);
            cid = (TextView) convertView.findViewById(R.id.cid);


        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final int pos = position;
        t = taskReportList.get(position);
        viewHolder.ActEndDate.setText(t.getActEndDate());
        viewHolder.ActStartDate.setText(t.getActStartDate());
        viewHolder.TaskHeading.setText(t.getTaskHeading());
        viewHolder.TaskStatus.setText(t.getTaskStatus());
        viewHolder.cid.setText(t.getCid());
        if (position % 2 == 0) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }
}
