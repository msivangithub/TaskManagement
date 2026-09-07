package com.office.taskmanager.fragmentssss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.office.taskmanager.Adaptes.MultitaskAdapter;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.services.addbutton;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.MultiTask;
import com.office.taskmanager.util.PreferenceUtil;
import com.office.taskmanager.util.ProjectVariables;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskFragment3 extends Fragment implements RestfulListener {

    static addbutton _ab;
    private Button addUser;
    private String priority = "";
    private RestfulListener listener;
    static final String FTP_HOST = "myaccountsretail.com";
    private int month, day, year;
    private int seconds, minutes, hour;
    static final String FTP_USER = "myRetail";
    static final String FTP_PASS = "vKsj30!9";
    LinearLayout mImage, mVideo, recordAudio;
    String imageURI = "";
    String audioURI = "";
    private static final int MAX_PROGRESS = 100;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private boolean isCanceled;
    String taskhed;
    RecyclerView recyclerView;
    MultitaskAdapter multitaskAdapter;
    AppUtil appUtil;
    ArrayList<AppUtil> appUtils = new ArrayList<>();
    ArrayList<MultiTask> multiTaskArrayList;
    ProgressDialog pd;
    View view;
    String time;
    String currentdate;

    public static TaskFragment3 newInstance(addbutton ab) {
        Bundle args = new Bundle();
        _ab = ab;
        TaskFragment3 fragment1 = new TaskFragment3();
        fragment1.setArguments(args);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.task_fragment3, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setHasOptionsMenu(true);
        listener = this;
        appUtil = new AppUtil();
        appUtils = new ArrayList<>();
        intializeVariables();
        multiTaskArrayList = new ArrayList<MultiTask>();
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait.....");

        multiTaskArrayList = AppUtil.getMultiTasks();
        multitaskAdapter = new MultitaskAdapter(getActivity(), TaskFragment3.this, R.layout.multitask_rowitems, multiTaskArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(multitaskAdapter);
        timeAndDateFormat();
        return view;
    }

    private void timeAndDateFormat() {
         /*Time Format*/
        final Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        hour = calander.get(Calendar.HOUR_OF_DAY);
        minutes = calander.get(Calendar.MINUTE);
        time = simpleDateFormat.format(calander.getTime());
          /*Date Format*/
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = new Date();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        currentdate = ss.format(date);
    }

    private void intializeVariables() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        addUser = (Button) view.findViewById(R.id.adduser);
        setOnClickListenear();
    }

    private void setOnClickListenear() {
            /*Task Creation Fragment using User Details and Submit The AddUser Button*/
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentVideo", Context.MODE_PRIVATE);
                String Video = sharedPreferences.getString("video", "novideo");
                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("CurrentImage", Context.MODE_APPEND);
                String images = sharedPreferences1.getString("Image", "noimage");
                SharedPreferences sharedPrefer = getActivity().getSharedPreferences("CurrentAudio", Context.MODE_APPEND);
                String audio = sharedPrefer.getString("Audio", "noaudio");
                //Collections.reverse(Arrays.asList(multiTaskArrayList));
                if (multiTaskArrayList.size() > 0)
                    pd.show();

                for (int i = 0; i < multiTaskArrayList.size(); i++) {
                    Log.e("task " + i, multiTaskArrayList.get(i).getTaskDes());
                    MultiTask multiTask = multiTaskArrayList.get(i);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject();
                        obj.accumulate(ProjectVariables.IMEID, multiTask.getImeId());
                        obj.accumulate(ProjectVariables.TASKFROMID, multiTask.getTaskFromId());
                        obj.accumulate(ProjectVariables.TASKOID, multiTask.getTaskToId());
                        obj.accumulate(ProjectVariables.EXPSTARTDAE, (multiTask.getExpStartDate() == null) ? currentdate : multiTask.getExpStartDate());
                        obj.accumulate(ProjectVariables.EXPENDDATE, (multiTask.getExpEndDate() == null) ? currentdate : multiTask.getExpEndDate());
                        obj.accumulate(ProjectVariables.ACTSDATE, (multiTask.getActStartDate() == null) ? currentdate : multiTask.getActStartDate());
                        obj.accumulate(ProjectVariables.ACTENDDATE, multiTask.getActEndDate());
                        obj.accumulate(ProjectVariables.TASKSTAT, multiTask.getTaskStatus());
                        obj.accumulate(ProjectVariables.TASKHEAD, multiTask.getTaskHeading());
                        obj.accumulate(ProjectVariables.TASKDES, multiTask.getTaskDes());
                        obj.accumulate(ProjectVariables.PRIORITY, multiTask.getPriority());
                        obj.accumulate(ProjectVariables.STARTTIME, (multiTask.getStartFromTime() == null) ? time : multiTask.getStartFromTime());
                        obj.accumulate(ProjectVariables.ENDTIME, (multiTask.getStartToTime() == null) ? time : multiTask.getStartToTime());
                        obj.accumulate(ProjectVariables.REPEATEDDAYS, multiTask.getRepeatedDays());
                        obj.accumulate("video", Video);
                        obj.accumulate("Image", images);
                        obj.accumulate("Audio", audio);
                        obj.accumulate("Uid", PreferenceUtil.getInstance().getString(getActivity(), "Uid", "uid"));

                    } catch (Exception e) {
                    }
                    if (AppUtil.isNetworkAvailable(getActivity())) {
                        Log.e("Sending json is ", obj.toString());
                        try {
                            AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 0, ProjectVariables.TASK_CREAT, listener, obj, multiTask.getUniqid() + "");
                            post.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastMessegNetwork();
                    }

                }

            }


        });
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
        AppUtil.setPriority("");
    }

    @Override
    public void getData(String s, String status, int rType, String temp) {

        if (status.equalsIgnoreCase("1")) {
            String uniqueId = temp;
            if (multiTaskArrayList.size() == 1)
                pd.dismiss();
            for (int remove = 0; remove < multiTaskArrayList.size(); remove++) {

                if (multiTaskArrayList.get(remove).getUniqid().equalsIgnoreCase(uniqueId)) {

                    multiTaskArrayList.remove(remove);
                    multitaskAdapter.notifyDataSetChanged();
                }
            }
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

}// Start the operation
