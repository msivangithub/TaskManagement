package com.mytask.taskmanager.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mytask.taskmanager.R;
import com.mytask.taskmanager.util.DatePickerFragment;
import com.mytask.taskmanager.util.OnDateSetCompleted;
import com.mytask.taskmanager.util.PreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AttendanceActivity extends AppCompatActivity {
    TextView mUserName, mDate, mTime, mFromDate, mToDate;
    String UserName = "";
    private int month, day, year;
    private int seconds, minutes, hour;
    LinearLayout mLinearLayoutApplyleve ,mLinearLayoutAbsent,mLinearLayoutPresent;
    Button mPresent,mAbsent,mApplyleave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserName = (TextView) findViewById(R.id.Username);
        mDate = (TextView) findViewById(R.id.currentDate);
        mTime = (TextView) findViewById(R.id.currentTime);
        mFromDate = (TextView) findViewById(R.id.fromDate);
        mToDate = (TextView) findViewById(R.id.toDate);
        mApplyleave = (Button)findViewById(R.id.applyleve);
        mAbsent = (Button)findViewById(R.id.absent);
        mPresent = (Button)findViewById(R.id.present);
        mLinearLayoutApplyleve = (LinearLayout)findViewById(R.id.applyLeve_linearlayout);
        mLinearLayoutAbsent = (LinearLayout)findViewById(R.id.absent_linearlayout);
        mLinearLayoutPresent = (LinearLayout)findViewById(R.id.present_linearlayout);


        mLinearLayoutApplyleve.setVisibility(View.GONE);
        mLinearLayoutAbsent.setVisibility(View.GONE);
        mLinearLayoutPresent.setVisibility(View.GONE);
/*Date format*/
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = new Date();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String currentdate = ss.format(date);
/*Time Format*/
        SimpleDateFormat simpleDateFormat;
        final Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        hour = calander.get(Calendar.HOUR_OF_DAY);
        minutes = calander.get(Calendar.MINUTE);
        String time = simpleDateFormat.format(calander.getTime());


        UserName = PreferenceUtil.getInstance().getString(AttendanceActivity.this, "FirstName", "User");
        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapser.setExpandedTitleColor(Color.parseColor("#ffffff"));
        collapser.setTitle(UserName);

        mUserName.setText(UserName);
        mDate.setText(currentdate);
        mTime.setText(time);
        mFromDate.setText(currentdate);
        mToDate.setText(currentdate);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(year, month, day, mFromDate);
                toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                    @Override
                    public void onDateCompleted(int year, int month, int day) {
                        AttendanceActivity.this.year = year;
                        AttendanceActivity.this.month = month;
                        AttendanceActivity.this.day = day;
                    }
                });
                toDatePickerFragment.show(AttendanceActivity.this.getSupportFragmentManager(), "1");
            }
        });
        mToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(year, month, day, mToDate);
                toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                    @Override
                    public void onDateCompleted(int year, int month, int day) {
                        AttendanceActivity.this.year = year;
                        AttendanceActivity.this.month = month;
                        AttendanceActivity.this.day = day;
                    }
                });
                toDatePickerFragment.show(AttendanceActivity.this.getSupportFragmentManager(), "1");
            }
        });



        mApplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinearLayoutAbsent.setVisibility(View.GONE);
                mLinearLayoutPresent.setVisibility(View.GONE);
                mLinearLayoutApplyleve.setVisibility(View.VISIBLE);
            }
        });
        mAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinearLayoutAbsent.setVisibility(View.VISIBLE);
                mLinearLayoutPresent.setVisibility(View.GONE);
                mLinearLayoutApplyleve.setVisibility(View.GONE);
            }
        });
        mPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinearLayoutAbsent.setVisibility(View.GONE);
                mLinearLayoutPresent.setVisibility(View.VISIBLE);
                mLinearLayoutApplyleve.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }


}
