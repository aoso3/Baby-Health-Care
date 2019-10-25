package custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.amal.mybabyhealthcare.R;

import java.util.ArrayList;

import model.QuantitySample;

/**
 * Created by Amal on 4/23/2016.
 */
public class QuantityGridAdapter extends ArrayAdapter<QuantitySample> implements ListAdapter {
    private final Context context;
    private final ArrayList<QuantitySample> types;
    private final String unit;

    public void add_sample(QuantitySample sample)
    {
        types.add(sample);
        notifyDataSetChanged();
    }

    public void update_sample(QuantitySample sample)
    {
        int index = types.indexOf(sample);
        types.remove(index);
        types.add(index, sample);
        notifyDataSetChanged();
    }

    public void delete_sample(int position)
    {
        types.remove(position);
        notifyDataSetChanged();
    }

    public QuantityGridAdapter(Context context, ArrayList<QuantitySample> objects, String unit) {
        super(context, R.layout.main_sections_list_item, objects);

        this.context = context;
        types = objects;
        this.unit = unit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.quantity_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.quantity_item_age);
        textView.setText(types.get(position).age_weeks);

        TextView textView2 = (TextView) rowView.findViewById(R.id.quantity_item_measurement);
        textView2.setText(types.get(position).measurement);

        TextView textView3 = (TextView) rowView.findViewById(R.id.quantity_item_unit);
        textView3.setText(unit);

        return rowView;
    }

    @Override
    public QuantitySample getItem(int position) {
        return types.get(position);
    }
}
