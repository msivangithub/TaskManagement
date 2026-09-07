package com.office.taskmanager.LoginSetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.office.taskmanager.R;
import com.office.taskmanager.services.AppNetworkCall;
import com.office.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OtpConfirmationActivity extends AppCompatActivity {
    ProjectVariables projectVariables = new ProjectVariables();

    TextView OTPPhonenumber, OTPEmail, PhoneOtpConfirmation, EmailOtpConfirmation;
    TextView phonetext, phonecolon, emailtext, emailcolon;
    EditText phoneotpnumber, EmailOTPNumber;
    //ImageButton PhoneOTPButton, EmailOTPButton;
    Button mSubmit;
    String otp;
    private static String URl;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp1);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("  OTP  ");
        OTPPhonenumber = (TextView) findViewById(R.id.id_otpmobilenumber);
        mSubmit = (Button) findViewById(R.id.otp_Button);
        phoneotpnumber = (EditText) findViewById(R.id.otp_editext);
        OTPPhonenumber.setText(projectVariables.PHONENO1);
        // projectVariables.CLIENTID;
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = phoneotpnumber.getText().toString();
                if (otp.equals(ProjectVariables.PHOTP)) {
                    URl = ProjectVariables.getSendSms(ProjectVariables.CLIENTID);
                    AppNetworkCall networkCall = new AppNetworkCall(OtpConfirmationActivity.this, URl) {
                        @Override
                        public void onComplete(JSONArray array) {
                            updataresponse(array);
                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            super.onFailure(errorMsg);
                        }
                    };
                    networkCall.execute();


                } else {
                    Toast.makeText(OtpConfirmationActivity.this, "Please Check OTP", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updataresponse(JSONArray array) {

        try {
            jsonObject = array.getJSONObject(0);

            if (jsonObject.getString("Result").equalsIgnoreCase("SmsSent")) {
                Toast.makeText(OtpConfirmationActivity.this, jsonObject.getString("Result"), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(OtpConfirmationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                finish();
                return true;

        }
        return (super.onOptionsItemSelected(menuItem));
    }
}


