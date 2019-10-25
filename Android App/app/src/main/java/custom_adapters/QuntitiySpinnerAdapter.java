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

import model.Quantity;

/**
 * Created by Amal on 4/23/2016.
 */
public class QuntitiySpinnerAdapter extends ArrayAdapter<Quantity> implements android.widget.SpinnerAdapter {

    private final Context context;
    private final ArrayList<Quantity> items;

    public QuntitiySpinnerAdapter(Context context, ArrayList<Quantity> objects) {
        super(context, R.layout.support_simple_spinner_dropdown_item, objects);

        this.context = context;
        items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.quantity_spinner_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.quantity_spinner_item_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.quntity_spinner_item_pic);
        textView.setText(items.get(position).name);
        imageView.setVisibility(View.GONE);
        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.quantity_spinner_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.quantity_spinner_item_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.quntity_spinner_item_pic);
        View wall = rowView.findViewById(R.id.quantity_spinner_wall);
        wall.setBackgroundColor(context.getResources().getColor(R.color.white));
        imageView.setVisibility(View.VISIBLE);
        textView.setText(items.get(position).name);
        textView.setTextColor(context.getResources().getColor(R.color.secondary_text_material_light));

        ///////only now
        if(items.get(position).getId().equals("1"))
            imageView.setImageResource(R.drawable.weight);
        else if(items.get(position).getId().equals("2"))
            imageView.setImageResource(R.drawable.height);
        else
            imageView.setImageResource(R.drawable.head);

            return rowView;
    }

    @Override
    public Quantity getItem(int position) {
            return items.get(position);
    }
}
