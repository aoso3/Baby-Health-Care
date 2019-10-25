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

import model.FoodTypeSample;

/**
 * Created by Amal on 4/21/2016.
 */
public class FoodTypeSampleListAdapter extends ArrayAdapter<FoodTypeSample> implements ListAdapter {
    private final Context context;
    private final ArrayList<FoodTypeSample> samples;

    public FoodTypeSampleListAdapter(Context context, ArrayList<FoodTypeSample> objects) {
        super(context, R.layout.main_sections_list_item, objects);

        this.context = context;
        samples = objects;
    }

    public void add_item(FoodTypeSample sample)
    {
        samples.add(sample);
        notifyDataSetChanged();
    }

    public void delete_item(int position)
    {
        samples.remove(position);
        notifyDataSetChanged();
    }

    public void update_item(FoodTypeSample sample)
    {
        int index = samples.indexOf(sample);
        samples.remove(index);
        samples.add(index, sample);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.food_type_sample_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.food_type_sample_item_date);
        textView.setText(samples.get(position).date);

        TextView textView2 = (TextView) rowView.findViewById(R.id.food_type_sample_item_has_note);
        if(samples.get(position).note != null && !samples.get(position).note.isEmpty())
            textView2.setText(context.getResources().getString(R.string.with_note));
        else
            textView2.setText(context.getResources().getString(R.string.no_note));

        return rowView;
    }

    @Override
    public FoodTypeSample getItem(int position) {
        return samples.get(position);
    }
}
