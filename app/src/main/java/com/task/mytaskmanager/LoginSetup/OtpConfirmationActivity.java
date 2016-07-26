package com.task.mytaskmanager.LoginSetup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.util.ProjectVariables;


public class OtpConfirmationActivity extends AppCompatActivity implements View.OnClickListener {

    TextView OTPPhonenumber, OTPEmail, PhoneOtpConfirmation, EmailOtpConfirmation;
    TextView phonetext,phonecolon,emailtext,emailcolon;
    EditText PhoneOTPNumber, EmailOTPNumber;
    ImageButton PhoneOTPButton, EmailOTPButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpconfirmation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialization Values

        OTPPhonenumber = (TextView) findViewById(R.id.id_otpmobilenumber);
        OTPEmail = (TextView) findViewById(R.id.id_OtpEmailid);
        PhoneOtpConfirmation = (TextView) findViewById(R.id.id_phoneOtpConfirmation);
        EmailOtpConfirmation = (TextView) findViewById(R.id.id_emailOtpConfirmation);

        phonetext=(TextView)findViewById(R.id.id_phonetext);
        phonecolon=(TextView)findViewById(R.id.id_phonecolon);

        emailtext=(TextView)findViewById(R.id.id_emailtext);
        emailcolon=(TextView)findViewById(R.id.id_emailcolon);

        PhoneOTPNumber = (EditText) findViewById(R.id.id_phoneOTPNumber);
        EmailOTPNumber = (EditText) findViewById(R.id.id_emailOTpNumber);

        PhoneOTPButton = (ImageButton) findViewById(R.id.id_phoneOTPButton);
        EmailOTPButton = (ImageButton) findViewById(R.id.id_emailOTPButton);

        //Clicking Events

        PhoneOTPButton.setOnClickListener(this);
        EmailOTPButton.setOnClickListener(this);

        //setting Values To feilds

        OTPPhonenumber.setText(ProjectVariables.PHONENO1);
        OTPEmail.setText(ProjectVariables.JEMAILID);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.id_phoneOTPButton:
                phonetext.setVisibility(View.GONE);
                phonecolon.setVisibility(View.GONE);
                PhoneOTPNumber.setVisibility(View.GONE);
                PhoneOTPButton.setVisibility(View.GONE);

                PhoneOtpConfirmation.setVisibility(View.VISIBLE);
                PhoneOtpConfirmation.setText("Phone Verified Successfully");
                break;

            case R.id.id_emailOTPButton:
                emailtext.setVisibility(View.GONE);
                emailcolon.setVisibility(View.GONE);
                EmailOTPButton.setVisibility(View.GONE);
                EmailOTPNumber.setVisibility(View.GONE);

                EmailOtpConfirmation.setVisibility(View.VISIBLE);
                EmailOtpConfirmation.setText("Email Verified Successfully");

                break;

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
