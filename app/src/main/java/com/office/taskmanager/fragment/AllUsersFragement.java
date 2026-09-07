package com.office.taskmanager.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lb.recyclerview_fast_scroller.RecyclerViewFastScroller;
import com.office.taskmanager.Adaptes.AllUsersAdapter;
import com.office.taskmanager.Pojo.TaskUser;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.PreferenceUtil;
import com.office.taskmanager.util.ProjectVariables;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllUsersFragement extends Fragment implements RestfulListener, SearchView.OnQueryTextListener {
    RecyclerView mRecycleruserDelete;
    ArrayList<TaskUser> users = new ArrayList<>();
    JSONArray jsonArray;
    AllUsersAdapter allUsersAdapter;
    RecyclerViewFastScroller fastScroller;

    RestfulListener listener;

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
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>All Users</font>"));
        mRecycleruserDelete = (RecyclerView) view.findViewById(R.id.userdelete);

          /*RecyclerViewFastScroller Designing*/
        fastScroller = (RecyclerViewFastScroller) view.findViewById(R.id.fastscroller);
        mRecycleruserDelete.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                //TODO if the items are filtered, considered hiding the fast scroller here
                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    if (firstVisibleItemPosition == -1)
                        fastScroller.setVisibility(View.GONE);
                    return;
                }
                final int lastVisibleItemPosition = findLastVisibleItemPosition();
                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                //if all items are shown, hide the fast-scroller
                fastScroller.setVisibility(allUsersAdapter.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
            }
        });
        fastScroller.setRecyclerView(mRecycleruserDelete);
        fastScroller.setViewsToUse(R.layout.recycler_view_fast_scroller__fast_scroller, R.id.fastscroller_bubble, R.id.fastscroller_handle);


        listener = this;
        //recyclerView = (FastScrollRecyclerView) view.findViewById(R.id.recycler);
        AsynHttpPost post = new AsynHttpPost(getActivity(), 0, 190, ProjectVariables.USERS + PreferenceUtil.getInstance().getString(getActivity(), "UserRole", "user") + "," + PreferenceUtil.getInstance().getString(getActivity(), "Compname", "companyname"), this, null, "");
        post.execute();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void getData(String s, String status, int rType, String temp) {
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
              /*  fastScrollAdapter = new FastScrollAdapter(getActivity(),AllUsersFragement.this, R.layout.user_rowitems, users);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(fastScrollAdapter);*/

                allUsersAdapter = new AllUsersAdapter(getActivity(), AllUsersFragement.this, R.layout.user_rowitems, users);
                mRecycleruserDelete.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecycleruserDelete.setItemAnimator(new DefaultItemAnimator());
                mRecycleruserDelete.setHasFixedSize(true);
                mRecycleruserDelete.setAdapter(allUsersAdapter);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        } else if (rType == 109) {
            try {
                JSONArray array = new JSONArray(s);
                JSONObject obj = array.getJSONObject(0);

                String result = obj.getString("Result");

                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                allUsersAdapter.setFilter(users);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                return true; // Return true to expand action view
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<TaskUser> filteredModelList = filter(users, newText);
        allUsersAdapter.setFilter(filteredModelList);
        return true;
    }

    private ArrayList<TaskUser> filter(ArrayList<TaskUser> taskArrayList, String query) {
        query = query.toLowerCase();
        final ArrayList<TaskUser> filteredModelList = new ArrayList<>();
        for (TaskUser list : taskArrayList) {
            final String text = list.getFirstName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(list);
            }
        }
        return filteredModelList;
    }
}
