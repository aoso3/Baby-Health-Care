package com.example.amal.mybabyhealthcare.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.main_navigation.MainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import custom_interfaces.CallBack;
import server_side.APIManager;
import server_side.APITypes;
import pref_managers.UserSessionManager;
import model.User;

public class RegistrationActivity extends AppCompatActivity {

    Button link_to_log_btn, reg_btn;
    EditText input_email, input_name, last_name, input_password, input_confirm_password;
    RadioButton male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        onInit();
    }

    private void onInit()
    {
        link_to_log_btn = (Button) findViewById(R.id.btnLinkToLoginScreen);
        reg_btn = (Button) findViewById(R.id.btnRegister);
        input_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        input_email = (EditText) findViewById(R.id.reg_email);
        input_password = (EditText) findViewById(R.id.reg_password);
        input_confirm_password = (EditText) findViewById(R.id.reg_confirm_pass_input);
        male = (RadioButton) findViewById(R.id.reg_male_rad);
        female = (RadioButton) findViewById(R.id.reg_female_rad);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_register();
            }
        });

        link_to_log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_loginLink();
            }
        });
    }

    private void onClick_register()
    {
        if(validate()) {
            if (input_confirm_password.getText().toString().equals(input_password.getText().toString())) {
                Map<String, String> map = new HashMap<>();
                map.put("first_name", input_name.getText().toString());
                map.put("last_name", last_name.getText().toString());
                map.put("email", input_email.getText().toString());
                map.put("password", input_password.getText().toString());
                map.put("id_role", "1");
                if (male.isChecked())
                    map.put("gender", "male");
                else
                    map.put("gender", "female");
                final JSONObject user = new JSONObject(map);

                final APIManager api = new APIManager(APITypes.REGISTER);

                api.sendRequest(user, new CallBack() {
                    @Override
                    public void onResponse() {
                        if (api.query_status) {
                            User user = api.loggedin_user;
                            UserSessionManager session = new UserSessionManager(RegistrationActivity.this);
                            session.createLoginSession(user);

                            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } else {
                            Toast.makeText(RegistrationActivity.this, api.message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else
                Toast.makeText(RegistrationActivity.this, input_confirm_password.getText() + "  " + input_password.getText(), Toast.LENGTH_LONG).show();
        }
    }

    private void onClick_loginLink()
    {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    boolean validate()
    {
        if(input_name.getText().toString().isEmpty() || last_name.getText().toString().isEmpty()
                || input_email.getText().toString().isEmpty() || input_password.getText().toString().isEmpty()
                || input_confirm_password.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid empty fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
