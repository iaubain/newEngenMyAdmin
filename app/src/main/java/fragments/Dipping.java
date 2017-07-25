package fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.olranz.payfuel.engenmyadmin.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import config.ClientData;
import config.ClientServices;
import config.ServerClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplebean.dippingbean.DippingBean;
import simplebean.dippingbean.DippingResp;
import simplebean.orders.CommonRequest;
import simplebean.tankbean.TankBean;
import simplebean.tankbean.TankList;

import static java.lang.System.out;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDippingInteraction} interface
 * to handle interaction events.
 * Use the {@link Dipping#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dipping extends Fragment {
    private static final String ARG_USER_ID = "userId";
    private static final String ARG_BRANCH_ID = "branchId";

    private int userId;
    private int branchId;

    private Spinner mSpinner;
    private EditText dipValue, waterValue;
    private Button submit;

    private int selectedTankId;
    private String selectedTankName;
    private List<Integer> tankIds;
    private List<String> tankNames;
    private List<String> viewTankNames;
    private ArrayAdapter<String> adapter;
    private Typeface font;


    private OnDippingInteraction dipListener;

    public Dipping() {
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
    public static Dipping newInstance(int userId, int branchId) {
        Dipping fragment = new Dipping();
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
            branchId = getArguments().getInt(ARG_BRANCH_ID);        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dipListener.onDippingChangeTitle(getClass().getSimpleName());
        return inflater.inflate(R.layout.dipping_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getTanks();

        font=Typeface.createFromAsset(getContext().getAssets(), "font/ubuntu.ttf");
        dipValue=(EditText) view.findViewById(R.id.dipValue);
        dipValue.setTypeface(font);
        waterValue =(EditText) view.findViewById(R.id.waterValue);
        waterValue.setTypeface(font);
        submit=(Button) view.findViewById(R.id.submit);
        submit.setTypeface(font, Typeface.BOLD);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate fields
                Double dipV, waterV;
                try {

                }catch (Exception e){
                    uiFeed(e.getMessage());
                }
                if(!TextUtils.isEmpty(dipValue.getText().toString())){

                    dipV=Double.valueOf(dipValue.getText().toString());
                    if(TextUtils.isEmpty(waterValue.getText().toString()))
                        waterV=0.0;
                    else
                        waterV=Double.valueOf(waterValue.getText().toString());

                    //proceed to confirm box
                    /*
                    this.setId(id);
        this.setUserId(userId);
        this.setTankId(tankId);
        this.setDippingQty(dippingQty);
        this.setDippingTime(dippingTime);
        this.setWaterValue(waterValue);
        this.setCreatedBy(createdBy);
                     */

                    String currentDate = new Date().getTime()+"";
                    DateFormat dFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
                    try{
                        currentDate = dFormat.format(new Date());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    DippingBean dippingBean=new DippingBean(null, userId+"", selectedTankId+"", dipV+"", currentDate, waterV+"", currentDate, selectedTankName);
                    confirmBox(dippingBean);
                }else{
                    // setting error to any field which has an error
                    uiFeed(getContext().getResources().getString(R.string.invalidData));
                }
            }
        });
    }

    private void confirmBox(final DippingBean dippingBean){

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
            boxTitle.setText("Confirm Dipping Values");

            boxTitle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= boxTitle.getRight() - boxTitle.getTotalPaddingRight()) {
                            // your action for drawable click event
                            out.print("Inside event");
                            return true;
                        }
                    }
                    out.print("Outside event");
                    return false;
                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    publishDippingValues(dippingBean);
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
            lbl.setText(getResources().getString(R.string.dipping));
            lbl.setTextSize(21);
            lbl.setId(View.generateViewId());
            lbl.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            boxContent.addView(lbl);

            TextView lblValue = new TextView(getContext());
            lblValue.setTypeface(font, Typeface.BOLD);
            lblValue.setText(dippingBean.getDippingQty()+"\n");
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
            lblValue.setText(dippingBean.getWaterValue()+"\n");
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
            lblValue.setText(dippingBean.getTankName()+"\n");
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
    private void confirmBoxOld(final DippingBean dippingBean){
        if(dippingBean != null){
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
            title.setText(getResources().getString(R.string.dippingBoxTitle));

            TextView content=(TextView) dialog.findViewById(R.id.dialogContent);
            content.setText(mSpinner.getSelectedItem().toString());
            content.append(getResources().getString(R.string.ln));

            content.append(getResources().getString(R.string.dipping));
            content.append(String.valueOf(dippingBean.getDippingQty()));
            content.append(getResources().getString(R.string.ln));

            content.append(getResources().getString(R.string.water));
            content.append(String.valueOf(dippingBean.getWaterValue()));
            content.append(getResources().getString(R.string.ln));

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
                    publishDippingValues(dippingBean);
                }
            });

            dialog.show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDippingInteraction) {
            dipListener = (OnDippingInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDippingInteraction");
        }
    }

    private void publishDippingValues(final DippingBean dippingBean){
        String dippingData=new ClientData().mapping(dippingBean);
        try {
            ClientServices clientServices = ServerClient.getClient().create(ClientServices.class);
            Call<DippingResp> callService = clientServices.dipping(dippingBean);
            callService.enqueue(new Callback<DippingResp>() {
                @Override
                public void onResponse(Call<DippingResp> call, Response<DippingResp> response) {

                    //HTTP status code
                    int statusCode = response.code();
                    try{
                        Log.d("Server Result", new ClientData().mapping(response.body()));
                        final DippingResp dippingResp = response.body();

                        if(dippingResp.getStatusCode() == 100){
                            //Successful Tanking
                            uiFeed(dippingResp.getMessage());
                        }else{
                            //Notify User that an error happened
                            uiFeed(dippingResp.getMessage());
                        }

                    } catch (final Exception e) {
                        uiFeed(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<DippingResp> call, Throwable t) {
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

                // dummy data
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
                        selectedTankId=tankIds.get(position);
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

    @Override
    public void onDetach() {
        super.onDetach();
        dipListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDippingInteraction {
        void onDippingInteraction(Object object, int statusCode, String message);
        void onDippingChangeTitle(String title);
    }
}
