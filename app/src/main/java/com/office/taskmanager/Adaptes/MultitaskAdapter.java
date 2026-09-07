package com.office.taskmanager.Adaptes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.office.taskmanager.R;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.MultiTask;
import com.office.taskmanager.util.PositionClickListener;

import java.util.ArrayList;

/**
 * Created by GhanaShyam on 8/30/2016.
 */
public class MultitaskAdapter extends RecyclerView.Adapter<MultitaskAdapter.MyViewHolder> {

    public static final String EMAIL = "name";
    public static final String IMAGE = "image";
    public static final String FNAME = "fname";
    public static final String PHONE = "phone";
    public static final String CITY = "city";
    public static final String TAG = "UserActivity";
    ArrayList<MultiTask> appUtilArrayList;
    Context context;
    RestfulListener listener;
    Activity a;
    String _type;
    PositionClickListener clickListener;

    public MultitaskAdapter(Context context, RestfulListener rl, int user_rowitems, ArrayList<MultiTask> taskUsers) {
        this.appUtilArrayList = taskUsers;
        this.context = context;
        a = (Activity) context;
        listener = rl;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multitask_rowitems, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return appUtilArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView heading, taskDesc, taskuser;
        public ImageButton mImageMenu;
        public ImageView imageView;
        public LinearLayout selectMultiTask;

        public MyViewHolder(final View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.task_heading);
            taskDesc = (TextView) itemView.findViewById(R.id.task_desc);
            taskuser = (TextView) itemView.findViewById(R.id.task_user);
            taskDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.multi_task_details);
                    dialog.setTitle("MultiTask Details...!");
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(true);
                    final TextView taskheading = (TextView) dialog.findViewById(R.id.getTaskHeading);
                    final TextView taskdes = (TextView) dialog.findViewById(R.id.getTaskDes);
                    final TextView selectuser = (TextView) dialog.findViewById(R.id.getSelectUser);
                    final TextView starttotime = (TextView) dialog.findViewById(R.id.getStartToTime);
                    final TextView startfromtime = (TextView) dialog.findViewById(R.id.getStartFromTime);
                    final TextView actenddate = (TextView) dialog.findViewById(R.id.getActEndDate);
                    final TextView actstartdate = (TextView) dialog.findViewById(R.id.getActStartDate);
                    taskheading.setText(appUtilArrayList.get(getAdapterPosition()).getTaskHeading());
                    taskdes.setText(appUtilArrayList.get(getAdapterPosition()).getTaskDes());
                    selectuser.setText(appUtilArrayList.get(getAdapterPosition()).getSelectUser());
                    starttotime.setText(appUtilArrayList.get(getAdapterPosition()).getStartToTime());
                    startfromtime.setText(appUtilArrayList.get(getAdapterPosition()).getStartFromTime());
                    actenddate.setText(appUtilArrayList.get(getAdapterPosition()).getActEndDate());
                    actstartdate.setText(appUtilArrayList.get(getAdapterPosition()).getActStartDate());

                 /*   Intent intent = new Intent(context, UserTaskDetailsActivity.class);
                    intent.putExtra("TASK_HEADING", appUtilArrayList.get(getAdapterPosition()).getTaskHeading());
                    intent.putExtra("TASK", appUtilArrayList.get(getAdapterPosition()).getTaskDes());
                    intent.putExtra("ASIGNBY", appUtilArrayList.get(getAdapterPosition()).getSelectUser());
                    intent.putExtra("START", appUtilArrayList.get(getAdapterPosition()).getStartToTime());
                    intent.putExtra("END", appUtilArrayList.get(getAdapterPosition()).getStartFromTime());
                    intent.putExtra("STARTTIME", appUtilArrayList.get(getAdapterPosition()).getActEndDate());
                    intent.putExtra("ENDTIME", appUtilArrayList.get(getAdapterPosition()).getActStartDate());
                    intent.putExtra("UNAME", appUtilArrayList.get(getAdapterPosition()).getExpEndDate());
                    intent.putExtra("PROFILE", appUtilArrayList.get(getAdapterPosition()).getExpStartDate());
                    intent.putExtra("STATUS", appUtilArrayList.get(getAdapterPosition()).getImeId());
                    intent.putExtra("TASK_ID", appUtilArrayList.get(getAdapterPosition()).getPriority());
                    intent.putExtra("TASK_FROMID" ,appUtilArrayList.get(getAdapterPosition()).getTaskFromId());
                    intent.putExtra("TASK_FROMID" ,appUtilArrayList.get(getAdapterPosition()).getTaskStatus());

                    Activity act = (Activity) context;
                    act.startActivity(intent);
                    act.overridePendingTransition(R.anim.right_enter, R.anim.left_out);*/
                }
            });


        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final MultiTask appUtil = appUtilArrayList.get(position);
        holder.heading.setText(appUtil.getTaskHeading());
        holder.taskDesc.setText(appUtil.getTaskDes());
        holder.taskuser.setText(appUtil.getSelectUser());

    }

}