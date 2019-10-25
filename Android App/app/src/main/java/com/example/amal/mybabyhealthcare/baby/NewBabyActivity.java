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

import custom_interfaces.CallBack;
import model.User;
import server_side.APIManager;
import server_side.APITypes;
import Helpers.ImagesManager;
import Helpers.DatePickerDialogManager;
import pref_managers.UserSessionManager;

public class NewBabyActivity extends Activity {

    Button save, cancle, add_photo;
    EditText name, last_name, birth;
    RadioButton male, female;
    Uri image_uri;
    ImageView baby_image;

    boolean image_changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_baby);

        onInit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onInit();
    }

    private void onInit()
    {
        save   = (Button) findViewById(R.id.save_new_baby_btn);
        cancle = (Button) findViewById(R.id.new_baby_cancle);
        name   = (EditText) findViewById(R.id.new_baby_name_btn);
        last_name = (EditText) findViewById(R.id.new_baby_last_name_btn);
        birth  = (EditText) findViewById(R.id.new_baby_birth_btn);
        male   = (RadioButton) findViewById(R.id.add_baby_male_rad);
        female = (RadioButton) findViewById(R.id.add_baby_female_rad);
        add_photo = (Button) findViewById(R.id.add_new_baby_pic_btn);
        baby_image = (ImageView) findViewById(R.id.new_baby_img);

        birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    DatePickerDialogManager.showDialog(NewBabyActivity.this, birth);
            }
        });

        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogManager.showDialog(NewBabyActivity.this, birth);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewBabyActivity.this, MainActivity.class);
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

        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(NewBabyActivity.this);
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
            baby_image.setImageURI(image_uri);
            image_changed = true;
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onSave_click()
    {
        if(validate()) {
            if (!image_changed) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("parent_id", getParentID());
                map1.put("first_name", name.getText().toString());
                map1.put("last_name", last_name.getText().toString());
                map1.put("birth_date", birth.getText().toString());
                if (male.isChecked())
                    map1.put("gender", User.Gender.MALE);
                else
                    map1.put("gender", User.Gender.FEMALE);

                JSONObject baby_info = new JSONObject(map1);

                final APIManager api = new APIManager(APITypes.ADD_BABY);

                api.sendRequest(baby_info, new CallBack() {
                    @Override
                    public void onResponse() {
                        Toast.makeText(NewBabyActivity.this, api.message, Toast.LENGTH_LONG).show();
                        if (api.query_status)
                            finish();
                    }
                });
            } else {
                Map<String, String> map1 = new HashMap<>();
                map1.put("parent_id", getParentID());
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
                final APIManager api = new APIManager(APITypes.ADD_BABY);
                api.sendMultiPartsRequest(baby_info, map3, new CallBack() {
                    @Override
                    public void onResponse() {
                        Toast.makeText(NewBabyActivity.this, api.message, Toast.LENGTH_LONG).show();
                        if (api.query_status) {
                            finish();
                        }
                    }
                });

            }
        }
        //get sql_id
        //insert to sqlite
    }

    private String getParentID()
    {
        UserSessionManager session = new UserSessionManager(NewBabyActivity.this);
        return session.getUserDetails().getId();
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
