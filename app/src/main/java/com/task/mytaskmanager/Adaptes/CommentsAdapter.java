package com.task.mytaskmanager.Adaptes;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.ButtonBarLayout;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ZoomControls;

import com.task.mytaskmanager.Pojo.Comments;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.util.DateUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by GhanaShyam on 8/16/2016.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    ArrayList<Comments> commentsList;
    Context context;
    ProgressDialog pdDialog;
    private ImageView image;
    private ZoomControls zoom;
    static MediaPlayer mPlayer;
    Comments comments;
    private boolean intialStage = true;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progress;
    private static String file_url = "http://makeindiakart.com/taskfiles/";
    public static final int progress_bar_type = 0;
    private ProgressDialog prgDialog;
    String audioName = "sample.mp3";
    private Activity activity;

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
        ImageButton play, image, audio;
        public ImageButton mImageMenu;
        public LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            comment = (TextView) itemView.findViewById(R.id.insidecomment);
            roles = (TextView) itemView.findViewById(R.id.user_roles);
            play = (ImageButton) itemView.findViewById(R.id.playVideo);
            audio = (ImageButton) itemView.findViewById(R.id.play_Audio);
            image = (ImageButton) itemView.findViewById(R.id.image);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardView_comments);
            mImageMenu = (ImageButton) itemView.findViewById(R.id.Button_menu);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.comments_details);
                    dialog.setTitle("Comments Details...!");
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();
                    final TextView commet = (TextView) dialog.findViewById(R.id.comment_heading);
                    final Button button = (Button) dialog.findViewById(R.id.ok);
                    commet.setText(commentsList.get(getAdapterPosition()).getComments());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
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
        comments = commentsList.get(position);
        holder.comment.setText(comments.getComments());
        holder.roles.setText(comments.getUserRole());

    }


    public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            //http://makeindiakart.com/taskfiles/Image_1290.jpg
            try {
                String MainUrl = "http://makeindiakart.com/taskfiles/";
                URL url = new URL(MainUrl + params[0]);
                InputStream is = url.openConnection().getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitMap = BitmapFactory.decodeStream(is, null, options);
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


    private void showPopupMenu(View view, int position) {

        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.comments_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        try {
            Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
            mPopup.setForceShowIcon(true);
        } catch (Exception e) {
            e.printStackTrace();
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
                case R.id.capture_video:
                    if (commentsList.get(position).getVideo().isEmpty() || commentsList.get(position).getVideo().equalsIgnoreCase("") || commentsList.get(position).getVideo().length() == 0 || commentsList.get(position).getVideo().equalsIgnoreCase("novideo")) {
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
                        MainUrl = MainUrl + commentsList.get(position).getVideo();
                        Uri video = Uri.parse(MainUrl);
                        videoview.setMediaController(mediaController);
                        videoview.setVideoURI(video);
                        videoview.start();

                    }
                    break;
                case R.id.capture_image:
                    if (commentsList.get(position).getImage().isEmpty() || commentsList.get(position).getImage().equalsIgnoreCase("") || commentsList.get(position).getImage().length() == 0) {
                        Toast.makeText(context, "No image available for this comment", Toast.LENGTH_SHORT).show();
                    } else {
                        LoadImageFromURL loadImage = new LoadImageFromURL();
                        loadImage.execute(commentsList.get(position).getImage());

                    }
                    break;
                case R.id.play_Audio:
                    if (commentsList.get(position).getAudio().isEmpty() || commentsList.get(position).getAudio().equalsIgnoreCase("") || commentsList.get(position).getAudio().length() == 0) {
                        Toast.makeText(context, "No audio available for this comment", Toast.LENGTH_SHORT).show();
                    } else {
                      /*  File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + audioName);
                        // Check if the Music file already exists
                        if (file.exists()) {
                            Toast.makeText(context, "File already exist under SD card, playing Music", Toast.LENGTH_LONG).show();
                            // Play Music
                            playMusic();
                            // If the Music File doesn't exist in SD card (Not yet downloaded)
                        } else {*/
                        new DownloadMusicfromInternet().execute(commentsList.get(position).getAudio());
                        //  }
                    }
                    break;
                default:
            }
            return false;
        }
    }


    private class DownloadMusicfromInternet extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... params) {
            int count;
            try {
                String MainUrl = "http://makeindiakart.com/taskfiles/";
                URL url = new URL(MainUrl + params[0]);
                Log.d("DownloadManager", "download url:" + url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();

                // Get Music file length
                int lenghtOfFile = connection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream());
                // Output stream to write file in SD card
                audioName = params[0];
                File f = new File(Environment.getExternalStorageDirectory(), audioName);
                OutputStream output = new FileOutputStream(f);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress which triggers onProgressUpdate method
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    // Write data to file
                    output.write(data, 0, count);
                }
                // Flush output
                output.flush();
                // Close streams
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgDialog = new ProgressDialog(context);
            prgDialog.setMessage("Downloading Audio file. Please wait...");
            prgDialog.show();

        }

        @Override
        protected void onPostExecute(Void s) {
            if (prgDialog.isShowing()) {
                prgDialog.dismiss();
            }
            Toast.makeText(context, "Download complete, playing Music", Toast.LENGTH_LONG).show();
            // Play the music
            playMusic();
        }

    }

    // Play Music
    protected void playMusic() {
        // Read Mp3 file present under SD card
        Uri myUri1 = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/" + audioName);
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.setDataSource(context, myUri1);
            mPlayer.prepare();
            // Start playing the Music file
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    // Once Music is completed playing, enable the button
                    Toast.makeText(context, "Music completed playing", Toast.LENGTH_LONG).show();

                }
            });
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(context, "URI cannot be accessed, permissed needed", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(context, "Media Player is not in correct state", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "IO Error occured", Toast.LENGTH_LONG).show();
        }
    }
}

