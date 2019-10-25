package com.example.amal.mybabyhealthcare.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.patient_review.ReviewActivity;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import Helpers.ImagesManager;
import custom_interfaces.CallBack;
import model.Doctor;
import model.DoctorProfileInfo;
import pref_managers.ChildSessionManager;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import server_side.AppConfig;

public class DoctorProfileActivity extends AppCompatActivity {

    Doctor doctor;
    DoctorProfileInfo profileInfo;
    Boolean request_sent = false;
    Integer link_id;
    Boolean has_reviewed = false;
    Integer review_id;

    ChildSessionManager childManager;
    UserSessionManager userManager;

    Button review, link;
    ImageView image;

    TextView patients, waiting, reviews, name, phone, email;
    RatingBar office, explaination;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(AppConfig.SOCKET_URL);
        } catch (URISyntaxException e) { e.printStackTrace(); }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("doctor");
        onInit();
    }

    void onInit()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        review = (Button) findViewById(R.id.doctor_prof_review);
        link = (Button) findViewById(R.id.doctor_prof_send_req);
        patients = (TextView) findViewById(R.id.doctor_prof_patients);
        waiting = (TextView) findViewById(R.id.doctor_prof_waiting);
        reviews = (TextView) findViewById(R.id.doctor_prof__review_num);
        office = (RatingBar) findViewById(R.id.doctor_pro_office);
        explaination = (RatingBar) findViewById(R.id.doctor_prof_explain);
        name = (TextView) findViewById(R.id.doctor_prof_name);
        phone = (TextView) findViewById(R.id.doctor_prof_phone);
        email = (TextView) findViewById(R.id.doctor_prof_email);
        image = (ImageView) findViewById(R.id.doctor_prof_image);

        childManager = new ChildSessionManager(DoctorProfileActivity.this);
        userManager = new UserSessionManager(DoctorProfileActivity.this);

        review.setVisibility(View.GONE);

        get_profile_info();
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
    protected void onStart() {
        super.onStart();
        mSocket.connect();
        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("doctor");
        onInit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        doctor = (Doctor) intent.getSerializableExtra("doctor");
        onInit();
    }

    void showprof()
    {
        if(!request_sent) {
            link.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            link.setText("Link");
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    link();
                }
            });
        }
        else
        {
            if(is_linked) {
                link.setText("Unregister");
                review.setVisibility(View.VISIBLE);
            }
            else
                link.setText("Delete request");

            link.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unlink();
                }
            });
        }

        if(!is_linked)
            review.setVisibility(View.GONE);

        if(!has_reviewed) {
            review.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            review.setText("Review");
            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    review();
                }
            });
        }
        else
        {
            review.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            review.setText("Delete Review");
            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unReview();
                }
            });
        }

        patients.setText(profileInfo.getPatients_count());
        waiting.setText(profileInfo.getWaiting_time() + " min");
        reviews.setText(profileInfo.getReviews_count());
        phone.setText(doctor.phone);
        email.setText(doctor.email);
        String temp = "Dr. " + doctor.first_name + " " + doctor.last_name;
        name.setText(temp);

        office.setRating(Float.valueOf(profileInfo.getOffice_cleanliness()));
        explaination.setRating(Float.valueOf(profileInfo.getExplination_clarity()));

        if(doctor.pic != null && !doctor.pic.isEmpty())
            ImagesManager.loadNormalImageView(AppConfig.URL_USER_AVATAR + doctor.pic, image);

        office.setEnabled(false);
        explaination.setEnabled(false);
    }

    void get_profile_info()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", doctor.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.GET_DOCTOR_PROFILE_INFO);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    profileInfo = api.doctorProfile;
                    is_linked();
                }
            }
        });
    }

    private void unReview() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", String.valueOf(review_id));
        map1.put("id_doctor", doctor.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.DELETE_REVIEW);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                Toast.makeText(DoctorProfileActivity.this, api.message, Toast.LENGTH_LONG).show();

                if(api.query_status) {
                    has_reviewed = false;
                    review_id = null;

                    //btn
                    review.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    review.setText("Review");
                    review.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            review();
                        }
                    });
                    review.invalidate();
                }
            }
        });
    }

    void review()
    {
        Intent i = new Intent(DoctorProfileActivity.this, ReviewActivity.class);
        i.putExtra("doctor", doctor);
        startActivity(i);
    }

    void link()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", childManager.getBabyDetails().getId());
        map1.put("id_doctor", doctor.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.LINK_TO_DOCTOR);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                Toast.makeText(DoctorProfileActivity.this, api.message, Toast.LENGTH_LONG).show();

                if (api.query_status) {
                    request_sent = true;
                    link_id = Integer.valueOf(api.new_id);
                    //btn
                    link.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    link.setText("Delete Request");
                    link.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            unlink();
                        }
                    });
                    link.invalidate();

                    sendNotification();
                }
            }
        });
    }

    void unlink()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id", String.valueOf(link_id));
        JSONObject id = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.UNLINK_TO_DOCTOR);
        api.sendRequest(id, new CallBack() {
            @Override
            public void onResponse() {
                Toast.makeText(DoctorProfileActivity.this, api.message, Toast.LENGTH_LONG).show();

                if (api.query_status) {
                    request_sent = false;
                    link_id = null;

                    //btn
                    link.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    link.setText("Link");
                    link.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            link();
                        }
                    });
                    link.invalidate();

                    review.setVisibility(View.GONE);
                    review.invalidate();

                    if(!is_linked)
                        delete_notification();
                }
            }
        });
    }

    void has_reviewed()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_user", userManager.getUserDetails().getId());
        map1.put("id_doctor", doctor.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.HAS_REVIEWED);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
                if (api.query_status) {
                    has_reviewed = api.has_reviewed;
                    if(has_reviewed)
                        review_id = api.review_id;
                    showprof();

                }
            }
        });
    }

    void request_sent()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", childManager.getBabyDetails().getId());
        map1.put("id_doctor", doctor.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.REQUEST_WAS_SENT);
        api.sendRequest(info, new CallBack() {
                    @Override
                    public void onResponse() {
                        if (api.query_status) {
                            request_sent = api.request_sent;
                            if(request_sent)
                                link_id = api.link_id;
                            showprof();
                        }
                    }
                }
        );
    }

    Boolean is_linked = false;
    void is_linked()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", childManager.getBabyDetails().getId());
        map1.put("id_doctor", doctor.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.IS_LINKED_TO_DOCTOR);
        api.sendRequest(info, new CallBack() {
                    @Override
                    public void onResponse() {
                        if (api.query_status) {
                            is_linked = api.is_linked;
                            if(is_linked) {
                                link_id = api.link_id;
                                request_sent = true;
                                has_reviewed();
                            }
                            else request_sent();
                        }
                    }
                }
        );
    }

    void sendNotification()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_user", userManager.getUserDetails().getId());
        map1.put("id_child", childManager.getBabyDetails().getId());
        map1.put("id_req", String.valueOf(link_id));
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.SEND_NEW_LINK_REQ_NOTIFICATION);
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

    void delete_notification()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("id_child", childManager.getBabyDetails().getId());
        map1.put("id_doctor", doctor.getId());
        JSONObject info = new JSONObject(map1);

        final APIManager api = new APIManager(APITypes.DELETE_NOTIFICATION);
        api.sendRequest(info, new CallBack() {
            @Override
            public void onResponse() {
            }
        });
    }

    private void attemptSend(JSONObject message) {
        mSocket.emit("new_message2", message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new_message2");
    }
}
