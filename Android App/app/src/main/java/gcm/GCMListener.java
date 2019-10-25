package gcm;

import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;

import Helpers.MyNotificationsManager;
import model.Notification;

/**
 * Created by Amal on 5/17/2016.
 */
public class GCMListener extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String message = data.getString("message");
        Log.v("GCM_message", message);
        Notification newNotif = getNotification(message);
        if(newNotif != null)
            MyNotificationsManager.pushNotification(this, newNotif);
    }

    private Notification getNotification(String message) {

        ObjectMapper objMap = new ObjectMapper();
        try {
            return objMap.readValue(message, Notification.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("HelloGCM")
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(i++, notificationBuilder.build());
    }
}
