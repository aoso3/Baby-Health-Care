package custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.amal.mybabyhealthcare.R;

import java.util.ArrayList;

import model.MainSection;
import server_side.AppConfig;
import Helpers.ImagesManager;

/**
 * Created by Amal on 4/16/2016.
 */
public class MainSectionsListAdapter extends ArrayAdapter<MainSection> implements ListAdapter {
    private final Context context;
    private final ArrayList<MainSection> sections;

    public MainSectionsListAdapter(Context context, int layout, ArrayList<MainSection> objects) {
        super(context, layout, objects);

        this.context = context;
        sections = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.main_sections_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.main_section_title);
        textView.setText(sections.get(position).name);

        TextView textView2 = (TextView) rowView.findViewById(R.id.main_section_description);
        textView2.setText(sections.get(position).description);

        NetworkImageView imageView = (NetworkImageView) rowView.findViewById(R.id.main_section_ico);
        ImagesManager.loadNetworkImageView(AppConfig.URL_MAIN_SECTIONS_ICONS + sections.get(position).android_app_icon, imageView);

        return rowView;
    }

    @Override
    public MainSection getItem(int position) {
        return sections.get(position);
    }
}
