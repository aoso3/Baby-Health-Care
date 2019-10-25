package com.example.amal.mybabyhealthcare.vaccinations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import custom_interfaces.CallBack;
import model.Notification;
import model.VaccinationConfirmationReq;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import Helpers.ConfirmationAlertManager;

public class VaccenationConfirmationReqActivity extends AppCompatActivity {

    TextView text, doctor, vacc, date;
    Button confirm, refuse;

    Notification notif;
    VaccinationConfirmationReq req;
    UserSessionManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccenation_confirmation_req);

        Intent i = getIntent();
        notif = (Notification) i.getSerializableExtra("notification");
        onInit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent i = getIntent();
        notif = (Notification) i.getSerializableExtra("notification");
        onInit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        notif = (Notification) intent.getSerializableExtra("notification");
        onInit();
    }

    void onInit()
    {
        userManager = new UserSessionManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        see_notification();
        text = (TextView) findViewById(R.id.confirm_req_text);
        doctor = (TextView) findViewById(R.id.confirm_req_doctor);
        vacc = (TextView) findViewById(R.id.confirm_req_vacc_name);
        date = (TextView) findViewById(R.id.confirm_req_date);
        confirm = (Button) findViewById(R.id.confirmation_req_confirm);
        refuse = (Button) findViewById(R.id.confirmation_req_refuse);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationAlertManager.showDialog(VaccenationConfirmationReqActivity.this,
                        "Confirm request",
                        "Are you sure you want to confirm adding this vaccination?",
                        new CallBack() {
                            @Override
                            public void onResponse() {
                                confirm();
                            }
                        });
            }
        });

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationAlertManager.showDialog(VaccenationConfirmationReqActivity.this,
                        "Refuse request",
                        "Are you sure you want to refuse adding this vaccination?",
                        new CallBack() {
                            @Override
                            public void onResponse() {
                                refuse();
                            }
                        });
            }
        });

        getRequest();
    }

    void showRequest()
    {
        text.setText(notif.getText());

        String temp = "Doctor: " + req.getDoctor().first_name + " " + req.getDoctor().last_name + ".";
        doctor.setText(temp);

        temp = "Vaccination: " + req.getVaccination().name;
        vacc.setText(temp);

        date.setText(req.getDate());
    }

    void getRequest()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", notif.getAdditional_data());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_VAC_CONFIRM_REQ);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    req = api.vacc_req;
                    showRequest();
                } else
                    Toast.makeText(VaccenationConfirmationReqActivity.this, api.message, Toast.LENGTH_LONG).show();
            }
        });
    }

    void confirm()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", notif.getAdditional_data());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.CONFIRM_VAC_REQ);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                Toast.makeText(VaccenationConfirmationReqActivity.this, api.message, Toast.LENGTH_LONG).show();
                if(api.query_status)
                    finish();
            }
        });
    }

    void refuse()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", notif.getAdditional_data());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.REFUSE_VAC_REQUEST);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                Toast.makeText(VaccenationConfirmationReqActivity.this, api.message, Toast.LENGTH_LONG).show();
                if(api.query_status)
                    finish();
            }
        });
    }

    void see_notification()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_user", userManager.getUserDetails().getId());
        map1.put("id_notification", notif.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.SEE_NOTIFICATION);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {}
        });
    }
}
