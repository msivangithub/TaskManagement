package com.task.mytaskmanager.fragmentssss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.services.addbutton;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskFragment3 extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton mnone, low, Medium, High;
    static addbutton _ab;
    private Button addUser;

    public static TaskFragment3 newInstance(addbutton ab) {

        Bundle args = new Bundle();
        _ab = ab;

        TaskFragment3 fragment1 = new TaskFragment3();
        fragment1.setArguments(args);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_fragment3, container, false);
       addUser = (Button) view.findViewById(R.id.adduser);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        mnone = (RadioButton) view.findViewById(R.id.none);
        low = (RadioButton) view.findViewById(R.id.low);
        Medium = (RadioButton) view.findViewById(R.id.Medium);
        High = (RadioButton) view.findViewById(R.id.High);


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.none) {
                    Toast.makeText(getActivity(), "choice: None", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.low) {
                    Toast.makeText(getActivity(), "choice: low", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.Medium) {
                    Toast.makeText(getActivity(), "choice: Medium", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.High) {
                    Toast.makeText(getActivity(), "choice: High", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        _ab.addVisible(true);
    }
}
