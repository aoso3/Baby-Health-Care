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

import model.Doctor;
import server_side.AppConfig;
import Helpers.ImagesManager;

/**
 * Created by Amal on 5/21/2016.
 */
public class DoctorsListItemAdapter extends ArrayAdapter<Doctor> {
    private final Context context;
    private final ArrayList<Doctor> doctors;

    public DoctorsListItemAdapter(Context context, ArrayList<Doctor> objects) {
        super(context, R.layout.doctors_list_item, objects);

        this.context = context;
        doctors = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.doctors_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.doctors_list_item_doctor_name);
        String name = doctors.get(position).first_name + " " + doctors.get(position).last_name;
        textView.setText(name);

        TextView textView1 = (TextView) rowView.findViewById(R.id.doctors_list_item_doctor_points);
        textView1.setText(doctors.get(position).rate);

        ImageView img = (ImageView) rowView.findViewById(R.id.doctors_list_item_doctor_photo);

        if(doctors.get(position).pic != null && !doctors.get(position).pic.isEmpty())
            ImagesManager.loadNormalImageView(AppConfig.URL_USER_AVATAR + doctors.get(position).pic, img);
        else
            img.setImageResource(R.drawable.user_icon);

        return rowView;
    }

    @Override
    public Doctor getItem(int position) {
        return doctors.get(position);
    }
}
