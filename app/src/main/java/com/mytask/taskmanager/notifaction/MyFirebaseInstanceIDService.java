package com.mytask.taskmanager.notifaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mytask.taskmanager.services.RestfulListener;
import com.mytask.taskmanager.util.PreferenceUtil;

//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    PreferenceUtil util;
    private static final String TAG = "MyFirebaseIIDService";
    Context context;
    private static String status_regID ="0";
    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        // Save to Sharedreferences
        editor.putString("registration_id", token);
        editor.apply();

       // PreferenceUtil.getInstance().saveString(context, "currentToken", token);
        Log.d(TAG, "Token: " + token);
    }

}