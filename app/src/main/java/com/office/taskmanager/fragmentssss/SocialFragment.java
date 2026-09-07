package com.office.taskmanager.fragmentssss;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.office.taskmanager.R;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class SocialFragment extends Fragment {
    public static final String TASK_HEADING = "task_heading";
    TextView mTaskHeading;
    String taskheading;
    public static SocialFragment newInstance(String taskheading) {
        
        Bundle args = new Bundle();
        SocialFragment fragment = new SocialFragment();
       /* args.putString(TASK_HEADING,taskheading);*/
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_activity,container,false);


        return view;
    }
}
