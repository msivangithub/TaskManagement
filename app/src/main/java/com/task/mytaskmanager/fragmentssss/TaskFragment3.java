package com.task.mytaskmanager.fragmentssss;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Network;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.services.addbutton;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskFragment3 extends Fragment implements RestfulListener {

    private RadioGroup radioGroup;
    private RadioButton mnone, low, Medium, High;
    static addbutton _ab;
    private Button addUser;
    private String priority = "";
    private RestfulListener listener;
    String android_id;
    Button recordVideo;
    static final String FTP_HOST = "myaccountsretail.com";
    private int month, day, year;
    private int seconds, minutes, hour;
    /*********
     * FTP USERNAME
     ***********/
    static final String FTP_USER = "myRetail";
    /*********
     * FTP PASSWORD
     ***********/
    static final String FTP_PASS = "vKsj30!9";
    private Object currentdate;
    LinearLayout mImage, mVideo;
    String imageURI = "";

    public static TaskFragment3 newInstance(addbutton ab) {
        Bundle args = new Bundle();
        _ab = ab;
        TaskFragment3 fragment1 = new TaskFragment3();
        fragment1.setArguments(args);
        return fragment1;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_fragment3, container, false);
        setHasOptionsMenu(true);
        listener = this;
        recordVideo = (Button) view.findViewById(R.id.record);
        addUser = (Button) view.findViewById(R.id.adduser);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        mnone = (RadioButton) view.findViewById(R.id.none);
        low = (RadioButton) view.findViewById(R.id.low);
        Medium = (RadioButton) view.findViewById(R.id.Medium);
        High = (RadioButton) view.findViewById(R.id.High);
        /*Time Format*/
        final Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        hour = calander.get(Calendar.HOUR_OF_DAY);
        minutes = calander.get(Calendar.MINUTE);
        final String time = simpleDateFormat.format(calander.getTime());
          /*Date Format*/
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = new Date();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        final String currentdate = ss.format(date);
        /*Record Video Button*/
        recordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.image_video);
                d.setTitle("Select video or Image.....!");
                d.show();
                mImage = (LinearLayout) d.findViewById(R.id.getImage);
                mVideo = (LinearLayout) d.findViewById(R.id.getVideo);
                Button CAncel = (Button) d.findViewById(R.id.CAncel);
                CAncel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                                /*Click the AleartDialog video popsition*/
                mVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);
                        // takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().getPath()+"videocapture_example.mp4");
                        startActivityForResult(takeVideoIntent, 001);
                    }
                });
                                 /*Click the AleartDialog image popsition*/
                mImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 003);
                    }
                });
            }
        });

        /*Task Creation Fragment using User Details and Submit The AddUser Button*/
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                String Video = sharedPreferences.getString("video", "novideo");
                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("CurrentImage", Context.MODE_APPEND);
                String images = sharedPreferences1.getString("Image", "noimage");
              /*  if (Video.equalsIgnoreCase("novideo")) {
                    Toast.makeText(getActivity(), "Please record the Video", Toast.LENGTH_LONG).show();
                    return;
                }*/
                Log.e("values", AppUtil.getTaskDes());
                Log.e("values", AppUtil.getTaskHeading());
                Log.e("values", AppUtil.getActStartDate());
                Log.e("values", AppUtil.getActEndDate());

                if (AppUtil.getTaskDes().isEmpty() || AppUtil.getTaskDes().length() == 0) {
                    Toast.makeText(getActivity(), "Please write task description", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (AppUtil.getTaskHeading().isEmpty() || AppUtil.getTaskHeading().length() == 0) {
                    Toast.makeText(getActivity(), "Please write task heading", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (AppUtil.getActStartDate().isEmpty() || AppUtil.getActStartDate().length() == 0) {
                    Toast.makeText(getActivity(), "Please select start date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (AppUtil.getActEndDate().isEmpty() || AppUtil.getActEndDate().length() == 0) {
                    Toast.makeText(getActivity(), "Please select end date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (priority.equalsIgnoreCase("") && priority.isEmpty()) {
                    Toast.makeText(getActivity(), "Please select priority of task", Toast.LENGTH_LONG).show();
                } else {

                    AppUtil.setTaskStatus("S");
                    // AppUtil.setTaskHeading("MAKE Task");

                    android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    AppUtil.setImeId(android_id);
//                    if (!(AppUtil.getTaskFromId().isEmpty() && AppUtil.getTaskFromId().equalsIgnoreCase("")) && !(AppUtil.getTaskToId().isEmpty() && AppUtil.getTaskToId().equalsIgnoreCase(""))
//                            && !(AppUtil.getExpStartDate().isEmpty() && AppUtil.getExpStartDate().equalsIgnoreCase("")) && !(AppUtil.getExpEndDate().isEmpty() && AppUtil.getExpEndDate().equalsIgnoreCase(""))
//                            && !(AppUtil.getActStartDate().isEmpty() && AppUtil.getActStartDate().equalsIgnoreCase("")) && !!(AppUtil.getActEndDate().isEmpty() && AppUtil.getActEndDate().equalsIgnoreCase(""))
//                            && !(AppUtil.getPriority().isEmpty() && AppUtil.getPriority().equalsIgnoreCase(""))) {
                    JSONObject obj = null;

                    try {
                        obj = new JSONObject();
                        obj.accumulate(ProjectVariables.IMEID, AppUtil.getImeId());
                        obj.accumulate(ProjectVariables.TASKFROMID, AppUtil.getTaskFromId());
                        obj.accumulate(ProjectVariables.TASKOID, AppUtil.getTaskToId());
                        obj.accumulate(ProjectVariables.EXPSTARTDAE, (AppUtil.getExpStartDate() == null) ? currentdate : AppUtil.getExpStartDate());
                        obj.accumulate(ProjectVariables.EXPENDDATE, (AppUtil.getExpEndDate() == null) ? currentdate : AppUtil.getExpEndDate());
                        obj.accumulate(ProjectVariables.ACTSDATE, (AppUtil.getActStartDate() == null) ? currentdate : AppUtil.getActStartDate());
                        obj.accumulate(ProjectVariables.ACTENDDATE, AppUtil.getActEndDate());
                        obj.accumulate(ProjectVariables.TASKSTAT, AppUtil.getTaskStatus());
                        obj.accumulate(ProjectVariables.TASKHEAD, AppUtil.getTaskHeading());
                        obj.accumulate(ProjectVariables.TASKDES, AppUtil.getTaskDes());
                        obj.accumulate(ProjectVariables.PRIORITY, AppUtil.getPriority());
                        obj.accumulate(ProjectVariables.STARTTIME, (AppUtil.getStartFromTime() == null) ? time : AppUtil.getStartFromTime());
                        obj.accumulate(ProjectVariables.ENDTIME, (AppUtil.getStartToTime() == null) ? time : AppUtil.getStartToTime());
                        obj.accumulate("video", Video);
                        obj.accumulate("Image", images);

                    } catch (Exception e) {
                    }
                    if (AppUtil.isNetworkAvailable(getActivity())) {
                        Log.e("Sending json is ", obj.toString());
                        AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 0, ProjectVariables.TASK_CREAT, listener, obj, "");
                        post.execute();
                    } else {
                        ToastMessegNetwork();
                    }
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.none) {
                    priority = "none";
                    AppUtil.setPriority(priority);
                } else if (checkedId == R.id.low) {
                    priority = "l";
                    AppUtil.setPriority(priority);
                } else if (checkedId == R.id.Medium) {
                    priority = "m";
                    AppUtil.setPriority(priority);
                } else if (checkedId == R.id.High) {
                    priority = "h";
                    AppUtil.setPriority(priority);
                }
            }
        });

        return view;
    }

    private void ToastMessegNetwork() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View toastlayout = inflater.inflate(R.layout.toast_network_connection, (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout));
        Toast toast = new Toast(getActivity());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastlayout);
        toast.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        _ab.addVisible(true);
    }

    public void clearAputils() {
        AppUtil.setActEndDate("");
        AppUtil.setExpEndDate("");
        AppUtil.setExpStartDate("");
        AppUtil.setActStartDate("");
        AppUtil.setPriority("");
        AppUtil.setTaskDes("");
        AppUtil.setTaskFromId("");
        AppUtil.setTaskHeading("");
        AppUtil.setTaskStatus("");
        AppUtil.setTaskToId("");
        AppUtil.getStartFromTime();
        AppUtil.getStartToTime();
    }

    @Override
    public void getData(String s, String status, int rType) {

        if (status.equalsIgnoreCase("1")) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
            SharedPreferences.Editor curentEdit = sharedPreferences.edit();
            curentEdit.putString("video", "novideo");
            curentEdit.commit();
            ToastMesseg();
            clearAputils();


        } else {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }

    }

    private void ToastMesseg() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View toastlayout = inflater.inflate(R.layout.toast_task_cretion, (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout));
        Toast toast = new Toast(getActivity());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastlayout);
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 001) {

                Uri selectedimg = data.getData();
                String origanImage = getPath(selectedimg);
                String[] imageArray = origanImage.split("/");
                int length = imageArray.length;

                String convertedImage = imageArray[length - 1];
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("video", convertedImage);
                curentEdit.commit();
                Toast.makeText(getActivity(), "Video Recorded " + convertedImage, Toast.LENGTH_LONG).show();
                UploadTask u = null;
                try {
                    u = new UploadTask(getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    u.execute();
                } catch (FileNotFoundException e) {

            }
        } else if (requestCode == 003) {
                Toast.makeText(getActivity(), "003 result" + requestCode, Toast.LENGTH_LONG).show();

                Uri selectedimg = data.getData();
                imageURI = getPath(selectedimg);
                String[] imageArray = imageURI.split("/");

                int length = imageArray.length;
                String convertedImage = imageArray[length - 1];

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentImage", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("Image", convertedImage);
                curentEdit.commit();
                UploadTask u = null;

                try {
                    u = new UploadTask(getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    u.execute();
                } catch (FileNotFoundException e) {

                }
            }
        }
    }

    private class UploadTask extends AsyncTask<Void, Void, String> {
        InputStream f;
        String v;
        ProgressDialog pdForVideoUpload;

        @Override
        protected void onPreExecute() {
            pdForVideoUpload = new ProgressDialog(getActivity());
            pdForVideoUpload.show();
            pdForVideoUpload.setTitle("Uploading....");
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
                ftp.connect(FTP_HOST, FTP_USER, FTP_PASS);
                boolean status = false;
                status = ftp.setWorkingDirectory("/makeindiakart.com/taskfiles");
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
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }


}
