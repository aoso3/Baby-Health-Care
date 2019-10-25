package com.example.amal.mybabyhealthcare.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_adapters.DoctorsListItemAdapter;
import custom_interfaces.CallBack;
import model.Doctor;
import server_side.APIManager;
import server_side.APITypes;

public class FindDoctorActivity extends AppCompatActivity {

    SearchView search;
    ListView doctors_list;

    Doctor last_doctor;
    DoctorsListItemAdapter adapter;
    String search_word;
    ArrayList<Doctor> doctors;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor2);

        doctors = new ArrayList<>();
        last_doctor = new Doctor("0");
        last_doctor.rate = String.valueOf(Integer.MAX_VALUE);
        onInit();
    }

    private void onInit()
    {
        //GoogleMap googleMap;
       // googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search_word = "";
        doctors = new ArrayList<>();

        search = (SearchView) findViewById(R.id.doctors_search);
        doctors_list = (ListView) findViewById(R.id.doctors_list);

        doctors_list.setDivider(null);

        fetchDoctor();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    last_doctor = new Doctor("0");
                    last_doctor.rate = String.valueOf(Integer.MAX_VALUE);
                }
                search_word = newText;
                searchDoctor();
                return true;
            }
        });

        doctors_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(FindDoctorActivity.this, DoctorProfileActivity.class);
                i.putExtra("doctor", (Doctor) parent.getAdapter().getItem(position));
                startActivity(i);
            }
        });

        doctors_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                flag = true;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //Algorithm to check if the last item is visible or not
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && flag) {
                    // you have reached end of list, load more data probably call your database to load more data
                    flag = false;
                    fetchDoctor();
                }
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

    private void searchDoctor()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", last_doctor.getId());
        map1.put("rate", last_doctor.rate);
        map1.put("word", search_word);
        map1.put("count", String.valueOf(doctors.size()));
        final JSONObject doctor_inf = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.SEARCH_DOCTOR);

        api.sendRequest(doctor_inf, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    if (api.doctors != null) {
                        doctors = api.doctors;
                        if (!doctors.isEmpty())
                            last_doctor = doctors.get(doctors.size() - 1);
                        else {
                            last_doctor = new Doctor("0");
                            last_doctor.rate = String.valueOf(Integer.MAX_VALUE);
                        }
                        adapter = new DoctorsListItemAdapter(FindDoctorActivity.this, doctors);
                        doctors_list.setAdapter(adapter);
                    }
                }
            }
        });
    }

    int i = 0;
    private void fetchDoctor()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", last_doctor.getId());
        map1.put("rate", last_doctor.rate);
        map1.put("word", search_word);
        map1.put("count", String.valueOf(doctors.size()));
        Log.v("amal count "+String.valueOf(i++), String.valueOf(doctors.size()));
        final JSONObject doctor_inf = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.SEARCH_DOCTOR);

        api.sendRequest(doctor_inf, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    if (api.doctors != null && !api.doctors.isEmpty()) {
                        doctors.addAll(api.doctors);
                        if (!doctors.isEmpty())
                            last_doctor = api.doctors.get(api.doctors.size() - 1);
                        else {
                            last_doctor = new Doctor("0");
                            last_doctor.rate = String.valueOf(Integer.MAX_VALUE);
                        }

                        if(doctors_list.getAdapter() == null) {
                            adapter = new DoctorsListItemAdapter(FindDoctorActivity.this, doctors);
                            doctors_list.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
