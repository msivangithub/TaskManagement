package com.mytask.taskmanager.fragmentssss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mytask.taskmanager.R;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class SocialFragment extends Fragment {

    public static SocialFragment newInstance() {
        
        Bundle args = new Bundle();

        SocialFragment fragment = new SocialFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_activity,container,false);
    }
}
