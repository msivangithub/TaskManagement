package com.office.taskmanager.activity;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.office.taskmanager.R;

import java.util.ArrayList;

/**
 * Created by MANJU on 16-07-2016.
 */
public class TasksAdapter extends ArrayAdapter<Tasks> {
    public String text;

    private Activity context;
    ArrayList<Tasks> tasksArrayList;

    public TasksAdapter(Activity context, int resource, ArrayList<Tasks> taskses) {
        super(context, resource, taskses);
        this.tasksArrayList = taskses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public class ViewHolder {
        TextView textView, colortextview;
    }

    @Override
    public int getCount() {
        return tasksArrayList.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
           /* LayoutInflater inflater = context.getLayoutInflater();*/
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.spinner_layout, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textdisplay);
            viewHolder.colortextview = (TextView) convertView.findViewById(R.id.colordisplay);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            Tasks tasks = tasksArrayList.get(position);
            if (viewHolder.textView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.textView.setText(tasks.getTaskname());
                text = viewHolder.textView.getText().toString();
                if (text.equalsIgnoreCase("All")) {
                    viewHolder.colortextview.setBackgroundColor(Color.WHITE);
                } else if (text.equalsIgnoreCase("Completed")) {
                    viewHolder.colortextview.setBackgroundColor(Color.GREEN);
                } else if (text.equalsIgnoreCase("Pending")) {
                    viewHolder.colortextview.setBackgroundColor(Color.RED);
                } else if (text.equalsIgnoreCase("Progress")) {
                    viewHolder.colortextview.setBackgroundColor(Color.YELLOW);
                }
            }


        return convertView;
    }


}
