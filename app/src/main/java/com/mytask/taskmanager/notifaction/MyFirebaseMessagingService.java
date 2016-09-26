package com.mytask.taskmanager.notifaction;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.activity.MainActivity;
import com.mytask.taskmanager.util.AppUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by GhanaShyam on 9/14/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Context context;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        context = MyFirebaseMessagingService.this;
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message Tital: " + remoteMessage.getNotification().getTitle());

        //Calling method to generate notification
        String tag = remoteMessage.getNotification().getTag();
        String body = remoteMessage.getNotification().getBody();
        String titel = remoteMessage.getNotification().getTitle();
        String icon = remoteMessage.getNotification().getIcon();

        sendNotifications(body, titel, icon);
    }

    private void sendNotifications(String messageBody, String titel, String icon) {
        new LoadImageFromURL(context, messageBody, titel, "http://myaccountsonline.co.in/Taskmanger/taskfiles/" + icon).execute();
        Log.d(TAG, "Notification Icon name: " + icon.toString());
    }


    class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        private String title, message, imageUrl;

        public LoadImageFromURL(Context context, String messageBody, String titel, String icon) {
            this.mContext = context;
            this.message = messageBody;
            this.title = titel;
            this.imageUrl = icon;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //http://makeindiakart.com/taskfiles/Image_1290.jpg
            InputStream in;

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.my_taskmanager96);
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                Log.e("ERRRRRRRR", e.getMessage().toString());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                Log.e("ERRRRRRRR", e.getMessage().toString());
            }
            return icon;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                int numMessages = 0;
                NotificationManager notificationManagers = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notif = new Notification.Builder(mContext)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.my_taskmanager96)
                        .setSound(defaultSoundUri)
                        .setLargeIcon(result)
                        .setNumber(++numMessages)
                       // .setStyle(new Notification.BigPictureStyle().bigPicture(result))
                        .build();
                notif.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManagers.notify(1, notif);
                // hide the notification after its selected

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Etrtr", e.getMessage().toString());
            }
        }
    }



    /* Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            // String MainUrl = "http://myaccountsonline.co.in/Taskmanger/taskfiles/";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //  Bitmap bm = BitmapFactory.decodeResource(getResources(), Integer.parseInt(MainUrl + icon));
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setLargeIcon(result)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());*/

/* Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                int numMessages = 0;
                NotificationManager notificationManagers = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notif = new Notification.Builder(mContext)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.my_taskmanager96)
                        .setSound(defaultSoundUri)
                        .setLargeIcon(getCircleBitmap(result))
                        .setNumber(++numMessages)
                        .setStyle(new Notification.BigPictureStyle().bigPicture(result))
                        .build();
                notif.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManagers.notify(1, notif);*/
}