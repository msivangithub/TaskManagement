package com.office.taskmanager.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.office.taskmanager.R;
import com.office.taskmanager.util.ProjectVariables;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mEmail, mFname, mPhone, mCity;
    ImageButton mCall;
    String email = "";
    String image = "";
    String fname = "";
    String phone = "";
    String city = "";
    public static final String EMAIL = "name";
    public static final String IMAGE = "image";
    public static final String FNAME = "fname";
    public static final String PHONE = "phone";
    public static final String CITY = "city";

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initPermissions();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (ImageView) findViewById(R.id.profileImage);
        mEmail = (TextView) findViewById(R.id.email);
        mFname = (TextView) findViewById(R.id.fname);
        mPhone = (TextView) findViewById(R.id.phone);
        mCity = (TextView) findViewById(R.id.city);
        mCall = (ImageButton) findViewById(R.id.calling);
        mCall.setOnClickListener(this);

        email = getIntent().getStringExtra(EMAIL);
        image = getIntent().getStringExtra(IMAGE);
        fname = getIntent().getStringExtra(FNAME);
        phone = getIntent().getStringExtra(PHONE);
        city = getIntent().getStringExtra(CITY);

        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapser.setExpandedTitleColor(Color.parseColor("#ffffff"));
        collapser.setTitle(fname);

        mEmail.setText(email);
        mPhone.setText(phone);
        mCity.setText(city);
        mFname.setText(fname);
        try {
            Glide.with(this).load(ProjectVariables.IMAGE_PATH + image)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  Picasso.with(this)
                .load(ProjectVariables.IMAGE_PATH + image)
                .placeholder(R.drawable.amply_background)   // optional
                .error(R.drawable.amply_background)      // optional
                .resize(300, 300)
                .into(imageView);*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_email);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    Intent emailID = new Intent(UserActivity.this, ComposeEmailActivity.class);
                    emailID.putExtra(EMAIL, email);
                    startActivity(emailID);
                }
            });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calling:
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + phone;
                myIntent.setData(Uri.parse(phNum));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(myIntent);
                 break;
         }
    }

    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(UserActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(UserActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
