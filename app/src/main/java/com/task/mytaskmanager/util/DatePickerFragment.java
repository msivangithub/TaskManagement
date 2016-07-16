package com.task.mytaskmanager.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NEWSYSTEM1 on 5/20/2016.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static int year, month, day;
    private static TextView textView;
    private OnDateSetCompleted onDateSetCompleted;
    String currentdate;

    public static DatePickerFragment newInstance(int year, int month, int day, TextView textView) {
        DatePickerFragment pickerFragment = new DatePickerFragment();

        // Supply num input as an argument.
        DatePickerFragment.year = year;
        DatePickerFragment.month = month;
        DatePickerFragment.day = day;
        DatePickerFragment.textView = textView;

        return pickerFragment;
    }

    public void setOnDateSetCompleted(OnDateSetCompleted onDateSetCompleted) {
        this.onDateSetCompleted = onDateSetCompleted;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
/*remove this part after*/
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        /*open this part*/
        // DateUtil util = DateUtil.newInstance();
        //  String monthName = util.getMonthName(month);
        // String date = day + "-" + month + "-" + year;
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        currentdate = ss.format(c.getTime());
        //currentdate = day + "-" + month + "-" + year;
        textView.setText(currentdate);
        onDateSetCompleted.onDateCompleted(year, month, day);

    }

}