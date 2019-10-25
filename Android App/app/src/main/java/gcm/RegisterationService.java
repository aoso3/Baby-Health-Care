package gcm;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import custom_interfaces.CallBack;
import pref_managers.UserSessionManager;
import server_side.APIManager;
import server_side.APITypes;

/**
 * Created by Amal on 5/17/2016.
 */
public class RegisterationService extends IntentService {

    private UserSessionManager userManager;

    public RegisterationService()
    {
        this("RegisterationService");
    }

    public RegisterationService(String name)
    {
        super(name);
    }

    //Get a token for this device and send it to the server
    @Override
    protected void onHandleIntent(Intent intent) {
        userManager = new UserSessionManager(this);
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            if(!userManager.has_token()) {
                final String token = instanceID.getToken("802027307986", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Map<String, String> map = new HashMap<>();
                map.put("id_user", userManager.getUserDetails().getId());
                map.put("token", token);
                final JSONObject device_info = new JSONObject(map);

                final APIManager api = new APIManager(APITypes.ADD_DEVICE);

                api.sendRequest(device_info, new CallBack() {
                    @Override
                    public void onResponse() {
                        if (api.add_device_status)
                            userManager.put_token(api.token_id);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
