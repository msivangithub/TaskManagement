package com.mytask.taskmanager.fragmentssss;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lb.recyclerview_fast_scroller.RecyclerViewFastScroller;
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

    private boolean FAB_Status = false;
    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;

    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    CoordinatorLayout rootLayout;
    RecyclerViewFastScroller fastScroller;
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

        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Task Details</font>"));
        setHasOptionsMenu(true);

        rootLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) view.findViewById(R.id.fab_3);

        show_fab_1 = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.fab3_hide);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplication(), "Floating Action Button 1", Toast.LENGTH_SHORT).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplication(), "Floating Action Button 2", Toast.LENGTH_SHORT).show();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplication(), "Floating Action Button 3", Toast.LENGTH_SHORT).show();
            }
        });


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

        fastScroller = (RecyclerViewFastScroller) view.findViewById(R.id.fastscroller);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                //TODO if the items are filtered, considered hiding the fast scroller here
                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    // this avoids trying to handle un-needed calls
                    if (firstVisibleItemPosition == -1)
                        //not initialized, or no items shown, so hide fast-scroller
                        fastScroller.setVisibility(View.GONE);
                    return;
                }
                final int lastVisibleItemPosition = findLastVisibleItemPosition();
                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                //if all items are shown, hide the fast-scroller
                fastScroller.setVisibility(adapter1.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
            }
        });
        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setViewsToUse(R.layout.recycler_view_fast_scroller__fast_scroller, R.id.fastscroller_bubble, R.id.fastscroller_handle);


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
                String comm, userroles, videoPlay, image, audio, profile;

                JSONArray array = new JSONArray(s);
                for (int c = 0; c < array.length(); c++) {
                    Comments c1 = new Comments();
                    JSONObject obj = array.getJSONObject(c);
                    comm = obj.getString("Comments");
                    userroles = obj.getString("UserRole");
                    videoPlay = obj.getString("video");
                    image = obj.getString("image");
                    audio = obj.getString("Audio");
                    profile = obj.getString("UImage");
                    c1.setComments(comm);
                    c1.setUserRole(userroles);
                    c1.setVideo(videoPlay);
                    c1.setImage(image);
                    c1.setAudio(audio);
                    c1.setProfile(profile);
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
                    String uname = obj.getString("Uname");
                    String uimage = obj.getString("UImage");
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
                    t.setUname(uname);
                    t.setProfile(uimage);
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
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (FAB_Status) {
                        hideFAB();
                        FAB_Status = false;
                    }
                    return false;
                }
            });

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
    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }


    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);

    }
}

