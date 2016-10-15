package com.mytask.taskmanager.LoginSetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.mytask.taskmanager.Pojo.UserRoles;
import com.mytask.taskmanager.R;
import com.mytask.taskmanager.activity.MainActivity;
import com.mytask.taskmanager.services.AsynHttpPost;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.AlertDialogManager;
import com.mytask.taskmanager.util.AppUtil;
import com.mytask.taskmanager.util.PreferenceUtil;
import com.mytask.taskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RestfulListener {

    private static final String TAG = "LoginActivity";
    private static TextView forgotPassword;
    private static CheckBox show_hide_password;
    private EditText phoneNo, password;
    private Button loginButton, signUp;
    TextView newusersignup;
    String userPhone, passText;
    PreferenceUtil util;

    UserRoles roles = new UserRoles();
    AlertDialogManager dialogManager = new AlertDialogManager();
    CheckBox rember;
    String defaultRembere;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // defaultRembere = preferences.getString("remberpass", null);
/*
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }*/
        util = PreferenceUtil.getInstance();
        initViews();
        setListeners();
    }

    private void setListeners() {
        rember.setOnClickListener(this);
        signUp.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {
                // If it is checkec then show password else hide
                // password
                if (isChecked) {
                    show_hide_password.setText(R.string.hide_pwd);// change
                    // checkbox
                    // text
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    show_hide_password.setText(R.string.show_pwd);// change
                    // checkbox
                    // text
                    password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password
                }

            }
        });
    }


    private void initViews() {
        rember = (CheckBox) findViewById(R.id.remember);
        String defaultUserName = "";
        final PreferenceUtil util = PreferenceUtil.getInstance();
        defaultUserName = util.getString(LoginActivity.this, ProjectVariables.USERNAME, "0");
        phoneNo = (EditText) findViewById(R.id.login_emailid);
        if (!defaultUserName.equalsIgnoreCase("0")) {
            phoneNo.setText(defaultUserName);
        }
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.loginBtn);

        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        signUp = (Button) findViewById(R.id.createAccount);
        rember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    defaultRembere = util.getString(LoginActivity.this, ProjectVariables.PASSWORD, "0");
                    if (!defaultRembere.equalsIgnoreCase("0")) {
                        password.setText(String.valueOf(defaultRembere));
                    }
                    }else {
                    password.setText("");

                }
            }
        });

        show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {
                // If it is checkec then show password else hide
                // password
                if (isChecked) {
                    show_hide_password.setText(R.string.hide_pwd);// change
                    // checkbox
                    // text
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    show_hide_password.setText(R.string.show_pwd);// change
                    // checkbox
                    // text
                    password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.createAccount:
                Intent creatAc = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(creatAc);
                break;

            case R.id.forgot_password:
                Intent forgotPs = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPs);
                break;

            case R.id.loginBtn:

                getLogin();

        }


    }


    public void getLogin() {

      /* // if (validation()) {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        // [END subscribe_topics]
        // Log and toast
        String msg = getString(R.string.registered_emailid);
        Log.d(TAG, msg);
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();*/
        userPhone = phoneNo.getText().toString();
        passText = password.getText().toString();
        if ((!userPhone.isEmpty() && !userPhone.equalsIgnoreCase("")) && (!passText.isEmpty() && !passText.equalsIgnoreCase(""))) {
            try {

                JSONObject loginObject = new JSONObject();
                loginObject.accumulate(ProjectVariables.LOGIN_PHONE, userPhone);
                loginObject.accumulate(ProjectVariables.PASSWORD, passText);

                if (AppUtil.isNetworkAvailable(LoginActivity.this)) {

                    AsynHttpPost post = new AsynHttpPost(LoginActivity.this, 1, 0, ProjectVariables.LOGIN, LoginActivity.this, loginObject, "");
                    post.execute();

                } else {
                    ToastMesseg();
                }
            } catch (Exception e) {
            }
        } else {

            Toast.makeText(LoginActivity.this, "Please enter username & password", Toast.LENGTH_LONG).show();

        }

    }

    private void ToastMesseg() {
        dialogManager.showAlertDialog(LoginActivity.this, "Error Connection....!", "Please Check Your Network Connection", false);

       /* LayoutInflater inflater = getLayoutInflater();
        View toastlayout = inflater.inflate(R.layout.toast_network_connection, (ViewGroup) findViewById(R.id.custom_toast_layout));
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastlayout);
        toast.show();*/
    }

    @Override
    public void getData(String s, String status, int rType) {
        if (status.equalsIgnoreCase("1")) {

            try {
                JSONArray Response = new JSONArray(s);
                JSONObject jsonObject = Response.getJSONObject(0);
                String loginUserId = jsonObject.getString(ProjectVariables.UID);
                String mailId = jsonObject.getString("MailID");
                String UserName = jsonObject.getString("FirstName");
                String fromUid = jsonObject.getString(ProjectVariables.UID);
                String Image = jsonObject.getString("Image");
                String city = jsonObject.getString("City");
                String phone = jsonObject.getString("PhoneNo");
                String Compname = jsonObject.getString("Compname");
                String BranchName = jsonObject.getString("BranchName");
                String uid = jsonObject.getString("Uid");
                ProjectVariables.JRESULT = jsonObject.getString(ProjectVariables.RESULT);
                ProjectVariables.JREMARKS = jsonObject.getString(ProjectVariables.REMARKS);
                if (ProjectVariables.JRESULT.equals("Sucess")) {
                    util.saveString(LoginActivity.this, ProjectVariables.USERLOGINID, loginUserId);
                    util.saveString(LoginActivity.this, "MailID", mailId);
                    util.saveString(LoginActivity.this, "FirstName", UserName);
                    util.saveString(LoginActivity.this, "Uid", fromUid);
                    // Toast.makeText(getApplicationContext(), jsonObject.getString(ProjectVariables.USER_ROLE), Toast.LENGTH_LONG).show();
                    util.saveString(LoginActivity.this, ProjectVariables.USER_ROLE, jsonObject.getString(ProjectVariables.USER_ROLE));
                    util.saveString(LoginActivity.this, ProjectVariables.STATUS, ProjectVariables.LOGGED_IN);
                    util.saveString(LoginActivity.this, ProjectVariables.USERNAME, userPhone);
                    util.saveString(LoginActivity.this, "ProfileImage", Image);
                    util.saveString(LoginActivity.this, "PhoneNo", phone);
                    util.saveString(LoginActivity.this, ProjectVariables.PASSWORD, passText);
                    util.saveString(LoginActivity.this, "City", city);
                    util.saveString(LoginActivity.this, ProjectVariables.COMPNAME, jsonObject.getString(ProjectVariables.COMPNAME));
                    util.saveString(LoginActivity.this, "BranchName", BranchName);
                    util.saveString(LoginActivity.this, "Uid", uid);

                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, ProjectVariables.JRESULT, Toast.LENGTH_LONG).show();
                    startActivity(mainActivity);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, ProjectVariables.JREMARKS, Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }

        } else {
            dialogManager.showAlertDialog(LoginActivity.this, "Error Connection....!", "Unable to Response from server", false);
            //Toast.makeText(LoginActivity.this, "Unable to Response from server ", Toast.LENGTH_LONG).show();
        }
    }


    private boolean validation() {
        boolean valid = true;
        boolean result = true;
        boolean validEmailIdFlag = false;
        boolean emailFlag = false;
        boolean passFlag = false;
        phoneNo = (EditText) findViewById(R.id.login_emailid);

        passText = password.getText().toString();
        userPhone = phoneNo.getText().toString();

        if (userPhone.isEmpty() || userPhone.length() < 10) {
            phoneNo.setError("Enter valid Phone Number");
            result = false;
        } else {
            phoneNo.setError(null);
        }

        /*if (userText != null) {
            if (userText.trim().equalsIgnoreCase("")) {
                phoneNo.setError("Email  is empty");
                emailFlag = false;
            } else {
                phoneNo.setError(null);
                emailFlag = true;
            }
        } else {
            phoneNo.setError("Email  is empty");
            emailFlag = false;
        }*/
        if (passText != null) {
            if (passText.trim().equalsIgnoreCase("")) {
                password.setError("Password  is empty");
                passFlag = false;
            } else {
                password.setError(null);
                passFlag = true;
            }
        } else {
            password.setError("Password  is empty");
            passFlag = false;
        }
      /*  if (userText != null) {
            if (!isValidMail(userText)) {
                phoneNo.setError("Enter valid email Id");
                validEmailIdFlag = false;
            } else {
                phoneNo.setError(null);
                validEmailIdFlag = true;
            }
        } else {
            phoneNo.setError(" email is empty");
            validEmailIdFlag = false;
        }*/
        if (validEmailIdFlag && validEmailIdFlag) {
            result = true;
        } else {
            result = false;
        }
        return result;

    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
