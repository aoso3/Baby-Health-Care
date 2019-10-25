package com.example.amal.mybabyhealthcare.sections;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.doctor.FindDoctorActivity;
import com.example.amal.mybabyhealthcare.doctor.LinkedDoctorsActiviy;
import com.example.amal.mybabyhealthcare.food.FoodTypesActivity;
import com.example.amal.mybabyhealthcare.growth.GrowthActivity;
import com.example.amal.mybabyhealthcare.skills.SkillsActivity;
import com.example.amal.mybabyhealthcare.sleep.SleepActivity;
import com.example.amal.mybabyhealthcare.vaccinations.VaccinationsRecordActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_adapters.SecondarySectionsGridAdapter;
import custom_interfaces.CallBack;
import model.SecodarySection;
import server_side.APIManager;
import server_side.APITypes;

public class MainSectionDetailsActivity extends AppCompatActivity {

    ListView list;

    ArrayList<SecodarySection> sections;
    String main_section_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_section_details);

        Intent i = getIntent();
        main_section_id = i.getStringExtra("main_section_id");

        onInit();
    }

    private void onInit()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.secondary_sections_List);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SecodarySection section = (SecodarySection) parent.getAdapter().getItem(position);
                Intent i = new Intent();
                if (section.getType().equals("main")) {
                    if (section.getId_main_section().equals("1")) {
                        i = new Intent(MainSectionDetailsActivity.this, GrowthActivity.class);
                    } else if (section.getId_main_section().equals("2")) {
                        i = new Intent(MainSectionDetailsActivity.this, FoodTypesActivity.class);
                    } else if (section.getId_main_section().equals("3")) {
                        i = new Intent(MainSectionDetailsActivity.this, SleepActivity.class);
                    } else if (section.getId_main_section().equals("5")) {
                        i = new Intent(MainSectionDetailsActivity.this, VaccinationsRecordActivity.class);
                    }if (section.getId_main_section().equals("6")) {
                        if(section.name.equals("Your child's doctor"))
                            i = new Intent(MainSectionDetailsActivity.this, LinkedDoctorsActiviy.class);
                        else i = new Intent(MainSectionDetailsActivity.this, FindDoctorActivity.class);
                    } else if(section.getId_main_section().equals("4")) {
                        i = new Intent(MainSectionDetailsActivity.this, SkillsActivity.class);
                    }
                    startActivity(i);
                }
            }
        });

        list.setDivider(null);

        getSections();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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

    private void getSections()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", main_section_id);
        JSONObject id = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_SECONDARY_SECTIONS);

        api.sendRequest(id, new CallBack() {
            @Override
            public void onResponse() {
                if (api.get_secondary_sections_status)
                {
                    sections = api.secondary_sections;
                    if(sections != null) {
                        SecondarySectionsGridAdapter adapter = new custom_adapters.SecondarySectionsGridAdapter(MainSectionDetailsActivity.this, sections);
                        list.setAdapter(adapter);
                    }
                }
            }
        });
    }
}
