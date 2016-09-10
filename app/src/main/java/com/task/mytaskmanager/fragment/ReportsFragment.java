package com.task.mytaskmanager.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.mytaskmanager.R;

public class ReportsFragment extends Fragment {

    public static ReportsFragment newInstance() {

        Bundle args = new Bundle();
        ReportsFragment fragment = new ReportsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_reports, container, false);
        getActivity().setTitle("Reports");
        return view;
    }

}
