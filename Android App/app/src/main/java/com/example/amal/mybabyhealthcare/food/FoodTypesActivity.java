package com.example.amal.mybabyhealthcare.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import custom_adapters.FoodTypeListAdapter;
import custom_adapters.SpinnerNavAdapter;
import custom_interfaces.CallBack;
import model.Baby;
import model.FoodType;
import model.SpinnerNavItem;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;

public class FoodTypesActivity extends AppCompatActivity implements ActionBar.OnNavigationListener{

    private ArrayList<SpinnerNavItem> navSpinner;
    private SpinnerNavAdapter adapter;
    private FoodTypeListAdapter types_adapter;
    private SpinnerNavItem navItem;

    ListView types_list;

    ArrayList<FoodType> types;
    ArrayList<FoodType> blocked_types;
    ArrayList<FoodType> good_types;
    ArrayList<FoodType> pending_types;
    ChildSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_types);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        navSpinner = new ArrayList<>();
        navSpinner.add(new SpinnerNavItem("All", 0));
        navSpinner.add(new SpinnerNavItem(getResources().getString(R.string.good_food), R.drawable.good));
        navSpinner.add(new SpinnerNavItem(getResources().getString(R.string.blocked_food), R.drawable.block));
        navSpinner.add(new SpinnerNavItem(getResources().getString(R.string.pending_food), R.drawable.pending));

        navItem = navSpinner.get(0);

        adapter = new SpinnerNavAdapter(FoodTypesActivity.this, navSpinner);

        getSupportActionBar().setListNavigationCallbacks(adapter, this);

        onInit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        getTypes();

    }

    private void onInit() {
        session = new ChildSessionManager(FoodTypesActivity.this);
        types_list = (ListView) findViewById(R.id.food_types_grid);
        types_list.setDivider(null);

        types_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodType selected_type = (FoodType) parent.getAdapter().getItem(position);
                Intent i = new Intent(FoodTypesActivity.this, FoodTypeDetailsActivity.class);
                i.putExtra("selected_type", selected_type);
                startActivity(i);
            }
        });

        types_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                ConfirmationAlertManager.showDialog(FoodTypesActivity.this, "Delete type",
                        "Are you sure you want to delete this type?", new CallBack() {
                            @Override
                            public void onResponse() {
                                Map<String, String> map = new HashMap<>();
                                map.put("id", ((FoodType) parent.getAdapter().getItem(position)).getId());
                                JSONObject data = new JSONObject(map);

                                final APIManager api = new APIManager(APITypes.DELETE_FOOD_TYPE);
                                api.sendRequest(data, new CallBack() {
                                    @Override
                                    public void onResponse() {
                                        Toast.makeText(FoodTypesActivity.this, api.message, Toast.LENGTH_LONG).show();
                                        if (api.query_status) {
                                            if (navItem.getTitle().equals(getString(R.string.good_food))) {
                                                types.remove(good_types.get(position));
                                                good_types.remove(position);
                                                types_adapter.change_data_set(good_types);
                                            } else if (navItem.getTitle().equals(getString(R.string.blocked_food))) {
                                                types.remove(blocked_types.get(position));
                                                blocked_types.remove(position);
                                                types_adapter.change_data_set(blocked_types);
                                            } else if (navItem.getTitle().equals(getString(R.string.pending_food))) {
                                                types.remove(pending_types.get(position));
                                                pending_types.remove(position);
                                                types_adapter.change_data_set(pending_types);
                                            } else if (navItem.getTitle().equals("All")) {
                                                blocked_types.remove(types.get(position));
                                                pending_types.remove(types.get(position));
                                                good_types.remove(types.get(position));
                                                types.remove(position);
                                                types_adapter.change_data_set(types);
                                            }
                                            types_list.invalidate();

                                        }
                                    }
                                });
                            }
                        });
                return false;
            }
        });
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
            Intent i = new Intent(FoodTypesActivity.this, FoodTypeDetailsActivity.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        if(!adapter.getItem(itemPosition).equals(navItem)) {
            if(adapter.getItem(itemPosition).equals(navSpinner.get(0))) {
                navItem = navSpinner.get(0);
                if (types_adapter != null) {
                    types_adapter.change_data_set(types);
                    types_list.invalidate();
                }
            }
            else if(adapter.getItem(itemPosition).equals(navSpinner.get(1))) {
                navItem = navSpinner.get(1);
                if (types_adapter != null) {
                    types_adapter.change_data_set(good_types);
                    types_list.invalidate();
                }
            }
            else if(adapter.getItem(itemPosition).equals(navSpinner.get(2))) {
                navItem = navSpinner.get(2);
                if (types_adapter != null) {
                    types_adapter.change_data_set(blocked_types);
                    types_list.invalidate();
                }
            }
            else if(adapter.getItem(itemPosition).equals(navSpinner.get(3))) {
                navItem = navSpinner.get(3);
                if (types_adapter != null) {
                    types_adapter.change_data_set(pending_types);
                    types_list.invalidate();
                }
            }
        }
        return false;
    }

    private void getTypes()
    {
        Baby selected_baby = session.getBabyDetails();
        if(selected_baby != null) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("id", selected_baby.getId());
            JSONObject id = new JSONObject(map1);

            final APIManager api = new APIManager(APITypes.GET_FOOD_TYPES);

            api.sendRequest(id, new CallBack() {
                @Override
                public void onResponse() {
                    if (api.get_food_type_status) {
                        types = new ArrayList<>();
                        blocked_types = new ArrayList<>();
                        pending_types = new ArrayList<>();
                        good_types = new ArrayList<>();

                        types = api.foodTypes;
                        if (types != null) {
                            for (int i = 0; i < types.size(); i++)
                                if(types.get(i).status.equals("blocked"))
                                    blocked_types.add(types.get(i));

                            for (int i = 0; i < types.size(); i++)
                                if(types.get(i).status.equals("good"))
                                    good_types.add(types.get(i));

                            for (int i = 0; i < types.size(); i++)
                                if(types.get(i).status.equals("pending"))
                                    pending_types.add(types.get(i));

                            if(navItem.equals(navSpinner.get(0))) {
                                types_adapter = new FoodTypeListAdapter(FoodTypesActivity.this, types);
                                types_list.setAdapter(types_adapter);
                            }else if(navItem.equals(navSpinner.get(1))) {
                                types_adapter = new FoodTypeListAdapter(FoodTypesActivity.this, good_types);
                                types_list.setAdapter(types_adapter);
                            }else if(navItem.equals(navSpinner.get(2))) {
                                types_adapter = new FoodTypeListAdapter(FoodTypesActivity.this, blocked_types);
                                types_list.setAdapter(types_adapter);
                            }else if(navItem.equals(navSpinner.get(3))) {
                                types_adapter = new FoodTypeListAdapter(FoodTypesActivity.this, pending_types);
                                types_list.setAdapter(types_adapter);
                            }

                        }
                    }
                }
            });
        }
    }
}
