package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.olranz.payfuel.spmyadmin.R;

import java.util.ArrayList;
import java.util.List;

import config.ClientData;
import modules.Progress;
import modules.RequestNozzlePerShift;
import simplebean.reportmoneybean.MoneyReportBean;
import simplebean.reportmoneybean.ReportMoneyRequest;
import simplebean.reportnozzle.NozzleReportBean;
import simplebean.reportnozzle.ReportNozzleResponse;
import utilities.ReportNozzleAdapter;

public class ReportNozzleIndex extends Fragment implements RequestNozzlePerShift.RequestNozzleInteraction, ReportNozzleAdapter.NozzleReportInteraction {
    private String tag = getClass().getSimpleName();
    private static final String ARG_ADMIN_ID = "adminId";
    private static final String ARG_PUMP_AGENT_ID = "pumpAgentId";
    private static final String ARG_PUMP_AGENT_NAME = "pumpAgentName";
    private static final String ARG_MONEY_REPORT = "moneyReport";

    private int adminId;
    private int pumpAgentId;
    private String pumpAgentName;
    private String moneyReport;
    private List<MoneyReportBean> moneyReportBeanList;
    private List<NozzleReportBean> mReportNozzleList;

    private RecyclerView mRecyclerView;
    private ReportNozzleAdapter mAdapter;
    private TextView tv;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private Button submit;
    private Progress progress;
    private ReportNozzleIndexInteraction mListener;

    public ReportNozzleIndex() {
    }

    public static ReportNozzleIndex newInstance(int adminId, int pumpAgentId, String pumpAgentName, String moneyReport) {
        ReportNozzleIndex fragment = new ReportNozzleIndex();
        Bundle args = new Bundle();
        args.putInt(ARG_ADMIN_ID, adminId);
        args.putInt(ARG_PUMP_AGENT_ID, pumpAgentId);
        args.putString(ARG_MONEY_REPORT, moneyReport);
        args.putString(ARG_PUMP_AGENT_NAME, pumpAgentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            adminId = getArguments().getInt(ARG_ADMIN_ID);
            pumpAgentId = getArguments().getInt(ARG_PUMP_AGENT_ID);
            pumpAgentName = getArguments().getString(ARG_PUMP_AGENT_NAME);
            ReportMoneyRequest reportMoneyRequest = (ReportMoneyRequest) new ClientData().jsonObject(ReportMoneyRequest.class, getArguments().getString(ARG_MONEY_REPORT));
            moneyReportBeanList = reportMoneyRequest.getMoneyReportBeenList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.report_nozzle_index_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mReportNozzleList = new ArrayList<>();

        mRecyclerView=(RecyclerView) view.findViewById(R.id.nozzleList);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        requestNozzle();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReportNozzleIndexInteraction) {
            mListener = (ReportNozzleIndexInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void requestNozzle(){
        Log.d(tag, "requesting nozzles for the user: "+ pumpAgentId);
        progress = new Progress(getActivity(), "Loading nozzles");
        new RequestNozzlePerShift(ReportNozzleIndex.this, pumpAgentId).startRequest();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRequestNozzles(int statusCode, String message, ReportNozzleResponse reportNozzleResponse) {
        if(progress != null)
            progress.dismissProgress();

        if(statusCode != 100){
            //refresh pop up
            refreshPopUp(message);
            return;
        }
        //put the nozzles into an adapter
        if(reportNozzleResponse.getReportNozzle().isEmpty()){
            refreshPopUp("Error: "+ message);
            return;
        }
        mAdapter = new ReportNozzleAdapter(ReportNozzleIndex.this, getActivity(), reportNozzleResponse.getReportNozzle());
        mRecyclerView.setAdapter(mAdapter);

        mReportNozzleList.clear();

        for(NozzleReportBean nozzleReportBean : reportNozzleResponse.getReportNozzle()){
            nozzleReportBean.setNewIndex(nozzleReportBean.getOldIndex());
            mReportNozzleList.add(nozzleReportBean);
        }
    }

    private void refreshPopUp(String message){
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(message)
                    .setTitle("Notification...");
            // Add the buttons
            builder.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    requestNozzle();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    //redirect to the home page
                    mListener.onReportNozzleInteractionRequestHome(true);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNozzleReportInteraction(int status, NozzleReportBean mReportNozzle) {
        if(status == 1){
            if(mReportNozzle.getNewIndex().compareTo(mReportNozzle.getOldIndex())>=0){
                NozzleReportBean report=getReportElement(mReportNozzle);
                if(report != null){
                    mReportNozzleList.remove(report);
                    report.setNewIndex(mReportNozzle.getNewIndex());
                    mReportNozzleList.add(mReportNozzle);
                }else{
                    Toast.makeText(getContext(), "Object not found in nozzle list", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), "Unsupported values", Toast.LENGTH_SHORT).show();
            }
        }else{
                NozzleReportBean report=getReportElement(mReportNozzle);
                if(report != null){
                    mReportNozzleList.remove(report);
                    report.setNewIndex(mReportNozzle.getOldIndex());
                    mReportNozzleList.add(mReportNozzle);
                }
        }
//        List<NozzleReportBean> tempList = new ArrayList<>();
//        tempList.addAll(mReportNozzleList);

//        if(mReportNozzleList.size() == 0 && status == 1){
//            mReportNozzleList.add(mReportNozzle);
//        }else{
//            if(status == 1){
//                if(!isElementAvailable(tempList, mReportNozzle)) {
//                    mReportNozzleList.add(mReportNozzle);
//                }else{
//                    NozzleReportBean report=getReportElement(mReportNozzle);
//                    if(report != null){
//                        mReportNozzleList.remove(report);
//                        mReportNozzleList.add(mReportNozzle);
//                    }
//                }
//            } else if(status == -1){
//                if(tempList.size() > 0)
//                    for(NozzleReportBean report : tempList){
//                        if(report.getNozzleId() == report.getNozzleId()){
//                            mReportNozzleList.remove(report);
//                        }
//                    }
//            }
//        }
//        if(tempList.size()>0)
//            tempList.clear();
    }

    private boolean isElementAvailable(List<NozzleReportBean> tempReport, NozzleReportBean nozzleReportBean){
        for(NozzleReportBean temp : tempReport){
            if(temp.getNozzleId() == nozzleReportBean.getNozzleId())
                return true;
        }
        return false;
    }

    private NozzleReportBean getReportElement(NozzleReportBean nozzleReportBean){
        for(NozzleReportBean temp : mReportNozzleList){
            if(temp.getNozzleId() == nozzleReportBean.getNozzleId())
                return temp;
        }
        return null;
    }

    public interface ReportNozzleIndexInteraction {
        void onReportNozzleInteraction(int statusCode, String message, Object object);
        void onReportNozzleInteractionRequestHome(boolean isRequestedHome);
    }
}
