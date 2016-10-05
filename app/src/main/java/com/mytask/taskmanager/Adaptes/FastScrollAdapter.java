package com.mytask.taskmanager.Adaptes;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytask.taskmanager.Pojo.TaskUser;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.fragment.AllUsersFragement;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.ProjectVariables;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FastScrollAdapter extends RecyclerView.Adapter<FastScrollAdapter.UserViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter, RestfulListener {

    private Context mContext;
    ArrayList<TaskUser> taskUserArrayList;
    RestfulListener listener;
    Activity a;

    public FastScrollAdapter(Context mContext, RestfulListener rl, int user_rowitems, ArrayList<TaskUser> taskUserArrayList) {
        this.mContext = mContext;
        this.taskUserArrayList = taskUserArrayList;
        a = (Activity) mContext;
        listener = rl;

    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_rowitems, null);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rowitems, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        TaskUser taskUser = taskUserArrayList.get(position);
        holder.user.setText(taskUser.getFirstName());
        holder.roles.setText(taskUser.getUserLevel());
        Picasso.with(mContext)
                .load(ProjectVariables.IMAGE_PATH + taskUser.getImage())
                .placeholder(R.drawable.imge_placeholder)   // optional
                .error(R.drawable.imge_placeholder)      // optional
                .resize(300, 300)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return taskUserArrayList.size();

    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return String.valueOf(taskUserArrayList.get(position).getFirstName().charAt(0));
    }

    @Override
    public void getData(String s, String status, int rType) {

    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView user, roles;
        public ImageButton mImageMenu;
        public ImageView imageView;
        public LinearLayout user_details;

        public UserViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.user);
            roles = (TextView) itemView.findViewById(R.id.userlevel);
            imageView = (ImageView) itemView.findViewById(R.id.profiles_imageView);
            user_details = (LinearLayout) itemView.findViewById(R.id.user_details);

        }
    }
}