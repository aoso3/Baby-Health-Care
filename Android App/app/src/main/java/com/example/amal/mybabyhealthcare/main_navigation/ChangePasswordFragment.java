package com.example.amal.mybabyhealthcare.main_navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import custom_interfaces.CallBack;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;

/**
 * Created by Amal on 4/15/2016.
 */
public class ChangePasswordFragment extends Fragment {

    Button save, cancle;
    EditText old_password, new_password;
    View rootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.change_password_layout, container, false);

        onInit();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        onInit();
    }

    private void onInit()
    {
        if(rootView != null) {
            save = (Button) rootView.findViewById(R.id.change_pass_save_btn);
            old_password = (EditText) rootView.findViewById(R.id.change_pass_old_txt);
            new_password = (EditText) rootView.findViewById(R.id.chnge_pass_new_txt);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSave_click();
                }
            });
        }
    }

    private void onSave_click()
    {
        Map<String, String> map = new HashMap<>();
        map.put("email", (new UserSessionManager(getActivity()).getUserDetails().getEmail()));
        map.put("old", old_password.getText().toString());
        map.put("new", new_password.getText().toString());
        final JSONObject data = new JSONObject(map);

        final APIManager api = new APIManager(APITypes.CHANGE_PASSWORD);
        api.sendRequest(data, new CallBack() {
            @Override
            public void onResponse() {
//                if (api.change_password_status) {
//                    Toast t = Toast.makeText(getActivity(), "updated successfully", Toast.LENGTH_LONG);
//                    t.show();
//                } else {
//                    Toast.makeText(getActivity(), api.error_message, Toast.LENGTH_LONG).show();
//                }
                Toast.makeText(getActivity(), api.message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
