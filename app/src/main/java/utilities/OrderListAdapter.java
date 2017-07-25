package utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.olranz.payfuel.engenmyadmin.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import simplebean.orders.OrderBean;

/**
 * Created by Hp on 11/14/2016.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private Context context;
    private List<OrderBean> mOrders;
    private OrderInteraction orderInteraction;
    private Dialog dialog;
    private Typeface font;

    public OrderListAdapter(Context context, OrderInteraction orderInteraction, List<OrderBean> mOrders, Dialog dialog) {
        this.context = context;
        this.mOrders = mOrders;
        this.orderInteraction = orderInteraction;
        this.dialog=dialog;
        font=Typeface.createFromAsset(context.getAssets(), "font/ubuntu.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_style, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderBean order= mOrders.get(position);
        holder.orderNo.setText(order.getOrderNo());
        holder.orderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderInteraction.onOrderInteraction(false, order, dialog);
            }
        });

        DateFormat dFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
        try{
            holder.orderDesc.setText(order.getProductName()+" / "+dFormat.format(dFormat.parse(order.getCreationTime())));
        }catch (Exception e){
            e.printStackTrace();
            holder.orderDesc.setText(order.getProductName()+" / "+order.getCreationTime());
        }
//        holder.orderDesc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                orderInteraction.onOrderInteraction(false, order, dialog);
//            }
//        });

        holder.leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderInteraction.onOrderInteraction(false, order, dialog);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView orderNo;
        TextView orderDesc;
        ImageView leftIcon;

        private ViewHolder(View v) {
            super(v);
            orderNo = (TextView) v.findViewById(R.id.orderNo);
            orderNo.setTypeface(font, Typeface.BOLD);

            orderDesc = (TextView) v.findViewById(R.id.orderDesc);
            orderDesc.setTypeface(font, Typeface.BOLD);

            leftIcon = (ImageView) v.findViewById(R.id.leftIcon);
        }
    }

    public interface OrderInteraction {
        void onOrderInteraction(boolean isClose, OrderBean orderBean, Dialog dialog);
    }
}
