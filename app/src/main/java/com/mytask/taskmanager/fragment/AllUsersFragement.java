package com.mytask.taskmanager.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mytask.taskmanager.Adaptes.AllUsersAdapter;
import com.mytask.taskmanager.Pojo.TaskUser;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllUsersFragement extends Fragment implements RestfulListener {
    RecyclerView mRecycleruserDelete;
    ArrayList<TaskUser> users = new ArrayList<>();
    JSONArray jsonArray;
    AllUsersAdapter allUsersAdapter;

    public static AllUsersFragement newInstance() {

        Bundle args = new Bundle();

        AllUsersFragement fragment = new AllUsersFragement();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_all_users_fragement, container, false);
        mRecycleruserDelete = (RecyclerView) view.findViewById(R.id.userdelete);
        AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 190, ProjectVariables.USERS + PreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user") + "," + PreferenceUtil.getInstance().getString(getActivity(), "Compname", "companyname"), this, null, "");
        post.execute();
        setHasOptionsMenu(true);
        getActivity().setTitle("All Users");
        return view;
    }

    @Override
    public void getData(String s, String status, int rType) {
        if (rType == 190) {
            users = new ArrayList<>();
            try {
                users = new ArrayList<>();
                jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    TaskUser user = new TaskUser();
                    user.setFirstName(obj.getString(ProjectVariables.FNAME));
                    user.setUid(obj.getString(ProjectVariables.UID));
                    user.setUserLevel(obj.getString(ProjectVariables.USERLEVEL));
                    user.setUserRole(obj.getString(ProjectVariables.USERROLE));
                    user.setImage(obj.getString(ProjectVariables.IMAGE_AllUSER));
                    user.setEmailid(obj.getString(ProjectVariables.EMA_ILID));
                    user.setPhone(obj.getString(ProjectVariables.PH_ONE));
                    user.setCity(obj.getString(ProjectVariables.CI_TY));
                    users.add(user);
                }

                allUsersAdapter = new AllUsersAdapter(getActivity(),AllUsersFragement.this, R.layout.user_rowitems, users);
                mRecycleruserDelete.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecycleruserDelete.setItemAnimator(new DefaultItemAnimator());
                mRecycleruserDelete.setHasFixedSize(true);
                mRecycleruserDelete.setAdapter(allUsersAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (rType == 109) {
            try {
                JSONArray array = new JSONArray(s);
                JSONObject obj = array.getJSONObject(0);

                String result = obj.getString("Result");

                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
