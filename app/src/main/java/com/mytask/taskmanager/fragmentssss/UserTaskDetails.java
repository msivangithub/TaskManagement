package com.mytask.taskmanager.fragmentssss;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.mytask.taskmanager.Adaptes.ShowDetailsAdapter;
import com.mytask.taskmanager.Adaptes.TaskDetailsAdapter;
import com.mytask.taskmanager.Pojo.Comments;
import com.mytask.taskmanager.Pojo.Task;
import com.mytask.taskmanager.Pojo.TaskUser;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.activity.Tasks;
import com.mytask.taskmanager.activity.TasksAdapter;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.AsynHttpPost143;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.AppUtil;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GhanaShyam on 7/18/2016.
 */
public class UserTaskDetails extends Fragment implements View.OnClickListener, RestfulListener {
    ArrayList<TaskUser> users;
    ArrayList<Tasks> mytasks;
    ArrayList<Task> TaskList;
    RestfulListener listener;
    Spinner status;
    Button show_Details, delete_Tasks, show_edt;
    ArrayList<TaskUser> selectedUsers;
    TextView statusdisplay;
    Tasks tasks;
    String[] employee = new String[]{"MANJUNATH", "PRAKASH", "VENKATESH", "KRISHNA", "VENUGOPAL"};
    RecyclerView recyclerView;
    TasksAdapter statusAdapter;
    Spinner employeename;
    TaskDetailsAdapter adapter1 = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String employeeId;
    JSONObject obj;

