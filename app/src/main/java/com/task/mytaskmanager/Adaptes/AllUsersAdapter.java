package com.task.mytaskmanager.Adaptes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.squareup.picasso.Picasso;
import com.task.mytaskmanager.Pojo.Comments;
import com.task.mytaskmanager.Pojo.Task;
import com.task.mytaskmanager.Pojo.TaskUser;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.fragment.AllUsersFragement;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.PreferenceUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by GhanaShyam on 8/30/2016.
 */
public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.MyViewHolder> implements RestfulListener {

    ArrayList<TaskUser> taskUserArrayList;
    Context context;
    RestfulListener listener;
    Activity a;
    String _type;


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

        public MyViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.user);
            roles = (TextView) itemView.findViewById(R.id.userlevel);
            mImageMenu = (ImageButton) itemView.findViewById(R.id.Button_menu);
            imageView = (ImageView) itemView.findViewById(R.id.profiles_imageView);
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
                .load("http://makeindiakart.com/taskfiles/"+taskUser.getImage())
                .placeholder(R.drawable.all_user)   // optional
                .error(R.drawable.all_user)      // optional
                .resize(300,300)                        // optional
                .into(holder.imageView);
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
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Confirm Delete....!");
                    dialog.setMessage("Are you sure you want delete this ?");
                    dialog.setIcon(android.R.drawable.ic_delete);
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
