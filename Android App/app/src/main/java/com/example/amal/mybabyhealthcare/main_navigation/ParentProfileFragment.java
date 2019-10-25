package com.example.amal.mybabyhealthcare.main_navigation;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import Helpers.DatePickerDialogManager;
import Helpers.ImagesManager;
import custom_interfaces.CallBack;
import model.User;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;
import server_side.AppConfig;

/**
 * Created by Amal on 4/12/2016.
 */
public class ParentProfileFragment extends Fragment {

    Button save;
    public Button change_pic;
    EditText name, last_name, email, birth;
    public ImageView image;
    View rootView = null;
    RadioButton male, female;
    User logged_user;
    UserSessionManager session;

    boolean image_changed = false;
    public Uri image_uri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.parent_profile, container, false);

        onInit();

        return rootView;
    }

    private void onInit()
    {
        session = new UserSessionManager(getActivity());
        logged_user = session.getUserDetails();

        if(rootView != null)
        {
            save = (Button) rootView.findViewById(R.id.save_parent_prof_update);
            change_pic = (Button) rootView.findViewById(R.id.change_parent_pic_btn);
            name   = (EditText) rootView.findViewById(R.id.parent_name_txt);
            last_name = (EditText) rootView.findViewById(R.id.parent_last_name_txt);
            email  = (EditText) rootView.findViewById(R.id.parent_email_txt);
            birth  = (EditText) rootView.findViewById(R.id.parent_birth_txt);
            male   = (RadioButton) rootView.findViewById(R.id.parent_prof_male_rad);
            female = (RadioButton) rootView.findViewById(R.id.parent_prof_female_rad);
            image = (ImageView) rootView.findViewById(R.id.parent_image);

            email.setEnabled(false);

            if(logged_user.pic != null)
                ImagesManager.loadNormalImageView(AppConfig.URL_USER_AVATAR + logged_user.pic, image);


            birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus)
                        DatePickerDialogManager.showDialog(getActivity(), birth);
                }
            });

            birth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialogManager.showDialog(getActivity(), birth);
                }
            });

            showUserData();

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSave_click();
                }
            });

            change_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Crop.pickImage(getActivity());
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        //onInit();
    }

    private void onSave_click()
    {
        if(validate()) {
            if (!image_changed) {
                Map<String, String> map = new HashMap<>();
                map.put("id", logged_user.getId());
                map.put("first_name", name.getText().toString());
                map.put("last_name", last_name.getText().toString());
                map.put("birth_date", birth.getText().toString());
                if (male.isChecked())
                    map.put("gender", User.Gender.MALE);
                else
                    map.put("gender", User.Gender.FEMALE);
                final JSONObject user = new JSONObject(map);

                final APIManager api = new APIManager(APITypes.UPDATE_PARENT);

                api.sendRequest(user, new CallBack() {
                    @Override
                    public void onResponse() {
                        if (api.query_status) {
                            logged_user = api.loggedin_user;

                            //update session
                            User new_user = session.getUserDetails();
                            new_user.setBirth_date(birth.getText().toString());
                            new_user.setFirst_name(name.getText().toString());
                            new_user.setGender(last_name.getText().toString());
                            new_user.setLast_name(birth.getText().toString());
                            UserSessionManager session = new UserSessionManager(getActivity());
                            session.createLoginSession(api.loggedin_user);
                        }
                        Toast.makeText(getActivity(), api.message, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("id", logged_user.getId());
                map.put("first_name", name.getText().toString());
                map.put("last_name", last_name.getText().toString());
                map.put("birth_date", birth.getText().toString());
                map.put("img_key", "user_avatar");
                if (male.isChecked())
                    map.put("gender", User.Gender.MALE);
                else
                    map.put("gender", User.Gender.FEMALE);
                final JSONObject user = new JSONObject(map);

                InputStream iStream = null;
                try {
                    if (getActivity() != null)
                        iStream = getActivity().getContentResolver().openInputStream(image_uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final byte[] inputData = ImagesManager.getBytesFromUri(iStream);

                Map<String, byte[]> map3 = new HashMap<>();
                map3.put("user_avatar", inputData);
                final APIManager api = new APIManager(APITypes.UPDATE_PARENT);
                api.sendMultiPartsRequest(user, map3, new CallBack() {
                    @Override
                    public void onResponse() {
                        if (getActivity() != null) {
                            if (api.query_status) {
                                logged_user = api.loggedin_user;

                                //update session
                                User new_user = session.getUserDetails();
                                new_user.setBirth_date(birth.getText().toString());
                                new_user.setFirst_name(name.getText().toString());
                                new_user.setGender(last_name.getText().toString());
                                new_user.setLast_name(birth.getText().toString());
                                UserSessionManager session = new UserSessionManager(getActivity());
                                session.createLoginSession(api.loggedin_user);
                            }
                            Toast.makeText(getActivity(), api.message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    private void showUserData()
    {
        name.setText(logged_user.getFirst_name());
        last_name.setText(logged_user.getLast_name());
        email.setText(logged_user.getEmail());
        birth.setText(logged_user.getBirth_date());
        if(logged_user.getGender().equals(User.Gender.MALE))
            male.setChecked(true);
        else
            female.setChecked(true);

//        byte[] img_bytes = session.getUserImage();
//        if(img_bytes != null)
//            image.setImageBitmap(BitmapFactory.decodeByteArray(img_bytes, 0, img_bytes.length));
////        else {
////            Bitmap icon = BitmapFactory.decodeResource(getResources(),
////                    R.drawable.user_icon);
////            image.setImageBitmap(icon);
////            //ImagesManager.loadNormalImageView(AppConfig.URL_USER_AVATAR + "user.png", image);
////        }
    }

    boolean validate()
    {
        if(name.getText().toString().isEmpty() || birth.getText().toString().isEmpty()
                || last_name.getText().toString().isEmpty())
        {
            Toast.makeText(getActivity(), "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
