package com.example.amal.mybabyhealthcare.skills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_adapters.SkillsExpandableListAdapter;
import custom_interfaces.CallBack;
import date.Period;
import model.ChildSkill;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class SkillsActivity extends AppCompatActivity {

    ArrayList<ChildSkill> childSkills;
    HashMap<String, ArrayList<ChildSkill>> list_map;
    ArrayList<String> group_titles;

    ExpandableListView list;
    SkillsExpandableListAdapter adapter;
    ChildSessionManager childSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ExpandableListView) findViewById(R.id.skills_expandable_listView);
        View view = View.inflate(this, R.layout.skills_list_header, null);
        list.addHeaderView(view);
    }

    void onInit()
    {
        childSessionManager = new ChildSessionManager(this);

        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(((ChildSkill)adapter.getChild(groupPosition,childPosition)).getId().equals("none")) {
                    Intent i = new Intent(SkillsActivity.this, NewSkillActivity.class);
                    i.putExtra("selected_Child_skill", (ChildSkill) adapter.getChild(groupPosition, childPosition));
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(SkillsActivity.this, NewSkillActivity.class);
                    i.putExtra("selected_Child_skill", (ChildSkill) adapter.getChild(groupPosition, childPosition));
                    i.putExtra("edit", true);
                    startActivity(i);
                }

                return false;
            }
        });

        getSkills();
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    private void getSkills()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", childSessionManager.getBabyDetails().getId());
        JSONObject id = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_ALL_SKILLS);

        api.sendRequest(id, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    childSkills = api.baby_Child_skills;
                    buildListMap();
                    adapter = new SkillsExpandableListAdapter(SkillsActivity.this, group_titles, list_map);
                    list.setAdapter(adapter);
                }
            }
        });
    }

    private void buildListMap()
    {
        list_map = new HashMap<>();
        group_titles = new ArrayList<>();

        for (int i = 0; i < childSkills.size(); i++)
        {
            Period age = childSessionManager.getBabyDetails().getAge();
            if(childSkills.get(i).getId().equals("none"))
                childSkills.get(i).Rerate(age.getYear()*12+age.getMonth());

            String key = "From " + childSkills.get(i).skill.getAge_from() + " to " + childSkills.get(i).skill.getAge_to();

            if(!list_map.containsKey(key)) {
                list_map.put(key, new ArrayList<ChildSkill>());
                group_titles.add(key);
            }

            list_map.get(key).add(childSkills.get(i));
        }
    }
}
