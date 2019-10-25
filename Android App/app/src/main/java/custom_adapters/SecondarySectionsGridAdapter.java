package custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amal.mybabyhealthcare.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import model.SecodarySection;
import server_side.AppConfig;
import Helpers.ImagesManager;

/**
 * Created by Amal on 4/17/2016.
 */
public class SecondarySectionsGridAdapter extends ArrayAdapter<SecodarySection> {
    private final Context context;
    private final ArrayList<SecodarySection> sections;

    public SecondarySectionsGridAdapter(Context context, ArrayList<SecodarySection> objects)
    {
        super(context, R.layout.details_grid_item, objects);
        this.context = context;
        this.sections = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.details_grid_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.second_section_name);
        textView.setText(sections.get(position).name);

        TextView textView2 = (TextView) rowView.findViewById(R.id.second_section_description);
        textView2.setText(sections.get(position).description);

        final CircleImageView imageView = (CircleImageView) rowView.findViewById(R.id.second_section_ico);
        ImagesManager.loadNormalImageView(AppConfig.URL_MAIN_SECTIONS_DETAILS_ICONS + sections.get(position).icon, imageView);

        return rowView;
    }

    @Override
    public SecodarySection getItem(int position) {
        return sections.get(position);
    }
}
