package utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.olranz.payfuel.engenmyadmin.R;

import java.math.BigDecimal;
import java.util.List;

import simplebean.paymentmodebean.PaymentMode;
import simplebean.reportmoneybean.MoneyReportBean;

/**
 * Created by Hp on 11/10/2016.
 */
public class ReportMoneyAdapter extends RecyclerView.Adapter<ReportMoneyAdapter.ViewHolder>  {
    private Context context;
    private List<PaymentMode>mPays;
    private ReportInteraction reportInteraction;

    public ReportMoneyAdapter(ReportInteraction reportInteraction, Context context, List<PaymentMode> mPays) {
        this.reportInteraction=reportInteraction;
        this.context = context;
        this.mPays = mPays;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_style, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PaymentMode paymentMode=mPays.get(position);
        holder.paymentMode.setText(paymentMode.getName());
        holder.amountSet.setText("");
        holder.paymentMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpView(holder.amountSet, paymentMode);
            }
        });

        if(paymentMode.getName().equalsIgnoreCase("cash")){
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cash, 0, 0);
        }else if(paymentMode.getName().equalsIgnoreCase("voucher")){
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.voucher, 0, 0);
        }else if(paymentMode.getName().equalsIgnoreCase("mtn")){
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mtnmobilemoney, 0, 0);
        }else if(paymentMode.getName().equalsIgnoreCase("tigo")){
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tigocash, 0, 0);
        }else if(paymentMode.getName().equalsIgnoreCase("airtel")){
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.airtel, 0, 0);
        }else if(paymentMode.getName().equalsIgnoreCase("visa")){
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.visa, 0, 0);
        }else if(paymentMode.getName().equalsIgnoreCase("master")){;
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mastercard, 0, 0);
        }else if(paymentMode.getName().equalsIgnoreCase("debt")){
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.debt, 0, 0);
        }else if(paymentMode.getName().equalsIgnoreCase("engen card")){
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.engenonecard, 0, 0);
        }else{
            holder.paymentMode.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cash, 0, 0);
        }
    }

    public void add(int position, PaymentMode item) {
        mPays.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(PaymentMode item) {
        int position = mPays.indexOf(item);
        mPays.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mPays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView paymentMode;
        public TextView amountSet;

        public ViewHolder(View v) {
            super(v);
            paymentMode = (TextView) v.findViewById(R.id.paymentMode);
            amountSet = (TextView) v.findViewById(R.id.amount);
        }
    }

    private void popUpView(final TextView holder, final PaymentMode paymentMode){
        try {
            final TextView amountHolder=holder;
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.input_amount, null);
            final EditText userAmount = (EditText) promptsView.findViewById(R.id.amount);
            if(!TextUtils.isEmpty(amountHolder.getText().toString()))
                userAmount.setText(amountHolder.getText().toString());
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.setAmount)
            .setView(promptsView);
            // Add the buttons
            builder.setNeutralButton(context.getResources().getString(R.string.reset), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    try{
                        reportInteraction.onReportInteraction(-1,new MoneyReportBean(paymentMode.getPaymentModeId(),paymentMode.getName(),new BigDecimal(userAmount.getText().toString().trim())));
                    }catch (Exception e){
                        reportInteraction.onReportInteraction(-1,new MoneyReportBean(paymentMode.getPaymentModeId(),paymentMode.getName(),new BigDecimal(0)));
                        e.printStackTrace();
                    }
                    amountHolder.setText("");
                    userAmount.setText("");
//                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(!TextUtils.isEmpty(userAmount.getText().toString().trim())){
                        amountHolder.setText(userAmount.getText().toString().trim());
                        reportInteraction.onReportInteraction(1, new MoneyReportBean(paymentMode.getPaymentModeId(),paymentMode.getName(), new BigDecimal(userAmount.getText().toString().trim())));
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

        } catch (Exception e) {
            Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public interface ReportInteraction{
        void onReportInteraction(int status, MoneyReportBean onMoneyReport);
    }
}
