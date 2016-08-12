package com.task.mytaskmanager.LoginSetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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


        if (AppUtil.isNetworkAvailable(LoginActivity.this)) {
            userText = username.getText().toString();
            passText = password.getText().toString();

            if ((!userText.isEmpty() && !userText.equalsIgnoreCase("")) && (!passText.isEmpty() && !passText.equalsIgnoreCase(""))) {
                try {

                    JSONObject loginObject = new JSONObject();
                    loginObject.accumulate(ProjectVariables.LOGIN_PHONE, userText);
                    loginObject.accumulate(ProjectVariables.PASSWORD, passText);

                    AsynHttpPost post = new AsynHttpPost(LoginActivity.this, 1, 0, ProjectVariables.LOGIN, LoginActivity.this, loginObject, "");
                    post.execute();
                } catch (Exception e) {

                }

            } else {
                Toast.makeText(LoginActivity.this, "Please enter username or password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, ProjectVariables.PLEASE_CHECK_YOUR_NETWORK_CONNECTION, Toast.LENGTH_LONG).show();
        }
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
                ProjectVariables.JRESULT = jsonObject.getString(ProjectVariables.RESULT);
                ProjectVariables.JREMARKS = jsonObject.getString(ProjectVariables.REMARKS);
                if (ProjectVariables.JRESULT.equals("Sucess")) {
                    util.saveString(LoginActivity.this, ProjectVariables.USERLOGINID, loginUserId);
                    util.saveString(LoginActivity.this, "MailID", mailId);
                    util.saveString(LoginActivity.this, "FirstName", UserName);
                   // Toast.makeText(getApplicationContext(), jsonObject.getString(ProjectVariables.USER_ROLE), Toast.LENGTH_LONG).show();
                    util.saveString(LoginActivity.this, ProjectVariables.USER_ROLE, jsonObject.getString(ProjectVariables.USER_ROLE));
                    util.saveString(LoginActivity.this, ProjectVariables.STATUS, ProjectVariables.LOGGED_IN);
                    util.saveString(LoginActivity.this, ProjectVariables.USERNAME, userText);

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
}
