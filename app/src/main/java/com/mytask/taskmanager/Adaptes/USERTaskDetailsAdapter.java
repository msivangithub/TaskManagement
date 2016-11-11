package com.mytask.taskmanager.Adaptes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.lb.recyclerview_fast_scroller.RecyclerViewFastScroller;

import com.mytask.taskmanager.Pojo.Task;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.activity.Tasks;
import com.mytask.taskmanager.activity.UserTaskDetailsActivity;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;
import com.mytask.taskmanager.util.RefreshLisener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by NEWSYSTEM1 on 6/9/2016.
 */
public class USERTaskDetailsAdapter extends RecyclerView.Adapter<USERTaskDetailsAdapter.MyViewHolder> implements RestfulListener ,RecyclerViewFastScroller.BubbleTextGetter {


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
    String UserRole = "";
    private int lastPosition = -1;
    Task t;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String REFRESH_DELAY = "1";
    RefreshLisener refreshLisener;
    JSONObject jsonObject;
    String video, image, task_comment, audio;
    String ImageName = "";

    public USERTaskDetailsAdapter(Context context, RestfulListener rl, int taskId, List<Task> billToBillArrayList, String type) {

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
        return billToBillArrayList == null ? 0 : billToBillArrayList.size();
    }

    @Override
    public void getData(String s, String status, int rType) {

    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (billToBillArrayList.get(pos).getTaskHeading().length() != 0) {
            return Character.toString(billToBillArrayList.get(pos).getTaskHeading().charAt(0));
        }
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView task, task_header, mTaskDate, mTaskTime, mAsignBy;
        public CheckBox check;
        public ImageView icon_entry;
        public LinearLayout taskrow;
        public View itemView;
        public ImageButton mImageMenu;


        public MyViewHolder(View convertView) {
            super(convertView);
            itemView = convertView;

            task = (TextView) convertView.findViewById(R.id.taskTitle);
            task_header = (TextView) convertView.findViewById(R.id.txt_taskheader);
            taskrow = (LinearLayout) convertView.findViewById(R.id.taskrow);
            mTaskDate = (TextView) convertView.findViewById(R.id.taskDate);
            mTaskTime = (TextView) convertView.findViewById(R.id.taskTime);
            mAsignBy = (TextView) convertView.findViewById(R.id.asignBy);
            //check = (CheckBox) convertView.findViewById(R.id.check);
            icon_entry = (ImageView) convertView.findViewById(R.id.icon_entry);
            mImageMenu = (ImageButton) convertView.findViewById(R.id.Button_menu);
            mImageMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(mImageMenu, getAdapterPosition());
                }
            });
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
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final int pos = position;

        t = billToBillArrayList.get(position);
        viewHolder.task.setText(t.getTaskDes());
        viewHolder.task_header.setText(t.getTaskHeading());
        viewHolder.mTaskDate.setText(t.getActStartDate());
        viewHolder.mTaskTime.setText(t.getStartTime());
        viewHolder.mAsignBy.setText(t.getUname());
      /*  Picasso.with(_context)
                .load(ProjectVariables.IMAGE_PATH + billToBillArrayList.get(position).getProfile())
                .placeholder(R.drawable.profile_sample)   // optional
                .error(R.drawable.profile_sample)      // optional
                .resize(300, 300)
                .into(viewHolder.icon_entry);*/
        Log.e("Task Head", t.getTaskHeading());

       /*
        Side view generate random color in recyclearView
        */

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

       // setAnimation(viewHolder.itemView, position);

        Log.e("UserRole", UserRole);
        if (UserRole.equalsIgnoreCase("3")) {
            viewHolder.mImageMenu.setVisibility(View.GONE);
        }
        if (position % 2 == 0) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }


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

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.card_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        try {
            Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
            mPopup.setForceShowIcon(true);
        } catch (Exception e) {

        }
        popup.show();
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private int position;

        public MyMenuItemClickListener(int positon) {
            this.position = positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.cardMenu_items:
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(_context);
                    dialog.setTitle("Confirm Delete....!");
                    dialog.setMessage("Are you sure you want delete this ?");
                    dialog.setIcon(R.drawable.delete_task);
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            JSONObject obj = new JSONObject();
                            try {
                                obj.accumulate("Cid", billToBillArrayList.get(position).getTaskId() + "");
                                Log.e("Delete taskId :", billToBillArrayList.get(position).getTaskToId());
                                billToBillArrayList.remove(position);
                                notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AsynHttpPost post = new AsynHttpPost(_context, 0, 143, ProjectVariables.TASK_DELETED, listener, obj, "");
                            post.execute();

                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case R.id.cardMenu_resend:
                    final AlertDialog.Builder resend = new AlertDialog.Builder(_context);
                    resend.setTitle("Resend Task....!");
                    resend.setMessage("Are you sure you want resend task ?");
                    resend.setIcon(R.drawable.resend_);
                    resend.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            JSONObject obj = new JSONObject();
                            try {
                                obj.accumulate("Cid", billToBillArrayList.get(position).getTaskId() + "");
                                Log.e("Resend Task :", billToBillArrayList.get(position).getTaskToId());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AsynHttpPost post = new AsynHttpPost(_context, 0, 154, ProjectVariables.TASK_RESEND, listener, obj, "");
                            post.execute();

                        }
                    });
                    resend.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    resend.show();
                    break;
                default:
            }
            return false;
        }

    }
}
