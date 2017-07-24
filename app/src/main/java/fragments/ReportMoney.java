package fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.olranz.payfuel.spmyadmin.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import config.ClientData;
import config.ClientServices;
import config.ServerClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplebean.paymentmodebean.PaymentMode;
import simplebean.paymentmodebean.PaymentModeResponse;
import simplebean.reportmoneybean.ReportResponse;
import simplebean.reportmoneybean.ReportMoneyRequest;
import simplebean.userbean.UserDetails;
import simplebean.userbean.UserResponse;
import utilities.ReportMoneyAdapter;
import simplebean.reportmoneybean.MoneyReportBean;
import utilities.UserListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentReportMoney} interface
 * to handle interaction events.
 * Use the {@link ReportMoney#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportMoney extends Fragment implements ReportMoneyAdapter.ReportInteraction, UserListAdapter.UserListInteraction {
    private String tag = getClass().getSimpleName();
    private static final String ARG_USER_ID = "userId";
    private static final String ARG_BRANCH_ID = "branchId";
    private int userId;
    private int branchId;
    private int pumpAgentId;
    private Dialog dialog;
    private String userName;

    List<UserDetails> userDetailsList;

    private List<PaymentMode> mPays;
    private List<MoneyReportBean> mReport;
    private OnFragmentReportMoney mListener;

    private RecyclerView mRecyclerView;
    private ReportMoneyAdapter mAdapter;
    private TextView tv;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView totalAmountLabel;
    private BigDecimal summation;

    private ProgressDialog progressDialog;
    private Button submit;

    public ReportMoney() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @param branchId Parameter 2.
     * @return A new instance of fragment Dipping.
     */
    public static ReportMoney newInstance(int userId, int branchId) {
        ReportMoney fragment = new ReportMoney();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.report_money_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mReport=new ArrayList<>();
        summation=new BigDecimal(0);

        progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        tv=(TextView) view.findViewById(R.id.tv);

        totalAmountLabel=(TextView) view.findViewById(R.id.totalLabelValue);
        submit=(Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mReport.size()>0){
                    confirm(mReport);
                }else{
                    uiFeed("No report yet");
                }
            }
        });

        mRecyclerView=(RecyclerView) view.findViewById(R.id.paymentlist);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadPumpAgents();
    }

    private void loadPumpAgents(){
        TextView close;
        final RecyclerView userList;
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());;

        dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.user_list);

        close = (TextView) dialog.findViewById(R.id.topBar);
        userList = (RecyclerView) dialog.findViewById(R.id.recyclerView);

        progressDialog.setMessage("Loading pump attendants");
        progressDialog.show();
        //get pumps from url
        try {
            ClientServices clientServices = ServerClient.getClient().create(ClientServices.class);
            Call<UserResponse> callService = clientServices.getShiftUsers(userId);
            callService.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    progressDialog.dismiss();
                    //HTTP status code
                    int statusCode = response.code();
                    if(statusCode != 200){
                        uiGoToHome(response.message());
                        return;
                    }
                    Log.d(tag,"Server Result: \n"+new ClientData().mapping(response.body()));
                    try{
                        Log.d(getClass().getSimpleName(),new ClientData().mapping(response.body()));

                        if(response.body().getStatusCode() == 100){
                            //dispatch Data
                            userDetailsList=response.body().getUserDetailsList();
                            userList.setHasFixedSize(true);
                            userList.setLayoutManager(mLayoutManager);
                            UserListAdapter adapter=new UserListAdapter(getContext(), ReportMoney.this,userDetailsList, dialog);
                            userList.setAdapter(adapter);
                        }else{
                            //Notify User that an error happened
                            uiRefresh(response.body().getMessage());
                        }

                    } catch (final Exception e) {
                        uiRefresh(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(getClass().getSimpleName(), t.toString());
                    uiRefresh("Connectivity failure");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            uiGoToHome(e.getMessage());
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mListener.onFragmentReportInteraction(Dipping.newInstance(userId, branchId), 0 ,"Go to home");
            }
        });
        dialog.show();
    }

    public void loadPayments(){
        progressDialog.setMessage("Loading payment methods");
        progressDialog.show();

        try {
            ClientServices clientServices = ServerClient.getClient().create(ClientServices.class);
            Call<PaymentModeResponse> callService = clientServices.getPaymentMode(userId);
            callService.enqueue(new Callback<PaymentModeResponse>() {
                @Override
                public void onResponse(Call<PaymentModeResponse> call, Response<PaymentModeResponse> response) {

                    if(progressDialog != null)
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                    //HTTP status code
                    int statusCode = response.code();
                    if(statusCode != 200){
                        uiFeed(response.message());
                        return;
                    }
                    Log.d(tag,"Server Response: \n"+new ClientData().mapping(response.body()));
                    try{
                        PaymentModeResponse paymentModeResponse=response.body();
                        if(paymentModeResponse.getStatusCode() != 100){
                            uiFeed(paymentModeResponse.getMessage());
                            return;
                        }
                        mPays=paymentModeResponse.getPaymentModeList();
                        if(mPays.size()>0){
                            mAdapter = new ReportMoneyAdapter(ReportMoney.this,getContext(), mPays);
                            mRecyclerView.setAdapter(mAdapter);
                        }else{
                            uiFeed("Failed to load Payment methods");
                        }
                    } catch (final Exception e) {
                        uiFeed(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<PaymentModeResponse> call, Throwable t) {
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

    private void confirm(final List<MoneyReportBean> reportBeanList){
        try {
            String value="";
            for(MoneyReportBean reportBean : reportBeanList){
                value += reportBean.getPaymentName()+" : "+reportBean.getTotalValue()+"\n";
            }
            value+="\n";
            value+="Total: "+totalAmountLabel.getText().toString();
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Confirm...")
            .setMessage(value);
            // Add the buttons
            if(! reportBeanList.isEmpty()){
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //publishReport(new ReportMoneyRequest(userId, reportBeanList));
                        requestNozzles(reportBeanList);
                        dialog.dismiss();
                    }
                });
            }

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            uiFeed(""+e.getMessage());
        }
    }

    private void requestNozzles(List<MoneyReportBean> reportBeanList){
        mListener.onRequestUserNozzles(new ReportMoneyRequest(userId, reportBeanList), pumpAgentId, userName, 100, "Next");
    }
    private void publishReport(ReportMoneyRequest reportMoneyRequest){
        progressDialog.show();
        progressDialog.setMessage("Loading Pumps");
        //get pumps from url
        try {
            Log.d(tag,"Server Request: \n"+new ClientData().mapping(reportMoneyRequest));
            ClientServices clientServices = ServerClient.getClient().create(ClientServices.class);
            Call<ReportResponse> callService = clientServices.publishReport(reportMoneyRequest);
            callService.enqueue(new Callback<ReportResponse>() {
                @Override
                public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                    progressDialog.dismiss();
                    //HTTP status code
                    int statusCode = response.code();
                    if(statusCode != 200){
                        uiFeed(response.message());
                        return;
                    }
                    Log.d(tag,"Server Result: \n"+new ClientData().mapping(response.body()));
                    try{
                        Log.d(getClass().getSimpleName(),new ClientData().mapping(response.body()));
                        mReport.clear();
                        uiFeed("Response: "+response.body().getMessage());

                    } catch (final Exception e) {
                        uiFeed(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ReportResponse> call, Throwable t) {
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

    private void uiGoToHome(String feedBack){
        if(progressDialog != null)
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(feedBack)
                    .setTitle("Notification...");
            // Add the buttons
            builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    mListener.onFragmentReportInteraction(Dipping.newInstance(userId, branchId), 0 ,"Go to home");
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


            Toast.makeText(getContext(), feedBack, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uiRefresh(String feedBack){
        if(progressDialog != null)
            if(progressDialog.isShowing())
                progressDialog.dismiss();

        if(dialog != null)
            if(dialog.isShowing())
                dialog.dismiss();
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(feedBack)
                    .setTitle("Notification...");
            // Add the buttons
            builder.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    loadPumpAgents();
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mListener.onFragmentReportInteraction(Dipping.newInstance(userId, branchId), 0 ,"Go to home");
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


            Toast.makeText(getContext(), feedBack, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void uiFeed(String feedBack){
        if(progressDialog != null)
            if(progressDialog.isShowing())
                progressDialog.dismiss();
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(feedBack)
                    .setTitle("Notification...");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


            Toast.makeText(getContext(), feedBack, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

        @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentReportMoney) {
            mListener = (OnFragmentReportMoney) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mReport = null;
        mListener = null;
    }

    @Override
    public void onReportInteraction(int status, MoneyReportBean onMoneyReport) {
        List<MoneyReportBean> tempReport= new ArrayList<>();
        for(MoneyReportBean temp : mReport){
            tempReport.add(temp);
        }
        if(mReport.size() == 0 && status == 1){
            mReport.add(onMoneyReport);
            summation=onMoneyReport.getTotalValue();
            totalAmountLabel.setText(""+summation.toString());
        }else{
            if(status == 1){
                if(!isElementAvailable(tempReport, onMoneyReport)) {
                    mReport.add(onMoneyReport);
                    summation=summation.add(onMoneyReport.getTotalValue());
                    totalAmountLabel.setText(""+summation.toString());
                }else{
                    MoneyReportBean report=getReportElement(onMoneyReport);
                    if(report != null){
                        mReport.remove(report);
                        mReport.add(onMoneyReport);
                        summation=summation.subtract(report.getTotalValue());
                        summation=summation.add(onMoneyReport.getTotalValue());
                        totalAmountLabel.setText(""+summation.toString());
                    }
                }
            } else if(status == -1){
                if(tempReport.size() > 0)
                    for(MoneyReportBean report : tempReport){
                        if(report.getPaymentId() == onMoneyReport.getPaymentId()){
                            mReport.remove(report);
                            summation=summation.subtract(report.getTotalValue());
                            totalAmountLabel.setText(""+summation.toString());
                        }
                    }
            }
        }
        if(tempReport.size()>0)
            tempReport.clear();

//        if(!mReport.contains(onMoneyReport) && status == 1){
//            for(OnMoneyReport report : mReport){
//                if(report.getPaymentName().equalsIgnoreCase(onMoneyReport.getPaymentName())){
//
//                }
//            }
//            mReport.add(onMoneyReport);
//        } else if(mReport.contains(onMoneyReport) && status == -1)
//            mReport.add(onMoneyReport);
    }

    private boolean isElementAvailable(List<MoneyReportBean> tempReport, MoneyReportBean moneyReportBean){
        for(MoneyReportBean temp : tempReport){
            if(temp.getPaymentId() == moneyReportBean.getPaymentId())
                return true;
        }
        return false;
    }

    @Nullable
    private MoneyReportBean getReportElement(MoneyReportBean moneyReportBean){
        for(MoneyReportBean temp : mReport){
            if(temp.getPaymentId() == moneyReportBean.getPaymentId())
                return temp;
        }
        return null;
    }

    @Override
    public void onUserListInteraction(int status, int pumpAgentId, Object object, Dialog dialog) {
        if(status==1){
            this.pumpAgentId=pumpAgentId;
            if(dialog != null)
                if(dialog.isShowing())
                    dialog.dismiss();
            loadPayments();
            UserDetails userDetails=(UserDetails) object;
            tv.setText("Money report for "+ userDetails.getFname());
            this.userName = userDetails.getFname();
        }else{
            if(dialog != null)
                if(dialog.isShowing())
                    dialog.dismiss();
            mListener.onFragmentReportInteraction(Dipping.newInstance(userId, branchId), 0 ,"Go to home");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentReportMoney {
        void onFragmentReportInteraction(Object object, int statusCode, String message);
        void onRequestUserNozzles(ReportMoneyRequest reportMoneyRequest, int pumpAgentId, String pumpAgenName, int statusCode, String message);
    }
}
