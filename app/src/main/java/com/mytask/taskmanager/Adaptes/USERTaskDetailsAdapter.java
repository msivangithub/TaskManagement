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
            icon_entry = (ImageView) itemView.findViewById(R.id.icon_entry);
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
                }
            });

              /*   taskrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Log.e("UserRole", UserRole);
                    final Dialog d = new Dialog(_context);
                    //d.setTitle("Task Details");

                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    d.setContentView(R.layout.show_task_detils_row);
                    d.setContentView(R.layout.show_task_detils_row);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(d.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    d.show();
                    d.getWindow().setAttributes(lp);
                    JSONObject obj = new JSONObject();
                    try {
                        obj.accumulate("Cid", billToBillArrayList.get(getAdapterPosition()).getTaskId() + "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AsynHttpPost post = new AsynHttpPost(_context, 0, 999, ProjectVariables.TASK_COMMENTS, listener, obj, "");
                    post.execute();

                    final TextView task, taskHead, asignBy, start, end, status, startTime, endTime ,Uname;
                    final ImageView mProfile;
                    final RecyclerView Comment;
                    task = (TextView) d.findViewById(R.id.txt_show_desc);
                    taskHead = (TextView) d.findViewById(R.id.txt_show_task_head);
                    asignBy = (TextView) d.findViewById(R.id.txt_show_assignBy);
                    start = (TextView) d.findViewById(R.id.txt_show_start);
                    end = (TextView) d.findViewById(R.id.txt_show_endDate);
                    startTime = (TextView) d.findViewById(R.id.startTime);
                    endTime = (TextView) d.findViewById(R.id.endTime);
                    status = (TextView) d.findViewById(R.id.TaskStatus);
                    Uname = (TextView)d.findViewById(R.id.uname);
                    mProfile = (ImageView)d.findViewById(R.id.profile_taskdetails);

                    Button pComments = (Button) d.findViewById(R.id.previousComments);
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
                    task.setText(billToBillArrayList.get(getAdapterPosition()).getTaskDes());
                    taskHead.setText(billToBillArrayList.get(getAdapterPosition()).getTaskHeading());
                    asignBy.setText(billToBillArrayList.get(getAdapterPosition()).getTaskFromId());
                    start.setText(billToBillArrayList.get(getAdapterPosition()).getExpStartDate());
                    end.setText(billToBillArrayList.get(getAdapterPosition()).getExpEndDate());
                    status.setText(billToBillArrayList.get(getAdapterPosition()).getTaskStatus());
                    startTime.setText(billToBillArrayList.get(getAdapterPosition()).getStartTime());
                    endTime.setText(billToBillArrayList.get(getAdapterPosition()).getEndTime());
                    Uname.setText(billToBillArrayList.get(getAdapterPosition()).getUname());
                    Picasso.with(_context)
                            .load(ProjectVariables.IMAGE_PATH + billToBillArrayList.get(getAdapterPosition()).getProfile())
                            .placeholder(R.drawable.profile_sample)   // optional
                            .error(R.drawable.profile_sample)      // optional
                            .resize(200 ,200)
                            .into(mProfile);

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
                            MainUrl = MainUrl + billToBillArrayList.get(getAdapterPosition()).getVideo();
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

                    *//*
                    Task Replay Button in Adapter
                     *//*
                    Button task_edt = (Button) d.findViewById(R.id.id_task_edt);
                    task_edt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                            final RecyclerView Comments;
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

                            Comments = (RecyclerView) updateDialog.findViewById(R.id.TaskComment);
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
                            final EditText comment = (EditText) updateDialog.findViewById(R.id.taskComments);
                            Spinner statusSpinner = (Spinner) updateDialog.findViewById(R.id.taskSpinner);
                            Button submit = (Button) updateDialog.findViewById(R.id.task_submit);
                            *//**
             *Click the Capture Video and Capture Image Using Alear Dialog
             *//*
                            ImageButton record = (ImageButton) updateDialog.findViewById(R.id.record);


                            record.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final Dialog d = new Dialog(_context);
                                    d.setContentView(R.layout.image_video);
                                    d.setTitle("Select video or Image.....!");
                                    d.show();
                                    mImage = (LinearLayout) d.findViewById(R.id.getImage);
                                    mVideo = (LinearLayout) d.findViewById(R.id.getVideo);
                                    LinearLayout recordAudio = (LinearLayout) d.findViewById(R.id.getAudio);
                                    Button CAncel = (Button) d.findViewById(R.id.CAncel);
                                    CAncel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            d.dismiss();
                                        }
                                    });

                                    recordAudio.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            d.dismiss();
                                            _context.startActivity(new Intent(_context, RecordAudioActivity.class));

                                        }
                                    });
                                *//*Click the AleartDialog video popsition*//*
                                    mVideo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            d.dismiss();
                                            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
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
                        *//*Spinner drop Down in task replay Adapter*//*
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


                                 *//*   mSwipeRefreshLayout.setRefreshing(false);
                                    ArrayList<Comments> currentPojo = new ArrayList<Comments>();
                                    currentPojo = AppUtil.getCurrentPojo();
                                    if (currentPojo.size() > 0) {
                                        CommentsAdapter cAdapter = new CommentsAdapter(currentPojo, _context);
                                        Comments.setLayoutManager(new LinearLayoutManager(_context));
                                        Comments.setItemAnimator(new DefaultItemAnimator());
                                        Comments.setHasFixedSize(true);
                                        Comments.setAdapter(cAdapter);
                                    }
                                }
                            });*//*

                        *//*task replay Adapter Screen get the comments , status and capture image or video then click SUBMIT Button*//*
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    SharedPreferences sharedPreferences = _context.getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                                    video = sharedPreferences.getString("video", "novideo");
                                    SharedPreferences sharedPreferences1 = _context.getSharedPreferences("CurrentImage", Context.MODE_PRIVATE);
                                    image = sharedPreferences1.getString("Image", "noimage");
                                    SharedPreferences sharedPreferen = _context.getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
                                    audio = sharedPreferen.getString("Audio", "noaudio");
                                    task_comment = comment.getText().toString();
                                    if (!(task_comment.equalsIgnoreCase("") && task_comment.isEmpty()) && !(status[0].equalsIgnoreCase("") && status[0].isEmpty())) {
                                        JSONObject obj = new JSONObject();
                                        try {
                                            obj.accumulate("Cid", billToBillArrayList.get(getAdapterPosition()).getTaskId() + "");
                                            obj.accumulate("TaskStatus", status[0]);
                                            obj.accumulate("Comments", task_comment);

                                            if (!video.equalsIgnoreCase(ProjectVariables.NOVIDEO))
                                                obj.accumulate("video", video);

                                            if (!image.equalsIgnoreCase(ProjectVariables.NOIMAGE))
                                                obj.accumulate("Image", image);

                                            if (!audio.equalsIgnoreCase(ProjectVariables.NOAUDIO))
                                                obj.accumulate("Audio", audio);

                                            obj.accumulate("TaskToId", PreferenceUtil.getInstance().getString(_context, "currentUser", "000"));
                                            obj.accumulate("TaskFromId", PreferenceUtil.getInstance().getString(_context, "Uid", "c001"));


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
            });*/
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
       /* Picasso.with(_context)
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

        //setAnimation(viewHolder.itemView, position);

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
