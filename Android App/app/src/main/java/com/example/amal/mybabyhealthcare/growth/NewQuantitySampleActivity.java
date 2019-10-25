package com.example.amal.mybabyhealthcare.growth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Helpers.DatePickerDialogManager;
import custom_interfaces.CallBack;
import date.CustomDate;
import date.Period;
import model.Quantity;
import model.QuantitySample;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class NewQuantitySampleActivity extends Activity {

    Quantity selected_quantity;
    QuantitySample selected_sample;

    EditText age, measurement, date;
    TextView unit;
    Button save;
    TextView title;

    ChildSessionManager childSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quantity_sample);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent i = getIntent();
        selected_quantity = (Quantity) i.getSerializableExtra("selected_quantity");
        selected_sample = (QuantitySample) i.getSerializableExtra("selected_sample");

        onInit();
    }

    void onInit()
    {
        childSessionManager = new ChildSessionManager(this);
        age = (EditText) findViewById(R.id.new_quantity_age);
        unit = (TextView) findViewById(R.id.new_quntity_unit);
        measurement = (EditText) findViewById(R.id.new_quantity_meas);
        save = (Button) findViewById(R.id.new_quantity_save);
        title = (TextView) findViewById(R.id.new_quantity_sample_title);
        date = (EditText) findViewById(R.id.new_quantity_date);

        age.setEnabled(false);

        final CustomDate min_date = new CustomDate(childSessionManager.getBabyDetails().birth_date);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        String max_date_str = dateFormatter.format(newCalendar.getTime());
        final CustomDate max_date = new CustomDate(max_date_str);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogManager.showDialog(NewQuantitySampleActivity.this, min_date.getDate(), max_date.getDate(),
                        date, new CallBack() {
                    @Override
                    public void onResponse() {
                        Period p = childSessionManager.getBabyDetails().getAge(new CustomDate(date.getText().toString()));
                        int age_val = p.getDay()/7 + p.getMonth()*4 + p.getYear()*12*4;
                        age.setText(String.valueOf(age_val));
                        age.invalidate();
                    }
                });
            }
        });

        unit.setText(selected_quantity.unit);

        if(selected_sample != null)
        {
            date.setText(selected_sample.date);
            age.setText(selected_sample.age_weeks);
            measurement.setText(selected_sample.measurement);
            title.setText(getString(R.string.edit_sample));
        }
        else
            title.setText(getString(R.string.new_sample));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_sample == null)
                    addQuantity();
                else update_quantity();
            }
        });
    }

    void addQuantity()
    {
        if(validate()) {
            if (childSessionManager.isSelected()) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("id_child", childSessionManager.getBabyDetails().getId());
                map1.put("id_quantities", selected_quantity.getId());
                map1.put("age_weeks", age.getText().toString());
                map1.put("date", date.getText().toString());
                map1.put("measurement", measurement.getText().toString());

                JSONObject sample = new JSONObject(map1);

                final APIManager api = new APIManager(APITypes.ADD_QUANTITY_SAMPLE);

                api.sendRequest(sample, new CallBack() {
                    @Override
                    public void onResponse() {
                        Toast.makeText(NewQuantitySampleActivity.this, api.message, Toast.LENGTH_LONG).show();
                        if (api.query_status) {
                            QuantitySample sample = new QuantitySample(api.new_id,
                                    measurement.getText().toString(),
                                    age.getText().toString(),
                                    date.getText().toString());
                            Intent i = new Intent(NewQuantitySampleActivity.this, GrowthActivity.class);
                            setResult(1, i);
                            i.putExtra("new_sample", sample);
                            finish();
                        }
                    }
                });
            }
        }
    }

    void update_quantity()
    {
        if(validate()) {
            if (childSessionManager.isSelected()) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("id", selected_sample.getId());
                map1.put("id_child", childSessionManager.getBabyDetails().getId());
                map1.put("id_quantities", selected_quantity.getId());
                map1.put("age_weeks", age.getText().toString());
                map1.put("date", date.getText().toString());
                map1.put("measurement", measurement.getText().toString());

                JSONObject sample = new JSONObject(map1);

                final APIManager api = new APIManager(APITypes.UPDATE_QUANTITY_SAMPLE);

                api.sendRequest(sample, new CallBack() {
                    @Override
                    public void onResponse() {
                        Toast.makeText(NewQuantitySampleActivity.this, api.message, Toast.LENGTH_LONG).show();
                        if (api.query_status) {
                            selected_sample.age_weeks = age.getText().toString();
                            selected_sample.date = date.getText().toString();
                            selected_sample.measurement = measurement.getText().toString();
                            Intent i = new Intent(NewQuantitySampleActivity.this, GrowthActivity.class);
                            setResult(2, i);
                            i.putExtra("updated_sample", selected_sample);
                            finish();
                        }
                    }
                });
            }
        }
    }

    boolean validate()
    {
        if(date.getText().toString().isEmpty() || measurement.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
