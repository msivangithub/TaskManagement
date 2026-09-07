package com.office.taskmanager.fragment;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.office.taskmanager.Adaptes.TaskReportAdapter;
import com.office.taskmanager.Pojo.TaskBranches;
import com.office.taskmanager.Pojo.TaskReport;
import com.office.taskmanager.Pojo.TaskUser;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost143;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.SharedPreferenceUtil;
import com.office.taskmanager.util.ProgressDialogsUtils;
import com.office.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReportsFragment extends Fragment implements RestfulListener {

    private static final int THE_COLOR_GOES_HERE = 100;
    private ArrayList<TaskUser> users = new ArrayList<>();
    private ArrayList<TaskBranches> BranchesReportslist = new ArrayList<>();
    private ArrayList<TaskReport> taskReportArrayList;
    private ArrayList<TaskUser> selectedUsers;
    private TaskReportAdapter adapter1 = null;
    private Spinner employeename, select_branch, status_spinner, priority_spinner;
    private String employeeId, status, priority;
    private JSONObject obj;
    private RestfulListener listener;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String[] branchesInfo;
    private String[] employeeinfo;
    private LinearLayout linearLayout_selectUser;
    private String branchId;
    private boolean firstTime = true;
    private ProgressDialogsUtils dialogsUtils = new ProgressDialogsUtils();
    private ProgressDialog pd;
    private int month, day, year;
    private String currentdate, dayDifference;
    private View view;

    public static ReportsFragment newInstance() {

        Bundle args = new Bundle();
        ReportsFragment fragment = new ReportsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reports, container, false);
        branchesInfo = new String[1];
        employeeinfo = new String[1];
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Reports</font>"));
        getActivity().setTitleColor(R.color.white);
        setHasOptionsMenu(true);
        listener = this;
        users = new ArrayList<>();
        taskReportArrayList = new ArrayList<>();
        selectedUsers = new ArrayList<>();
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait....");
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        intialVariables();
        currenDate();
        getAllBranchs();
        return view;
    }

    private void intialVariables() {
        employeename = (Spinner) view.findViewById(R.id.id_employeenames);
        select_branch = (Spinner) view.findViewById(R.id.select_branch);
        status_spinner = (Spinner) view.findViewById(R.id.status_spinner);
        priority_spinner = (Spinner) view.findViewById(R.id.priority_spinner);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_reports);
        linearLayout_selectUser = (LinearLayout) view.findViewById(R.id.select_user);
    }

    private void currenDate() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = new Date();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        currentdate = ss.format(date);
    }

    private void getAllBranchs() {
        if (firstTime)
            pd.show();
        if (AppUtil.isNetworkAvailable(getActivity())) {
            try {
                AsynHttpPost143 postsss = new AsynHttpPost143(getActivity(), 2, 168, ProjectVariables.BRANCHES_REPORTS, listener, null, "", firstTime);
                postsss.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();
        }
        select_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                TextView selectedText = (TextView) adapterView.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.parseColor("#124514"));
                }
                if (BranchesReportslist.size() != 0) {
                    branchId = BranchesReportslist.get(pos).getBranchId();
                    try {
                        AsynHttpPost143 post = new AsynHttpPost143(getActivity(), 0, 167, ProjectVariables.USERS_BYBRANCHES_REPORTS + SharedPreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user") + "," + branchId, listener, obj, "", firstTime);
                        post.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        employeename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // String branchId = BranchesReportslist.get(position).getBranchId();
                employeeId = users.get(position).getUid();
                obj = new JSONObject();
                try {
                    obj.accumulate("Uid", employeeId);
                    obj.accumulate("Branchid", branchId);
                    obj.accumulate("TaskStatus", status);
                    obj.accumulate("priority", priority);
                    Log.e("Task reports = ", obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }

                if (AppUtil.isNetworkAvailable(getActivity())) {
                    try {
                        AsynHttpPost143 post1 = new AsynHttpPost143(getActivity(), 0, 675, ProjectVariables.TASKREPORT, listener, obj, "", firstTime);
                        post1.execute();
                        Log.e("Task reports = ", obj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    ToastMessegNetwork();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                status = status_spinner.getSelectedItem().toString();

                obj = new JSONObject();
                try {
                    obj.accumulate("Uid", employeeId);
                    obj.accumulate("Branchid", branchId);
                    obj.accumulate("TaskStatus", status);
                    obj.accumulate("priority", priority);
                    Log.e("Task reports = ", obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }

                if (AppUtil.isNetworkAvailable(getActivity())) {
                    try {
                        AsynHttpPost143 post1 = new AsynHttpPost143(getActivity(), 0, 675, ProjectVariables.TASKREPORT, listener, obj, "", firstTime);
                        post1.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastMessegNetwork();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        priority_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                priority = priority_spinner.getSelectedItem().toString();
                obj = new JSONObject();
                try {
                    obj.accumulate("Uid", employeeId);
                    obj.accumulate("Branchid", branchId);
                    obj.accumulate("TaskStatus", status);
                    obj.accumulate("priority", priority);

                    Log.e("Task reports = ", obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }

                if (AppUtil.isNetworkAvailable(getActivity())) {
                    try {
                        AsynHttpPost143 post1 = new AsynHttpPost143(getActivity(), 0, 675, ProjectVariables.TASKREPORT, listener, obj, "", firstTime);
                        post1.execute();
                        Log.e("Task reports = ", obj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastMessegNetwork();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
    public void getData(String s, String status, int rType, String temp) {

        selectedUsers = new ArrayList<>();
        taskReportArrayList = new ArrayList<>();
        Log.e("Reports List =", taskReportArrayList.toString());

        if (rType == 675) {
            if (firstTime) {
                pd.dismiss();
                firstTime = false;
            }
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
                    String prioritys = obj.getString(ProjectVariables.PRIORITYS);

                    TaskReport t = new TaskReport();

                    t.setPriority(prioritys);
                    t.setActEndDate(actenddate);
                    t.setActStartDate(ActStartDate);
                    t.setTaskHeading(taskheading);
                    t.setTaskStatus(taskstatus);
                    t.setCid(cid);

                    taskReportArrayList.add(t);

                }
            } catch (JSONException e) {
                e.printStackTrace();

                // Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }

            // mSwipeRefreshLayout.setRefreshing(false);
            adapter1 = new TaskReportAdapter(getActivity(), ReportsFragment.this, R.layout.task_reports, taskReportArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter1);
         /*   RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
            recyclerView.addItemDecoration(itemDecoration);*/


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
                if (getActivity() != null) {
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    employeename.getBackground().setColorFilter(THE_COLOR_GOES_HERE, PorterDuff.Mode.MULTIPLY);
                    employeename.setAdapter(spinnerArrayAdapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (rType == 168) {
            try {
                JSONArray jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    TaskBranches branch = new TaskBranches();
                    branch.setBranchName(jsonObject.getString(ProjectVariables.BNAME));
                    branch.setBranchId(jsonObject.getString(ProjectVariables.BRANCHID));

                    BranchesReportslist.add(branch);
                }

                AppUtil.setBranchesInfo(BranchesReportslist);
                String[] us = new String[BranchesReportslist.size()];
                for (int i = 0; i < BranchesReportslist.size(); i++) {
                    us[i] = BranchesReportslist.get(i).getBranchName();
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                select_branch.getBackground().setColorFilter(THE_COLOR_GOES_HERE, PorterDuff.Mode.MULTIPLY);
                select_branch.setAdapter(spinnerArrayAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

