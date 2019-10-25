package com.example.amal.mybabyhealthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;

import com.example.amal.mybabyhealthcare.account.LoginActivity;
import com.example.amal.mybabyhealthcare.main_navigation.MainActivity;

import pref_managers.UserSessionManager;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        final UserSessionManager session = new UserSessionManager(LauncherActivity.this);

        Thread sleeping = new Thread() {
            public void run()
            {
                try {
                    // Thread will sleep for 2 seconds
                    sleep(2 * 1000);

                    if (session.isLoggedIn())
                    {
                        Intent i = new Intent(LauncherActivity.this, MainActivity.class);
                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(LauncherActivity.this, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                        startActivity(i, bundle);
                    }
                    else
                    {
                        Intent i = new Intent(LauncherActivity.this, LoginActivity.class);
                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(LauncherActivity.this, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                        startActivity(i, bundle);
                    }

                    //Remove activity
                    finish();

                } catch (Exception e) {
                    Log.e(LauncherActivity.class.getName(), e.getMessage());
                }
            }
        };

        sleeping.start();
    }
}
