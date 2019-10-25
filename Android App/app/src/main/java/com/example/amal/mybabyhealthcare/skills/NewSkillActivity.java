package com.example.amal.mybabyhealthcare.skills;

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

import custom_interfaces.CallBack;
import date.CustomDate;
import date.Period;
import model.ChildSkill;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import Helpers.DatePickerDialogManager;

public class NewSkillActivity extends Activity {

    EditText age, date;
    Button save, delete;
    TextView title, skill_text;

    ChildSkill selected_Child_skill;
    ChildSessionManager childSessionManager;
    boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_skill);

        Intent i = getIntent();
        selected_Child_skill = (ChildSkill) i.getSerializableExtra("selected_Child_skill");
        edit = i.getBooleanExtra("edit", false);
        onInit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void onInit()
    {
        childSessionManager = new ChildSessionManager(this);

        age = (EditText) findViewById(R.id.new_skill_age);
        date = (EditText) findViewById(R.id.new_skill_date);
        save = (Button) findViewById(R.id.new_skill_save);
        delete = (Button) findViewById(R.id.delete_skill);
        title = (TextView) findViewById(R.id.new_skill_title);
        skill_text = (TextView) findViewById(R.id.new_skill_txt);

        if(!edit) {
            title.setText("Add new skill");
            delete.setVisibility(View.GONE);
        }
        else {
            title.setText("Edit skill");
            date.setText(selected_Child_skill.date);
            age.setText(selected_Child_skill.age);
        }

        skill_text.setText(selected_Child_skill.skill.getSkill());

        age.setEnabled(false);

        final CustomDate min_date = new CustomDate(childSessionManager.getBabyDetails().birth_date);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        String max_date_str = dateFormatter.format(newCalendar.getTime());
        final CustomDate max_date = new CustomDate(max_date_str);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogManager.showDialog(NewSkillActivity.this, min_date.getDate(), max_date.getDate(), date, new CallBack() {
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit)
                 addSkill();
                else update_skill();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_skill();
            }
        });

    }

    void addSkill()
    {
        if(childSessionManager.isSelected()) {
            ChildSkill childSkill = new ChildSkill("",
                    age.getText().toString(),
                    date.getText().toString(),
                    selected_Child_skill.skill);
            Period p = childSessionManager.getBabyDetails().getAge();
            childSkill.Rerate(p.getYear() * 12 + p.getMonth());
            Map<String, String> map1 = new HashMap<>();
            map1.put("id_child", childSessionManager.getBabyDetails().getId());
            map1.put("date", date.getText().toString());
            map1.put("age_months", age.getText().toString());
            map1.put("id_skills", selected_Child_skill.skill.getId());
            map1.put("rate", childSkill.getRate());

            JSONObject sample = new JSONObject(map1);

            final APIManager api = new APIManager(APITypes.ADD_SKILL);

            api.sendRequest(sample, new CallBack() {
                @Override
                public void onResponse() {
                    Toast.makeText(NewSkillActivity.this, api.message, Toast.LENGTH_LONG).show();
                    if(api.query_status)
                        finish();
                }
            });
        }
    }

    void update_skill()
    {
        if(validate()) {
            if (childSessionManager.isSelected()) {
                ChildSkill childSkill = new ChildSkill("",
                        age.getText().toString(),
                        date.getText().toString(),
                        selected_Child_skill.skill);
                Period p = childSessionManager.getBabyDetails().getAge();
                childSkill.Rerate(p.getYear() * 12 + p.getMonth());

                Map<String, String> map1 = new HashMap<>();
                map1.put("id", selected_Child_skill.getId());
                map1.put("id_child", childSessionManager.getBabyDetails().getId());
                map1.put("date", date.getText().toString());
                map1.put("age_months", age.getText().toString());
                map1.put("id_skills", selected_Child_skill.skill.getId());
                map1.put("rate", childSkill.getRate());

                JSONObject sample = new JSONObject(map1);

                final APIManager api = new APIManager(APITypes.UPDATE_SKILL);

                api.sendRequest(sample, new CallBack() {
                    @Override
                    public void onResponse() {
                        Toast.makeText(NewSkillActivity.this, api.message, Toast.LENGTH_LONG).show();
                        if (api.query_status)
                            finish();
                    }
                });
            }
        }
    }

    void delete_skill()
    {
        if(validate()) {
            if (childSessionManager.isSelected()) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("id", selected_Child_skill.getId());

                JSONObject sample = new JSONObject(map1);

                final APIManager api = new APIManager(APITypes.DELETE_SKILL);

                api.sendRequest(sample, new CallBack() {
                    @Override
                    public void onResponse() {
                        Toast.makeText(NewSkillActivity.this, api.message, Toast.LENGTH_LONG).show();
                        if (api.query_status)
                            finish();
                    }
                });
            }
        }
    }

    boolean validate()
    {
        if(date.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
