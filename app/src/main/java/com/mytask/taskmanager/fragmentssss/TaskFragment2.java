package com.mytask.taskmanager.fragmentssss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.mytask.taskmanager.Adaptes.SeekAdapter;
import com.mytask.taskmanager.Pojo.TaskUser;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.services.addbutton;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskFragment2 extends Fragment implements RestfulListener {
    static addbutton _ab;
    private Spinner branches_spinner;
    ArrayList<TaskUser> users = new ArrayList<>();
    private RecyclerView rc;

    public static TaskFragment2 newInstance(addbutton addbutton) {

        Bundle args = new Bundle();
        _ab = addbutton;
        TaskFragment2 fragment1 = new TaskFragment2();
        fragment1.setArguments(args);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment2, container, false);
        rc = (RecyclerView) view.findViewById(R.id.seekrecyclerview);
        setHasOptionsMenu(true);
        branches_spinner = (Spinner) view.findViewById(R.id.branches);

        AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 0, ProjectVariables.USERS+ PreferenceUtil.getInstance().getString(getActivity(),"UserRole","user")+","+PreferenceUtil.getInstance().getString(getActivity(),"Compname","companyname"), this, null, "");
        post.execute();

        branches_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // _ab.addVisible(false);
    }

    @Override
    public void getData(String s, String status, int requestType) {

        users.clear();
        users = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(s);

            for (int i = 0; i < array.length(); i++) {

                JSONObject obj = array.getJSONObject(i);

                TaskUser user = new TaskUser();

                user.setFirstName(obj.getString(ProjectVariables.FNAME));

                user.setUid(obj.getString(ProjectVariables.UID));


                users.add(user);
            }
            String[] us = new String[users.size()];
            for (int i = 0; i < users.size(); i++) {
                us[i] = users.get(i).getFirstName();
            }
            SeekAdapter sdadapter1 = new SeekAdapter(getActivity(), R.layout.task_row, users);
            rc.setLayoutManager(new LinearLayoutManager(getActivity()));
            rc.setItemAnimator(new DefaultItemAnimator());
            rc.setHasFixedSize(true);
            rc.setAdapter(sdadapter1);

//            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, us); //selected item will look like a spinner set from XML
//            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            branches_spinner.setAdapter(spinnerArrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
