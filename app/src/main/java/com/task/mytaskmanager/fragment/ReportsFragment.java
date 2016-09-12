package com.task.mytaskmanager.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.task.mytaskmanager.Adaptes.TaskReportAdapter;
import com.task.mytaskmanager.Pojo.TaskReport;
import com.task.mytaskmanager.Pojo.TaskUser;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.AsynHttpPost143;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.PreferenceUtil;
import com.task.mytaskmanager.util.ProjectVariables;
import com.task.mytaskmanager.util.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportsFragment extends Fragment implements RestfulListener {
    ArrayList<TaskUser> users = new ArrayList<>();
    ArrayList<TaskReport> taskReportArrayList;
    ArrayList<TaskUser> selectedUsers;
    TaskReportAdapter adapter1 = null;
    Spinner employeename;
    String employeeId;
    JSONObject obj;
    RestfulListener listener;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static ReportsFragment newInstance() {

        Bundle args = new Bundle();
        ReportsFragment fragment = new ReportsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        getActivity().setTitle("Reports");
        setHasOptionsMenu(true);
        listener = this;
        users = new ArrayList<>();
        taskReportArrayList = new ArrayList<>();
        selectedUsers = new ArrayList<>();
        employeename = (Spinner) view.findViewById(R.id.id_employeenames);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_reports);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        obj = new JSONObject();
                        try {
                            obj.accumulate("Uid", employeeId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (AppUtil.isNetworkAvailable(getActivity())) {

                            AsynHttpPost143 post1 = new AsynHttpPost143(getActivity(), 0, 675, ProjectVariables.TASKREPORT, listener, obj, "");
                            post1.execute();

                        } else {
                            ToastMessegNetwork();
                        }
                    }
                }, 1500);
            }
        });
        employeename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                employeeId = users.get(position).getUid();
                obj = new JSONObject();
                try {
                    obj.accumulate("Uid", employeeId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (AppUtil.isNetworkAvailable(getActivity())) {

                    AsynHttpPost post1 = new AsynHttpPost(getActivity(), 0, 675, ProjectVariables.TASKREPORT, listener, obj, "");
                    post1.execute();

                } else {
                    ToastMessegNetwork();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 167, ProjectVariables.USERS + PreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user") + "," + PreferenceUtil.getInstance().getString(getActivity(), "Compname", "companyname"), listener, obj, "");
        post.execute();
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
    public void getData(String s, String status, int rType) {

        selectedUsers = new ArrayList<>();
        taskReportArrayList = new ArrayList<>();

        if (rType == 675) {
            if (adapter1 != null) {
                adapter1.notifyDataSetChanged();
                taskReportArrayList = new ArrayList<>();
            }
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    String actenddate = obj.getString(ProjectVariables._ACTENDDATE);
                    String ActStartDate = obj.getString(ProjectVariables._ACTSTARTDATE);
                    String taskheading = obj.getString(ProjectVariables._TASKHEADING);
                    String taskstatus = obj.getString(ProjectVariables._TASKSTATUS);
                    String cid = obj.getString(ProjectVariables._CID);

                    TaskReport t = new TaskReport();

                    t.setActEndDate(actenddate);
                    t.setActStartDate(ActStartDate);
                    t.setTaskHeading(taskheading);
                    t.setTaskStatus(taskstatus);
                    t.setCid(cid);

                    taskReportArrayList.add(t);

                }
            } catch (JSONException e) {
                e.printStackTrace();

                Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_LONG).show();
            }

            mSwipeRefreshLayout.setRefreshing(false);
            adapter1 = new TaskReportAdapter(getActivity(), ReportsFragment.this, R.layout.task_reports, taskReportArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
            recyclerView.setAdapter(adapter1);

        } else if (rType == 167) {

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
