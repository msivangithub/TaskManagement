package com.task.mytaskmanager.Adaptes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.task.mytaskmanager.Pojo.Comments;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.PreferenceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GhanaShyam on 8/16/2016.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    ArrayList<Comments> commentsList;
    Context context;

    public CommentsAdapter(ArrayList<Comments> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forcomment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView comment, roles;
        ImageButton play, image;

        public MyViewHolder(View itemView) {
            super(itemView);
            comment = (TextView) itemView.findViewById(R.id.insidecomment);
            roles = (TextView) itemView.findViewById(R.id.user_roles);
            play = (ImageButton) itemView.findViewById(R.id.playVideo);
            image = (ImageButton) itemView.findViewById(R.id.image);


        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Comments comments = commentsList.get(position);
        holder.comment.setText(comments.getComments());
        holder.roles.setText(comments.getUserRole());

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comments.getVideo().isEmpty() || comments.getVideo().equalsIgnoreCase("") || comments.getVideo().length() == 0 || comments.getVideo().equalsIgnoreCase("novideo")) {
                    Toast.makeText(context, "No video available for this comment", Toast.LENGTH_SHORT).show();
                } else {
                    Dialog showVideo = new Dialog(context);
                    showVideo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    showVideo.setContentView(R.layout.showvideo);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(showVideo.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    showVideo.show();
                    VideoView videoview = (VideoView) showVideo.findViewById(R.id.videoPreview);
                    MediaController mediaController = new MediaController(context);
                    mediaController.setAnchorView(videoview);
                    String MainUrl = "http://makeindiakart.com/taskfiles/";
                    MainUrl = MainUrl + comments.getVideo();
                    Uri video = Uri.parse(MainUrl);
                    videoview.setMediaController(mediaController);
                    videoview.setVideoURI(video);
                    videoview.start();

                }
            }
        });
    }
}