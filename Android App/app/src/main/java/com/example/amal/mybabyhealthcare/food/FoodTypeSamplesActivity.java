package com.example.amal.mybabyhealthcare.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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

import Helpers.ConfirmationAlertManager;
import custom_adapters.FoodTypeSampleListAdapter;
import custom_interfaces.CallBack;
import model.FoodType;
import model.FoodTypeSample;
import server_side.APIManager;
import server_side.APITypes;

public class FoodTypeSamplesActivity extends AppCompatActivity {

    ListView samples_list;

    ArrayList<FoodTypeSample> samples;
    FoodType selected_type;
    FoodTypeSampleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type_samples);

        Intent intent = getIntent();
        selected_type = (FoodType) intent.getSerializableExtra("selected_type");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onInit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSamples();
    }

    private void onInit()
    {
        samples_list = (ListView) findViewById(R.id.food_type_samples_list);

        if(selected_type != null)
            getSupportActionBar().setTitle(selected_type.name);

        samples_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodTypeSample selected_sample = (FoodTypeSample) parent.getAdapter().getItem(position);
                Intent i = new Intent(FoodTypeSamplesActivity.this, FoodTypeSampleDetails.class);
                i.putExtra("selected_sample", selected_sample);
                startActivity(i);
            }
        });

        samples_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                ConfirmationAlertManager.showDialog(FoodTypeSamplesActivity.this, "Delete sample",
                        "Are you sure you want to delete this sample?", new CallBack() {
                            @Override
                            public void onResponse() {
                                Map<String, String> map = new HashMap<>();
                                map.put("id", ((FoodTypeSample) parent.getAdapter().getItem(position)).getId());
                                JSONObject data = new JSONObject(map);

                                final APIManager api = new APIManager(APITypes.DELETE_FOOD_SAMPLE);
                                api.sendRequest(data, new CallBack() {
                                    @Override
                                    public void onResponse() {
                                        Toast.makeText(FoodTypeSamplesActivity.this, api.message, Toast.LENGTH_LONG).show();
                                        if (api.query_status) {
                                            adapter.delete_item(position);
                                            samples_list.invalidate();
                                        }
                                    }
                                });
                            }
                        });
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_samples_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add_food_sample)
        {
            Intent i = new Intent(FoodTypeSamplesActivity.this, FoodTypeSampleDetails.class);
            i.putExtra("selected_type", selected_type);
            startActivity(i);
            return true;
        }
        else if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    private void getSamples()
    {
        if(selected_type != null) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("id_food", selected_type.getId());
            JSONObject id = new JSONObject(map1);

            final APIManager api = new APIManager(APITypes.GET_FOOD_TYPE_SAMPLES);

            api.sendRequest(id, new CallBack() {
                @Override
                public void onResponse() {
                    if (api.get_food_type_samples_status) {
                        samples = api.foodTypeSamples;
                        if (samples != null) {
                            adapter = new custom_adapters.FoodTypeSampleListAdapter(FoodTypeSamplesActivity.this, samples);
                            samples_list.setAdapter(adapter);
                        }
                    }
                }
            });
        }
    }
}
