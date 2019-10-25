package pref_managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.ImageView;

import model.Baby;
import Helpers.ImagesManager;

/**
 * Created by Amal on 4/20/2016.
 */
public class ChildSessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Sharedpref file name
    private static final String PREF_NAME         = "pref_baby";

    // All Shared Preferences Keys
    private static final String KEY_IS_SELECTED    = "isSelected";
    public  static final String KEY_BABY_ID        = "babyId";
    public  static final String KEY_BABY_SQLITE_ID = "babySqliteId";
    public  static final String KEY_BABY_NAME      = "babyName";
    public  static final String KEY_BABY_LAST_NAME = "babyLastName";
    public  static final String KEY_BIRTH          = "birth";
    public  static final String KEY_GENDER         = "gender";
    public  static final String KEY_IMAGE          = "img";
    public  static final String KEY_IMAGE_URL      = "img_url";

    // Constructor
    public ChildSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    /**
     * Create selected baby session
     * */
    public void createBabySession(Baby baby){

        // Storing selected value as TRUE
        editor.putBoolean(KEY_IS_SELECTED, true);

        // Storing key values in pref
        editor.putString(KEY_BABY_ID, baby.getId());
        editor.putString(KEY_BABY_NAME, baby.first_name);
        editor.putString(KEY_BABY_LAST_NAME, baby.last_name);
        editor.putString(KEY_BIRTH, baby.birth_date);
        editor.putString(KEY_GENDER, baby.gender);
        editor.putString(KEY_IMAGE_URL, baby.pic);

        // commit changes
        editor.apply();
    }

    public void createBabySession(Baby baby, ImageView img){

        // Storing selected value as TRUE
        editor.putBoolean(KEY_IS_SELECTED, true);

        // Storing key values in pref
        editor.putString(KEY_BABY_ID, baby.getId());
        editor.putString(KEY_BABY_NAME, baby.first_name);
        editor.putString(KEY_BABY_LAST_NAME, baby.last_name);
        editor.putString(KEY_BIRTH, baby.birth_date);
        editor.putString(KEY_GENDER, baby.gender);

        String img_str = Base64.encodeToString(ImagesManager.getBytesFromImageView(img), 0);
        editor.putString(KEY_IMAGE, img_str);

        // commit changes
        editor.apply();
    }

    /**
     * Get stored session data
     * */
    public Baby getBabyDetails(){
        return new Baby(
                pref.getString(KEY_BABY_ID, null),
                pref.getString(KEY_BABY_SQLITE_ID, null),
                pref.getString(KEY_BABY_NAME, null),
                pref.getString(KEY_BABY_LAST_NAME, null),
                pref.getString(KEY_BIRTH, null),
                pref.getString(KEY_GENDER, null),
                pref.getString(KEY_IMAGE_URL, null)
        );
    }

    public byte[] getBabyImage()
    {
        if(pref.getString(KEY_IMAGE, null) != null) {
            byte[] imageAsBytes = Base64.decode(pref.getString(KEY_IMAGE, null).getBytes(), Base64.DEFAULT);
            return imageAsBytes;
        }

        return null;
    }

    /**
     * Clear session details
     * */
    public void deselectBaby(){
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isSelected(){
        return pref.getBoolean(KEY_IS_SELECTED, false);
    }
}
