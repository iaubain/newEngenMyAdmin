package modules;

import android.util.Log;

import config.ClientData;
import config.ClientServices;
import config.ServerClient;
import fragments.ReportMoney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplebean.reportnozzle.ReportNozzleResponse;
import utilities.UserListAdapter;

/**
 * Created by Hp on 1/3/2017.
 */

public class RequestNozzlePerShift {
    private RequestNozzleInteraction mListener;
    private int userId;
    private String tag = getClass().getSimpleName();

    public RequestNozzlePerShift(RequestNozzleInteraction mListener, int userId) {
        this.mListener = mListener;
        this.userId = userId;
    }

    public void startRequest(){
        try {
            ClientServices clientServices = ServerClient.getClient().create(ClientServices.class);
            Call<ReportNozzleResponse> callService = clientServices.getReportNozzle(userId);
            callService.enqueue(new Callback<ReportNozzleResponse>() {
                @Override
                public void onResponse(Call<ReportNozzleResponse> call, Response<ReportNozzleResponse> response) {
                    //HTTP status code
                    int statusCode = response.code();
                    if(statusCode != 200){
                        mListener.onRequestNozzles(statusCode, response.message(), null);
                        return;
                    }
                    Log.d(tag,"Server Result: \n"+new ClientData().mapping(response.body()));
                    try{
                        Log.d(getClass().getSimpleName(),new ClientData().mapping(response.body()));

                        if(response.body().getStatusCode() == 100){
                            //dispatch Data
                            mListener.onRequestNozzles(response.body().getStatusCode(), response.body().getMessage(), response.body());
                        }else{
                            //Notify User that an error happened
                            mListener.onRequestNozzles(response.body().getStatusCode(), response.body().getMessage(), null);
                        }

                    } catch (final Exception e) {
                        mListener.onRequestNozzles(statusCode, e.getLocalizedMessage(), null);
                    }
                }

                @Override
                public void onFailure(Call<ReportNozzleResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(getClass().getSimpleName(), t.toString());
                    mListener.onRequestNozzles(500, t.getLocalizedMessage(), null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onRequestNozzles(500, e.getLocalizedMessage(), null);
        }
    }

    public interface RequestNozzleInteraction{
        void onRequestNozzles(int statusCode, String message, ReportNozzleResponse reportNozzleResponse);
    }
}
