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

import model.FoodType;

/**
 * Created by Amal on 4/21/2016.
 */
public class FoodTypeListAdapter extends ArrayAdapter<FoodType> implements ListAdapter {
    private final Context context;
    public ArrayList<FoodType> types;

    public void change_data_set(ArrayList<FoodType> types)
    {
        this.types = new ArrayList<>(types);
        notifyDataSetChanged();
    }

    public void add_item(FoodType sample)
    {
        types.add(sample);
        notifyDataSetChanged();
    }

    public void delete_item(int position)
    {
        types.remove(position);
        notifyDataSetChanged();
    }

    public void update_item(FoodType sample)
    {
        int index = types.indexOf(sample);
        types.remove(index);
        types.add(index, sample);
        notifyDataSetChanged();
    }

    public FoodTypeListAdapter(Context context, ArrayList<FoodType> objects) {
        super(context, R.layout.main_sections_list_item, objects);

        this.context = context;
        types = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.food_type_grid_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.food_type_item_name);
        textView.setText(types.get(position).name);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.food_type_item_rating);
        switch (types.get(position).rating)
        {
            case "yum":  imageView.setImageResource(R.drawable.yum);
                break;
            case "yuck": imageView.setImageResource(R.drawable.yuck);
                break;
            case "normal": imageView.setImageResource(R.drawable.normal);
                break;
        }

        ImageView imageView2 = (ImageView) rowView.findViewById(R.id.food_type_item_status);
        switch (types.get(position).status)
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
    public FoodType getItem(int position) {
        return types.get(position);
    }

    @Override
    public int getCount() {
        return types.size();
    }

}
