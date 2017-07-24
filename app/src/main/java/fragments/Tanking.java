package fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.olranz.payfuel.spmyadmin.R;

import java.util.ArrayList;
import java.util.List;

import config.ClientData;
import config.ClientServices;
import config.ServerClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplebean.orders.OrderBean;
import simplebean.orders.OrderRequest;
import simplebean.orders.OrderResponse;
import simplebean.orders.CommonRequest;
import simplebean.tankbean.TankBean;
import simplebean.tankbean.TankList;
import simplebean.tankingbean.TankingBean;
import simplebean.tankingbean.TankingResp;
import utilities.DataFactory;
import utilities.OrderListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTankingInteraction} interface
 * to handle interaction events.
 * Use the {@link Tanking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tanking extends Fragment implements OrderListAdapter.OrderInteraction {
    private static final String ARG_USER_ID = "userId";
    private static final String ARG_BRANCH_ID = "branchId";

    private int userId;
    private int branchId;
    private Dialog dialog;
    private Spinner mSpinner;
    private EditText pretank, postTank, waterValue;
    private TextView orderDetails;
    private Button submit;

    private String selectedTankId;
    private String selectedTankName;
    private List<Integer> tankIds;
    private List<String> tankNames;
    private List<String> viewTankNames;
    private ArrayAdapter<String> adapter;
    private Typeface font;
    private OrderBean order;
    private OnTankingInteraction tankingListener;

    public Tanking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @param branchId Parameter 2.
     * @return A new instance of fragment Tanking.
     */
    public static Tanking newInstance(int userId, int branchId) {
        Tanking fragment = new Tanking();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putInt(ARG_BRANCH_ID, branchId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
            branchId = getArguments().getInt(ARG_BRANCH_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tankingListener.onTankingChangeTitle(getClass().getSimpleName());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tanking_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getOrders(branchId+"");
        getTanks();
        font=Typeface.createFromAsset(getContext().getAssets(), "font/ubuntu.ttf");
        pretank=(EditText) view.findViewById(R.id.preTank);
        pretank.setTypeface(font);
        postTank=(EditText) view.findViewById(R.id.postTank);
        postTank.setTypeface(font);
        orderDetails = (TextView) view.findViewById(R.id.orderDetails);
        orderDetails.setTypeface(font);
//        theoretical=(EditText) view.findViewById(R.id.theoretical);
//        delivered=(EditText) view.findViewById(R.id.delivered);
//        numberPlate=(EditText) view.findViewById(R.id.numberPlate);
//        numberPlate.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        waterValue =(EditText) view.findViewById(R.id.waterValue);
        waterValue.setTypeface(font);
        submit=(Button) view.findViewById(R.id.submit);
        submit.setTypeface(font,Typeface.BOLD);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate fields
                String preT,postT,theor, waterV;
                String deliv, numberP;
                if(!TextUtils.isEmpty(pretank.getText().toString())&&
                        !TextUtils.isEmpty(postTank.getText().toString())){

                    preT=pretank.getText().toString();
                    postT=postTank.getText().toString();
                    if(TextUtils.isEmpty(waterValue.getText().toString()))
                        waterV="0";
                    else
                        waterV=waterValue.getText().toString();

                    //proceed to confirm box
                    TankingBean tankingBean=new TankingBean(null, String.valueOf(userId), selectedTankId, order.getOrderNo(), preT, postT, waterV, String.valueOf(userId), selectedTankName);
                    confirmBox(tankingBean);
                }else{
                    // setting error to any field which has an error
                    uiFeed(getContext().getResources().getString(R.string.invalidData));
                }
            }
        });
    }

    private void confirmBox(final TankingBean tankingBean){

        try {
            final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.confirm_box);

            final TextView boxTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
            boxTitle.setTypeface(font, Typeface.BOLD);
            ImageView close = (ImageView) dialog.findViewById(R.id.icClose);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);
            cancel.setTypeface(font, Typeface.BOLD);
            Button ok = (Button) dialog.findViewById(R.id.ok);
            ok.setTypeface(font, Typeface.BOLD);
            LinearLayout boxContent = (LinearLayout) dialog.findViewById(R.id.dialogContent);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            boxTitle.setText("Confirm Tanking Values");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    publishTankingValues(tankingBean);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            //setting box content
            TextView lbl = new TextView(getContext());
            lbl.setTypeface(font);
            lbl.setText(getResources().getString(R.string.orderNo));
            lbl.setTextSize(21);
            lbl.setId(View.generateViewId());
            lbl.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lbl);

            TextView lblValue = new TextView(getContext());
            lblValue.setTypeface(font, Typeface.BOLD);
            lblValue.setText(order.getOrderNo()+"\n");
            lblValue.setTextSize(27);
            lblValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            lblValue.setId(View.generateViewId());
            lblValue.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lblValue);

            lbl = new TextView(getContext());
            lbl.setTypeface(font);
            lbl.setText(getResources().getString(R.string.product));
            lbl.setTextSize(21);
            lbl.setId(View.generateViewId());
            lbl.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lbl);

            lblValue = new TextView(getContext());
            lblValue.setTypeface(font, Typeface.BOLD);
            lblValue.setText(order.getProductName()+"\n");
            lblValue.setTextSize(27);
            lblValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            lblValue.setId(View.generateViewId());
            lblValue.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lblValue);

            lbl = new TextView(getContext());
            lbl.setTypeface(font);
            lbl.setText(getResources().getString(R.string.pretank));
            lbl.setTextSize(21);
            lbl.setId(View.generateViewId());
            lbl.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lbl);

            lblValue = new TextView(getContext());
            lblValue.setTypeface(font, Typeface.BOLD);
            lblValue.setText(tankingBean.getPreCalculatedDip()+"\n");
            lblValue.setTextSize(27);
            lblValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            lblValue.setId(View.generateViewId());
            lblValue.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lblValue);

            lbl = new TextView(getContext());
            lbl.setTypeface(font);
            lbl.setText(getResources().getString(R.string.posttank));
            lbl.setTextSize(21);
            lbl.setId(View.generateViewId());
            lbl.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lbl);

            lblValue = new TextView(getContext());
            lblValue.setTypeface(font, Typeface.BOLD);
            lblValue.setText(tankingBean.getPostCalculatedDip()+"\n");
            lblValue.setTextSize(27);
            lblValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            lblValue.setId(View.generateViewId());
            lblValue.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lblValue);

            lbl = new TextView(getContext());
            lbl.setTypeface(font);
            lbl.setText(getResources().getString(R.string.water));
            lbl.setTextSize(21);
            lbl.setId(View.generateViewId());
            lbl.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lbl);

            lblValue = new TextView(getContext());
            lblValue.setTypeface(font, Typeface.BOLD);
            lblValue.setText(tankingBean.getWaterValue()+"\n");
            lblValue.setTextSize(27);
            lblValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            lblValue.setId(View.generateViewId());
            lblValue.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lblValue);

            lbl = new TextView(getContext());
            lbl.setTypeface(font);
            lbl.setText(getResources().getString(R.string.TankId));
            lbl.setTextSize(21);
            lbl.setId(View.generateViewId());
            lbl.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lbl);

            lblValue = new TextView(getContext());
            lblValue.setTypeface(font, Typeface.BOLD);
            lblValue.setText(tankingBean.getTankName()+"\n");
            lblValue.setTextSize(27);
            lblValue.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            lblValue.setId(View.generateViewId());
            lblValue.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lblValue);

            dialog.show();

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void confirmBoxOld(final TankingBean tankingBean){
        if(tankingBean != null){


            //show a box
            final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.dialog_confirm);

            TextView close=(TextView) dialog.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            TextView title=(TextView) dialog.findViewById(R.id.dialogTitle);
            title.setText(getResources().getString(R.string.tankingBoxTitle));

            TextView content=(TextView) dialog.findViewById(R.id.dialogContent);
            content.setText(mSpinner.getSelectedItem().toString());
            content.append(getResources().getString(R.string.ln));

            content.append(getResources().getString(R.string.pretank));
            content.append(tankingBean.getPreCalculatedDip());
            content.append(getResources().getString(R.string.ln));

            content.append(getResources().getString(R.string.posttank));
            content.append(tankingBean.getPostCalculatedDip());
            content.append(getResources().getString(R.string.ln));

            content.append(getResources().getString(R.string.water));
            content.append(String.valueOf(tankingBean.getWaterValue()));
            content.append(getResources().getString(R.string.ln));

            content.append(getResources().getString(R.string.TankId));
            content.append(tankingBean.getTankId());
            content.append(getResources().getString(R.string.ln));

            Button cancel=(Button) dialog.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            Button submit=(Button) dialog.findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //attempt to make a URL post
                    dialog.dismiss();
                    publishTankingValues(tankingBean);
                }
            });

            dialog.show();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTankingInteraction) {
            tankingListener = (OnTankingInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTankingInteraction");
        }
    }

    private void publishTankingValues(final TankingBean tankingBean){
        String tankingData=new ClientData().mapping(tankingBean);
        try {
            ClientServices clientServices = ServerClient.getClient().create(ClientServices.class);
            Call<TankingResp> callService = clientServices.tanking(tankingBean);
            callService.enqueue(new Callback<TankingResp>() {
                @Override
                public void onResponse(Call<TankingResp> call, Response<TankingResp> response) {

                    //HTTP status code
                    int statusCode = response.code();
                    Log.d(getClass().getSimpleName(), "Server response code: "+statusCode+" body:"+ new ClientData().mapping(response.body()));
                    try{
                        Log.d("Tanking Server Result\n ", new ClientData().mapping(response.body()));
                        final TankingResp tankingResp = response.body();

                        if(tankingResp.getStatusCode() == 100){
                            //Successful Tanking
                            uiFeed(tankingResp.getMessage());
                        }else{
                            //Notify User that an error happened
                            uiFeed(tankingResp.getMessage());
                        }

                    } catch (final Exception e) {
                        uiFeed(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<TankingResp> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(getClass().getSimpleName(), t.toString());
                    uiFeed(t.getMessage()+"");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            uiFeed(e.getMessage());
        }
    }

    private void getTanks(){
        tankIds=new ArrayList<>();
        tankNames=new ArrayList<>();
        viewTankNames =new ArrayList<>();
        try{
            mSpinner=(Spinner) getView().findViewById(R.id.mTankView);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Log.d(getClass().getSimpleName(),"Server request: "+new ClientData().mapping(new CommonRequest(branchId+"")));
            ClientServices clientServices = ServerClient.getClient().create(ClientServices.class);
            Call<TankList> callService = clientServices.getTankList(new CommonRequest(branchId+""));
            callService.enqueue(new Callback<TankList>() {
                @Override
                public void onResponse(Call<TankList> call, Response<TankList> response) {

                    //HTTP status code
                    int statusCode = response.code();
                    Log.d(getClass().getSimpleName(), "Server response code: "+statusCode+" body:"+ new ClientData().mapping(response.body()));
                    try{
                        Log.d(getClass().getSimpleName(),new ClientData().mapping(response.body()));
                        final TankList tankList = response.body();

                        if(tankList.getStatusCode() == 100){
                            //launch Admin app home screen
                            populateSpinner(tankList.getmTanks());
                        }else{
                            //Notify User that an error happened
                            uiFeed(tankList.getMessage());
                        }

                    } catch (final Exception e) {
                        uiFeed(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<TankList> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(getClass().getSimpleName(), t.toString());
                    uiFeed(t.getMessage()+"");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            uiFeed(e.getMessage());
        }
    }

    private void populateSpinner(List<TankBean> mTanks){
        if(!mTanks.isEmpty()){
            for(TankBean tank: mTanks){
//                if((tank.getStatus() == 7) && (tank.getBranchId() == branchId)) {
//                    viewTankNames.add(tank.getTankId() + ": " + tank.getName());
//                    tankNames.add(tank.getName());
//                    tankIds.add(tank.getTankId());
//                }

//                viewTankNames.add(tank.getTankId() + ": " + tank.getName());
                viewTankNames.add(tank.getName());
                tankNames.add(tank.getName());
                tankIds.add(tank.getTankId());
            }

            if(!viewTankNames.isEmpty()){
                adapter=new ArrayAdapter<String>(getContext(),R.layout.tank_style, viewTankNames);
                mSpinner.setAdapter(adapter);
                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        selectedTankId=String.valueOf(tankIds.get(position));
                        selectedTankName=tankNames.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
    }

    private void uiFeed(String feedBack){

        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.SimpleDialog);
            builder.setMessage(feedBack)
                    .setTitle(R.string.dialog_title);
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

            Toast.makeText(getContext(), feedBack, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try{
            final TextView tv=(TextView) getView().findViewById(R.id.tv);
            if(!TextUtils.isEmpty(feedBack)){
                tv.setText(feedBack);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("");
                    }
                }, 4000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refreshOrGoToHome(String feedBack){

        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.SimpleDialog);
            builder.setMessage(feedBack)
                    .setTitle(R.string.dialog_title);
            // Add the buttons
            builder.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    getOrders(branchId+"");
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    //go to dipping page
                    tankingListener.onTankingGoToHome(true);
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
            Button button2 = (Button) dialog.getWindow().findViewById(android.R.id.button2);
            button2.setTypeface(font, Typeface.BOLD);

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try{
            final TextView tv=(TextView) getView().findViewById(R.id.tv);
            if(!TextUtils.isEmpty(feedBack)){
                tv.setText(feedBack);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("");
                    }
                }, 4000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tankingListener = null;
    }

    @Override
    public void onOrderInteraction(boolean isClose, OrderBean orderBean, Dialog dialog) {
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
        if(orderBean == null){
            //go to dipping page
            tankingListener.onTankingGoToHome(true);
            return;
        }
        order = orderBean;
        orderDetails.setText(order.getOrderNo()+"/"+order.getProductName());
    }

    private void getOrders(String branchId){

        final TextView dialogTitle;
        final RecyclerView ordersView;
        ImageView close;
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());;

        dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.order_box);

        dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setTypeface(font, Typeface.BOLD);
        ordersView = (RecyclerView) dialog.findViewById(R.id.orderList);
        close = (ImageView) dialog.findViewById(R.id.icClose);


        final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading orders, please wait...");
        progressDialog.show();

        try {
            ClientServices clientServices = ServerClient.getClient().create(ClientServices.class);
            OrderRequest orderRequest = new OrderRequest(301, branchId);
            Log.d("Request", DataFactory.objectToString(orderRequest));
            String request = DataFactory.objectToString(orderRequest);
            Call<OrderResponse> callService = clientServices.getBranchOrders(orderRequest);
            callService.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    //HTTP status code
                    int statusCode = response.code();
                    if(statusCode != 200){
                        refreshOrGoToHome(response.message());
                    }
                    try{
                        Log.d(getClass().getSimpleName(),new ClientData().mapping(response.body()));
                        final OrderResponse orderResponse = response.body();
                        Log.d("Response", DataFactory.objectToString(orderResponse));
                        if(orderResponse.getStatusCode() == 100 && !orderResponse.getOrderBeanList().isEmpty()){
                            //launch Admin app tanking popup
                            dialog.show();
                            ordersView.setLayoutManager(mLayoutManager);
                            OrderListAdapter adapter=new OrderListAdapter(getContext(), Tanking.this,orderResponse.getOrderBeanList(), dialog);
                            ordersView.setAdapter(adapter);
                        }else{
                            //Notify User that an error happened
                            refreshOrGoToHome(orderResponse.getMessage());
                        }

                    } catch (final Exception e) {
                        refreshOrGoToHome(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    // Log error here since request failed
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e(getClass().getSimpleName(), t.toString());
                    refreshOrGoToHome(t.getMessage()+"");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            refreshOrGoToHome(e.getMessage());
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                if(dialog != null && dialog.isShowing())
                    dialog.dismiss();
                tankingListener.onTankingGoToHome(true);
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTankingInteraction {
        void onTankingInteraction(Object object, int statusCode, String message);
        void onTankingChangeTitle(String title);
        void onTankingGoToHome(boolean isGoToHome);
    }
}
