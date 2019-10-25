package com.example.amal.mybabyhealthcare.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import custom_interfaces.CallBack;
import model.Baby;
import model.FoodType;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class FoodTypeDetailsActivity extends AppCompatActivity {

    EditText name;
    TextView title;
    Button save, show_sample;
    RadioButton yum, yuck, normal, good, blocked, pending;

    ChildSessionManager session;
    FoodType selected_type;
    Baby selected_baby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
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
        session = new ChildSessionManager(FoodTypeDetailsActivity.this);
        selected_baby = session.getBabyDetails();

        name = (EditText) findViewById(R.id.food_type_name);
        yum = (RadioButton) findViewById(R.id.yum_rad);
        yuck = (RadioButton) findViewById(R.id.yuck_rad);
        normal = (RadioButton) findViewById(R.id.normal_rad);
        good = (RadioButton) findViewById(R.id.food_good_rad);
        blocked = (RadioButton) findViewById(R.id.food_blocked_rad);
        pending = (RadioButton) findViewById(R.id.food_pending_rad);
        save = (Button) findViewById(R.id.save_food_type_save_btn);
        show_sample = (Button) findViewById(R.id.show_food_type_samples_btn);

        if (selected_type != null) {
            name.setText(selected_type.name);
            if (selected_type.rating.equals("yum"))
                yum.setChecked(true);
            else if (selected_type.rating.equals("yuck"))
                yuck.setChecked(true);
            else normal.setChecked(true);
            if (selected_type.status.equals("good"))
                good.setChecked(true);
            else if (selected_type.status.equals("blocked"))
                blocked.setChecked(true);
            else pending.setChecked(true);

            show_sample.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selected_type != null) {
                        Intent i = new Intent(FoodTypeDetailsActivity.this, FoodTypeSamplesActivity.class);
                        i.putExtra("selected_type", selected_type);
                        startActivity(i);
                    }
                }
            });

            getSupportActionBar().setTitle(getString(R.string.food_type_details));
        }
        else {
            show_sample.setVisibility(View.GONE);
            getSupportActionBar().setTitle(R.string.new_food_type);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave_click();
            }
        });
    }

    private void onSave_click() {
        if(validate()) {
            final String rate, status;
            if (selected_type == null) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("id_child", selected_baby.getId());
                map1.put("name", name.getText().toString());
                if (yum.isChecked()) {
                    map1.put("rating", "yum");
                    rate = "yum";
                } else if (yuck.isChecked()) {
                    map1.put("rating", "yuck");
                    rate = "yuck";
                } else {
                    map1.put("rating", "normal");
                    rate = "normal";
                }
                if (good.isChecked()) {
                    map1.put("status", "good");
                    status = "good";
                } else if (blocked.isChecked()) {
                    map1.put("status", "blocked");
                    status = "blocked";
                } else {
                    map1.put("status", "pending");
                    status = "pending";
                }
                JSONObject type_info = new JSONObject(map1);

                final APIManager api = new APIManager(APITypes.ADD_FOOD_TYPE);
                api.sendRequest(type_info, new CallBack() {
                    @Override
                    public void onResponse() {
                        Toast.makeText(FoodTypeDetailsActivity.this, api.message, Toast.LENGTH_LONG).show();
                        if (api.query_status)
                            finish();
                    }
                });
            } else {
                Map<String, String> map1 = new HashMap<>();
                map1.put("id", selected_type.getId());
                map1.put("id_child", selected_baby.getId());
                map1.put("name", name.getText().toString());
                if (yum.isChecked())
                    map1.put("rating", "yum");
                else if (yuck.isChecked())
                    map1.put("rating", "yuck");
                else
                    map1.put("rating", "normal");
                if (good.isChecked())
                    map1.put("status", "good");
                else if (blocked.isChecked())
                    map1.put("status", "blocked");
                else
                    map1.put("status", "pending");
                JSONObject type_info = new JSONObject(map1);

                final APIManager api = new APIManager(APITypes.UPDATE_FOOD_TYPE);
                api.sendRequest(type_info, new CallBack() {
                    @Override
                    public void onResponse() {
                        Toast.makeText(FoodTypeDetailsActivity.this, api.message, Toast.LENGTH_LONG).show();
                        if (api.query_status)
                            finish();
                    }
                });
            }
        }
    }

    boolean validate()
    {
        if(name.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
