package com.task.mytaskmanager.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.task.mytaskmanager.LoginSetup.LoginActivity;
import com.task.mytaskmanager.Pojo.Task;
import com.task.mytaskmanager.Pojo.TaskUser;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.fragment.AddUserFragment;
import com.task.mytaskmanager.fragment.AllUsersFragement;
import com.task.mytaskmanager.fragment.ReportsFragment;
import com.task.mytaskmanager.fragment.TaskcreationFragment;
import com.task.mytaskmanager.fragmentssss.SettingsFragments;
import com.task.mytaskmanager.fragmentssss.TaskDetails;
import com.task.mytaskmanager.fragmentssss.UserTaskDetails;
import com.task.mytaskmanager.util.AlertUtil;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.PreferenceUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String UserRole = "";
    TextView user, Mail;
    ImageView image;
    URL url = null;
    Bitmap bitmap;
    String ImageName = "";
    String imageURI = "";
    String audioURI = "";
    ProjectVariables variables = new ProjectVariables();
    static final String FTP_HOST = "myaccountsretail.com";
    ProgressDialog pd;
    /*********
     * FTP USERNAME
     ***********/
    static final String FTP_USER = "myRetail";
    /*********
     * FTP PASSWORD
     ***********/
    static final String FTP_PASS = "vKsj30!9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermissions();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserRole = PreferenceUtil.getInstance().getString(MainActivity.this, ProjectVariables.USER_ROLE, "4");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //http://makeindiakart.com/taskfiles/Image_1290.jpg
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        user = (TextView) v.findViewById(R.id.txt_UserName);
        Mail = (TextView) v.findViewById(R.id.txt_Mail);
        image = (ImageView) v.findViewById(R.id.imageView);
        LoadImageFromURL loadImage = new LoadImageFromURL();
        loadImage.execute();
        user.setText(PreferenceUtil.getInstance().getString(MainActivity.this, "FirstName", "User"));
        Mail.setText(PreferenceUtil.getInstance().getString(MainActivity.this, "MailID", "user@mail.com"));
