package com.mytask.taskmanager.fragmentssss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mytask.taskmanager.Pojo.TaskUser;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.services.addbutton;
import com.mytask.taskmanager.util.AppUtil;
import com.mytask.taskmanager.util.DatePickerFragment;
import com.mytask.taskmanager.util.OnDateSetCompleted;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;
import com.mytask.taskmanager.util.TimePickerFragment;
import com.mytask.taskmanager.util.onTimeSetCompleted;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskFragment1 extends Fragment implements RestfulListener {
    private TextView mTextViewFromDate, mTextViewToDate, mTextViewFromTime, mTextViewToTime;
    private ImageView mImageButtonFrom, mImageButtonTo, mfromTimeImage, mToTimeImage;
    private EditText TaskHeader;
    private int month, day, year;
    private int seconds, minutes, hour;
    private String status;
    private static String fromDate, toDate;
    static addbutton _ab;
    Spinner users_Spinner;
    ArrayList<TaskUser> users = new ArrayList<>();
    EditText edt_task;

    public static TaskFragment1 newInstance(addbutton addbutton) {

        Bundle args = new Bundle();
        _ab = addbutton;
        TaskFragment1 fragment1 = new TaskFragment1();
        fragment1.setArguments(args);
        Log.e("Values", AppUtil.getActStartDate());
        Log.e("Values", AppUtil.getActEndDate());
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_fragment1, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setHasOptionsMenu(true);
        TaskHeader = (EditText) view.findViewById(R.id.taskHeader);
        users_Spinner = (Spinner) view.findViewById(R.id.TaskUsers);
        /*Date format*/
        mTextViewFromDate = (TextView) view.findViewById(R.id.fromDate);
        mTextViewToDate = (TextView) view.findViewById(R.id.toDate);
        mImageButtonFrom = (ImageView) view.findViewById(R.id.fromDateImage);
        mImageButtonTo = (ImageView) view.findViewById(R.id.toDateImage);
        /*Time Format*/
        mTextViewFromTime = (TextView) view.findViewById(R.id.fromTime);
        mTextViewToTime = (TextView) view.findViewById(R.id.toTime);
        mToTimeImage = (ImageView) view.findViewById(R.id.toTimeImage);
        mfromTimeImage = (ImageView) view.findViewById(R.id.fromTimeImage);

        edt_task = (EditText) view.findViewById(R.id.taskDes);
        JSONObject obj = new JSONObject();
        edt_task.addTextChangedListener(watcher);
        TaskHeader.addTextChangedListener(watcher1);


        Log.e("Values", AppUtil.getActStartDate());
        Log.e("Values", AppUtil.getActEndDate());

        AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 0, ProjectVariables.USERS+PreferenceUtil.getInstance().getString(getActivity(),"UserRole","user")+","+PreferenceUtil.getInstance().getString(getActivity(),"Compname","companyname"), this, null, "");
        post.execute();
        dateFormat();
        timeFormat();
        setHasOptionsMenu(true);

        users_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (users.size() != 0)
                    AppUtil.setTaskFromId(PreferenceUtil.getInstance().getString(getActivity(), "Uid", "c001"));
                AppUtil.setTaskToId(users.get(position).getUid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        Log.e("Values", AppUtil.getActStartDate());
        if (!AppUtil.getActStartDate().equalsIgnoreCase(""))
            mTextViewFromDate.setText(AppUtil.getActStartDate());
        if (!AppUtil.getActEndDate().equalsIgnoreCase(""))
            mTextViewToDate.setText(AppUtil.getActEndDate());

        if (!AppUtil.getStartFromTime().equalsIgnoreCase(""))
            mTextViewFromTime.setText(AppUtil.getStartFromTime());
        if (!AppUtil.getStartToTime().equalsIgnoreCase(""))
            mTextViewToTime.setText(AppUtil.getStartToTime());

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        _ab.addVisible(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            AppUtil.setTaskDes(s.toString());


        }
    };

    TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            AppUtil.setTaskHeading(s.toString());


        }
    };

    private void timeFormat() {
        SimpleDateFormat simpleDateFormat;
        final Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        hour = calander.get(Calendar.HOUR_OF_DAY);
        minutes = calander.get(Calendar.MINUTE);
        String time = simpleDateFormat.format(calander.getTime());
        mTextViewFromTime.setText(time);
        mTextViewToTime.setText(time);
        Log.e("Current Time:=", time);

        mfromTimeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = TimePickerFragment.newTimePickerFragment(hour, minutes, status, mTextViewFromTime);
                newFragment.setOnTimeSetCompleted(new onTimeSetCompleted() {
                    @Override
                    public void onTimeSetCompleted(int hour, int minute, String aMpM) {
                        TaskFragment1.this.hour = hour;
                        TaskFragment1.this.minutes = minute;
                        TaskFragment1.this.status = status;
                        AppUtil.setStartFromTime(hour + ":" + minute + aMpM);
                        mTextViewFromTime.setText(hour + ":" + minute + aMpM);
                        Log.e("From Current Time :=", hour + ":" + minute + aMpM);

                    }
                });
                newFragment.show(TaskFragment1.this.getFragmentManager(), "1");

            }
        });
        mToTimeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = TimePickerFragment.newTimePickerFragment(hour, minutes, status, mTextViewToTime);
                newFragment.setOnTimeSetCompleted(new onTimeSetCompleted() {
                    @Override
                    public void onTimeSetCompleted(int hour, int minute, String aMpM) {
                        TaskFragment1.this.hour = hour;
                        TaskFragment1.this.minutes = minute;
                        TaskFragment1.this.status = status;
                        AppUtil.setStartToTime(hour + ":" + minute + aMpM);
                        mTextViewToTime.setText(hour + ":" + minute + aMpM);
                        Log.e("To Current Time:=", hour + ":" + minute + aMpM);
                    }
                });
                newFragment.show(TaskFragment1.this.getFragmentManager(), "1");
            }
        });
    }


    private void dateFormat() {

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = new Date();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String currentdate = ss.format(date);

        mTextViewFromDate.setText(currentdate);
        mTextViewToDate.setText(currentdate);
        mImageButtonFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(year, month, day, mTextViewFromDate);
                toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                    @Override
                    public void onDateCompleted(int year, int month, int day) {
                        TaskFragment1.this.year = year;
                        TaskFragment1.this.month = month;
                        TaskFragment1.this.day = day;
                        AppUtil.setActStartDate(day + "-" + month + "-" + year);
                        AppUtil.setExpStartDate(day + "-" + month + "-" + year);
                    }
                });

                toDatePickerFragment.show(TaskFragment1.this.getFragmentManager(), "1");
            }
        });
        mImageButtonTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(year, month, day, mTextViewToDate);
                toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                    @Override
                    public void onDateCompleted(int year, int month, int day) {
                        TaskFragment1.this.year = year;
                        TaskFragment1.this.month = month;
                        TaskFragment1.this.day = day;
                        AppUtil.setActEndDate(day + "-" + month + "-" + year);
                        AppUtil.setExpEndDate(day + "-" + month + "-" + year);
                    }
                });

                toDatePickerFragment.show(TaskFragment1.this.getFragmentManager(), "1");
            }
        });

    }

    @Override
    public void getData(String s, String status, int type) {
        users.clear();
        users = new ArrayList<>();
        try {

            users = new ArrayList<>();
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
            users_Spinner.setAdapter(spinnerArrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
