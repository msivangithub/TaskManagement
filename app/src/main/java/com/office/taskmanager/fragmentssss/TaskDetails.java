package com.office.taskmanager.fragmentssss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lb.recyclerview_fast_scroller.RecyclerViewFastScroller;
import com.office.taskmanager.Adaptes.USERTaskDetailsAdapter;
import com.office.taskmanager.Pojo.Comments;
import com.office.taskmanager.Pojo.Task;
import com.office.taskmanager.Pojo.TaskUser;
import com.office.taskmanager.R;
import com.office.taskmanager.activity.Tasks;
import com.office.taskmanager.activity.TasksAdapter;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.AsynHttpPost143;
import com.office.taskmanager.services.ImageUploadTask;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.PreferenceUtil;
import com.office.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by GhanaShyam on 7/18/2016.
 */
public class TaskDetails extends Fragment implements View.OnClickListener, RestfulListener, SearchView.OnQueryTextListener {

    private ArrayList<TaskUser> users;
    private ArrayList<Tasks> mytasks;
    private ArrayList<Task> TaskList;
    private RestfulListener listener;
    private Spinner status;
    private ProgressDialog pdForVideoUpload;
    private Button show_Details, delete_Tasks, show_edt;
    private ArrayList<TaskUser> selectedUsers;
    private TextView statusdisplay;
    private Tasks tasks;
    private RecyclerView recyclerView;
    private TasksAdapter statusAdapter;
    private Spinner employeename;
    private USERTaskDetailsAdapter adapter1 = null;
    private static final String FTP_HOST = "myaccountsretail.com";
    private ProgressDialog pd;
    private static final String FTP_USER = "myRetail";

    private static final String FTP_PASS = "vKsj30!9";
    private String ImageName = "";
    private String imageURI = "";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String employeeId;
    private JSONObject obj;
    private String audioURI = "";
    private PreferenceUtil util;

    private boolean FAB_Status = false;
    private FloatingActionButton fab;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    private Animation show_fab_1;
    private Animation hide_fab_1;
    private Animation show_fab_2;
    private Animation hide_fab_2;
    private Animation show_fab_3;
    private Animation hide_fab_3;
    private CoordinatorLayout rootLayout;
    private RecyclerViewFastScroller fastScroller;
    private LinearLayout mInternet;
    private LinearLayout allTask_LinearLayout, repeartTask_LinearLayout, round_linearLayout;
    private TextView task, repeart;
    private View view;

    public static TaskDetails newInstance() {
        Bundle args = new Bundle();
        TaskDetails fragment = new TaskDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_task_details, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Task Details</font>"));
        setHasOptionsMenu(true);
        intialVariables();
        users = new ArrayList<>();
        listener = this;
        TaskList = new ArrayList<>();
        selectedUsers = new ArrayList<>();
        mytasks = TaskList();
        pdForVideoUpload = new ProgressDialog(getActivity());
        pdForVideoUpload.setTitle("Please wait.......");
        LinearLayoutDesign();
        RecyclerViewFastScrollerDesign();
        SwipeRefreshLayoutDesign();
        EmployeeNameDropDown();

        return view;
    }


    private void intialVariables() {
        try {
            rootLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
            round_linearLayout = (LinearLayout) view.findViewById(R.id.round_linearLayout);
            allTask_LinearLayout = (LinearLayout) view.findViewById(R.id.allTask_LinearLayout);
            repeartTask_LinearLayout = (LinearLayout) view.findViewById(R.id.repeartTask_LinearLayout);
            task = (TextView) view.findViewById(R.id.task);
            repeart = (TextView) view.findViewById(R.id.repeart);
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
            show_Details = (Button) view.findViewById(R.id.id_show);
            delete_Tasks = (Button) view.findViewById(R.id.id_delete);
            show_edt = (Button) view.findViewById(R.id.id_edit);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
            employeename = (Spinner) view.findViewById(R.id.id_employeenames);
            show_Details.setOnClickListener(this);
            delete_Tasks.setOnClickListener(this);
            show_edt.setOnClickListener(this);
            fabCliskListenear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fabCliskListenear() {
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
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google" +
                        ".com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!");
                startActivity(Intent.createChooser(intent, "Share"));
                //Toast.makeText(getActivity().getApplication(), "Floating Action Button 3", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LinearLayoutDesign() {
        try {
            round_linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            allTask_LinearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            task.setTextColor(Color.parseColor("#ffffff"));
            repeartTask_LinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
            repeart.setTextColor(Color.parseColor("#1B5E20"));

            allTask_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    round_linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    allTask_LinearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    task.setTextColor(Color.parseColor("#ffffff"));

                    repeartTask_LinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    repeart.setTextColor(Color.parseColor("#1B5E20"));

                }
            });
            repeartTask_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    round_linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    repeartTask_LinearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    repeart.setTextColor(Color.parseColor("#ffffff"));

