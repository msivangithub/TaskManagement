package com.office.taskmanager.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.office.taskmanager.R;
import com.office.taskmanager.util.PreferenceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SettingActivity extends AppCompatActivity {

    TextView user, Mail, phone, branchname, city;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        user = (TextView) findViewById(R.id.txt_UserName);
        Mail = (TextView) findViewById(R.id.txt_Mail);
        image = (ImageView)findViewById(R.id.profileImage);
        phone = (TextView) findViewById(R.id.phoneNo);
        branchname = (TextView) findViewById(R.id.branchname);
      //  city = (TextView)findViewById(R.id.city);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ProfileImageFromURL loadImage = new ProfileImageFromURL();
        loadImage.execute();

        user.setText(PreferenceUtil.getInstance().getString(SettingActivity.this, "FirstName", "User"));
        Mail.setText(PreferenceUtil.getInstance().getString(SettingActivity.this, "MailID", "user@mail.com"));
        phone.setText(PreferenceUtil.getInstance().getString(SettingActivity.this, "PhoneNo", "phoneno"));
        branchname.setText(PreferenceUtil.getInstance().getString(SettingActivity.this, "BranchName", "branch"));
      //  city.setText(PreferenceUtil.getInstance().getString(SettingActivity.this, "City", "city"));

        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapser.setExpandedTitleColor(Color.parseColor("#880E4F"));
        collapser.setTitle(PreferenceUtil.getInstance().getString(SettingActivity.this, "FirstName", "User"));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public class ProfileImageFromURL extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            //http://makeindiakart.com/taskfiles/Image_1290.jpg
            try {
                String MainUrl = "http://makeindiakart.com/taskfiles/";
                URL url = new URL(MainUrl + PreferenceUtil.getInstance().getString(SettingActivity.this, "ProfileImage", "Image_5756.jpg"));
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                return bitMap;

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            image.setImageBitmap(result);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));

    }
}
