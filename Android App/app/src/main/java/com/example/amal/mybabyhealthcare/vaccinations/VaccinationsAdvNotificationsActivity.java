package com.example.amal.mybabyhealthcare.vaccinations;

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

import custom_adapters.NotificationsListAdapter;
import custom_interfaces.CallBack;
import model.Notification;
import pref_managers.ChildSessionManager;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class VaccinationsAdvNotificationsActivity extends AppCompatActivity {

    ListView notifications_list;
    ArrayList<Notification> notifications;
    UserSessionManager userSessionManager;
    ChildSessionManager childSessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_fragment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    void onInit()
    {
        //if(rootView != null && getActivity() != null)
        {
            userSessionManager = new UserSessionManager(VaccinationsAdvNotificationsActivity.this);
            childSessionManager = new ChildSessionManager(VaccinationsAdvNotificationsActivity.this);

            notifications_list = (ListView) findViewById(R.id.advs_list);

            notifications_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(VaccinationsAdvNotificationsActivity.this, VaccinationAdvActivity.class);
                    i.putExtra("notification", (Notification) (parent.getAdapter().getItem(position)));
                    startActivity(i);
                }
            });

            getAdvs();
        }
    }

    void getAdvs()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_user", userSessionManager.getUserDetails().getId());
        map1.put("id_child", childSessionManager.getBabyDetails().getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_ALL_VAC_ADV);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    if (api.notifications != null) {
                        notifications = api.notifications;
                        NotificationsListAdapter adapter = new NotificationsListAdapter(VaccinationsAdvNotificationsActivity.this, notifications);
                        notifications_list.setAdapter(adapter);
                    }
                } else Toast.makeText(VaccinationsAdvNotificationsActivity.this, api.message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
