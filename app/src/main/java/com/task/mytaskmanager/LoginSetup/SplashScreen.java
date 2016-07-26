package com.task.mytaskmanager.LoginSetup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.task.mytaskmanager.R;
import com.task.mytaskmanager.fragmentssss.MainActivity;
import com.task.mytaskmanager.util.PreferenceUtil;
import com.task.mytaskmanager.util.ProjectVariables;


/**
 * Created by MANJU on 27-05-2016.
 */
public class SplashScreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
       StartAnimations();

    }


    private void StartAnimations() {

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);


        Thread timerThread = new Thread() {

            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    PreferenceUtil prefs = PreferenceUtil.getInstance();
                    String pstatus = prefs.getString(SplashScreen.this, ProjectVariables.STATUS, "default");


                    if (pstatus.equalsIgnoreCase(ProjectVariables.LOGGED_IN)) {
                        Intent i = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(i);

                    } else {
                        Intent i = new Intent(SplashScreen.this,LoginActivity.class);
                        startActivity(i);
                    }

                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
