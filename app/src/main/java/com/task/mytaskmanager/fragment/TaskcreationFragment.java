package com.task.mytaskmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.fragmentssss.AddressFragment;
import com.task.mytaskmanager.fragmentssss.DetailsFragment;
import com.task.mytaskmanager.fragmentssss.EssentialsFragment;
import com.task.mytaskmanager.fragmentssss.PermissionsFragment;
import com.task.mytaskmanager.fragmentssss.ProfileFragment;
import com.task.mytaskmanager.fragmentssss.SocialFragment;
import com.task.mytaskmanager.fragmentssss.TaskFragment1;
import com.task.mytaskmanager.fragmentssss.TaskFragment2;
import com.task.mytaskmanager.fragmentssss.TaskFragment3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GhanaShyam on 7/15/2016.
 */
public class TaskcreationFragment extends Fragment {
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
        getActivity().setTitle("Task Creation");
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
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
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
        tabLayout.getTabAt(6).setIcon(tabIcons[6]);
        tabLayout.getTabAt(7).setIcon(tabIcons[7]);
        tabLayout.getTabAt(8).setIcon(tabIcons[8]);
        tabLayout.getTabAt(9).setIcon(tabIcons[9]);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new DetailsFragment(), "ONE");
        adapter.addFrag(new DetailsFragment(), "TWO");
        adapter.addFrag(new DetailsFragment(), "THREE");
        adapter.addFrag(new DetailsFragment(), "ONE");
        adapter.addFrag(new DetailsFragment(), "TWO");
        adapter.addFrag(new DetailsFragment(), "THREE");
        adapter.addFrag(new DetailsFragment(), "ONE");
        adapter.addFrag(new DetailsFragment(), "TWO");
        adapter.addFrag(new DetailsFragment(), "THREE");
        adapter.addFrag(new DetailsFragment(), "THREE");

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
                    TaskFragment1 notesFragment = new TaskFragment1();
                    return notesFragment;

                case 1:
                    TaskFragment2 notesF = new TaskFragment2();
                    return notesF;
                case 2:
                    TaskFragment3 notes = new TaskFragment3();
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

