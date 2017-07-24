package utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.olranz.payfuel.spmyadmin.R;

/**
 * Created by Hp on 1/3/2017.
 */

public class SimplePopUp {
    private Context context;
    private String message;
    private Typeface font;
    public SimplePopUp(Context context, String message) {
        this.context = context;
        this.message = message;
        font=Typeface.createFromAsset(context.getAssets(), "font/ubuntu.ttf");
    }

    public void show(){
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setTitle(R.string.dialog_title);
            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            TextView textView = (TextView) dialog.getWindow().findViewById(android.R.id.message);
            textView.setTypeface(font);
            TextView alertTitle = (TextView) dialog.getWindow().findViewById(R.id.alertTitle);
            alertTitle.setTypeface(font);
            Button button1 = (Button) dialog.getWindow().findViewById(android.R.id.button1);
            button1.setTypeface(font, Typeface.BOLD);

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
