package Helpers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.amal.mybabyhealthcare.R;

import custom_interfaces.CallBack;

/**
 * Created by Amal on 5/24/2016.
 */
public class ConfirmationAlertManager {
    public static void showDialog(final Context context, final String title, String message, final CallBack onConfirm) {
        final Dialog alertDialog = new Dialog(context);

        alertDialog.setTitle(title);

        alertDialog.setContentView(R.layout.confirmation_dialog);

        TextView text = (TextView) alertDialog.findViewById(R.id.conf_dialog_text);
        text.setText(message);

        Button ok = (Button) alertDialog.findViewById(R.id.conf_dialog_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirm.onResponse();
                alertDialog.dismiss();
            }
        });

        Button cancle = (Button) alertDialog.findViewById(R.id.conf_dialog_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
