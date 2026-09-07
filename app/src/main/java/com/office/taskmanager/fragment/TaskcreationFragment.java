package com.office.taskmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.office.taskmanager.R;
import com.office.taskmanager.fragmentssss.TaskFragment1;
import com.office.taskmanager.fragmentssss.TaskFragment2;
import com.office.taskmanager.fragmentssss.TaskFragment3;
import com.office.taskmanager.services.addbutton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskcreationFragment extends Fragment implements addbutton {
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public static TaskcreationFragment newInstance() {

        Bundle args = new Bundle();

        TaskcreationFragment fragment = new TaskcreationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.taskcreation_fragment, container, false);
        getActivity().setTitle(Html.fromHtml("<font face=\"times new roman\" size:10px color='#ffffff'>Task Creation</font>"));
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setHasOptionsMenu(true);
        return view;
    }

    private void setupTabIcons() {
        int[] tabIcons = {

                R.drawable.user,
                R.drawable.lock,
                R.drawable.timer,
                R.drawable.lock,
                R.drawable.timer,
                R.drawable.timer,
                R.drawable.look,
                R.drawable.timer,
                R.drawable.look,
                R.drawable.timer,
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
       // tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(TaskFragment1.newInstance(TaskcreationFragment.this), "ONE");
        adapter.addFrag(TaskFragment2.newInstance(TaskcreationFragment.this), "THREE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void addVisible(boolean b) {


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

                    TaskFragment1 notesFragment = TaskFragment1.newInstance(TaskcreationFragment.this);
                    return notesFragment;

               /* case 1:

                    TaskFragment2 notesF = TaskFragment2.newInstance(TaskcreationFragment.this);
                    return notesF;*/

                case 1:

                    TaskFragment3 notes = TaskFragment3.newInstance(TaskcreationFragment.this);
                    return notes;


                case 3:


                case 4:


                case 5:

            }
            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }
    }
}

