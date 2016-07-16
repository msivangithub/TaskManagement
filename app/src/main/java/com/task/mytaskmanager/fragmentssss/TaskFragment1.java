package com.task.mytaskmanager.fragmentssss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.util.DatePickerFragment;
import com.task.mytaskmanager.util.OnDateSetCompleted;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskFragment1 extends Fragment {
    private TextView mTextViewFromDate, mTextViewToDate;
    private ImageView mImageButtonFrom, mImageButtonTo;
    private int month, day, year;
    private static String fromDate ,toDate ;

    public static TaskFragment1 newInstance() {

        Bundle args = new Bundle();

        TaskFragment1 fragment1 = new TaskFragment1();
        fragment1.setArguments(args);
        return fragment1;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_fragment1, container, false);

        mTextViewFromDate = (TextView) view.findViewById(R.id.fromDate);
        mTextViewToDate = (TextView) view.findViewById(R.id.toDate);
        mImageButtonFrom = (ImageView) view.findViewById(R.id.fromDateImage);
        mImageButtonTo = (ImageView) view.findViewById(R.id.toDateImage);

        dateFormat();
        setHasOptionsMenu(true);
        return view;
    }

    private void dateFormat() {

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
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
                    }
                });

                toDatePickerFragment.show(TaskFragment1.this.getFragmentManager(), "1");
            }
        });

    }
}
