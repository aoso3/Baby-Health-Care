package com.example.amal.mybabyhealthcare;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import Helpers.VolleyMultipartRequest;
import application_controller.ApplicationController;
import server_side.AppConfig;

class AppHelper {

    /**
     * Turn drawable resource into byte array.
     *
     * @param context parent context
     * @param id      drawable resource id
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Turn drawable into byte array.
     *
     * @param drawable data
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

public class NodeTest extends Activity {

    Button send;
    EditText mInputMessageView;

//    private Socket mSocket;
//    {
//        try {
//            mSocket = IO.socket("http://192.168.1.2:3000");
//        } catch (URISyntaxException e) { e.printStackTrace(); }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_test);

//        mSocket.connect();

        mInputMessageView = (EditText) findViewById(R.id.mInputMessageView);
        send = (Button) findViewById(R.id.nnn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileAccount();
            }
        });
    }

    private void saveProfileAccount() {
        // loading or check internet connection or something...
        // ... then
        String url = AppConfig.LOGIN_API + "test";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(NodeTest.this, new String(response.data), Toast.LENGTH_LONG).show();
                Log.v("amal", response.toString());
//                String resultResponse = new String(response.data);
//                try {
//                    JSONObject result = new JSONObject(resultResponse);
//                    String status = result.getString("status");
//                    String message = result.getString("message");
//
//                    if (status.equals("")) {
//                        // tell everybody you have succed upload image and post strings
//                        Log.i("Messsage", message);
//                    } else {
//                        Log.i("Unexpected", message);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                params.put("name", "amal");
                params.put("location", "Damascus");
                params.put("about", "aaaa");
                params.put("contact", "123456");
                JSONObject json = new JSONObject(params);

                Map<String, String> json_data = new HashMap<>();
                json_data.put("json_data", json.toString());
                return json_data;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("avatar", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), getResources().getDrawable(R.drawable.avatar)), "image/jpeg"));
                params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), getResources().getDrawable(R.drawable.baby_avatar)), "image/jpeg"));

                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(multipartRequest);
    }

    private void attemptSend() {
        JSONObject message = null;
        try {
            message = new JSONObject("{ name: '"+mInputMessageView.getText().toString().trim()+"' }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        if (TextUtils.isEmpty(message)) {
//            return;
//        }

        mInputMessageView.setText("");
//        mSocket.emit("new_message2", message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        mSocket.disconnect();
//        mSocket.off("new_message2");
    }
}