                    allTask_LinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    task.setTextColor(Color.parseColor("#1B5E20"));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RecyclerViewFastScrollerDesign() {
    /*RecyclerViewFastScroller Designing*/
        try {
            fastScroller = (RecyclerViewFastScroller) view.findViewById(R.id.fastscroller);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
                @Override
                public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                    super.onLayoutChildren(recycler, state);
                    //TODO if the items are filtered, considered hiding the fast scroller here
                    final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                    if (firstVisibleItemPosition != 0) {
                        if (firstVisibleItemPosition == -1)
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SwipeRefreshLayoutDesign() {
        try {
            /*************************************************************/
            // status = (Spinner) view.findViewById(R.id.id_status);
            statusAdapter = new TasksAdapter(getActivity(), android.R.layout.simple_spinner_item, mytasks);
        /*PullRefersh in TaskDetails */
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
                                obj.accumulate(ProjectVariables.UID, employeeId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mSwipeRefreshLayout.setRefreshing(false);
                            if (getActivity() != null) {
                                if (AppUtil.isNetworkAvailable(getActivity())) {
                                    try {
                                        AsynHttpPost143 posts = new AsynHttpPost143(getActivity(), 0, 123, ProjectVariables.getTasksByUserId, listener, obj, "", false);
                                        posts.execute();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {

                                    ToastMessegNetwork();
                                }
                            }
                        }
                    }, 1500);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void EmployeeNameDropDown() {
        try {
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
                    if (getActivity() != null) {
                        if (AppUtil.isNetworkAvailable(getActivity())) {
                            try {
                                AsynHttpPost post1 = new AsynHttpPost(getActivity(), 0, 123, ProjectVariables.getTasksByUserId, listener, obj, "");
                                post1.execute();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastMessegNetwork();
                        }
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            try {
                AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 11, ProjectVariables.USERS + PreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user") + "," + PreferenceUtil.getInstance().getString(getActivity(), "Compname", "companyname"), listener, obj, "");
                post.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }

    @Override
    public void getData(String s, String status, int rType, String temp) {
        selectedUsers = new ArrayList<>();
        TaskList = new ArrayList<>();
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


                SharedPreferences sharedPrefer = getActivity().getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentE = sharedPrefer.edit();
                curentE.putString("Audio", ProjectVariables.NOAUDIO);
                curentE.commit();

                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
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

                    getTaskJSONData(obj);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mSwipeRefreshLayout.setRefreshing(false);
            adapter1 = new USERTaskDetailsAdapter(getActivity(), TaskDetails.this, R.layout.task_row, TaskList, "add");
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


        } else if (rType == 11) {
            users = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    getEmployeJSONData(obj);
                }
                String[] us = new String[users.size()];
                for (int i = 0; i < users.size(); i++) {
                    us[i] = users.get(i).getFirstName();
                }
                if (getActivity() != null) {
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    employeename.setAdapter(spinnerArrayAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (rType == 143) {
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String result = obj.getString("Result");
                    Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (rType == 154) {
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String result = obj.getString("Result");
                    Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void getEmployeJSONData(JSONObject obj) {
        try {
            TaskUser user = new TaskUser();
            user.setFirstName(obj.getString(ProjectVariables.FNAME));
            user.setUid(obj.getString(ProjectVariables.UID));
            users.add(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTaskJSONData(JSONObject obj) {
        try {
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
            String taskImage = obj.getString(ProjectVariables.ImAGE);
            String video = obj.getString("video");
            String comments = obj.getString("Comments");
            String taskId = obj.getString("Cid");
            String uname = obj.getString("Uname");
            String uimage = obj.getString("UImage");
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
            t.setStartTime(startTime);
            t.setEndTime(endTime);
            t.setUname(uname);
            t.setProfile(uimage);
            t.setTaskimage(taskImage);
            TaskList.add(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(Uri uri) {
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
            if (cursor != null) {

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                pdForVideoUpload.setTitle("Video uploading....");
                //UploadTask uploadTask = null;
                try {
                    ImageUploadTask uploadTask1 = new ImageUploadTask(getActivity(), getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    uploadTask1.execute();
                    //uploadTask = new UploadTask(getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                    //uploadTask.execute();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                super.onActivityResult(requestCode, resultCode, data);

            }
        } else if (requestCode == 97) {
            Uri selectedimg = data.getData();
            audioURI = getPath(selectedimg);
            String[] imageArray = audioURI.split("/");

            int length = imageArray.length;
            String convertedImage = imageArray[length - 1];

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
            SharedPreferences.Editor curentEdit = sharedPreferences.edit();
            curentEdit.putString("Audio", convertedImage);
            curentEdit.commit();
            // UploadTask u = null;
            try {
                ImageUploadTask uploadTask1 = new ImageUploadTask(getActivity(), getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                uploadTask1.execute();
                // u = new UploadTask(getActivity().getContentResolver().openInputStream(selectedimg), convertedImage);
                // u.execute();
            } catch (FileNotFoundException e) {

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter1.setFilter(TaskList);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                return true; // Return true to expand action view
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            final ArrayList<Task> filteredModelList = filter(TaskList, newText);
            adapter1.setFilter(filteredModelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private ArrayList<Task> filter(ArrayList<Task> taskArrayList, String query) {
        query = query.toLowerCase();
        final ArrayList<Task> filteredModelList = new ArrayList<>();
        for (Task list : taskArrayList) {
            final String text = list.getTaskHeading().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(list);
            }
        }
        return filteredModelList;
    }
}

