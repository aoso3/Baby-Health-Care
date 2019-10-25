package com.example.amal.mybabyhealthcare.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.vaccinations.VaccenationConfirmationReqActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import custom_adapters.NotificationsListAdapter;
import custom_interfaces.CallBack;
import model.Notification;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;

/**
 * Created by Amal on 5/22/2016.
 */
public class VaccinationConfirmationReqNotificationsFragment extends Fragment {

    View rootView;

    ListView notifications_list;
    ArrayList<Notification> notifications;
    UserSessionManager userSessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.notifications_fragment, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        onInit();
    }

    void onInit()
    {
        if(rootView != null && getActivity() != null)
        {
            userSessionManager = new UserSessionManager(getActivity());

            notifications_list = (ListView) rootView.findViewById(R.id.advs_list);

            notifications_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getActivity(), VaccenationConfirmationReqActivity.class);
                    i.putExtra("notification", (Notification) (parent.getAdapter().getItem(position)));
                    startActivity(i);
                }
            });

            getRequests();
        }
    }

    void getRequests()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_user", userSessionManager.getUserDetails().getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_ALL_CONFIRM_VAC_REQ);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                if(api.query_status)
                {
                    if(api.notifications != null && getActivity()!=null)
                    {
                        notifications = api.notifications;
                        NotificationsListAdapter adapter = new NotificationsListAdapter(getActivity(), notifications);
                        notifications_list.setAdapter(adapter);
                    }
                }
                else Toast.makeText(getActivity(), api.message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