    public static UserTaskDetails newInstance() {

        Bundle args = new Bundle();

        UserTaskDetails fragment = new UserTaskDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_task_details, container, false);
        getActivity().setTitle("Task Details");
        setHasOptionsMenu(true);
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
        status = (Spinner) view.findViewById(R.id.id_status);
        statusAdapter = new TasksAdapter(getActivity(), android.R.layout.simple_spinner_item, mytasks);
        status.setAdapter(statusAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                obj = new JSONObject();
                try {
                    obj.accumulate(ProjectVariables.UID, PreferenceUtil.getInstance().getString(getActivity(), ProjectVariables.USERLOGINID, "0"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSwipeRefreshLayout.setRefreshing(false);
                if (AppUtil.isNetworkAvailable(getActivity())) {

                    AsynHttpPost143 post = new AsynHttpPost143(getActivity(), 0, 123, ProjectVariables.getTasksByUserId, listener, obj, "");
                    post.execute();

                } else {
                    ToastMessegNetwork();
                }
            }

        });
        employeename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                employeeId = users.get(position).getUid();
                PreferenceUtil.getInstance().saveString(getActivity(), "currentUser", employeeId);
                obj = new JSONObject();
                try {
                    obj.accumulate(ProjectVariables.UID, employeeId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (AppUtil.isNetworkAvailable(getActivity())) {

                    AsynHttpPost post1 = new AsynHttpPost(getActivity(), 0, 123, ProjectVariables.getTasksByUserId, listener, obj, "");
                    post1.execute();

                } else {
                    ToastMessegNetwork();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        obj = new JSONObject();
        try {
            obj.accumulate(ProjectVariables.UID, PreferenceUtil.getInstance().getString(getActivity(), ProjectVariables.USERLOGINID, "0"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (AppUtil.isNetworkAvailable(getActivity())) {
            AsynHttpPost143 post = new AsynHttpPost143(getActivity(), 0, 123, ProjectVariables.getTasksByUserId, listener, obj, "");
            post.execute();
        } else {
            ToastMessegNetwork();
        }
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
        switch (v.getId()) {
            case R.id.id_show:
                RecyclerView rv;
                //  AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                Dialog d = new Dialog(getActivity());
                d.setTitle("Task Details");
                d.setContentView(R.layout.show_details);
                rv = (RecyclerView) d.findViewById(R.id.recyclerview_showdet);
                // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)
                ArrayList<Task> users = AppUtil.getUserArrayList();
                ShowDetailsAdapter sdadapter = new ShowDetailsAdapter(getActivity(), R.layout.show_task_detils_row, users, false);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setHasFixedSize(true);
                rv.setAdapter(sdadapter);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(d.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                d.show();
                d.getWindow().setAttributes(lp);

                break;
            case R.id.id_edit:
                RecyclerView rv1 = null;
                //  AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                Dialog d1 = new Dialog(getActivity());
                d1.setTitle("Task Details");
                d1.setContentView(R.layout.show_details);
                rv1 = (RecyclerView) d1.findViewById(R.id.recyclerview_showdet);
                // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)
                ArrayList<Task> users1 = AppUtil.getUserArrayList();
                ShowDetailsAdapter sdadapter1 = new ShowDetailsAdapter(getActivity(), R.layout.show_task_detils_row, users1, true);
                rv1.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv1.setItemAnimator(new DefaultItemAnimator());
                rv1.setHasFixedSize(true);
                rv1.setAdapter(sdadapter1);
                WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
                lp1.copyFrom(d1.getWindow().getAttributes());
                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp1.height = WindowManager.LayoutParams.MATCH_PARENT;
                d1.show();
                d1.getWindow().setAttributes(lp1);
                break;
            case R.id.id_remind:
                break;
            case R.id.id_delete:
                ArrayList<Task> deletedTasks = AppUtil.getDeletedUserList();
                ArrayList<Task> modifyTasks = AppUtil.removedTasksByTwoLists(TaskList, deletedTasks);
                TaskList = modifyTasks;
                adapter1.notifyDataSetChanged();

                adapter1 = new TaskDetailsAdapter(getActivity(), UserTaskDetails.this, R.layout.task_row, modifyTasks, "delete");

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setHasFixedSize(true);

                recyclerView.setAdapter(adapter1);

                break;
            case R.id.id_smsalert:
                break;
            case R.id.id_score:
                break;
            case R.id.id_status:


                break;
        }
    }


    @Override
    public void getData(String s, String status, int rType) {
//        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        Log.e("Response from server is ", s);
        selectedUsers = new ArrayList<>();
        TaskList = new ArrayList<>();

            if (rType == 777) {
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

                    SharedPreferences sharedPrefer = getActivity().getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
                    SharedPreferences.Editor curentE = sharedPrefer.edit();
                    curentE.putString("Audio", ProjectVariables.NOAUDIO);
                    curentE.commit();

                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            if (rType == 999) {
                ArrayList<Comments> current = new ArrayList<>();
                try {
                    String comm, userroles, videoPlay, image, audio;

                    JSONArray array = new JSONArray(s);
                    for (int c = 0; c < array.length(); c++) {
                        Comments c1 = new Comments();
                        JSONObject obj = array.getJSONObject(c);
                        comm = obj.getString("Comments");
                        userroles = obj.getString("UserRole");
                        videoPlay = obj.getString("video");
                        image = obj.getString("image");
                        audio = obj.getString("Audio");
                        c1.setComments(comm);
                        c1.setUserRole(userroles);
                        c1.setVideo(videoPlay);
                        c1.setImage(image);
                        c1.setAudio(audio);
                        current.add(c1);

                    }

                    // if (array.length() > 0)
                    AppUtil.setCurrentPojo(current);

                } catch (JSONException e) {
                    e.printStackTrace();
                    AppUtil.setCurrentPojo(current);
                    // Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                    //AppUtil.setCurrentComments(new ArrayList<String>());
                }

            }
            if (rType == 123) {
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
                        String startTime = obj.getString(ProjectVariables.STARTTIME);
                        String endTime = obj.getString(ProjectVariables.ENDTIME);
                        String comments = obj.getString("Comments");
                        String taskId = obj.getString("Cid");
                        String video = obj.getString("video");
                        int tid = Integer.parseInt(taskId);
                        Task t = new Task();
                        t.setTaskId(tid);
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
                        t.setStartTime(startTime);
                        t.setEndTime(endTime);
                        TaskList.add(t);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_LONG).show();
                }

                mSwipeRefreshLayout.setRefreshing(false);
                adapter1 = new TaskDetailsAdapter(getActivity(), UserTaskDetails.this, R.layout.task_row, TaskList, "add");
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter1);

            } else {
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

            }
    }
}

