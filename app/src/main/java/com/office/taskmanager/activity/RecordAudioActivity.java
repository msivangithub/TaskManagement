package com.office.taskmanager.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.office.taskmanager.R;
import com.office.taskmanager.util.DateUtil;
import com.office.taskmanager.util.ProjectVariables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RecordAudioActivity extends Activity implements MediaPlayer.OnPreparedListener {
    Button play, stop, record, upload, download;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private String fileName = "";
    static final String FTP_HOST = "myaccountsretail.com";
    ProgressDialog prgDialog;
    /*********
     * FTP USERNAME
     ***********/
    static final String FTP_USER = "myRetail";
    /*********
     * FTP PASSWORD
     ***********/
    static final String FTP_PASS = "vKsj30!9";
    private String extensiom = ".3gp";
    MediaPlayer m;
    ProgressDialog pdForVideoUpload;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recordaudio);
        initPermissions();

        play = (Button) findViewById(R.id.button3);
        stop = (Button) findViewById(R.id.button2);
        record = (Button) findViewById(R.id.button);
        upload = (Button) findViewById(R.id.button4);

        stop.setEnabled(false);
        play.setEnabled(false);
        upload.setEnabled(false);
        m = new MediaPlayer();

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = DateUtil.getRandomNumberInRange(1, 100000);
                fileName = "Audio_" + number + extensiom;
                outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;

                myAudioRecorder = new MediaRecorder();

                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

                myAudioRecorder.setOutputFile(outputFile);
                try {
                    try {
                        myAudioRecorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myAudioRecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                play.setEnabled(false);
                record.setEnabled(false);
                stop.setEnabled(true);
                upload.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(false);
                play.setEnabled(true);
                upload.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                record.setEnabled(false);
                stop.setEnabled(false);
                play.setEnabled(false);
                upload.setEnabled(true);
                m = new MediaPlayer();

                m.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    if (m != null && m.isPlaying()) {
                        m.stop();
                        m.release();

                    }
                    m.setDataSource(outputFile);
                    m.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            m.start();

                        }
                    });
                    m.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File uploadFile = new File(outputFile);
                try {
                    InputStream sendingStream = new FileInputStream(uploadFile);
                    //ImageUploadTask uploadTask = new ImageUploadTask(RecordAudioActivity.this, sendingStream, fileName);
                   // uploadTask.execute();
                    UploadTask task = new UploadTask(sendingStream, fileName);
                    task.execute();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                    Log.e("Error", e.getMessage().toString());
                }

            }
        });

    }
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        m.start();
    }


    private class UploadTask extends AsyncTask<Void, Void, String> {
        InputStream f;
        String v;

        @Override
        protected void onPreExecute() {
            pdForVideoUpload = new ProgressDialog(RecordAudioActivity.this);
            pdForVideoUpload.setMessage("Uploading Audio file......");
            pdForVideoUpload.setCanceledOnTouchOutside(false);
            pdForVideoUpload.show();
            // showProgressDialogHorizontal();
        }

        public UploadTask(InputStream file, String videoName) {
            f = file;
            v = videoName;
        }
        @Override
        protected String doInBackground(Void... voids) {
            //uploadFile(f);
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect(ProjectVariables.FTP_HOST, ProjectVariables.FTP_USER, ProjectVariables.FTP_PASS);
                boolean status = false;
                status = ftp.setWorkingDirectory(ProjectVariables.IMAGE_FOLD);
                //status = ftp.setWorkingDirectory("/makeindiakart.com/taskfiles");
                //InputStream targetStream = getResources().openRawResource(+R.drawable.ic_launcher);
                InputStream targetStream = f;
                ftp.uploadFile(targetStream, v);
                Log.e("Status", status + "");
                pdForVideoUpload.dismiss();
                return new String("Upload Successful");

            } catch (Exception e) {
                pdForVideoUpload.dismiss();
                String t = "Failure : " + e.getLocalizedMessage();
                return t;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            finish();
            SharedPreferences sharedPreferences = getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
            SharedPreferences.Editor curentEdit = sharedPreferences.edit();
            curentEdit.putString("Audio", fileName);
            curentEdit.commit();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    private void showProgressDialogHorizontal() {

        pdForVideoUpload = new ProgressDialog(RecordAudioActivity.this);
        pdForVideoUpload.setTitle("Please Wait..");
        pdForVideoUpload.setMessage("Uploading Audio file......");
        pdForVideoUpload.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pdForVideoUpload.setCancelable(false);
        pdForVideoUpload.setMax(100);
        pdForVideoUpload.show();
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    try {
                        // Here I'm making thread sleep to show progress
                        Thread.sleep(200);
                        progressStatus += 2;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Update the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            pdForVideoUpload.setProgress(progressStatus);
                        }
                    });
                }
                pdForVideoUpload.dismiss();
            }
        }).start();
    }


    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(RecordAudioActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RecordAudioActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RecordAudioActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RecordAudioActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RecordAudioActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RecordAudioActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RecordAudioActivity.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RecordAudioActivity.this,
                    Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RecordAudioActivity.this,
                        new String[]{Manifest.permission.INTERNET},
                        3);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;

        }
    }
}