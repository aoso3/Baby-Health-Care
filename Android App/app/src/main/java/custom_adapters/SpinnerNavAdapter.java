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

import model.SpinnerNavItem;

/**
 * Created by Amal on 4/17/2016.
 */
public class SpinnerNavAdapter extends ArrayAdapter<SpinnerNavItem> implements android.widget.SpinnerAdapter {

    private final Context context;
    private final ArrayList<SpinnerNavItem> items;

    public SpinnerNavAdapter(Context context, ArrayList<SpinnerNavItem> objects) {
        super(context, R.layout.support_simple_spinner_dropdown_item, objects);

        this.context = context;
        items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.spinner_nav_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.spinner_item_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.spinner_item_ico);

        textView.setText(items.get(position).getTitle());
        imageView.setImageResource(items.get(position).getIcon());

        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.spinner_nav_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.spinner_item_title);
        textView.setTextColor(context.getResources().getColor(R.color.secondary_text_material_light));
        ImageView imageView = (ImageView) rowView.findViewById(R.id.spinner_item_ico);
        View wall = rowView.findViewById(R.id.spinner_nav_item_wall);
        wall.setBackgroundColor(context.getResources().getColor(R.color.white));
        textView.setText(items.get(position).getTitle());
        imageView.setImageResource(items.get(position).getIcon());

        return rowView;
    }

    @Override
    public SpinnerNavItem getItem(int position) {
        return items.get(position);
    }
}
