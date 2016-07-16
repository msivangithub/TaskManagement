package com.task.mytaskmanager.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.fragmentssss.AddressFragment;
import com.task.mytaskmanager.fragmentssss.DetailsFragment;
import com.task.mytaskmanager.fragmentssss.EssentialsFragment;
import com.task.mytaskmanager.fragmentssss.PermissionsFragment;
import com.task.mytaskmanager.fragmentssss.ProfileFragment;
import com.task.mytaskmanager.fragmentssss.SocialFragment;

import java.util.ArrayList;
import java.util.List;

public class AddUserActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EssentialsFragment(), "Essentials");
        adapter.addFragment(new DetailsFragment(), "Details");
        adapter.addFragment(new AddressFragment(), "Address");
        adapter.addFragment(new ProfileFragment(), "Profile");
        adapter.addFragment(new SocialFragment(), "Social");
        adapter.addFragment(new PermissionsFragment(), "Permissions");
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

                case 1:
                    DetailsFragment notesFragment1 = new DetailsFragment();
                    return notesFragment1;

                case 2:
                    AddressFragment notesFragment2 = new AddressFragment();
                    return notesFragment2;

                case 3:
                    ProfileFragment notesFragment3 = new ProfileFragment();
                    return notesFragment3;

                case 4:
                    SocialFragment notesFragment4 = new SocialFragment();
                    return notesFragment4;

                case 5:
                    PermissionsFragment notesFragment5 = new PermissionsFragment();
                    return notesFragment5;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
