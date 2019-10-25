package com.example.amal.mybabyhealthcare.growth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Helpers.ConfirmationAlertManager;
import custom_adapters.QuantityGridAdapter;
import custom_adapters.QuntitiySpinnerAdapter;
import custom_interfaces.CallBack;
import model.ChartData;
import model.Quantity;
import model.QuantitySample;
import model.User;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class GrowthActivity extends AppCompatActivity implements ActionBar.OnNavigationListener{

    private ArrayList<Quantity> quantities;
    private ArrayList<QuantitySample> quantity_samples;
    private QuntitiySpinnerAdapter adapter;
    private Quantity selected_quantity;
    private QuantitySample selected_sample;
    private ArrayList<ChartData> chart_data;

    private FrameLayout chart_layout;
    private ListView samples_List;
    private LineChart chart;
    private QuantityGridAdapter grid_adapter;
    private LineDataSet datasetUser;
    private LineData Line_data;

    ChildSessionManager childSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        get_quantities();
        onInit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            if(requestCode == 1) {
                QuantitySample new_sample = (QuantitySample) data.getSerializableExtra("new_sample");
                if (new_sample != null) {
                    grid_adapter.add_sample(new_sample);
                    samples_List.invalidate();

                    datasetUser.addEntry(new Entry(Float.valueOf(new_sample.measurement), Integer.valueOf(new_sample.age_weeks)));
                    Line_data.notifyDataChanged();
                    chart.notifyDataSetChanged();
                    chart.invalidate();
                }
            }
            else if (requestCode == 2)
            {
                QuantitySample updated_sample = (QuantitySample) data.getSerializableExtra("updated_sample");
                if (updated_sample != null) {
                    grid_adapter.update_sample(updated_sample);
                    samples_List.invalidate();

                    datasetUser.removeEntry(quantity_samples.indexOf(updated_sample));
                    datasetUser.addEntry(new Entry(Float.valueOf(updated_sample.measurement), Integer.valueOf(updated_sample.age_weeks)));
                    Line_data.notifyDataChanged();
                    chart.notifyDataSetChanged();
                    chart.invalidate();
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_samples_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add_food_sample)
        {
            Intent i = new Intent(GrowthActivity.this, NewQuantitySampleActivity.class);
            i.putExtra("selected_quantity", selected_quantity);
            startActivityForResult(i, 1);
            return true;
        }
        else if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        if(!adapter.getItem(itemPosition).equals(selected_quantity)) {
            selected_quantity = adapter.getItem(itemPosition);
            chart_layout.removeAllViews();
            chart_layout.invalidate();
            get_quantity_samples();
        }

        return false;
    }

    void onInit()
    {
        childSessionManager = new ChildSessionManager(this);
        
        samples_List = (ListView) findViewById(R.id.quantity_samples_list);

        View view = View.inflate(GrowthActivity.this, R.layout.growth_list_header, null);
        samples_List.addHeaderView(view);
        samples_List.setDivider(null);
        chart_layout = (FrameLayout) view.findViewById(R.id.growth_chart_layout);

        samples_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_sample = (QuantitySample) parent.getAdapter().getItem(position);
                Intent i = new Intent(GrowthActivity.this, NewQuantitySampleActivity.class);
                i.putExtra("selected_quantity", selected_quantity);
                i.putExtra("selected_sample", selected_sample);
                startActivityForResult(i, 2);
            }
        });

        samples_List.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                ConfirmationAlertManager.showDialog(GrowthActivity.this, "Delete sample",
                        "Are you sure you want to delete this sample?", new CallBack() {
                            @Override
                            public void onResponse() {
                                Map<String, String> map = new HashMap<>();
                                map.put("id", ((QuantitySample) parent.getAdapter().getItem(position)).getId());
                                JSONObject data = new JSONObject(map);

                                final APIManager api = new APIManager(APITypes.DELETE_QUANTITY_SAMPLE);
                                api.sendRequest(data, new CallBack() {
                                    @Override
                                    public void onResponse() {
                                        Toast.makeText(GrowthActivity.this, api.message, Toast.LENGTH_LONG).show();
                                        if (api.query_status) {
                                            QuantitySample deleted_sample = grid_adapter.getItem(position-1);
                                            grid_adapter.delete_sample(position-1);
                                            samples_List.invalidate();

                                            datasetUser.removeEntry(quantity_samples.indexOf(deleted_sample));
                                            Line_data.notifyDataChanged();
                                            chart.notifyDataSetChanged();
                                            chart.invalidate();
                                        }
                                    }
                                });
                            }
                        });
                return false;
            }
        });
    }

    void plot_chart()
    {
        chart = new LineChart(GrowthActivity.this);
        chart_layout.addView(chart);

        ArrayList<Entry> entries = new ArrayList<>();
        LineDataSet dataset = new LineDataSet(entries, "");
        ArrayList<String> labels = new ArrayList<>();
        Line_data = new LineData(labels, dataset);

        for (int i = 0; i <= 144; i++)
            Line_data.addXValue("");

        ArrayList<Entry> entriesP3 = new ArrayList<>();
        ArrayList<Entry> entriesP50 = new ArrayList<>();
        ArrayList<Entry> entriesP97 = new ArrayList<>();
        ArrayList<Entry> entriesUser = new ArrayList<>();

        for (int i = 0; i < chart_data.size(); i++)
        {
            double Y1 = Double.parseDouble(chart_data.get(i).getP3());
            double Y2 = Double.parseDouble(chart_data.get(i).getP50());
            double Y3 = Double.parseDouble(chart_data.get(i).getP97());
            int X = Integer.parseInt(chart_data.get(i).getAge_weeks());

            entriesP3.add(new Entry((float)Y1, X));
            entriesP50.add(new Entry((float)Y2, X));
            entriesP97.add(new Entry((float)Y3, X));
        }

        for (int i = 0; i < quantity_samples.size(); i++)
        {
            double Y = Double.parseDouble(quantity_samples.get(i).measurement);
            int X = Integer.parseInt(quantity_samples.get(i).age_weeks);
            entriesUser.add(new Entry((float) Y, X));
        }

        LineDataSet datasetP3 = new LineDataSet(entriesP3, "Percentile 3%");
        LineDataSet datasetP50 = new LineDataSet(entriesP50, "Percentile 50%");
        LineDataSet datasetP97 = new LineDataSet(entriesP97, "Percentile 97%");
        datasetUser = new LineDataSet(entriesUser, "Your Baby");

        datasetP3.setColor(getResources().getColor(R.color.chartColor1));
        datasetP50.setColor(getResources().getColor(R.color.chartColor2));
        datasetP97.setColor(getResources().getColor(R.color.chartColor3));
        datasetUser.setColor(getResources().getColor(R.color.colorPrimary));

        datasetP3.setDrawCubic(true);
        datasetP50.setDrawCubic(true);
        datasetP97.setDrawCubic(true);

        datasetP3.setDrawCircles(false);
        datasetP50.setDrawCircles(false);
        datasetP97.setDrawCircles(false);

        datasetP3.setDrawValues(false);
        datasetP50.setDrawValues(false);
        datasetP97.setDrawValues(false);

        datasetUser.setCircleColor(getResources().getColor(R.color.colorPrimary));
        datasetUser.setDrawValues(true);
        datasetUser.setDrawCircleHole(false);
        datasetUser.setCircleRadius(datasetUser.getCircleRadius()/3);

        Line_data.addDataSet(datasetUser);
        Line_data.addDataSet(datasetP3);
        Line_data.addDataSet(datasetP50);
        Line_data.addDataSet(datasetP97);

        chart.setData(Line_data);
        chart.setDescription(selected_quantity.name + " to Age Chart");
    }

    private void get_chart_data()
    {
        if(childSessionManager.isSelected()) {
            Map<String, String> map1 = new HashMap<>();
            if(childSessionManager.getBabyDetails().gender.equals(User.Gender.MALE))
                map1.put("sex", "1");
            else
                map1.put("sex", "2");

            map1.put("id_quantity", selected_quantity.getId());
            JSONObject chart_info = new JSONObject(map1);

            final APIManager api = new APIManager(APITypes.GET_CHART_DATA);

            api.sendRequest(chart_info, new CallBack() {
                @Override
                public void onResponse() {
                    if (api.query_status) {
                        chart_data = api.chart_data;
                        plot_chart();
                    }
                }
            });
        }
    }

    private GrowthActivity this_activity = this;
    private void get_quantities()
    {
        final APIManager api = new APIManager(APITypes.GET_QUANTITIES);
        api.sendRequest(new JSONObject(), new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    quantities = api.quantities;
                    adapter = new QuntitiySpinnerAdapter(GrowthActivity.this, quantities);
                    getSupportActionBar().setListNavigationCallbacks(adapter, this_activity);
                    selected_quantity = quantities.get(0);
                    get_quantity_samples();
                }
            }
        });
    }

    private void get_quantity_samples()
    {
        if(childSessionManager.isSelected()) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("id_child", childSessionManager.getBabyDetails().getId());
            map1.put("id_quantities", selected_quantity.getId());
            final JSONObject quantity_info = new JSONObject(map1);

            final APIManager api = new APIManager(APITypes.GET_QUANTITY_SAMPLES);

            api.sendRequest(quantity_info, new CallBack() {
                @Override
                public void onResponse() {
                    if (api.query_status) {
                        quantity_samples = api.quantity_samples;
                        if (quantity_samples != null) {
                            grid_adapter = new QuantityGridAdapter(GrowthActivity.this, quantity_samples, selected_quantity.unit);
                            samples_List.setAdapter(grid_adapter);
                        }
                        get_chart_data();
                    }
                }
            });
        }
    }


}
