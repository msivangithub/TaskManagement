package com.task.mytaskmanager.fragmentssss;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.services.addbutton;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONObject;

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

        View view = inflater.inflate(R.layout.task_fragment3, container, false);
        setHasOptionsMenu(true);
        listener = this;
        addUser = (Button) view.findViewById(R.id.adduser);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        mnone = (RadioButton) view.findViewById(R.id.none);
        low = (RadioButton) view.findViewById(R.id.low);
        Medium = (RadioButton) view.findViewById(R.id.Medium);
        High = (RadioButton) view.findViewById(R.id.High);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (priority.equalsIgnoreCase("") && priority.isEmpty()) {
                    Toast.makeText(getActivity(), "Please select priority of task", Toast.LENGTH_LONG).show();
                } else {
                    AppUtil.setTaskStatus("S");
                    AppUtil.setTaskHeading("MAKE Task");
                    AppUtil.setTaskDes("Samle Task");
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
                    } catch (Exception e) {

                    }
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
            Toast.makeText(getActivity(), "Task Created succesfully", Toast.LENGTH_LONG).show();
            clearAputils();

        } else {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }

    }


}
