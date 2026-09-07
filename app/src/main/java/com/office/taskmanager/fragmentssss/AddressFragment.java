package com.office.taskmanager.fragmentssss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.office.taskmanager.R;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class AddressFragment extends Fragment {

    public static AddressFragment newInstance() {
        
        Bundle args = new Bundle();

        AddressFragment fragment = new AddressFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_activity,container,false);
    }
}
