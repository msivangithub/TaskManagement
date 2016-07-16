package com.task.mytaskmanager.fragmentssss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.mytaskmanager.R;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskFragment2 extends Fragment {

    public static TaskFragment2 newInstance() {

        Bundle args = new Bundle();

        TaskFragment2 fragment1 = new TaskFragment2();
        fragment1.setArguments(args);
        return fragment1;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_fragment2,container,false);
    }
}
