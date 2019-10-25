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

import model.SleepSample;

/**
 * Created by Amal on 5/4/2016.
 */
public class SleepSampleItemAdapter extends ArrayAdapter<SleepSample> implements ListAdapter {
    private final Context context;
    private final ArrayList<SleepSample> samples;

    public void add_item(SleepSample sample)
    {
        samples.add(sample);
        notifyDataSetChanged();
    }

    public void delete_item(int position)
    {
        samples.remove(position);
        notifyDataSetChanged();
    }

    public void update_item(SleepSample sample)
    {
        int index = samples.indexOf(sample);
        samples.remove(index);
        samples.add(index, sample);
        notifyDataSetChanged();
    }

    public SleepSampleItemAdapter(Context context, ArrayList<SleepSample> objects) {
        super(context, R.layout.sleep_sample_list_item, objects);

        this.context = context;
        samples = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.sleep_sample_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.sleep_sample_item_date);
        textView.setText(samples.get(position).date);

        TextView textView1 = (TextView) rowView.findViewById(R.id.sleep_sample_item_age);
        textView1.setText(samples.get(position).age_months);

        TextView textView2 = (TextView) rowView.findViewById(R.id.sleep_sample_item_amount);
        textView2.setText(samples.get(position).amount);

        return rowView;
    }

    @Override
    public SleepSample getItem(int position) {
        return samples.get(position);
    }


}
