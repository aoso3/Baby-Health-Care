package custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.amal.mybabyhealthcare.R;

import java.util.ArrayList;

import model.FoodSample;

/**
 * Created by Amal on 4/18/2016.
 */
public class FoodSampleAdapter extends ArrayAdapter<FoodSample> implements ListAdapter {
    private final Context context;
    private final ArrayList<FoodSample> samples;

    public FoodSampleAdapter(Context context, ArrayList<FoodSample> objects) {
        super(context, R.layout.main_sections_list_item, objects);

        this.context = context;
        samples = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.food_sample_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.food_sample_item_name);
        textView.setText(samples.get(position).name);

        TextView textView1 = (TextView) rowView.findViewById(R.id.food_sample_item_date);
        textView1.setText(samples.get(position).tried_date);

        TextView textView2 = (TextView) rowView.findViewById(R.id.food_sample_item_note);
        if(samples.get(position).note != null && !samples.get(position).note.isEmpty())
            textView2.setText(context.getResources().getString(R.string.with_note));
        else
            textView2.setText(context.getResources().getString(R.string.no_note));

        ImageView imageView = (ImageView) rowView.findViewById(R.id.food_sample_item_rate);
        switch (samples.get(position).rating)
        {
            case "yum":  imageView.setImageResource(R.drawable.yum);
                break;
            case "yuck": imageView.setImageResource(R.drawable.yuck);
                break;
            case "normal": imageView.setImageResource(R.drawable.normal);
                break;
        }

        ImageView imageView2 = (ImageView) rowView.findViewById(R.id.food_sample_item_status);
        switch (samples.get(position).status)
        {
            case "good":  imageView2.setImageResource(R.drawable.good);
                break;
            case "blocked": imageView2.setImageResource(R.drawable.block);
                break;
            case "pending": imageView2.setImageResource(R.drawable.pending);
                break;
        }

        return rowView;
    }

    @Override
    public FoodSample getItem(int position) {
        return samples.get(position);
    }
}
