package com.office.taskmanager.util;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static int hour, minute, second;
    private static String status;
    private static TextView textView;
    private onTimeSetCompleted onTimeSetCompleted;
    String time = null;
    private static String TIMEPICKER = "timepicker";

    public static TimePickerFragment newTimePickerFragment(int hour, int minute, String status, TextView textView) {
        TimePickerFragment pickerFragment = new TimePickerFragment();

        TimePickerFragment.hour = hour;
        TimePickerFragment.minute = minute;
        TimePickerFragment.status = status;
        TimePickerFragment.textView = textView;

        return pickerFragment;
    }

    public void setOnTimeSetCompleted(onTimeSetCompleted timeSetCompleted) {
        this.onTimeSetCompleted = timeSetCompleted;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //Get the AM or PM for current time
        time = hourOfDay + ":" + minute;
        String aMpM = "AM";
        if (hourOfDay > 11) {
            aMpM = "PM";
        }
        //Make the 24 hour time format to 12 hour time format
        int currentHour;
        if (hourOfDay > 11) {
            currentHour = hourOfDay - 12;
        } else {
            currentHour = hourOfDay;
        }

        final Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        time = simpleDateFormat.format(calander.getTime());


        textView.setText(time);
      /*  textView.setText(textView.getText() + String.valueOf(currentHour)
                + " : " + String.valueOf(minute) + " " + aMpM + "\n");*/
        onTimeSetCompleted.onTimeSetCompleted(currentHour, minute, aMpM);

    }

}