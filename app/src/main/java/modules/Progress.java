package modules;

import android.app.ProgressDialog;
import android.content.Context;

import com.olranz.payfuel.spmyadmin.R;

/**
 * Created by Hp on 12/30/2016.
 */

public class Progress {
    private Context context;
    private String message;
    private ProgressDialog progressDialog;

    public Progress(Context context, String message) {
        this.context = context;
        this.message = message;
        progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
    }

    public void startProgress(){
        if(progressDialog != null){
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message+"...");
            progressDialog.show();
        }
    }

    public void updateMessage(String newMessage){
        if(progressDialog != null)
            if(progressDialog.isShowing())
                progressDialog.setMessage(newMessage);
    }

    public void dismissProgress(){
        if(progressDialog != null)
            if(progressDialog.isShowing())
                progressDialog.dismiss();
    }
}
