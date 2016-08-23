package com.task.mytaskmanager.fragmentssss;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.task.mytaskmanager.Adaptes.ShowDetailsAdapter;
import com.task.mytaskmanager.Adaptes.TaskDetailsAdapter;
import com.task.mytaskmanager.Adaptes.USERTaskDetailsAdapter;
import com.task.mytaskmanager.Databases.PostsDatabaseHelper;
import com.task.mytaskmanager.Pojo.Comments;
import com.task.mytaskmanager.Pojo.Post;
import com.task.mytaskmanager.Pojo.Task;
import com.task.mytaskmanager.Pojo.TaskUser;
import com.task.mytaskmanager.Pojo.User;
import com.task.mytaskmanager.Pojo.UserRoles;
import com.task.mytaskmanager.R;

import com.task.mytaskmanager.activity.Tasks;
import com.task.mytaskmanager.activity.TasksAdapter;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.PreferenceUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by GhanaShyam on 7/18/2016.
 */
public class TaskDetails extends Fragment implements View.OnClickListener, RestfulListener {
    ArrayList<TaskUser> users;
    ArrayList<Tasks> mytasks;
    ArrayList<Task> TaskList;
    RestfulListener listener;
    Spinner status;
    ProgressDialog pdForVideoUpload;
    Button show_Details, delete_Tasks, show_edt;
    ArrayList<TaskUser> selectedUsers;
    TextView statusdisplay;
    Tasks tasks;
    String[] employee = new String[]{"MANJUNATH", "PRAKASH", "VENKATESH", "KRISHNA", "VENUGOPAL"};
    RecyclerView recyclerView;
    TasksAdapter statusAdapter;
    Spinner employeename;
    USERTaskDetailsAdapter adapter1 = null;
    static final String FTP_HOST = "myaccountsretail.com";
    ProgressDialog pd;
    /*********
     * FTP USERNAME
     ***********/
    static final String FTP_USER = "myRetail";
    /*********
     * FTP PASSWORD
     ***********/
    static final String FTP_PASS = "vKsj30!9";
    String ImageName = "";
    String imageURI = "";

    public static TaskDetails newInstance() {

        Bundle args = new Bundle();

        TaskDetails fragment = new TaskDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_task_details, container, false);
        getActivity().setTitle("Task Details");
        setHasOptionsMenu(true);
        pdForVideoUpload = new ProgressDialog(getActivity());
        users = new ArrayList<>();
        show_Details = (Button) view.findViewById(R.id.id_show);
        delete_Tasks = (Button) view.findViewById(R.id.id_delete);
        show_edt = (Button) view.findViewById(R.id.id_edit);
        show_Details.setOnClickListener(this);
        delete_Tasks.setOnClickListener(this);
        show_edt.setOnClickListener(this);
        mytasks = TaskList();
        listener = this;
        TaskList = new ArrayList<>();
        selectedUsers = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        employeename = (Spinner) view.findViewById(R.id.id_employeenames);
       // status = (Spinner) view.findViewById(R.id.id_status);
        statusAdapter = new TasksAdapter(getActivity(), android.R.layout.simple_spinner_item, mytasks);
       // status.setAdapter(statusAdapter);

