package custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.amal.mybabyhealthcare.R;

import java.util.ArrayList;

import model.Baby;
import server_side.AppConfig;
import Helpers.ImagesManager;

/**
 * Created by Amal on 4/15/2016.
 */
public class SpinnerAdapter extends ArrayAdapter<Baby> implements android.widget.SpinnerAdapter {

    private final Context context;
    private final ArrayList<Baby> babies;

    public SpinnerAdapter(Context context,int layout, ArrayList<Baby> objects) {
        super(context, layout, objects);

        this.context = context;
        babies = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_spinner, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.spinner_baby_name);
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
        textView.setText(babies.get(position).first_name);

        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_spinner, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.spinner_baby_name);
        NetworkImageView imageView = (NetworkImageView) rowView.findViewById(R.id.spinner_baby_pic);
        textView.setTextColor(context.getResources().getColor(R.color.secondary_text_material_light));
        textView.setText(babies.get(position).first_name);

        imageView.setImageResource(R.drawable.baby_img_placeholder);

        if(babies.get(position).pic!=null)
            ImagesManager.loadNetworkImageView(AppConfig.URL_CHILDREN_AVATAR+babies.get(position).pic, imageView);

        return rowView;
    }

    @Override
    public Baby getItem(int position) {
        return babies.get(position);
    }
}
