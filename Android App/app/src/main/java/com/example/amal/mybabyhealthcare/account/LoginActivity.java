package com.example.amal.mybabyhealthcare.account;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.main_navigation.MainActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import custom_interfaces.CallBack;
import model.User;
import model.Vaccination;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import services.BaseVaccinationNotifyService;
import sqlite_database_handeler.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn, reg_btn;
    private EditText input_email, input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onInit();
    }

    private void onInit()
    {
        login_btn = (Button) findViewById(R.id.btnLogin);
        reg_btn = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        input_email = (EditText) findViewById(R.id.email);
        input_password = (EditText) findViewById(R.id.password);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_login();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_registrationLink();
            }
        });

        db = new DatabaseHelper(this);
    }

    private void onClick_login()
    {
        if(validate()) {
            Map<String, String> map = new HashMap<>();
            map.put("email", input_email.getText().toString());
            map.put("password", input_password.getText().toString());
            final JSONObject user = new JSONObject(map);

            final APIManager api = new APIManager(APITypes.LOGIN);
            api.sendRequest(user, new CallBack() {
                @Override
                public void onResponse() {
                    if (api.query_status) {
                        User user = api.loggedin_user;
                        //Log.v("amal_user", user.toString());
//                     if(user.pic != null && !user.pic.isEmpty())
//                     {
//                         ImageView temp_image = (ImageView)findViewById(R.id.temp_img);
//                         ImagesManager.loadNormalImageView(AppConfig.URL_USER_AVATAR + user.pic, temp_image);
//                         UserSessionManager session = new UserSessionManager(LoginActivity.this);
//                         session.createLoginSession(user, temp_image);
//                     }
//                     else {
                        UserSessionManager session = new UserSessionManager(LoginActivity.this);
                        session.createLoginSession(user);
//                     }

                        //getVs();

                        //test
//                     Calendar calendar = Calendar.getInstance();
//
//                     calendar.set(Calendar.HOUR_OF_DAY, 17); // For 1 PM or 2 PM
//                     calendar.set(Calendar.MINUTE, 12);
//                     calendar.set(Calendar.SECOND, 0);
//                     PendingIntent pi = PendingIntent.getService(getApplicationContext(), 0,
//                             new Intent(getApplicationContext(), BaseVaccinationNotifyService.class), PendingIntent.FLAG_UPDATE_CURRENT);
//                     AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                     am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                             AlarmManager.INTERVAL_DAY, pi);


                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(LoginActivity.this, api.message, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void onClick_registrationLink()
    {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    ArrayList<Vaccination> Vs;
    DatabaseHelper db;
    private void getVs() {
        Vs = new ArrayList<>();
        final APIManager api = new APIManager(APITypes.GET_BASE_VACCINATIONS);
        api.sendRequest(new JSONObject(), new CallBack() {
            @Override
            public void onResponse() {
                if (api.get_base_vaccinations_status) {
                    Vs = api.base_vaccinations;
                    if (Vs != null) {
                        if (!Vs.isEmpty())
                            for (int i = 0; i < Vs.size(); i++)
                                Vs.get(i).setSqlite_id(String.valueOf(db.createVaccination(Vs.get(i))));

                        Calendar calendar = Calendar.getInstance();

                        calendar.set(Calendar.HOUR_OF_DAY, 19); // For 1 PM or 2 PM
                        calendar.set(Calendar.MINUTE, 50);
                        calendar.set(Calendar.SECOND, 0);
                        PendingIntent pi = PendingIntent.getService(getApplicationContext(), 0,
                                new Intent(getApplicationContext(), BaseVaccinationNotifyService.class), PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY, pi);
                    }
                }
            }
        });
    }

    boolean validate()
    {
        if(input_email.getText().toString().isEmpty() || input_password.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
