package com.example.amal.mybabyhealthcare.main_navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.baby.BabyProfileActivity;
import com.example.amal.mybabyhealthcare.doctor.FindDoctorActivity;
import com.example.amal.mybabyhealthcare.food.FoodTypesActivity;
import com.example.amal.mybabyhealthcare.growth.GrowthActivity;
import com.example.amal.mybabyhealthcare.skills.SkillsActivity;
import com.example.amal.mybabyhealthcare.sleep.SleepActivity;
import com.example.amal.mybabyhealthcare.vaccinations.VaccinationConfirmationReqNotificationsActivity;
import com.example.amal.mybabyhealthcare.vaccinations.VaccinationsAdvNotificationsActivity;
import com.example.amal.mybabyhealthcare.vaccinations.VaccinationsRecordActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Helpers.ImagesManager;
import custom_adapters.MainSectionsListAdapter;
import custom_interfaces.CallBack;
import model.Baby;
import model.MainSection;
import model.User;
import pref_managers.ChildSessionManager;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import server_side.AppConfig;
import sqlite_database_handeler.DatabaseHelper;

/**
 * Created by Amal on 4/12/2016.
 */
public class MainProfile extends Fragment {

    View rootView;
    ImageButton edit_baby;
    Spinner babies_list;
    TextView birth;
    ListView main_sections_list;
    ImageView baby_pic;

    public Baby selected_baby;
    ArrayList<Baby> babies;
    ArrayList<MainSection> main_sections;

    ChildSessionManager childSession;
    UserSessionManager userSession;
    DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_profile, container, false);

        childSession = new ChildSessionManager(getActivity());
        userSession = new UserSessionManager(getActivity());

        if(childSession.isSelected())
            selected_baby = childSession.getBabyDetails();

        onInit();

        return rootView;
    }

    private void onInit()
    {
        if(rootView != null)
        {
            main_sections_list = (ListView) rootView.findViewById(R.id.main_sections_list);

            main_sections_list.setDivider(null);
            View view = View.inflate(getActivity(), R.layout.header_test, null);
            main_sections_list.addHeaderView(view);
            baby_pic = (ImageView) view.findViewById(R.id.main_prof_baby_pic);

            if(childSession.isSelected()) {
                if(childSession.getBabyDetails().pic!=null && !childSession.getBabyDetails().pic.isEmpty())
                    ImagesManager.loadNormalImageView(AppConfig.URL_CHILDREN_AVATAR + childSession.getBabyDetails().pic, baby_pic);
            }
            else {
                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.baby_placeholder);
                baby_pic.setImageBitmap(icon);
            }

            MainSectionsListAdapter adapter = new custom_adapters.MainSectionsListAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, new ArrayList<MainSection>());
            main_sections_list.setAdapter(adapter);

            edit_baby = (ImageButton) view.findViewById(R.id.edit_baby_prof_btn);
            babies_list = (Spinner) view.findViewById(R.id.babies_list);
            birth = (TextView) view.findViewById(R.id.baby_birth_view_txt);

            edit_baby.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BabyProfileActivity.class);
                    startActivity(intent);
                }
            });

            babies_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (babies != null) {
                        selected_baby = (Baby) (parent.getAdapter().getItem(position));
                        birth.setText(selected_baby.birth_date);

                        childSession.deselectBaby();
                        childSession.createBabySession(selected_baby);
                        if(selected_baby.pic!= null && !selected_baby.pic.isEmpty())
                            ImagesManager.loadNormalImageView(AppConfig.URL_CHILDREN_AVATAR + selected_baby.pic, baby_pic);
                        else baby_pic.setImageResource(R.drawable.baby_placeholder);

                    }
                }

            @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    if (babies != null) {
                        parent.setSelection(0);
                        selected_baby = (Baby) parent.getAdapter().getItem(0);
                        birth.setText(selected_baby.birth_date);
                        childSession.createBabySession(selected_baby);
                    }
                }
            });

            main_sections_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (main_sections != null) {
                        String selected_section_id = ((MainSection) parent.getAdapter().getItem(position)).getId();
//                        Intent i = new Intent(getActivity(), MainSectionDetailsActivity.class);
//                        i.putExtra("main_section_id", selected_section_id);
//                        startActivity(i);
                        Intent i = null;
                        if(selected_section_id.equals("1"))
                            i = new Intent(getActivity(), GrowthActivity.class);
                        else if(selected_section_id.equals("2"))
                            i = new Intent(getActivity(), FoodTypesActivity.class);
                        else if(selected_section_id.equals("3"))
                            i = new Intent(getActivity(), SleepActivity.class);
                        else if(selected_section_id.equals("4"))
                            i = new Intent(getActivity(), SkillsActivity.class);
                        else if(selected_section_id.equals("5"))
                            i = new Intent(getActivity(), VaccinationsRecordActivity.class);
                        else if(selected_section_id.equals("6"))
                            i = new Intent(getActivity(), FindDoctorActivity.class);
                        else if(selected_section_id.equals("7"))
                            i = new Intent(getActivity(), VaccinationsAdvNotificationsActivity.class);
                        else if(selected_section_id.equals("8"))
                            i = new Intent(getActivity(), VaccinationConfirmationReqNotificationsActivity.class);

                        startActivity(i);

                    }
                }
            });

