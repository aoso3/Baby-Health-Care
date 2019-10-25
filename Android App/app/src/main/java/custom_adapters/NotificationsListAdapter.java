package custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amal.mybabyhealthcare.R;

import java.util.ArrayList;

import model.Notification;
import server_side.AppConfig;
import Helpers.ImagesManager;

/**
 * Created by Amal on 5/22/2016.
 */
public class NotificationsListAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<Notification> notifications;

    public NotificationsListAdapter(Context context, ArrayList<Notification> objects) {
        super(context, R.layout.notification_list_item, objects);

        this.context = context;
        notifications = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.notification_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.notif_item_title);
        textView.setText(notifications.get(position).getTitle());

        TextView textView2 = (TextView) rowView.findViewById(R.id.notif_item_text);
        textView2.setText(notifications.get(position).getText());

        ImageView img = (ImageView) rowView.findViewById(R.id.notif_item_img);
        if(notifications.get(position).getImg() != null && !notifications.get(position).getImg().isEmpty())
            ImagesManager.loadNormalImageView(AppConfig.URL_NOTIFICATION_ICO + notifications.get(position).getImg(), img);
        else
            img.setImageResource(R.drawable.app_icon);

        TextView textView3 = (TextView) rowView.findViewById(R.id.notif_new);
        if(notifications.get(position).is_seen.equals("1"))
            textView3.setVisibility(View.GONE);

        return rowView;
    }

    @Override
    public Notification getItem(int position) {
        return notifications.get(position);
    }
}