        employeename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String employeeId = users.get(position).getUid();
                PreferenceUtil.getInstance().saveString(getActivity(), "currentUser", employeeId);
                JSONObject obj = new JSONObject();
                try {
                    obj.accumulate(ProjectVariables.UID, employeeId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 123, ProjectVariables.getTasksByUserId, listener, obj, "");
                post.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 0, ProjectVariables.USERS, this, null, "");
        post.execute();
        return view;
    }

    public ArrayList<Tasks> TaskList() {
        ArrayList<Tasks> taskses = new ArrayList<Tasks>();
        taskses.add(new Tasks("All"));
        taskses.add(new Tasks("Completed"));
        taskses.add(new Tasks("Pending"));
        taskses.add(new Tasks("Progress"));
        return taskses;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getData(String s, String status, int rType) {
//        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        Log.e("Response from server is ", s + " " + rType);
        selectedUsers = new ArrayList<>();
        TaskList = new ArrayList<>();
        /**/
        if (rType == 888) {
            try {
                JSONArray array = new JSONArray(s);
                JSONObject obj = array.getJSONObject(0);

                String result = obj.getString("Result");

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("video", ProjectVariables.NOVIDEO);
                curentEdit.commit();

                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("CurrentImage", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit1 = sharedPreferences1.edit();
                curentEdit1.putString("Image", ProjectVariables.NOIMAGE);
                curentEdit1.commit();

                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (rType == 999) {
            ArrayList<Comments> current = new ArrayList<>();
            try {
                String comm, userroles, videoPlay, image;

                JSONArray array = new JSONArray(s);
                for (int c = 0; c < array.length(); c++) {
                    Comments c1 = new Comments();
                    JSONObject obj = array.getJSONObject(c);
                    comm = obj.getString("Comments");
                    userroles = obj.getString("UserRole");
                    videoPlay = obj.getString("video");
                    image = obj.getString("image");
                    c1.setComments(comm);
                    c1.setUserRole(userroles);
                    c1.setVideo(videoPlay);
                    c1.setImage(image);
                    current.add(c1);
                }
                //if (array.length() > 0)
                AppUtil.setCurrentPojo(current);
            } catch (JSONException e) {
                e.printStackTrace();
                AppUtil.setCurrentPojo(current);
                // AppUtil.setCurrentComments(new ArrayList<String>());
            }

        } else if (rType == 123) {
            if (adapter1 != null) {
                adapter1.notifyDataSetChanged();
                TaskList = new ArrayList<>();
            }
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String aed = obj.getString(ProjectVariables.ACTENDDATE);
                    String asd = obj.getString(ProjectVariables.ACTSDATE);
                    String esd = obj.getString(ProjectVariables.EXPSTARTDAE);
                    String eed = obj.getString(ProjectVariables.EXPENDDATE);
                    String tkd = obj.getString(ProjectVariables.TASKDES);
                    String tfid = obj.getString(ProjectVariables.TASKFROMID);
                    String thead = obj.getString(ProjectVariables.TASKHEAD);
                    String tstat = obj.getString(ProjectVariables.TASKSTAT);
                    String ttoid = obj.getString(ProjectVariables.TASKOID);
                    String prty = obj.getString(ProjectVariables.PRIORITY);
                    String video = obj.getString("video");
                    String comments = obj.getString("Comments");
                    String taskId = obj.getString("Cid");
                    int cid = Integer.parseInt(taskId);
                    Task t = new Task();
                    t.setTaskId(cid);
                    t.setTaskComment(comments);
                    t.setActEndDate(aed);
                    t.setActStartDate(asd);
                    t.setExpEndDate(eed);
                    t.setExpStartDate(esd);
                    t.setTaskDes(tkd);
                    t.setTaskFromId(tfid);
                    t.setTaskHeading(thead);
                    t.setTaskStatus(tstat);
                    t.setTaskToId(ttoid);
                    t.setPriority(prty);
                    t.setVideo(video);
                    TaskList.add(t);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
            adapter1 = new USERTaskDetailsAdapter(getActivity(), TaskDetails.this, R.layout.task_row, TaskList, "add");
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);

            recyclerView.setAdapter(adapter1);

        } else if (rType == 0) {

            users = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject obj = array.getJSONObject(i);

                    TaskUser user = new TaskUser();
                    user.setFirstName(obj.getString(ProjectVariables.FNAME));
                    user.setUid(obj.getString(ProjectVariables.UID));

                    users.add(user);
                }


                String[] us = new String[users.size()];
                for (int i = 0; i < users.size(); i++) {
                    us[i] = users.get(i).getFirstName();
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                employeename.setAdapter(spinnerArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (rType == 143) {
            try {
                JSONArray array = new JSONArray(s);
                JSONObject obj = array.getJSONObject(0);

                String result = obj.getString("Result");

                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            if (requestCode == 667) {
                Uri selectedimg = data.getData();
                String origanImage = getPath(selectedimg);
                String[] imageArray = origanImage.split("/");

                int length = imageArray.length;
                String convertedImage = imageArray[length];
                Toast.makeText(getActivity(), "Video Recorded " + convertedImage, Toast.LENGTH_LONG).show();

                pdForVideoUpload.show();
                pdForVideoUpload.setTitle("Video uploading...");

                UploadTask uploadTask = null;

                try {
                    uploadTask = new UploadTask(getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    uploadTask.execute();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            else {
                super.onActivityResult(requestCode, resultCode, data);


            }
        }

    }

    private class UploadTask extends AsyncTask<Void, Void, String> {
        InputStream stream;
        String v;

        public UploadTask(InputStream inputStream, String videoName) {

            v = videoName;
            stream = inputStream;

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
                InputStream targetStream = stream;
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

