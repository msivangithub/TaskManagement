package com.task.mytaskmanager.fragmentssss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    /*********
     * FTP USERNAME
     ***********/
    static final String FTP_USER = "myRetail";
    /*********
     * FTP PASSWORD
     ***********/
    static final String FTP_PASS = "vKsj30!9";

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


            }
        }
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
        recordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);
                // takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().getPath()+"videocapture_example.mp4");

                startActivityForResult(takeVideoIntent, 001);

            }
        });
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                String Video = sharedPreferences.getString("video", "novideo");
                if (Video.equalsIgnoreCase("novideo")) {
                    Toast.makeText(getActivity(), "Please record the Video", Toast.LENGTH_LONG).show();
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
                        obj.accumulate(ProjectVariables.EXPSTARTDAE, AppUtil.getExpStartDate());
                        obj.accumulate(ProjectVariables.EXPENDDATE, AppUtil.getExpEndDate());
                        obj.accumulate(ProjectVariables.ACTSDATE, AppUtil.getActStartDate());
                        obj.accumulate(ProjectVariables.ACTENDDATE, AppUtil.getActEndDate());
                        obj.accumulate(ProjectVariables.TASKSTAT, AppUtil.getTaskStatus());
                        obj.accumulate(ProjectVariables.TASKHEAD, AppUtil.getTaskHeading());
                        obj.accumulate(ProjectVariables.TASKDES, AppUtil.getTaskDes());
                        obj.accumulate(ProjectVariables.PRIORITY, AppUtil.getPriority());

                        obj.accumulate("video", Video);
                    } catch (Exception e) {

                    }
                    Log.e("Sending json is ",obj.toString());
                    AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 0, ProjectVariables.TASK_CREAT, listener, obj, "");
                    post.execute();
//                    } else {
//                        Toast.makeText(getActivity(), "Please answer the all fields", Toast.LENGTH_LONG).show();
//                    }
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
    }

    @Override
    public void getData(String s, String status, int rType) {

        if (status.equalsIgnoreCase("1")) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);

            SharedPreferences.Editor curentEdit = sharedPreferences.edit();

            curentEdit.putString("video", "novideo");

            curentEdit.commit();

            Toast.makeText(getActivity(), "Task Created succesfully", Toast.LENGTH_LONG).show();
            clearAputils();

        } else {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
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
            pdForVideoUpload.setTitle("Video Uploading....");
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
