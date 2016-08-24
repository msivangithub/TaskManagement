package com.task.mytaskmanager.Adaptes;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.ZoomControls;

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
    ProgressDialog pdDialog;
    private ImageView image;
    private ZoomControls zoom;
    public CommentsAdapter(ArrayList<Comments> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
        pdDialog = new ProgressDialog(this.context);

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

        if (comments.getImage().isEmpty() || comments.getImage().equalsIgnoreCase("") || comments.getImage().length() == 0 || comments.getImage().equalsIgnoreCase("noimage")) {
            holder.image.setVisibility(View.GONE);
        }
        if (comments.getVideo().isEmpty() || comments.getVideo().equalsIgnoreCase("") || comments.getVideo().length() == 0 || comments.getVideo().equalsIgnoreCase("novideo")) {
            holder.play.setVisibility(View.GONE);
        }
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
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comments.getImage().isEmpty() || comments.getImage().equalsIgnoreCase("") || comments.getImage().length() == 0) {
                    Toast.makeText(context, "No image available for this comment", Toast.LENGTH_SHORT).show();
                } else {
                    LoadImageFromURL loadImage = new LoadImageFromURL();
                    loadImage.execute(comments.getImage());
                }
            }
        });
    }


    public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            //http://makeindiakart.com/taskfiles/Image_1290.jpg
            try {
                String MainUrl = "http://makeindiakart.com/taskfiles/";
                URL url = new URL(MainUrl + params[0]);
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                return bitMap;

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdDialog = new ProgressDialog(context);
            pdDialog.setMessage("Loading Image ....");
            pdDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pdDialog.dismiss();
            Dialog showimage = new Dialog(context);
            showimage.requestWindowFeature(Window.FEATURE_NO_TITLE);
            showimage.setContentView(R.layout.showimage);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(showimage.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            showimage.show();
            image = (ImageView) showimage.findViewById(R.id.showImage);
            zoom = (ZoomControls) showimage.findViewById(R.id.zoomControls1);
            zoom.setOnZoomInClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO Auto-generated method stub
                    float x = image.getScaleX();
                    float y = image.getScaleY();
                    image.setScaleX((float) (x + 1));
                    image.setScaleY((float) (y + 1));
                }
            });
            zoom.setOnZoomOutClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    float x = image.getScaleX();
                    float y = image.getScaleY();
                    image.setScaleX((float) (x - 1));
                    image.setScaleY((float) (y - 1));
                }
            });

            image.setImageBitmap(result);

        }
    }
}