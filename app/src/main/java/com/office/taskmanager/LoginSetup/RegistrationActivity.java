package com.office.taskmanager.LoginSetup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.office.taskmanager.R;
import com.office.taskmanager.services.AsynHttpPostRegister;
import com.office.taskmanager.services.RestfulListener;
import com.office.taskmanager.util.AlertDialogManager;
import com.office.taskmanager.util.AppUtil;
import com.office.taskmanager.util.JSONVariables;
import com.office.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity implements RestfulListener {

    EditText ETcompanyid, ETcompanyname, ETcityid, ETphonenumber, ETemailid;
    Button registerbutton;
    Button alredyusertext;
    private String IMEI;
    String regexstring = "^[0-9]$";
    RestfulListener listener;
    String email;
    boolean valid = true;
    CheckBox terms_conditions;
    String companyid, companyname, phonenumber, emailid, cityid;
    private static String URl;
    String result;
    JSONObject jsonObject;
    AlertDialogManager dialogManager = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initiliazePermissions();
        ETcompanyid = (EditText) findViewById(R.id.id_comapnyId);
        ETcompanyname = (EditText) findViewById(R.id.id_companyName);
        ETcityid = (EditText) findViewById(R.id.id_cityname);
        ETphonenumber = (EditText) findViewById(R.id.id_phonenumber);
        ETemailid = (EditText) findViewById(R.id.id_emailid);
        registerbutton = (Button) findViewById(R.id.id_register);
        alredyusertext = (Button) findViewById(R.id.alreadyuser);
        terms_conditions = (CheckBox) findViewById(R.id.terms_conditions);
        listener = this;

        //getting Android Device Id
        ProjectVariables.ANDROIDNO = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        registerbutton.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if (validation1()) {

                              if (!terms_conditions.isChecked() == false) {

                                  if (ProjectVariables.ANDROIDNO != null) {

                                      if (!(ProjectVariables.ANDROIDNO.trim().length() >= 1)) {

                                          Toast.makeText(RegistrationActivity.this, "Unable to get android id of your phone", Toast.LENGTH_LONG).show();
                                      }
                                      JSONObject obj = new JSONObject();
                                      try {
                                          obj.accumulate(JSONVariables.JCOMPANYID, ETcompanyid.getText().toString());
                                          obj.accumulate(JSONVariables.JCOMPNAME, ETcompanyname.getText().toString());
                                          obj.accumulate(JSONVariables.JPHONENO1, ETphonenumber.getText().toString());
                                          obj.accumulate(JSONVariables.JEMAILID, ETemailid.getText().toString());
                                          obj.accumulate(JSONVariables.JCITYID, ETcityid.getText().toString());
                                          obj.accumulate(JSONVariables.JANDROIDNO1, ProjectVariables.ANDROIDNO);
                                          obj.accumulate(JSONVariables.AAPPTYPE, ProjectVariables.TASKMANGER);
                                      } catch (Exception e) {
                                          e.printStackTrace();
                                      }
                                      if (AppUtil.isNetworkAvailable(RegistrationActivity.this)) {

                                          AsynHttpPostRegister post = new AsynHttpPostRegister(RegistrationActivity.this, 2, 0, ProjectVariables.REGISTER, listener, obj, "");
                                          post.execute();

                                      } else {
                                          dialogManager.showAlertDialog(RegistrationActivity.this, "Internet Connection Error !", ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, false);
                                      }
                                  }
                              } else {
                                  Toast.makeText(RegistrationActivity.this, "Please check terms and condition", Toast.LENGTH_LONG).show();
                              }
                          }
                      }
                  }

        );
        alredyusertext.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                  Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                  startActivity(intent);
                  finish();
              }
          }
        );
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
              /*  startActivity(new Intent(RegistrationActivity.this, HomeScreen.class));
                finish();*/
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void getData(String s, String status, int rType,String temp) {

        if (status.equalsIgnoreCase("1")) {
            try {
                ProjectVariables.EMAILID = ETemailid.getText().toString();
                ProjectVariables.PHONENO1 = ETphonenumber.getText().toString();
                JSONArray Response = new JSONArray(s);
                JSONObject registerResponse = Response.getJSONObject(0);
                //String result = loginResponse.getString("Result");
                String result = registerResponse.getString(JSONVariables.JRESULT);
                String fail = registerResponse.getString(JSONVariables.JREMARKS);

                ProjectVariables.PHOTP = registerResponse.getString(JSONVariables.JPHOTP);
                ProjectVariables.CLIENTID = registerResponse.getString(JSONVariables.JCLIENTID);

                if (result.equalsIgnoreCase("Successful")) {
                    Toast.makeText(RegistrationActivity.this, result, Toast.LENGTH_LONG).show();
                    Intent otp = new Intent(RegistrationActivity.this, OtpConfirmationActivity.class);
                    startActivity(otp);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, fail, Toast.LENGTH_LONG).show();
                }


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(RegistrationActivity.this, " " + s, Toast.LENGTH_LONG).show();
        }

    }


    private boolean validation1() {
        boolean valid = true;
        companyid = ETcompanyid.getText().toString();
        companyname = ETcompanyname.getText().toString();
        phonenumber = ETphonenumber.getText().toString();
        emailid = ETemailid.getText().toString();
        cityid = ETcityid.getText().toString();

        if (companyid.isEmpty()) {
            ETcompanyid.setError("Enter valid CompanyId");
            valid = false;
        } else {
            ETcompanyid.setError(null);
        }

        if (companyname.isEmpty()) {
            ETcompanyname.setError("Enter valid Company Name");
            valid = false;
        } else {
            ETcompanyname.setError(null);
        }
        if (phonenumber.isEmpty() || phonenumber.length() < 10) {
            ETphonenumber.setError("Enter valid Phone Number");
            valid = false;
        } else {
            ETphonenumber.setError(null);
        }
        if (emailid.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
            ETemailid.setError("Enter a valid email address");
            valid = false;
        } else {
            ETemailid.setError(null);
        }

        if (cityid.isEmpty()) {
            ETcityid.setError("Enter City");
            valid = false;
        } else {
            ETcityid.setError(null);
        }

        return valid;
    }


    private void initiliazePermissions() {

        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
       /* if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.INTERNET}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 3);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, 4);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 6);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.BLUETOOTH)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.BLUETOOTH}, 7);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.BLUETOOTH_ADMIN)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.BLUETOOTH_ADMIN)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 8);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(RegistrationActivity.this,
                        new String[]{Manifest.permission.CAMERA}, 9);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }*/

    }
}

