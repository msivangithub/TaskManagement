package com.task.mytaskmanager.Adaptes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.task.mytaskmanager.Pojo.Task;
import com.task.mytaskmanager.Pojo.TaskUser;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEWSYSTEM1 on 6/9/2016.
 */
public class SeekAdapter extends RecyclerView.Adapter<SeekAdapter.MyViewHolder> {
    List<TaskUser> billToBillArrayList;
    Context context;

    public SeekAdapter(Context context, int taskId, List<TaskUser> billToBillArrayList) {
        this.billToBillArrayList = billToBillArrayList;

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView task;
        public CheckBox check;

        public MyViewHolder(View convertView) {
            super(convertView);
            task = (TextView) convertView.findViewById(R.id.taskTitle);

            check = (CheckBox) convertView.findViewById(R.id.check);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final int pos = position;
        final TaskUser t = billToBillArrayList.get(position);
        viewHolder.task.setText(t.getFirstName());
        viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ArrayList<TaskUser> users = AppUtil.getSeekUsers();
                    users.add(billToBillArrayList.get(position));
                    AppUtil.setSeekUsers(users);



                } else {
                    String taskId = billToBillArrayList.get(position).getUid();
                    ArrayList<TaskUser> users = AppUtil.getSeekUsers();


                    AppUtil.setSeekUsers(AppUtil.removedseekUsers(users, taskId));
                }
            }
        });

    }


}
