package com.example.amal.mybabyhealthcare.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

import custom_interfaces.CallBack;
import date.CustomDate;
import model.FoodType;
import model.FoodTypeSample;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import Helpers.DatePickerDialogManager;

public class FoodTypeSampleDetails extends AppCompatActivity {

    FoodTypeSample selected_sample;
    TextView title;
    FoodType selected_type;

    EditText date, note, note_date;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type_sample_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        selected_sample = (FoodTypeSample) intent.getSerializableExtra("selected_sample");
        selected_type = (FoodType) intent.getSerializableExtra("selected_type");

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

    private void onInit() {
        date = (EditText) findViewById(R.id.food_type_sample_date_of_tried);
        note_date = (EditText) findViewById(R.id.food_type_sample_note_date);
        note = (EditText) findViewById(R.id.food_type_sample_note);
        save = (Button) findViewById(R.id.food_type_sample_save);

        ChildSessionManager childSessionManager = new ChildSessionManager(this);
        final CustomDate min_date = new CustomDate(childSessionManager.getBabyDetails().birth_date);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        String max_date_str = dateFormatter.format(newCalendar.getTime());
        final CustomDate max_date = new CustomDate(max_date_str);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogManager.showDialog(FoodTypeSampleDetails.this, min_date.getDate(), max_date.getDate(), date);
            }
        });

        note_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogManager.showDialog(FoodTypeSampleDetails.this, min_date.getDate(), max_date.getDate(), note_date);
            }
        });

        if (selected_sample != null) {
            date.setText(selected_sample.date);
            note_date.setText(selected_sample.note_date);
            note.setText(selected_sample.note);
            getSupportActionBar().setTitle(getString(R.string.food_type_sample_details));
        }
        else
            getSupportActionBar().setTitle(getString(R.string.new_food_type_sample));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave_click();
            }
        });
    }

    private void onSave_click() {
        if(validate()) {
            if (selected_type != null)
                if (selected_sample == null) {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("id_food", selected_type.getId());
                    map1.put("date", date.getText().toString());
                    map1.put("note_date", note_date.getText().toString());
                    map1.put("note", note.getText().toString());
                    JSONObject type_info = new JSONObject(map1);
                    final APIManager api = new APIManager(APITypes.ADD_FOOD_TYPE_SAMPLE);
                    api.sendRequest(type_info, new CallBack() {
                        @Override
                        public void onResponse() {
                            Toast.makeText(FoodTypeSampleDetails.this, api.message, Toast.LENGTH_LONG).show();
                            if (api.query_status)
                                finish();
                        }
                    });
                } else {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("id", selected_sample.getId());
                    map1.put("date", date.getText().toString());
                    map1.put("note_date", note_date.getText().toString());
                    map1.put("note", note.getText().toString());
                    JSONObject type_info = new JSONObject(map1);
                    final APIManager api = new APIManager(APITypes.UPDATE_FOOD_TYPE_SAMPLE);
                    api.sendRequest(type_info, new CallBack() {
                        @Override
                        public void onResponse() {
                            Toast.makeText(FoodTypeSampleDetails.this, api.message, Toast.LENGTH_LONG).show();
                            if (api.query_status)
                                finish();
                        }
                    });
                }
        }
    }

    boolean validate()
    {
        if(date.getText().toString().isEmpty() || note_date.getText().toString().isEmpty() ||
                note.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