//            if(selected_baby != null)
//                if(selected_baby.pic!=null)
//                    ImagesManager.loadNormalImageView(AppConfig.URL_CHILDREN_AVATAR+selected_baby.pic, baby_pic);
        }

        db = new DatabaseHelper(getActivity());

        getBabies();
        if(childSession.isSelected())
            getMainSections();
    }

    private void getBabies()
    {
        if(userSession.isLoggedIn()) {
            babies = db.getAllChildren();
            //if (babies.isEmpty())
             {
                UserSessionManager session = new UserSessionManager(getActivity());
                User user = session.getUserDetails();

                Map<String, String> map1 = new HashMap<>();
                map1.put("id", user.getId());
                JSONObject user_id = new JSONObject(map1);

                final APIManager api = new APIManager(APITypes.GET_BABIES);
                api.sendRequest(user_id, new CallBack() {
                    @Override
                    public void onResponse() {
                        if (api.get_babies_status) {
                            babies = api.babies;
                            if (babies != null) {
                                if (!babies.isEmpty())
                                    for (int i = 0; i < babies.size(); i++)
                                        babies.get(i).setSqlite_id(String.valueOf(db.createChild(babies.get(i))));
                                onGetBabies();
                            }
                        }
                    }
                });
            } //else onGetBabies();
        }
    }

    private void onGetBabies() {
        if (getActivity() != null) {
            SpinnerAdapter adapter = new custom_adapters.SpinnerAdapter(getActivity(),
                    android.R.layout.simple_spinner_item, babies);
            babies_list.setAdapter(adapter);
            if (selected_baby != null)
                for (int i = 0; i < babies_list.getAdapter().getCount(); i++)
                    if (selected_baby.equals(babies_list.getAdapter().getItem(i))) {
                        babies_list.setSelection(i);
                        birth.setText(selected_baby.birth_date);
                        break;
                    }
        }
    }

    private void getMainSections()
    {
        final APIManager api = new APIManager(APITypes.GET_MAIN_SECTIONS);
        api.sendRequest(new JSONObject(), new CallBack() {
            @Override
            public void onResponse() {
                if(api.get_main_sections_status)
                {
                    main_sections = api.main_sections;
                    if(main_sections != null) {
                        if(getActivity() != null) {
                            MainSectionsListAdapter adapter = new custom_adapters.MainSectionsListAdapter(getActivity(),
                                    android.R.layout.simple_list_item_1, main_sections);
                            main_sections_list.setAdapter(adapter);
                        }
                    }
                }
            }
        });
    }
}
