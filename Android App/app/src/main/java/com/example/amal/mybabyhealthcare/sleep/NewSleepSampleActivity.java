package com.example.amal.mybabyhealthcare.sleep;

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
import model.SleepSample;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class NewSleepSampleActivity extends Activity {

    EditText age, date, amount;
    Button save;
    TextView title;

    SleepSample selected_sample;
    ChildSessionManager childSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sleep_sample);

        Intent i = getIntent();
        selected_sample = (SleepSample)i.getSerializableExtra("selected_sample");

        onInit();
    }

    void onInit()
    {
        childSessionManager = new ChildSessionManager(this);

        age = (EditText) findViewById(R.id.new_sleep_sample_age);
        date = (EditText) findViewById(R.id.new_sleep_sample_date);
        amount = (EditText) findViewById(R.id.new__sleep_sample_amount);
        save = (Button) findViewById(R.id.new_sleep_sample_save);
        title = (TextView) findViewById(R.id.new_sleep_sample_title);

        age.setEnabled(false);

        final CustomDate min_date = new CustomDate(childSessionManager.getBabyDetails().birth_date);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        String max_date_str = dateFormatter.format(newCalendar.getTime());
        final CustomDate max_date = new CustomDate(max_date_str);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogManager.showDialog(NewSleepSampleActivity.this, min_date.getDate(), max_date.getDate(), date, new CallBack() {
                    @Override
                    public void onResponse() {
                        Period p = childSessionManager.getBabyDetails().getAge(new CustomDate(date.getText().toString()));
                        int age_val = p.getMonth() + p.getYear() * 12;
                        age.setText(String.valueOf(age_val));
                        age.invalidate();
                    }
                });
            }
        });

        if(selected_sample == null)
            title.setText(getString(R.string.new_sample));
        else {
            title.setText(getString(R.string.edit_sample));
            age.setText(selected_sample.age_months);
            date.setText(selected_sample.date);
            amount.setText(selected_sample.amount);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_sample == null)
                    addSample();
                else updateSample();
            }
        });
    }

    void addSample()
    {
        if(validate()) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("id_child", childSessionManager.getBabyDetails().getId());
            map1.put("date", date.getText().toString());
            map1.put("age_months", age.getText().toString());
            map1.put("amount", amount.getText().toString());

            JSONObject sample = new JSONObject(map1);

            final APIManager api = new APIManager(APITypes.ADD_SLEEP_SAMPLE);

            api.sendRequest(sample, new CallBack() {
                @Override
                public void onResponse() {
                    Toast.makeText(NewSleepSampleActivity.this, api.message, Toast.LENGTH_LONG).show();
                    if (api.query_status) {
                        SleepSample sample = new SleepSample(date.getText().toString(), age.getText().toString(), amount.getText().toString(), api.new_id);
                        Intent i = new Intent(NewSleepSampleActivity.this, SleepActivity.class);
                        setResult(1, i);
                        i.putExtra("new_sample", sample);
                        finish();
                    }
                }
            });
        }
    }

    void updateSample()
    {
        if(validate()) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("id", selected_sample.getId());
            map1.put("id_child", childSessionManager.getBabyDetails().getId());
            map1.put("date", date.getText().toString());
            map1.put("age_months", age.getText().toString());
            map1.put("amount", amount.getText().toString());

            JSONObject sample = new JSONObject(map1);

            final APIManager api = new APIManager(APITypes.UPDATE_SLEEP_SAMPLE);

            api.sendRequest(sample, new CallBack() {
                @Override
                public void onResponse() {
                    Toast.makeText(NewSleepSampleActivity.this, api.message, Toast.LENGTH_LONG).show();
                    if (api.query_status) {
                        selected_sample.age_months = age.getText().toString();
                        selected_sample.amount = age.getText().toString();
                        selected_sample.date = date.getText().toString();
                        Intent i = new Intent(NewSleepSampleActivity.this, SleepActivity.class);
                        setResult(2, i);
                        i.putExtra("updated_sample", selected_sample);
                        finish();
                    }
                }
            });
        }
    }

    boolean validate()
    {
        if(date.getText().toString().isEmpty() || amount.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
