package com.task.mytaskmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.fragmentssss.AddressFragment;
import com.task.mytaskmanager.fragmentssss.DetailsFragment;
import com.task.mytaskmanager.fragmentssss.EssentialsFragment;
import com.task.mytaskmanager.fragmentssss.PermissionsFragment;
import com.task.mytaskmanager.fragmentssss.ProfileFragment;
import com.task.mytaskmanager.fragmentssss.SocialFragment;

import java.util.ArrayList;
import java.util.List;

public class AddUserFragment extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static AddUserFragment newInstance() {

        Bundle args = new Bundle();

        AddUserFragment fragment = new AddUserFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_add_user_fragment, container, false);
        getActivity().setTitle("Task Creation");

        /*toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setHasOptionsMenu(true);
        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EssentialsFragment(), "Essentials");
//        adapter.addFragment(new DetailsFragment(), "Details");
//        adapter.addFragment(new AddressFragment(), "Address");
//        adapter.addFragment(new ProfileFragment(), "Profile");
//        adapter.addFragment(new SocialFragment(), "Social");
       // adapter.addFragment(new PermissionsFragment(), "Permissions");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    EssentialsFragment notesFragment = new EssentialsFragment();
                    return notesFragment;

//                case 1:
//                    DetailsFragment notesFragment1 = new DetailsFragment();
//                    return notesFragment1;
//
//                case 2:
//                    AddressFragment notesFragment2 = new AddressFragment();
//                    return notesFragment2;
//
//                case 3:
//                    ProfileFragment notesFragment3 = new ProfileFragment();
//                    return notesFragment3;
//
//                case 4:
//                    SocialFragment notesFragment4 = new SocialFragment();
//                    return notesFragment4;

//                case 1:
//                    PermissionsFragment notesFragment5 = new PermissionsFragment();
//                    return notesFragment5;


            }
            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return true;

    }


}