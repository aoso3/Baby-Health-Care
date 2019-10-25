package com.example.amal.mybabyhealthcare.baby;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.main_navigation.MainActivity;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import Helpers.ConfirmationAlertManager;
import Helpers.DatePickerDialogManager;
import Helpers.ImagesManager;
import custom_interfaces.CallBack;
import model.Baby;
import model.User;
import pref_managers.ChildSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import server_side.AppConfig;
import sqlite_database_handeler.DatabaseHelper;

public class BabyProfileActivity extends Activity {

    Button cancle, save, change_pic, delete;
    EditText name, birth, last_name;
    RelativeLayout wall;
    ImageView shoes;
    ImageView baby_cover, baby_img;
    Uri image_uri;
    RadioButton male, female;

    Baby selected_baby;

    ChildSessionManager childSession;
    boolean image_changed = false;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_profile);

        childSession = new ChildSessionManager(BabyProfileActivity.this);
        if(childSession.isSelected())
            selected_baby = childSession.getBabyDetails();
    }

    @Override
    protected void onStart() {
        super.onStart();

        childSession = new ChildSessionManager(BabyProfileActivity.this);
        if(childSession.isSelected())
            selected_baby = childSession.getBabyDetails();

        onInit();
    }

    private void onInit()
    {
        cancle = (Button) findViewById(R.id.cancle_baby_prof_btn);
        save   = (Button) findViewById(R.id.save_baby_prof_btn);
        name   = (EditText) findViewById(R.id.baby_name_txt);
        birth  = (EditText) findViewById(R.id.baby_birth_txt);
        wall   = (RelativeLayout) findViewById(R.id.baby_prof_wall);
        shoes  = (ImageView) findViewById(R.id.baby_prof_shoes);
        baby_cover = (ImageView) findViewById(R.id.baby_cover);
        male   = (RadioButton) findViewById(R.id.baby_prof_male_rad);
        female = (RadioButton) findViewById(R.id.baby_prof_female_rad);
        change_pic = (Button) findViewById(R.id.change_baby_pic_btn);
        baby_img = (ImageView) findViewById(R.id.parent_image);
        last_name = (EditText) findViewById(R.id.baby_last_name_txt);
        delete = (Button) findViewById(R.id.delete_baby);

        birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    DatePickerDialogManager.showDialog(BabyProfileActivity.this, birth);
            }
        });

        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogManager.showDialog(BabyProfileActivity.this, birth);
            }
        });

        if(selected_baby != null)
        {
            name.setText(selected_baby.first_name);
            last_name.setText(selected_baby.last_name);
            birth.setText(selected_baby.birth_date);

            if(!image_changed) {
                if(childSession.getBabyDetails().pic!=null && !childSession.getBabyDetails().pic.isEmpty())
                    ImagesManager.loadNormalImageView(AppConfig.URL_CHILDREN_AVATAR + childSession.getBabyDetails().pic, baby_img);
            }

            if (selected_baby.gender.equals(User.Gender.FEMALE))
            {
                wall.setBackgroundColor(getResources().getColor(R.color.girlColor));
                shoes.setImageResource(R.drawable.shoes_girl);
                baby_cover.setImageResource(R.drawable.pink);
                male.setChecked(false);
                female.setChecked(true);
            }
            else
            {
                wall.setBackgroundColor(getResources().getColor(R.color.boyColor));
                shoes.setImageResource(R.drawable.shoes_boy);
                baby_cover.setImageResource(R.drawable.blue);
                male.setChecked(true);
                female.setChecked(false);
            }
        }

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BabyProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave_click();
            }
        });

        change_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(BabyProfileActivity.this);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete_click();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            image_uri = Crop.getOutput(result);
            baby_img.setImageURI(image_uri);
            image_changed = true;
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onSave_click()
    {
        if(validate()) {
            if (selected_baby != null) {
                if (!image_changed) {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("id", selected_baby.getId());
                    map1.put("first_name", name.getText().toString());
                    map1.put("last_name", last_name.getText().toString());
                    map1.put("birth_date", birth.getText().toString());
                    if (male.isChecked())
                        map1.put("gender", User.Gender.MALE);
                    else
                        map1.put("gender", User.Gender.FEMALE);

                    JSONObject baby_info = new JSONObject(map1);

                    final APIManager api = new APIManager(APITypes.UPDATE_BABY);

                    api.sendRequest(baby_info, new CallBack() {
                        @Override
                        public void onResponse() {
                            Toast.makeText(BabyProfileActivity.this, api.message, Toast.LENGTH_LONG).show();
                            if (api.query_status) {
                                selected_baby.first_name = name.getText().toString();
                                selected_baby.last_name = last_name.getText().toString();
                                selected_baby.birth_date = birth.getText().toString();

                                //update session
                                childSession.createBabySession(api.new_baby);
                            }
                        }
                    });

                    //update sqlite
                    db = new DatabaseHelper(this);
                    db.updateBaby(selected_baby);
                } else {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("id", selected_baby.getId());
                    map1.put("first_name", name.getText().toString());
                    map1.put("last_name", last_name.getText().toString());
                    map1.put("birth_date", birth.getText().toString());
                    map1.put("img_key", "baby_avatar");
                    if (male.isChecked())
                        map1.put("gender", User.Gender.MALE);
                    else
                        map1.put("gender", User.Gender.FEMALE);

                    JSONObject baby_info = new JSONObject(map1);

                    InputStream iStream = null;
                    try {
                        iStream = getContentResolver().openInputStream(image_uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    byte[] inputData = ImagesManager.getBytesFromUri(iStream);

                    Map<String, byte[]> map3 = new HashMap<>();
                    map3.put("baby_avatar", inputData);
                    final APIManager api = new APIManager(APITypes.UPDATE_BABY);
                    api.sendMultiPartsRequest(baby_info, map3, new CallBack() {
                        @Override
                        public void onResponse() {
                            Toast.makeText(BabyProfileActivity.this, api.message, Toast.LENGTH_LONG).show();
                            if (api.query_status) {
                                //update session
                                childSession.createBabySession(api.new_baby);
                            }
                        }
                    });
                }
            } else
                Toast.makeText(BabyProfileActivity.this, "No baby is selected", Toast.LENGTH_LONG).show();
        }
    }

    private void onDelete_click()
    {
        ConfirmationAlertManager.showDialog(this, "Delete child",
                "Are you sure you want to delete this child!, you will lose all data.", new CallBack() {
                    @Override
                    public void onResponse() {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", selected_baby.getId());
                        JSONObject data = new JSONObject(map);

                        final APIManager api = new APIManager(APITypes.DELETE_BABY);
                        api.sendRequest(data, new CallBack() {
                            @Override
                            public void onResponse() {
                                Toast.makeText(BabyProfileActivity.this, api.message, Toast.LENGTH_LONG).show();
                                if (api.query_status) {
                                    childSession.deselectBaby();
                                    finish();
                                }
                            }
                        });
                    }
                });
    }

    boolean validate()
    {
        if(name.getText().toString().isEmpty() || birth.getText().toString().isEmpty()
                || last_name.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
