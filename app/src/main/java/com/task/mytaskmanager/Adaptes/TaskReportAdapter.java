package com.task.mytaskmanager.Adaptes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.task.mytaskmanager.Pojo.Comments;
import com.task.mytaskmanager.Pojo.Task;
import com.task.mytaskmanager.Pojo.TaskReport;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.activity.RecordAudioActivity;
import com.task.mytaskmanager.activity.Tasks;
import com.task.mytaskmanager.fragment.ReportsFragment;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.PreferenceUtil;
import com.task.mytaskmanager.util.ProjectVariables;
import com.task.mytaskmanager.util.RefreshLisener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
