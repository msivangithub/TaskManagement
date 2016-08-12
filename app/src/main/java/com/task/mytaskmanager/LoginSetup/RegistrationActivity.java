package com.task.mytaskmanager.LoginSetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.task.mytaskmanager.R;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements RestfulListener {


    EditText ETcompanyID, ETcompanyname, ETbranchname, ETaddrs1, ETaddrs2, ETaddrs3, ETcityid, ETphonenumber, ETemailid;
    Button registerbutton ,alredyusertext;

    String customer, company, branch, add1, add2, add3, city, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ETcompanyID = (EditText) findViewById(R.id.id_comapnyId);
        ETcompanyname = (EditText) findViewById(R.id.id_companyName);
        ETcityid = (EditText) findViewById(R.id.id_cityname);
        ETphonenumber = (EditText) findViewById(R.id.id_phonenumber);
        ETemailid = (EditText) findViewById(R.id.id_emailid);
        registerbutton = (Button) findViewById(R.id.id_register);
        alredyusertext = (Button) findViewById(R.id.alreadyuser);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer = ETcompanyID.getText().toString().trim();
                company = ETcompanyname.getText().toString().trim();


                city = ETcityid.getText().toString().trim();
                phone = ETphonenumber.getText().toString().trim();
                email = ETemailid.getText().toString().trim();
                if (AppUtil.isNetworkAvailable(RegistrationActivity.this)) {
                    if (ETcompanyID.getText().toString().trim().length() == 0) {
                        ETcompanyID.setError("Enter company id");
                        ETcompanyID.requestFocus();

                    } else if (ETcompanyname.getText().toString().trim().length() == 0) {
                        ETcompanyname.setError("Enter Comapny Name");
                        ETcompanyname.requestFocus();

                    } else if (ETphonenumber.getText().toString().trim().length() == 0 || ETphonenumber.getText().toString().length() < 10 || ETphonenumber.getText().toString().length() > 10) {
                        ETphonenumber.setError("Enter Valid Phone Number");
                        ETphonenumber.requestFocus();

                    } else if (ETemailid.getText().toString().trim().length() == 0) {
                        ETemailid.setError("Enter Email Id");
                        ETemailid.requestFocus();

                    } else if (!isValidEmail(ETemailid.getText().toString().trim())) {
                        ETemailid.setError("Invlid Email Id");
                        ETemailid.requestFocus();
                    } else if (ETcityid.getText().toString().trim().length() == 0) {
                        ETcityid.setError("Enter City Name");
                        ETcityid.requestFocus();

                    }

                    //Here we can call listener for calling webservice
                    JSONObject obj = new JSONObject();
                    try {
                        obj.accumulate(ProjectVariables.COMPANYID, ETcompanyID.getText().toString());
                        obj.accumulate(ProjectVariables.COMPNAME, ETcompanyname.getText().toString());
                        obj.accumulate(ProjectVariables.CITYID, ETcityid.getText().toString());
                        obj.accumulate(ProjectVariables.PHONENUBER, ETphonenumber.getText().toString());
                        obj.accumulate(ProjectVariables.EMAILID, ETemailid.getText().toString());
                        obj.accumulate(ProjectVariables.ANDROIDNO1, "1254788");
                    }catch (Exception e){

                    }
                    AsynHttpPost post = new AsynHttpPost(RegistrationActivity.this,2,0,ProjectVariables.REGISTER,RegistrationActivity.this,obj,"");
                    post.execute();


                } else {
                    Toast.makeText(RegistrationActivity.this, ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();
                }

            }


        });
        alredyusertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    //Email Validation
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void getData(String s, String status, int rType) {
        if (status.equalsIgnoreCase("1"))
        {
            try{
                ProjectVariables.JEMAILID= ETemailid.getText().toString();
                ProjectVariables.PHONENO1=ETphonenumber.getText().toString();
                JSONArray Response = new JSONArray(s);
                JSONObject loginResponse = Response.getJSONObject(0);
                String result = loginResponse.getString(ProjectVariables.RESULT);
                if (result.equalsIgnoreCase("inserted successfully")) {

                    Intent otp = new Intent(RegistrationActivity.this, OtpConfirmationActivity.class);
                    startActivity(otp);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, s, Toast.LENGTH_LONG).show();
                }


            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(RegistrationActivity.this, " " + s, Toast.LENGTH_LONG).show();
        }
    }
}
