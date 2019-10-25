package com.example.amal.mybabyhealthcare.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Helpers.ConfirmationAlertManager;
import custom_adapters.SleepSampleItemAdapter;
import custom_interfaces.CallBack;
import model.SleepChartData;
import model.SleepSample;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class SleepActivity extends AppCompatActivity {

    private ArrayList<SleepChartData> standard_data;
    private ArrayList<Double> baby_data;

    private FrameLayout chart_layout;
    private ListView samples_grid;
    private BarChart chart;
    private ArrayList<SleepSample> samples;
    private SleepSampleItemAdapter adapter;
    private BarDataSet datasetUser;
    private BarData bar_data;

    ChildSessionManager childSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onInit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //onInit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_samples_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }else if(item.getItemId() == R.id.action_add_food_sample)
        {
            Intent i = new Intent(SleepActivity.this, NewSleepSampleActivity.class);
            startActivityForResult(i, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean flag;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            flag = true;
            if(requestCode == 1) {
                SleepSample new_sample = (SleepSample) data.getSerializableExtra("new_sample");
                if (new_sample != null) {
                    adapter.add_item(new_sample);
                    samples_grid.invalidate();
                    get_chart_data();
                }
            }
            else if (requestCode == 2)
            {
                SleepSample updated_sample = (SleepSample) data.getSerializableExtra("updated_sample");
                if (updated_sample != null) {
                    adapter.update_item(updated_sample);
                    samples_grid.invalidate();
                    get_chart_data();
                }
            }
        }
    }

    void onInit()
    {
        childSessionManager = new ChildSessionManager(this);

        samples_grid = (ListView) findViewById(R.id.sleep_samples_list);
        View view = View.inflate(SleepActivity.this, R.layout.sleep_samples_list_header, null);
        samples_grid.addHeaderView(view);
        samples_grid.setDivider(null);
        chart_layout = (FrameLayout) view.findViewById(R.id.sleep_chart_layout);

        samples_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SleepActivity.this, NewSleepSampleActivity.class);
                i.putExtra("selected_sample", (SleepSample) parent.getAdapter().getItem(position));
                startActivityForResult(i, 2);
            }
        });

        samples_grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                ConfirmationAlertManager.showDialog(SleepActivity.this, "Delete sample",
                        "Are you sure you want to delete this sample?", new CallBack() {
                            @Override
                            public void onResponse() {
                                Map<String, String> map = new HashMap<>();
                                map.put("id", ((SleepSample) parent.getAdapter().getItem(position)).getId());
                                JSONObject data = new JSONObject(map);

                                final APIManager api = new APIManager(APITypes.DELETE_SLEEP_SAMPLE);
                                api.sendRequest(data, new CallBack() {
                                    @Override
                                    public void onResponse() {
                                        Toast.makeText(SleepActivity.this, api.message, Toast.LENGTH_LONG).show();
                                        if (api.query_status) {
                                            adapter.delete_item(position-1);
                                            samples_grid.invalidate();
                                            flag = true;
                                            get_chart_data();
                                        }
                                    }
                                });
                            }
                        });
                return false;
            }
        });

        if(chart == null) {
            chart = new BarChart(SleepActivity.this);
            chart_layout.addView(chart);
        }

        get_chart_data();
        getSamples();
    }

    void plot_chart(boolean plot_first) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < standard_data.size(); i++)
        {
            String label = standard_data.get(i).age_from + " to " + standard_data.get(i).age_to;
            labels.add(label);
            double Y1 = Double.parseDouble(standard_data.get(i).avrg_amount);
            entries.add(new BarEntry((float)Y1, i));
        }

        ArrayList<BarEntry> entries2 = new ArrayList<>();
        for (int i = 0; i < baby_data.size(); i++)
        {
            double Y2 = baby_data.get(i);
            if(Y2 != 0.0)
                entries2.add(new BarEntry((float)Y2, i));
        }

        BarDataSet dataset;
        dataset = new BarDataSet(entries, "Standard Average");
        datasetUser = new BarDataSet(entries2, "Your Baby");
        datasetUser.setColor(getResources().getColor(R.color.colorAccent));
        if(!plot_first)
        {
            chart_layout.removeView(chart);
            chart = new BarChart(this);
            chart_layout.addView(chart);
            chart_layout.invalidate();
            bar_data = new BarData(labels, dataset);
            bar_data.addDataSet(datasetUser);
            chart.setData(bar_data);
        }
        else
        {
            bar_data = new BarData(labels, dataset);
            bar_data.addDataSet(datasetUser);
            chart.setData(bar_data);
            flag = !flag;
        }
    }

    private void get_chart_data()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", childSessionManager.getBabyDetails().getId());
        JSONObject chart_info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_SLEEP_CHART_DATA);

        api.sendRequest(chart_info, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    standard_data = api.standard_sleep_data;
                    baby_data = api.baby_sleep_data;
                    plot_chart(!flag);
                }
            }
        });
    }

    private void getSamples()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", childSessionManager.getBabyDetails().getId());
        JSONObject id = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_SLEEP_SAMPLES);

        api.sendRequest(id, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    samples = api.sleep_samples;
                    if (samples != null) {
                        adapter = new custom_adapters.SleepSampleItemAdapter(SleepActivity.this, samples);
                        samples_grid.setAdapter(adapter);
                    }
                }
            }
        });
    }
}