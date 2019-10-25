package pref_managers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.ImageView;

import com.example.amal.mybabyhealthcare.account.LoginActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Helpers.ImagesManager;
import custom_interfaces.CallBack;
import model.User;
import server_side.APIManager;
import server_side.APITypes;

/**
 * Created by Amal on 4/8/2016.
 */
public class UserSessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    private static final String PREF_NAME      = "pref_user";
    private static final String KEY_IS_LOGIN   = "IsLoggedIn";
    public  static final String KEY_USER_ID    = "uid";
    public  static final String KEY_USER_ROLE  = "role_id";
    public  static final String KEY_USER_NAME  = "username";
    public  static final String KEY_First_NAME = "first_name";
    public  static final String KEY_LAST_NAME  = "last_name";
    public  static final String KEY_EMAIL      = "email";
    public  static final String KEY_BIRTH      = "birth";
    public  static final String KEY_GENDER     = "gender";
    public  static final String KEY_IMAGE      = "img";
    public static final String KEY_TOKEN_ID    = "token_id";
    public static final String KEY_HAS_TOKEN   = "has_token";
    public  static final String KEY_IMAGE_URL  = "img_url";


    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public void createLoginSession(User user){

        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getUsername());
        editor.putString(KEY_First_NAME, user.getFirst_name());
        editor.putString(KEY_LAST_NAME, user.getLast_name());
        editor.putString(KEY_USER_ROLE, user.getRole());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_BIRTH, user.getBirth_date());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_IMAGE_URL, user.pic);

        editor.apply();
    }

    public void createLoginSession(User user, ImageView img){
        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getUsername());
        editor.putString(KEY_First_NAME, user.getFirst_name());
        editor.putString(KEY_LAST_NAME, user.getLast_name());
        editor.putString(KEY_USER_ROLE, user.getRole());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_BIRTH, user.getBirth_date());
        editor.putString(KEY_GENDER, user.getGender());

        String img_str = Base64.encodeToString(ImagesManager.getBytesFromImageView(img), 0);
        editor.putString(KEY_IMAGE, img_str);

        editor.apply();
    }

    public boolean has_token()
    {
        return pref.getBoolean(KEY_HAS_TOKEN, false);
    }

    public void put_token(String id)
    {
        editor.putString(KEY_TOKEN_ID, id);
        editor.putBoolean(KEY_HAS_TOKEN, true);
        editor.apply();
    }


    public User getUserDetails(){
        return new User(
                pref.getString(KEY_USER_ID, null),
                pref.getString(KEY_USER_NAME, null),
                pref.getString(KEY_IMAGE_URL, null),
                pref.getString(KEY_EMAIL, null),
                pref.getString(KEY_First_NAME, null),
                pref.getString(KEY_LAST_NAME, null),
                pref.getString(KEY_BIRTH, null),
                pref.getString(KEY_GENDER, null),
                pref.getString(KEY_USER_ROLE, null),
                null
        );
    }

    public byte[] getUserImage()
    {
        if(pref.getString(KEY_IMAGE, null) != null) {
            byte[] imageAsBytes = Base64.decode(pref.getString(KEY_IMAGE, null).getBytes(), Base64.DEFAULT);
            return imageAsBytes;
        }

        return null;
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public void logoutUser(){
        ChildSessionManager childSession = new ChildSessionManager(_context);
        childSession.deselectBaby();

        Map<String, String> map1 = new HashMap<>();
        map1.put("id", pref.getString(KEY_TOKEN_ID, null));
        JSONObject json = new JSONObject(map1);
        APIManager api = new APIManager(APITypes.DELETE_DEVICE);
        api.sendRequest(json, new CallBack() {
            @Override
            public void onResponse() {
                editor.clear();
                editor.commit();

                Intent i = new Intent(_context, LoginActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                _context.startActivity(i);
            }
        });
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }
}
