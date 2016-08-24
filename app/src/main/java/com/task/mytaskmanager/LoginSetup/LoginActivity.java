package com.task.mytaskmanager.LoginSetup;

import android.content.Intent;
import android.os.Bundle;
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


import com.task.mytaskmanager.Pojo.UserRoles;
import com.task.mytaskmanager.R;
import com.task.mytaskmanager.fragmentssss.MainActivity;
import com.task.mytaskmanager.services.AsynHttpPost;
import com.task.mytaskmanager.services.RestfulListener;
import com.task.mytaskmanager.util.AppUtil;
import com.task.mytaskmanager.util.PreferenceUtil;
import com.task.mytaskmanager.util.ProjectVariables;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RestfulListener {


    private static TextView forgotPassword;
    private static CheckBox show_hide_password;
    private EditText username, password;
    private Button loginButton, signUp;
    TextView newusersignup;
    String userText, passText;
    PreferenceUtil util;
    UserRoles roles = new UserRoles();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        util = PreferenceUtil.getInstance();
        initViews();
        setListeners();
    }

    private void setListeners() {
        signUp.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }


    private void initViews() {
        String defaultUserName = "";
        PreferenceUtil util = PreferenceUtil.getInstance();
        defaultUserName = util.getString(LoginActivity.this, ProjectVariables.USERNAME, "0");
        username = (EditText) findViewById(R.id.login_emailid);
        if (!defaultUserName.equalsIgnoreCase("0")) {
            username.setText(defaultUserName);
        }
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.loginBtn);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        signUp = (Button) findViewById(R.id.createAccount);
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

                break;
        }

    }


    public void getLogin() {

      //  if (validation()) {

            userText = username.getText().toString();
            passText = password.getText().toString();
            if ((!userText.isEmpty() && !userText.equalsIgnoreCase("")) && (!passText.isEmpty() && !passText.equalsIgnoreCase(""))) {
                try {

                    JSONObject loginObject = new JSONObject();
                    loginObject.accumulate(ProjectVariables.LOGIN_PHONE, userText);
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
        LayoutInflater inflater = getLayoutInflater();
        View toastlayout = inflater.inflate(R.layout.toast_network_connection, (ViewGroup)findViewById(R.id.custom_toast_layout));
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastlayout);
        toast.show();
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
                String fromUid = jsonObject.getString("Uid");
                String Image = jsonObject.getString("Image");
                ProjectVariables.JRESULT = jsonObject.getString(ProjectVariables.RESULT);
                ProjectVariables.JREMARKS = jsonObject.getString(ProjectVariables.REMARKS);
                if (ProjectVariables.JRESULT.equals("Sucess")) {
                    util.saveString(LoginActivity.this, ProjectVariables.USERLOGINID, loginUserId);
                    util.saveString(LoginActivity.this, "MailID", mailId);
                    util.saveString(LoginActivity.this, "FirstName", UserName);
                    util.saveString(LoginActivity.this, "Uid",fromUid);
                    // Toast.makeText(getApplicationContext(), jsonObject.getString(ProjectVariables.USER_ROLE), Toast.LENGTH_LONG).show();
                    util.saveString(LoginActivity.this, ProjectVariables.USER_ROLE, jsonObject.getString(ProjectVariables.USER_ROLE));
                    util.saveString(LoginActivity.this, ProjectVariables.STATUS, ProjectVariables.LOGGED_IN);
                    util.saveString(LoginActivity.this, ProjectVariables.USERNAME, userText);
                    util.saveString(LoginActivity.this, "ProfileImage", Image);

                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, ProjectVariables.JRESULT, Toast.LENGTH_LONG).show();
                    startActivity(mainActivity);
                } else {
                    Toast.makeText(LoginActivity.this, ProjectVariables.JREMARKS, Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }


        } else {
            Toast.makeText(LoginActivity.this, "Response from server " + s, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validation() {
        boolean result = false;
        boolean validEmailIdFlag = false;
        boolean emailFlag = false;
        boolean passFlag = false;
        username = (EditText) findViewById(R.id.login_emailid);

        passText = password.getText().toString();
        userText = username.getText().toString();

        if (userText != null) {
            if (userText.trim().equalsIgnoreCase("")) {
                username.setError("Email  is empty");
                emailFlag = false;
            } else {
                username.setError(null);
                emailFlag = true;
            }
        } else {
            username.setError("Email  is empty");
            emailFlag = false;
        }
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
        if (userText != null) {
            if (!isValidMail(userText)) {
                username.setError("Enter valid email Id");
                validEmailIdFlag = false;
            } else {
                username.setError(null);
                validEmailIdFlag = true;
            }
        } else {
            username.setError(" email is empty");
            validEmailIdFlag = false;
        }
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
