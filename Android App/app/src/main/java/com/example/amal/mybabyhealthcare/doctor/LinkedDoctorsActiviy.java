package com.example.amal.mybabyhealthcare.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_adapters.DoctorsListItemAdapter;
import custom_interfaces.CallBack;
import model.Doctor;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class LinkedDoctorsActiviy extends AppCompatActivity {

    ArrayList<Doctor> doctors;
    ListView doctors_list;
    ChildSessionManager childSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_doctors_activiy);

        onInit();
    }

    void onInit()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        doctors_list = (ListView) findViewById(R.id.linked_doctors_list);
        doctors_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(LinkedDoctorsActiviy.this, DoctorProfileActivity.class);
                i.putExtra("doctor", (Doctor) parent.getAdapter().getItem(position));
                startActivity(i);
            }
        });

        doctors_list.setDivider(null);

        childSessionManager = new ChildSessionManager(this);

        getDoctors();
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

    private void getDoctors()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", childSessionManager.getBabyDetails().getId());
        final JSONObject doctor_inf = new JSONObject(map1);
        final APIManager api = new APIManager(APITypes.GET_LINKED_DOCTORS);
        api.sendRequest(doctor_inf, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    if (api.doctors != null) {
                        doctors = api.doctors;

                        DoctorsListItemAdapter adapter = new DoctorsListItemAdapter(LinkedDoctorsActiviy.this, doctors);
                        doctors_list.setAdapter(adapter);
                    }

                }
                else Toast.makeText(LinkedDoctorsActiviy.this, api.message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
