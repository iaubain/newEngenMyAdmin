package utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.olranz.payfuel.engenmyadmin.R;

import java.math.BigDecimal;
import java.util.List;

import simplebean.paymentmodebean.PaymentMode;
import simplebean.reportnozzle.NozzleReportBean;

/**
 * Created by Hp on 11/10/2016.
 */
public class ReportNozzleAdapter extends RecyclerView.Adapter<ReportNozzleAdapter.ViewHolder>  {
    private Context context;
    private List<NozzleReportBean> mNozzles;
    private NozzleReportInteraction nozzleReportInteraction;
    private Typeface font;

    public ReportNozzleAdapter(NozzleReportInteraction nozzleReportInteraction, Context context, List<NozzleReportBean> mNozzles) {
        this.nozzleReportInteraction = nozzleReportInteraction;
        this.context = context;
        this.mNozzles = mNozzles;
        font=Typeface.createFromAsset(context.getAssets(), "font/ubuntu.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_nozzle_style, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NozzleReportBean nozzleReportBean= mNozzles.get(position);
        holder.nozzleName.setText(nozzleReportBean.getNozzleName());
        holder.oldIndex.setText((CharSequence) nozzleReportBean.getOldIndex());
        holder.newIndex.setText("0.0");
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpView(holder.newIndex, nozzleReportBean);
            }
        });
    }

    public void add(int position, NozzleReportBean item) {
        mNozzles.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(PaymentMode item) {
        int position = mNozzles.indexOf(item);
        mNozzles.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mNozzles.size();
    }



    private void popUpView(final TextView newIndex, final NozzleReportBean nozzleReportBean){
        try {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.input_index, null);
            final EditText userSetIndex = (EditText) promptsView.findViewById(R.id.index);
            userSetIndex.setTypeface(font);
            if(!TextUtils.isEmpty(newIndex.getText().toString()))
                userSetIndex.setText(newIndex.getText().toString());
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.setAmount)
            .setView(promptsView);
            // Add the buttons
            builder.setNeutralButton(context.getResources().getString(R.string.reset), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    try{
                        nozzleReportInteraction.onNozzleReportInteraction(-1,new NozzleReportBean(nozzleReportBean.getNozzleId(),nozzleReportBean.getNozzleName(), nozzleReportBean.getProductName(), nozzleReportBean.getOldIndex(), new BigDecimal(userSetIndex.getText().toString().trim())));
                    }catch (Exception e){
                        nozzleReportInteraction.onNozzleReportInteraction(-1,new NozzleReportBean(nozzleReportBean.getNozzleId(),nozzleReportBean.getNozzleName(), nozzleReportBean.getProductName(), nozzleReportBean.getOldIndex(), nozzleReportBean.getOldIndex()));
                        e.printStackTrace();
                    }
                    newIndex.setText("");
                    newIndex.setText("");
//                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(!TextUtils.isEmpty(userSetIndex.getText().toString().trim())){
                        newIndex.setText(userSetIndex.getText().toString().trim());
                        nozzleReportInteraction.onNozzleReportInteraction(1, new NozzleReportBean(nozzleReportBean.getNozzleId(),nozzleReportBean.getNozzleName(), nozzleReportBean.getProductName(), nozzleReportBean.getOldIndex(), new BigDecimal(userSetIndex.getText().toString().trim())));
                    }
                    else
                        Toast.makeText(context, "Invalid Amount", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            TextView alertTitle = (TextView) dialog.getWindow().findViewById(R.id.alertTitle);
            alertTitle.setTypeface(font);
            Button button1 = (Button) dialog.getWindow().findViewById(android.R.id.button1);
            button1.setTypeface(font, Typeface.BOLD);
            Button button2 = (Button) dialog.getWindow().findViewById(android.R.id.button2);
            button2.setTypeface(font, Typeface.BOLD);

        } catch (Exception e) {
            Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView nozzleName;
        private TextView oldIndex;
        private TextView newIndex;

        ViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.nozzleIcon);
            nozzleName = (TextView) v.findViewById(R.id.nozzleName);
            nozzleName.setTypeface(font);
            oldIndex = (TextView) v.findViewById(R.id.oldIndex);
            oldIndex.setTypeface(font);
            newIndex = (TextView) v.findViewById(R.id.newIndex);
            newIndex.setTypeface(font, Typeface.BOLD);
        }
    }

    public interface NozzleReportInteraction {
        void onNozzleReportInteraction(int status, NozzleReportBean nozzleReportBean);
    }
}
