package custom_adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.doctor.DoctorProfileActivity;

import java.util.ArrayList;

import model.VaccenationRecord;

/**
 * Created by Amal on 5/22/2016.
 */
public class VaccinationRecordItemAdapter extends ArrayAdapter<VaccenationRecord> {
    private final Context context;
    private final ArrayList<VaccenationRecord> records;

    public VaccinationRecordItemAdapter(Context context, ArrayList<VaccenationRecord> objects) {
        super(context, R.layout.vaccination_record_item, objects);

        this.context = context;
        records = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.vaccination_record_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.vec_name_record_item);
        textView.setText(records.get(position).getVaccination().name);

        TextView textView2 = (TextView) rowView.findViewById(R.id.vec_date_record_item);
        textView2.setText(records.get(position).getDate());

        TextView textView3 = (TextView) rowView.findViewById(R.id.vec_doc_name_record_item);
        textView3.setText(records.get(position).getDoctor().first_name + " " + records.get(position).getDoctor().last_name);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DoctorProfileActivity.class);
                i.putExtra("doctor", records.get(position).getDoctor());
                context.startActivity(i);
            }
        });

        TextView textView4 = (TextView) rowView.findViewById(R.id.doc_email_record_item);
        textView4.setText(records.get(position).getDoctor().email);

        return rowView;
    }

    @Override
    public VaccenationRecord getItem(int position) {
        return records.get(position);
    }
}
