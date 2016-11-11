package com.mytask.taskmanager.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.mytask.taskmanager.Locations.AppLocationService;
import com.mytask.taskmanager.Locations.LocationAddress;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.util.AppUtil;
import com.mytask.taskmanager.util.DatePickerFragment;
import com.mytask.taskmanager.util.OnDateSetCompleted;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttendanceActivity extends AppCompatActivity {

    public static final String TAG = AttendanceActivity.class.getSimpleName();
    TextView mUserName, mDate, mTime, mFromDate, mToDate, mLatitude, mLongitude, mLocation;
    String UserName = "";
    private int month, day, year;
    private int seconds, minutes, hour;
    LinearLayout mLinearLayoutApplyleve, mLinearLayoutAbsent, mLinearLayoutPresent;
    TextView mPresent, mAbsent, mApplyleave;
    private GoogleApiClient mGoogleApiClient;
   ImageView imageView;
    String image;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    double currentLatitude;
    double currentLongitude;
    AppLocationService appLocationService;
    double latitude;
    double longitude;
    String locationAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        initPermissions();
        appLocationService = new AppLocationService(AttendanceActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image = PreferenceUtil.getInstance().getString(AttendanceActivity.this, "ProfileImage", "Image_5756.jpg");

        imageView = (ImageView)findViewById(R.id.profileImage) ;
        mUserName = (TextView) findViewById(R.id.Username);
        mDate = (TextView) findViewById(R.id.currentDate);
        mTime = (TextView) findViewById(R.id.currentTime);
        mFromDate = (TextView) findViewById(R.id.fromDate);
        mToDate = (TextView) findViewById(R.id.toDate);
        mApplyleave = (TextView) findViewById(R.id.applyleve);
        mAbsent = (TextView) findViewById(R.id.absent);
        mPresent = (TextView) findViewById(R.id.present);
        mLinearLayoutApplyleve = (LinearLayout) findViewById(R.id.applyLeve_linearlayout);
        mLinearLayoutAbsent = (LinearLayout) findViewById(R.id.absent_linearlayout);
        mLinearLayoutPresent = (LinearLayout) findViewById(R.id.present_linearlayout);
        mLongitude = (TextView) findViewById(R.id.getLongitude);
        mLocation = (TextView) findViewById(R.id.currentlocation);
        mLinearLayoutApplyleve.setVisibility(View.GONE);
        mLinearLayoutAbsent.setVisibility(View.GONE);
        mLinearLayoutPresent.setVisibility(View.GONE);


/*Date format*/
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = new Date();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String currentdate = ss.format(date);
/*Time Format*/
        SimpleDateFormat simpleDateFormat;
        final Calendar calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        hour = calander.get(Calendar.HOUR_OF_DAY);
        minutes = calander.get(Calendar.MINUTE);
        String time = simpleDateFormat.format(calander.getTime());


        UserName = PreferenceUtil.getInstance().getString(AttendanceActivity.this, "FirstName", "User");
        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        assert collapser != null;
        collapser.setExpandedTitleColor(Color.parseColor("#ffffff"));
        collapser.setTitle(UserName);

        Picasso.with(this)
                .load(ProjectVariables.IMAGE_PATH + image)
                .placeholder(R.drawable.amply_background)
                .error(R.drawable.amply_background)
                .resize(300, 300)
                .into(imageView);


        mUserName.setText(UserName);
        mDate.setText(currentdate);
        mTime.setText(time);
        mFromDate.setText(currentdate);
        mToDate.setText(currentdate);

        AppLocationService  gps = new AppLocationService(AttendanceActivity.this);
        if(gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            String result = "Latitude: " + latitude +
                   " Longitude: " + longitude;
            mLocation.setText(result);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(year, month, day, mFromDate);
                toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                    @Override
                    public void onDateCompleted(int year, int month, int day) {
                        AttendanceActivity.this.year = year;
                        AttendanceActivity.this.month = month;
                        AttendanceActivity.this.day = day;
                    }
                });
                toDatePickerFragment.show(AttendanceActivity.this.getSupportFragmentManager(), "1");
                mFromDate.setTextColor(Color.parseColor("#558B2F"));
                mFromDate.setTextSize(15);

            }
        });
        mToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment toDatePickerFragment = DatePickerFragment.newInstance(year, month, day, mToDate);
                toDatePickerFragment.setOnDateSetCompleted(new OnDateSetCompleted() {
                    @Override
                    public void onDateCompleted(int year, int month, int day) {
                        AttendanceActivity.this.year = year;
                        AttendanceActivity.this.month = month;
                        AttendanceActivity.this.day = day;
                    }
                });
                toDatePickerFragment.show(AttendanceActivity.this.getSupportFragmentManager(), "1");
                mToDate.setTextColor(Color.parseColor("#558B2F"));
                mToDate.setTextSize(15);
            }
        });


        mApplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinearLayoutAbsent.setVisibility(View.GONE);
                mLinearLayoutPresent.setVisibility(View.GONE);
                mLinearLayoutApplyleve.setVisibility(View.VISIBLE);
                mApplyleave.setTextColor(Color.parseColor("#ffffff"));
                mAbsent.setTextColor(Color.parseColor("#C8E6C9"));
                mPresent.setTextColor(Color.parseColor("#C8E6C9"));

            }
        });
        mAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinearLayoutAbsent.setVisibility(View.VISIBLE);
                mLinearLayoutPresent.setVisibility(View.GONE);
                mLinearLayoutApplyleve.setVisibility(View.GONE);
                mAbsent.setTextColor(Color.parseColor("#ffffff"));
                mApplyleave.setTextColor(Color.parseColor("#C8E6C9"));
                mPresent.setTextColor(Color.parseColor("#C8E6C9"));

            }
        });
        mPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinearLayoutAbsent.setVisibility(View.GONE);
                mLinearLayoutPresent.setVisibility(View.VISIBLE);
                mLinearLayoutApplyleve.setVisibility(View.GONE);
                mAbsent.setTextColor(Color.parseColor("#C8E6C9"));
                mApplyleave.setTextColor(Color.parseColor("#C8E6C9"));
                mPresent.setTextColor(Color.parseColor("#ffffff"));
                Location location = appLocationService
                        .getLocation();
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());
                } else {
                    showSettingsAlert();
                }

            }
        });
    }

    public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    AttendanceActivity.this);
            alertDialog.setTitle("SETTINGS");
            alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
            alertDialog.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            AttendanceActivity.this.startActivity(intent);
                        }
                    });
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.show();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.left_enter, R.anim.right_out);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            mLongitude.setText(String.valueOf(locationAddress));
        }
    }


    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(AttendanceActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AttendanceActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(AttendanceActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(AttendanceActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AttendanceActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(AttendanceActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        2);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


}


