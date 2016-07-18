package com.task.mytaskmanager.fragmentssss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.activity.Tasks;
import com.task.mytaskmanager.activity.TasksAdapter;

import java.util.ArrayList;

/**
 * Created by GhanaShyam on 7/18/2016.
 */
public class TaskDetails extends Fragment implements View.OnClickListener {

    ArrayList<Tasks> mytasks;
    Spinner status;
    TextView statusdisplay;
    Tasks tasks;
    String[] employee = new String[]{"MANJUNATH", "PRAKASH", "VENKATESH", "KRISHNA", "VENUGOPAL"};
    RecyclerView recyclerView;
    TasksAdapter statusAdapter;
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
        mytasks = TaskList();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        Spinner employeename = (Spinner) view.findViewById(R.id.id_employeenames);
        status = (Spinner) view.findViewById(R.id.id_status);

        statusAdapter = new TasksAdapter(getActivity(), android.R.layout.simple_spinner_item, mytasks);
        status.setAdapter(statusAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, employee);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeename.setAdapter(adapter);
        employeename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), employee[position], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        switch (v.getId()) {
            case R.id.id_show:
                break;
            case R.id.id_edit:
                break;
            case R.id.id_remind:
                break;
            case R.id.id_delete:
                break;
            case R.id.id_smsalert:
                break;
            case R.id.id_score:
                break;
            case R.id.id_status:


                break;
        }
    }
}

