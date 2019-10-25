package Helpers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.amal.mybabyhealthcare.R;

/**
 * Created by Amal on 4/8/2016.
 */
public class NumberPickerDialogManager {

    private static NumberPicker picker;
    private static Button cancle, ok;

    public static void showDialog(final Context context, String title, int min, int max, int value, final EditText editText) {

        final Dialog alertDialog = new Dialog(context);

        alertDialog.setTitle(title);

        alertDialog.setContentView(R.layout.num_picker_dialog);

        picker = (NumberPicker) alertDialog.findViewById(R.id.number_picker_dialog_picker);

        picker.setMinValue(min);
        picker.setMaxValue(max);
        picker.setValue(value);

        cancle = (Button) alertDialog.findViewById(R.id.num_picker_dialog_cancle_btn);
        ok = (Button) alertDialog.findViewById(R.id.num_picker_dialog_ok_btn);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(String.valueOf(picker.getValue()));
                alertDialog.dismiss();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