//user
        if (UserRole.equalsIgnoreCase("3")) {
            Menu m = navigationView.getMenu();

            MenuItem addItem = m.findItem(R.id.nav_addUser);
            addItem.setVisible(false);

            MenuItem creatItem = m.findItem(R.id.nav_taxCreation);
            creatItem.setVisible(false);

            MenuItem alluser = m.findItem(R.id.nav_allusers);
            alluser.setVisible(false);

            MenuItem reports = m.findItem(R.id.nav_allReports);
            reports.setVisible(false);
//ggyddgfyfyfyf
        } else if (UserRole.equalsIgnoreCase("5") || (UserRole.equalsIgnoreCase("6"))) {//manager
            Menu m = navigationView.getMenu();

            MenuItem addItem = m.findItem(R.id.nav_addUser);
            addItem.setVisible(false);

            MenuItem alluser = m.findItem(R.id.nav_allusers);
            alluser.setVisible(false);

        }

        if (UserRole.equalsIgnoreCase("4")) {
            Menu m = navigationView.getMenu();

            MenuItem alluser = m.findItem(R.id.nav_allusers);
            alluser.setVisible(false);

            MenuItem reports = m.findItem(R.id.nav_allReports);
            reports.setVisible(false);
        }
        if (savedInstanceState == null) {

            if (UserRole.equalsIgnoreCase("4") || UserRole.equalsIgnoreCase("1")) {//admin
                Fragment f = TaskDetails.newInstance();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
            } else {
                Fragment f = UserTaskDetails.newInstance();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
            }
        }
    }

    public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            //http://makeindiakart.com/taskfiles/Image_1290.jpg
            try {
                String MainUrl = "http://makeindiakart.com/taskfiles/";
                URL url = new URL(MainUrl + PreferenceUtil.getInstance().getString(MainActivity.this, "ProfileImage", "Image_5756.jpg"));
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertUtil.displayExitDialog(MainActivity.this);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addUser) {
            // Handle the camera action
            Fragment f = AddUserFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.nav_taxCreation) {

            clearAputils();
            /*startActivity(new Intent(MainActivity.this, AddUserActivity.class));*/
            Fragment f = TaskcreationFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.nav_allusers) {
            Fragment f = AllUsersFragement.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();
        } else if (id == R.id.nav_allReports) {
            ReportsFragment f = ReportsFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

        } else if (id == R.id.nav_taxDetails) {

            if (UserRole.equalsIgnoreCase("3")) {
                Fragment f = UserTaskDetails.newInstance();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
            } else {
                ArrayList<Task> tasks = new ArrayList<>();
                ArrayList<TaskUser> userTask = new ArrayList<>();
                AppUtil.setUserArrayList(tasks);
                AppUtil.setDeletedUserList(tasks);
                Fragment f = TaskDetails.newInstance();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
            }
        } else if (id == R.id.nav_share) {
            // https://play.google.com/store/apps/details?id=com.share.myapplication&hl=en
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google" +
                    ".com/store/apps/details?id=" + getApplicationContext().getPackageName());
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!");
            startActivity(Intent.createChooser(intent, "Share"));

        } else if (id == R.id.nav_setting) {

            Fragment f = SettingsFragments.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.commit();

        } else if (id == R.id.nav_logout) {

            PreferenceUtil util = PreferenceUtil.getInstance();
            util.saveString(MainActivity.this, ProjectVariables.STATUS, ProjectVariables.LOGGED_OUT);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void clearAputils() {
        AppUtil.setActEndDate("");
        AppUtil.setExpEndDate("");
        AppUtil.setExpStartDate("");
        AppUtil.setActStartDate("");
        AppUtil.setPriority("");
        AppUtil.setTaskDes("");
        AppUtil.setTaskFromId("");
        AppUtil.setTaskHeading("");
        AppUtil.setTaskStatus("");
        AppUtil.setTaskToId("");
        AppUtil.setStartFromTime("");
        AppUtil.setStartToTime("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(getApplicationContext(), "Main activity result", Toast.LENGTH_LONG).show();
        if (resultCode == Activity.RESULT_OK) {
            //  Toast.makeText(getApplicationContext(), "Main activity result1"+requestCode, Toast.LENGTH_LONG).show();
            if (requestCode == 667) {
                Uri selectedimg = data.getData();
                String origanImage = getAbsolutePath(selectedimg);
                String[] imageArray = origanImage.split("/");

                int length = imageArray.length;

                String convertedImage = imageArray[length - 1];

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentVideo", MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("video", convertedImage);
                curentEdit.commit();

                Toast.makeText(getApplicationContext(), "Video Recorded " + convertedImage, Toast.LENGTH_LONG).show();

                UploadTask u = null;

                try {
                    u = new UploadTask(getContentResolver().openInputStream(selectedimg), convertedImage);
                    u.execute();
                } catch (FileNotFoundException e) {

                }
            } else if (requestCode == 202) {

                Uri selectedimg = data.getData();
                imageURI = getAbsolutePath(selectedimg);

                String[] imageArray = imageURI.split("/");

                int length = imageArray.length;
                String convertedImage = imageArray[length - 1];

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentImage", MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("Image", convertedImage);
                curentEdit.commit();
                UploadTask u = null;

                try {
                    u = new UploadTask(getContentResolver().openInputStream(selectedimg), convertedImage);
                    u.execute();
                } catch (FileNotFoundException e) {

                }
            } else if (requestCode == 97) {
                Uri selectedimg = data.getData();
                audioURI = getPath(selectedimg);
                String[] imageArray = audioURI.split("/");

                int length = imageArray.length;
                String convertedImage = imageArray[length - 1];

                SharedPreferences sharedPreferences = getSharedPreferences("CurrentAudio", Context.MODE_PRIVATE);
                SharedPreferences.Editor curentEdit = sharedPreferences.edit();
                curentEdit.putString("Audio", convertedImage);
                curentEdit.commit();

                UploadTask u = null;

                try {
                    u = new UploadTask(getContentResolver().openInputStream(selectedimg), convertedImage);
                    u.execute();
                } catch (FileNotFoundException e) {

                }
            }
        }
    }

    private class UploadTask extends AsyncTask<Void, Void, String> {
        InputStream f;
        String v;
        ProgressDialog pdForVideoUpload;

        @Override
        protected void onPreExecute() {
            pdForVideoUpload = new ProgressDialog(MainActivity.this);
            pdForVideoUpload.show();
            pdForVideoUpload.setTitle("Uploading....");
        }

        public UploadTask(InputStream file, String videoName) {
            f = file;
            v = videoName;
        }

        @Override
        protected String doInBackground(Void... voids) {
            //uploadFile(f);
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect(FTP_HOST, FTP_USER, FTP_PASS);
                boolean status = false;
                status = ftp.setWorkingDirectory("/makeindiakart.com/taskfiles");
                //InputStream targetStream = getResources().openRawResource(+R.drawable.ic_launcher);
                InputStream targetStream = f;
                ftp.uploadFile(targetStream, v);
                Log.e("Status", status + "");
                pdForVideoUpload.dismiss();
                return new String("Upload Successful");

            } catch (Exception e) {
                pdForVideoUpload.dismiss();
                String t = "Failure : " + e.getLocalizedMessage();
                return t;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }

    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        3);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.INTERNET)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.INTERNET},
                        4);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.RECORD_AUDIO)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        5);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;

        }
    }
}
