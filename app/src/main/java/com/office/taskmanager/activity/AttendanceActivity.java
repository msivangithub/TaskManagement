package com.office.taskmanager.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.office.taskmanager.Locations.AppLocationService;
import com.office.taskmanager.Locations.LocationAddress;
import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPost;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.DatePickerFragment;
import com.office.taskmanager.util.OnDateSetCompleted;
import com.office.taskmanager.util.SharedPreferenceUtil;
import com.office.taskmanager.util.ProjectVariables;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttendanceActivity extends AppCompatActivity implements RestfulListener {

    public static final String TAG = AttendanceActivity.class.getSimpleName();
    TextView mUserName, mTime, mDate, mFromDate, mToDate, mLatitude, mLongitude, mLocation;
    String UserName = "";
    private int month, day, year;
    private int seconds, minutes, hour;
    LinearLayout mLinearLayoutApplyleve, mLinearLayoutAbsent, mLinearLayoutPresent;
    TextView mPresent, mAbsent, mApplyleave;
    TextView absentButton, leveButton;
    ImageView imageView;
    String image, address;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    double currentLatitude;
    double currentLongitude;
    AppLocationService appLocationService;
    double latitude;
    double longitude;
    String locationAddress;
    TextWatcher watcher;
    EditText writeLeve, writeAbsent;
    String getLeve, fromDate, toDate;
    RestfulListener listener;
    JSONObject jsonObject;
    String uid;
    String submitButton;
    JSONArray jsonArray;
    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        initPermissions();
        listener = AttendanceActivity.this;
        appLocationService = new AppLocationService(AttendanceActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uid = SharedPreferenceUtil.getInstance().getString(AttendanceActivity.this, "Uid", "Uid");
        image = SharedPreferenceUtil.getInstance().getString(AttendanceActivity.this, "ProfileImage", "Image_5756.jpg");
        UserName = SharedPreferenceUtil.getInstance().getString(AttendanceActivity.this, "FirstName", "User");

        imageView = (ImageView) findViewById(R.id.profileImage);
        mUserName = (TextView) findViewById(R.id.Username);
        mDate = (TextView) findViewById(R.id.currentDate);
        mTime = (TextView) findViewById(R.id.currentTime);
        mFromDate = (TextView) findViewById(R.id.fromDate);
        mToDate = (TextView) findViewById(R.id.toDate);
        mApplyleave = (TextView) findViewById(R.id.applyleve);
        mAbsent = (TextView) findViewById(R.id.absent);
        mPresent = (TextView) findViewById(R.id.present);
        writeAbsent = (EditText) findViewById(R.id.editext_absent);
        writeLeve = (EditText) findViewById(R.id.editext_leve);
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

        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        assert collapser != null;
        collapser.setExpandedTitleColor(Color.parseColor("#ffffff"));
        collapser.setTitle(UserName);
        // Glide.with(AttendanceActivity.this).load(ProjectVariables.IMAGE_PATH + image).into(imageView);
        try {
            Picasso.with(this)
                    .load(ProjectVariables.IMAGE_PATH + image)
                    .placeholder(R.drawable.amply_background)
                    .error(R.drawable.amply_background)
                    .resize(300, 300)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUserName.setText(UserName);
        mDate.setText(currentdate);
        mTime.setText(time);
        mFromDate.setText(currentdate);
        mToDate.setText(currentdate);
        try {
            AppLocationService gps = new AppLocationService(AttendanceActivity.this);
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                String result = "Latitude: " + latitude + " Longitude: " + longitude;
                mLocation.setText(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                submitButton = "Leave";
                mLinearLayoutAbsent.setVisibility(View.GONE);
                mLinearLayoutPresent.setVisibility(View.GONE);
                mLinearLayoutApplyleve.setVisibility(View.VISIBLE);
                mApplyleave.setTextColor(Color.parseColor("#000000"));
                mAbsent.setTextColor(Color.parseColor("#ffffff"));
                mPresent.setTextColor(Color.parseColor("#ffffff"));

                writeLeve.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }
        });
        mAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButton = "Absent";
                mLinearLayoutAbsent.setVisibility(View.VISIBLE);
                mLinearLayoutPresent.setVisibility(View.GONE);
                mLinearLayoutApplyleve.setVisibility(View.GONE);
                mAbsent.setTextColor(Color.parseColor("#000000"));
                mApplyleave.setTextColor(Color.parseColor("#ffffff"));
                mPresent.setTextColor(Color.parseColor("#ffffff"));
                writeAbsent = (EditText) findViewById(R.id.editext_absent);


                writeAbsent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });


            }
        });
        mPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButton = "Present";
                mLinearLayoutAbsent.setVisibility(View.GONE);
                mLinearLayoutPresent.setVisibility(View.GONE);
                mLinearLayoutApplyleve.setVisibility(View.GONE);
                mAbsent.setTextColor(Color.parseColor("#ffffff"));
                mApplyleave.setTextColor(Color.parseColor("#ffffff"));
                mPresent.setTextColor(Color.parseColor("#000000"));
                try {
                    Location location = appLocationService.getLocation();
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        LocationAddress locationAddress = new LocationAddress();
                        locationAddress.getAddressFromLocation(latitude, longitude,
                                getApplicationContext(), new GeocoderHandler());
                    } else {
                        showSettingsAlert();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                AttendanceActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
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
                break;
            case R.id.submit_upload:
                if (submitButton != null) {
                    if (validation() == submitButton.equalsIgnoreCase("Leave") && validation1() == submitButton.equalsIgnoreCase("Absent")) {
                        currentDate = mFromDate.getText().toString();
                        double lat = latitude;
                        double lon = longitude;
                        if (submitButton.equalsIgnoreCase("Leave")) {
                            getLeve = writeLeve.getText().toString();
                            fromDate = mFromDate.getText().toString();
                            toDate = mToDate.getText().toString();

                        } else if (submitButton.equalsIgnoreCase("Absent")) {
                            getLeve = writeAbsent.getText().toString();

                        } else if (submitButton.equalsIgnoreCase("Present")) {
                            address = String.valueOf(locationAddress);
                        }
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.accumulate(ProjectVariables.EMPID, uid);
                            jsonObject.accumulate(ProjectVariables.NAME, UserName);
                            jsonObject.accumulate(ProjectVariables.DATE, currentDate);
                            jsonObject.accumulate(ProjectVariables.TYPE, submitButton.toString());
                            jsonObject.accumulate(ProjectVariables.COMMENTS, getLeve.toString());
                            jsonObject.accumulate(ProjectVariables.LEAVEFROM, fromDate);
                            jsonObject.accumulate(ProjectVariables.LEAVETO, toDate);
                            jsonObject.accumulate(ProjectVariables.LATTITUDE, lat);
                            jsonObject.accumulate(ProjectVariables.LONGITUDE, lon);
                            jsonObject.accumulate(ProjectVariables.ADDRESS, address);
                            jsonObject.accumulate(ProjectVariables.MANAGERID, "3");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (AppUtil.isNetworkAvailable(AttendanceActivity.this)) {
                            try {
                                AsynHttpPost post = new AsynHttpPost(AttendanceActivity.this, 2, 123, ProjectVariables.TASK_ATTENDENCE, listener, jsonObject, "");
                                post.execute();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(AttendanceActivity.this, ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();

                        }
                    }
                    return true;
                }
        }
        return (super.onOptionsItemSelected(menuItem)

        );
    }

    private boolean validation1() {
        boolean valid = true;
        getLeve = writeAbsent.getText().toString();
        if (getLeve.isEmpty()) {
            writeAbsent.setError("Enter absent details");
            valid = false;
        } else {
            writeAbsent.setError(null);
        }
        return valid;
    }

    private boolean validation() {
        boolean valid = true;
        getLeve = writeLeve.getText().toString();
        if (getLeve.isEmpty()) {
            writeLeve.setError("Enter leave details");
            valid = false;
        } else {
            writeLeve.setError(null);
        }
        return valid;
    }


    @Override
    public void getData(String s, String status, int rType, String temp) {
        if (rType == 123) {
            if (status.equalsIgnoreCase("1")) {
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String result = jsonObject.getString("Result");
                    if (result.equalsIgnoreCase("inserted successfully")) {
                        Toast.makeText(AttendanceActivity.this, result, Toast.LENGTH_LONG).show();
                        writeLeve.setText("");
                        writeAbsent.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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


