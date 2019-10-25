package services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.main_navigation.MainActivity;

import java.util.ArrayList;

import date.CustomDate;
import date.Period;
import model.Baby;
import model.Vaccination;
import sqlite_database_handeler.DatabaseHelper;

/**
 * Created by Amal on 5/6/2016.
 */
public class BaseVaccinationNotifyService extends IntentService {

    private DatabaseHelper db;

    public BaseVaccinationNotifyService()
    {
        super(BaseVaccinationNotifyService.class.getSimpleName());

        db = new DatabaseHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<Baby> babies = db.getAllChildren();

        for (int i = 0 ; i < babies.size(); i++)
        {
            int[] arr = checkForVaccination(babies.get(i));
            if(arr.length != 0)
                makeNotificationForVaccination(babies.get(i), arr);
        }
    }

    private int childAgeMonths(Baby b)
    {
        CustomDate birthDate = new CustomDate(b.birth_date);
        Period p = birthDate.getPeriod();
        return  p.getYear()*12+p.getMonth();
    }

    private int[] checkForVaccination(Baby b)
    {
        ArrayList<Vaccination> Vs = db.getAllVaccination();
        ArrayList<Integer> Vs_ids = new ArrayList<>();
        for (int i = 0; i < Vs.size(); i++)
        {
            if(Integer.valueOf(Vs.get(i).age_from) <= childAgeMonths(b)) {
                db.createVaccinationShouldChildTake(Vs.get(i), b);
                Vs_ids.add(Integer.valueOf(Vs.get(i).getId()));
            }
        }

        int[] Vs_ids_arr = new int[Vs_ids.size()];
        for (int i = 0; i < Vs_ids.size(); i++)
            Vs_ids_arr[i] = Vs_ids.get(i);

        return Vs_ids_arr;
    }

    private void makeNotificationForVaccination(Baby b, int[] Vs_ids)
    {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent i = new Intent(this, MainActivity.class);///not main
        i.putExtra("Vs_ids_arr", Vs_ids);

        PendingIntent pending = PendingIntent.getActivity(this, 0, i, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getString(R.string.new_vaccination_notification_title))
                .setContentText(getString(R.string.new_vaccination_notification_text) + " " + b.first_name)
                .setSmallIcon(R.drawable.baby_avatar)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pending)
                .setSound(alarmSound)
                .build();

        notificationManager.notify(11, notification);
    }
}
