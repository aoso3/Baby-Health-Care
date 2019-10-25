package com.example.amal.mybabyhealthcare.doctor_visit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_adapters.VaccinationRecordItemAdapter;
import custom_interfaces.CallBack;
import model.VaccenationRecord;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class DoctorVisitRecordActivity extends AppCompatActivity {

    ListView records_list;
    ArrayList<VaccenationRecord> record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_visit);

        onInit();
    }

    void onInit()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        records_list = (ListView) findViewById(R.id.vec_records_list);
        records_list.setDivider(null);
        get_records();
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

    void get_records(){

        ChildSessionManager manager = new ChildSessionManager(DoctorVisitRecordActivity.this);
        if(manager.isSelected()) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("id_child", manager.getBabyDetails().getId());
            JSONObject child_id = new JSONObject(map1);

            final APIManager api = new APIManager(APITypes.GET_VEC_RECORD);

            api.sendRequest(child_id, new CallBack() {
                @Override
                public void onResponse() {
                    if(api.query_status)
                    {
                        if(api.record != null)
                        {
                            record = api.record;
                            VaccinationRecordItemAdapter adapter = new VaccinationRecordItemAdapter(DoctorVisitRecordActivity.this, record);
                            records_list.setAdapter(adapter);
                        }
                    }
                }
            });
        }


    }
}
