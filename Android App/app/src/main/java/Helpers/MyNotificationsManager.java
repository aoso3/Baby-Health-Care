package Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.main_navigation.MainActivity;
import com.example.amal.mybabyhealthcare.vaccinations.VaccenationConfirmationReqActivity;
import com.example.amal.mybabyhealthcare.vaccinations.VaccinationAdvActivity;

import model.Notification;
import server_side.AppConfig;

/**
 * Created by Amal on 5/22/2016.
 */
public class MyNotificationsManager {

    public static void pushNotification(Context context, Notification notif)
    {
        Intent intent = new Intent(context, MainActivity.class);
        if(notif.getType().equals(Notification.NotificationTypes.NEW_ADV))
            intent = new Intent(context, VaccinationAdvActivity.class);

        if(notif.getType().equals(Notification.NotificationTypes.VACCINATION_CONFIRMATION))
            intent = new Intent(context, VaccenationConfirmationReqActivity.class);

        intent.putExtra("notification", notif);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent,0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = ImagesManager.getBitmapFromURL(AppConfig.URL_NOTIFICATION_ICO + notif.getImg());

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.notif_ico)
                .setAutoCancel(true)
                .setContentTitle(notif.getTitle())
                .setContentText(notif.getText())
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.valueOf(notif.getId()), notificationBuilder.build());
    }
}
