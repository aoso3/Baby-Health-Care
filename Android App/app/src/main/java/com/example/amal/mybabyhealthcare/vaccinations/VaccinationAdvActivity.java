package com.example.amal.mybabyhealthcare.vaccinations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import Helpers.ImagesManager;
import custom_interfaces.CallBack;
import model.Notification;
import model.VaccinationAdv;
import pref_managers.ChildSessionManager;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import server_side.AppConfig;

public class VaccinationAdvActivity extends AppCompatActivity {

    VaccinationAdv adv;
    Boolean is_registered;
    Integer registeration_id;
    ChildSessionManager childManager;
    UserSessionManager userManager;
    Notification notif;
    ImageView image;

    TextView status, vacc, doctor, activation_date, closed_date, text, price, age_to, age_from;
    Button btn;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(AppConfig.SOCKET_URL);
        } catch (URISyntaxException e) { e.printStackTrace(); }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_adv);

        Intent i = getIntent();
        notif = (Notification) i.getSerializableExtra("notification");
        Log.v("notif", notif.toString());
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        notif = (Notification) intent.getSerializableExtra("notification");
        onInit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSocket.connect();
        Intent i = getIntent();
        notif = (Notification) i.getSerializableExtra("notification");
        onInit();
    }

    void onInit()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        status = (TextView) findViewById(R.id.adv_status);
        vacc = (TextView) findViewById(R.id.adv_vacc);
        doctor = (TextView) findViewById(R.id.adv_doctor);
        activation_date = (TextView) findViewById(R.id.adv_activation_date);
        closed_date = (TextView) findViewById(R.id.adv_closed_date);
        text = (TextView) findViewById(R.id.adv_text);
        price = (TextView) findViewById(R.id.adv_price);
        age_to = (TextView) findViewById(R.id.adv_to_age);
        age_from = (TextView) findViewById(R.id.adv_from_age);
        btn = (Button) findViewById(R.id.adv_reg_btn);
        image = (ImageView) findViewById(R.id.adv_pic);

        if(!notif.getImg().isEmpty())
            ImagesManager.loadNormalImageView(AppConfig.URL_NOTIFICATION_ICO + notif.getImg(), image);
        else
            image.setImageResource(R.drawable.vaccination);

        childManager = new ChildSessionManager(VaccinationAdvActivity.this);
        userManager = new UserSessionManager(VaccinationAdvActivity.this);
        see_notification();
        get_adv();
    }

    void showAdv()
    {
        if(adv.getStatus().equals(VaccinationAdv.AdvStatus.OPEN)) {
            status.setText("Active");
        }
        else {
            status.setText("Closed!");
            btn.setVisibility(View.GONE);
        }

        if(!is_registered) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    register();
                }
            });
        }
        else
        {
            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btn.setText("Unregister");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unRegister();
                }
            });
        }

        String temp;
        doctor.setText(notif.getText());

        temp = "Vaccination: " + adv.getVaccination().name + ".";
        vacc.setText(temp);

        temp = "Activation date: " + adv.getActivation_date();
        activation_date.setText(temp);

        temp = "Closed date: " + adv.getClose_date();
        closed_date.setText(temp);

        text.setText(adv.getText());

        temp = "Price: " + adv.getPrice();
        price.setText(temp);

        temp = "From Age: " + adv.getAge_from();
        age_from.setText(temp);

        temp = "To Age: " + adv.getAge_to();
        age_to.setText(temp);
    }

    void get_adv()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", notif.getAdditional_data());
        JSONObject id = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_VAC_ADV);
        api.sendRequest(id, new CallBack() {
            @Override
            public void onResponse() {
                if(api.query_status)
                {
                    if(api.adv != null)
                    {
                        adv = api.adv;
                        Log.v("adv", adv.toString());
                        is_registered();
                    }
                }
            }
        });
    }

    void is_registered() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", notif.getId_child());
        map1.put("id_adv", notif.getAdditional_data());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.IS_REGISTERED_FOR_ADV);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    is_registered = api.is_registered;
                    if(is_registered)
                        registeration_id = api.registeration_id;
                    Log.v("is_registered", is_registered.toString());
                    showAdv();
                }
            }
        });
    }

    void register() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", notif.getId_child());
        map1.put("id_adv", notif.getAdditional_data());
        JSONObject registration = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.REGISTER_FOR_VAC_ADV);
        api.sendRequest(registration, new CallBack() {
            @Override
            public void onResponse() {
                Toast.makeText(VaccinationAdvActivity.this, api.message, Toast.LENGTH_LONG).show();

                if (api.query_status) {
                    is_registered = true;
                    registeration_id = Integer.valueOf(api.new_id);

                    //btn
                    btn.setText("Unregister");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            unRegister();
                        }
                    });
                    btn.invalidate();

                    sendNotification();
                }
            }
        });
    }

    void unRegister()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", String.valueOf(registeration_id));
        JSONObject id = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.DELETE_REG_FOR_VAC_ADV);
        api.sendRequest(id, new CallBack() {
            @Override
            public void onResponse() {
                Toast.makeText(VaccinationAdvActivity.this, api.message, Toast.LENGTH_LONG).show();

                if(api.query_status) {
                    is_registered = false;
                    registeration_id = null;

                    //btn
                    btn.setText("Register");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            register();
                        }
                    });
                    btn.invalidate();
                }
            }
        });
    }

    void sendNotification()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_user", userManager.getUserDetails().getId());
        map1.put("id_child", notif.getId_child());
        map1.put("id_adv", notif.getAdditional_data());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.SEND_NEW_VAC_REQ_NOTIFICATION);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                if(api.query_status)
                {
                    attemptSend(api.notification_message);
                }
            }
        });
    }

    private void attemptSend(JSONObject message) {
        mSocket.emit("new_message2", message);
    }

    void see_notification()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_user", userManager.getUserDetails().getId());
        map1.put("id_notification", notif.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.SEE_NOTIFICATION);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {}
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new_message2");
    }
}
