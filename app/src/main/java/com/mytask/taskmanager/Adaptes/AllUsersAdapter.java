package com.mytask.taskmanager.Adaptes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mytask.taskmanager.activity.RecordAudioActivity;
import com.mytask.taskmanager.activity.UserActivity;
import com.mytask.taskmanager.util.PositionClickListener;
import com.squareup.picasso.Picasso;
import com.mytask.taskmanager.Pojo.TaskUser;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.ProjectVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by GhanaShyam on 8/30/2016.
 */
public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.MyViewHolder> implements RestfulListener {

    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String TAG = "UserActivity";
    ArrayList<TaskUser> taskUserArrayList;
    Context context;
    RestfulListener listener;
    Activity a;
    String _type;
    PositionClickListener clickListener;

    public AllUsersAdapter(Context context, RestfulListener rl, int user_rowitems, ArrayList<TaskUser> taskUsers) {
        this.taskUserArrayList = taskUsers;
        this.context = context;
        a = (Activity) context;
        listener = rl;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rowitems, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return taskUserArrayList.size();
    }

    @Override
    public void getData(String s, String status, int rType) {

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user, roles;
        public ImageButton mImageMenu;
        public ImageView imageView;
        public LinearLayout user_details;

        public MyViewHolder(final View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.user);
            roles = (TextView) itemView.findViewById(R.id.userlevel);
            mImageMenu = (ImageButton) itemView.findViewById(R.id.Button_menu);
            imageView = (ImageView) itemView.findViewById(R.id.profiles_imageView);
            user_details = (LinearLayout) itemView.findViewById(R.id.user_details);
          /*  user_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ImageView imageView ,mImageMenu;
                    final TextView mName , mEmailId,mPhone ,mCity;
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.demo);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();
                    mEmailId =(TextView)dialog.findViewById(R.id.email_id);
                    mPhone = (TextView)dialog.findViewById(R.id.phone_no);
                    imageView = (ImageView) dialog.findViewById(R.id.profiles_imageView);
                    mName = (TextView) dialog.findViewById(R.id.txt_UserName);
                    mCity = (TextView) dialog.findViewById(R.id.city);
                    mImageMenu = (ImageButton) dialog.findViewById(R.id.Button_menu);
                    Picasso.with(context)
                            .load("http://makeindiakart.com/taskfiles/" + taskUserArrayList.get(getAdapterPosition()).getImage())
                            .placeholder(R.drawable.imge_placeholder)   // optional
                            .error(R.drawable.imge_placeholder)      // optional
                            .resize(300, 300)
                            .into(imageView);
                    mName.setText(taskUserArrayList.get(getAdapterPosition()).getFirstName());
                    mEmailId.setText(taskUserArrayList.get(getAdapterPosition()).getEmailid());
                    mPhone.setText(taskUserArrayList.get(getAdapterPosition()).getPhone());
                    mCity.setText(taskUserArrayList.get(getAdapterPosition()).getCity());
                }
            });*/
            user_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + taskUserArrayList.get(getAdapterPosition()).getFirstName());
                    Toast.makeText(context, taskUserArrayList.get(getAdapterPosition()).getEmailid(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra(NAME, taskUserArrayList.get(getAdapterPosition()).getEmailid());
                    intent.putExtra(IMAGE, taskUserArrayList.get(getAdapterPosition()).getImage());
                    Activity act = (Activity) context;
                    act.startActivity(intent);
                }
            });


            mImageMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(mImageMenu, getAdapterPosition());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TaskUser taskUser = taskUserArrayList.get(position);
        holder.user.setText(taskUser.getFirstName());
        holder.roles.setText(taskUser.getUserLevel());
        Picasso.with(context)
                .load("http://makeindiakart.com/taskfiles/" + taskUser.getImage())
                .placeholder(R.drawable.imge_placeholder)   // optional
                .error(R.drawable.imge_placeholder)      // optional
                .resize(300, 300)
                .into(holder.imageView);
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.alluser_menu, popup.getMenu());
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
                case R.id.delete_items:
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Confirm Delete....!");
                    dialog.setMessage("Are you sure you want delete this ?");
                    dialog.setIcon(R.drawable.delete_task);
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            JSONObject obj = new JSONObject();
                            try {
                                obj.accumulate("Uid", taskUserArrayList.get(position).getUid() + "");
                                Log.e("Delete taskId :", taskUserArrayList.get(position).getUid());
                                taskUserArrayList.remove(position);
                                notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AsynHttpPost post = new AsynHttpPost(context, 0, 109, ProjectVariables.USER_DELETED, listener, obj, "");
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
                    return true;
                default:
            }
            return false;
        }
    }

}
