package com.example.amal.mybabyhealthcare.patient_review;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Helpers.NumberPickerDialogManager;
import custom_interfaces.CallBack;
import model.Doctor;
import pref_managers.ChildSessionManager;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class ReviewActivity extends AppCompatActivity {

    Doctor doctor;

    TextView waiting;
    RatingBar office, explaination;

    Button submit;

    ChildSessionManager childManager;
    UserSessionManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        onInit();
    }

    void onInit()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userManager = new UserSessionManager(ReviewActivity.this);
        childManager = new ChildSessionManager(ReviewActivity.this);

        submit = (Button) findViewById(R.id.review_submit);
        waiting = (EditText) findViewById(R.id.review_waiting);
        office = (RatingBar) findViewById(R.id.review_office);
        explaination = (RatingBar) findViewById(R.id.review_explanaition);

        waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerDialogManager.showDialog(ReviewActivity.this, "Waiting time", 0, 120, 0, (EditText) waiting);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review();
            }
        });
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
        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("doctor");

        onInit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        doctor = (Doctor) intent.getSerializableExtra("doctor");
        onInit();
    }

    void review()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_patient", userManager.getUserDetails().getId());
        map1.put("id_doctor", doctor.getId());
        map1.put("waiting_time", waiting.getText().toString());
        map1.put("office_cleanliness", String.valueOf(office.getRating()));
        map1.put("explination_clarity", String.valueOf(explaination.getRating()));
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.REVIEW_DOCTOR);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                Toast.makeText(ReviewActivity.this, api.message, Toast.LENGTH_LONG).show();
                if(api.query_status)
                {
                    finish();
                }
            }
        });
    }
}
