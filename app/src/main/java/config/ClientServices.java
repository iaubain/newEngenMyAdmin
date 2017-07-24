package config;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import simplebean.dippingbean.DippingBean;
import simplebean.dippingbean.DippingResp;
import simplebean.indexbean.SetIndexBean;
import simplebean.indexbean.UserPumpRequest;
import simplebean.indexbean.SetIndexResponse;
import simplebean.loginbean.AuthenticationBean;
import simplebean.loginbean.AuthenticationResp;
import simplebean.orders.OrderRequest;
import simplebean.orders.OrderResponse;
import simplebean.orders.CommonRequest;
import simplebean.paymentmodebean.PaymentModeResponse;
import simplebean.pumpbean.PumpList;
import simplebean.reportmoneybean.ReportResponse;
import simplebean.reportmoneybean.ReportMoneyRequest;
import simplebean.reportnozzle.ReportNozzleResponse;
import simplebean.shiftreport.ShiftReportRequest;
import simplebean.shiftreport.ShiftReportResponse;
import simplebean.tankbean.TankList;
import simplebean.tankingbean.TankingBean;
import simplebean.tankingbean.TankingResp;
import simplebean.userbean.UserResponse;

/**
 * Created by Owner on 7/9/2016.
 */
public interface ClientServices {
    /**
     * @param authenticationBean
     * @return
     * application login Interface
     */
    //login client service
    @POST(AppUrl.LOGIN_URL)
    Call<AuthenticationResp> loginUser(@Body AuthenticationBean authenticationBean);

    /**
     * @param dippingBean
     * @return
     */
    //Dipping
    @POST(AppUrl.DIPPING_URL)
    Call<DippingResp> dipping(@Body DippingBean dippingBean);

    //TANK LIST
    @POST(AppUrl.TANKING_URL)
    Call<TankingResp> tanking(@Body TankingBean tankingBean);

    //Tank List
    @POST(AppUrl.TANK_LIST_URL)
    Call<TankList> getTankList(@Body CommonRequest commonRequest);

    //Pump List
    @POST(AppUrl.PUMP_URL)
    Call<PumpList> getPumpList(@Body UserPumpRequest setIndexRequest);

    //set Index
    @POST(AppUrl.SET_INDEX_URL)
    Call<SetIndexResponse> setIndex(@Body SetIndexBean setIndexBean);

    //Payment List
    @GET(AppUrl.PAYMENT_MODE_URL +"{userId}")
    Call<PaymentModeResponse> getPaymentMode(@Path("userId") int userId);

    //Publish Report
    @POST(AppUrl.REPORT_MONEY_URL)
    Call<ReportResponse> publishReport(@Body ReportMoneyRequest reportMoneyRequest);

    //Branch User List
    @GET(AppUrl.GET_USER_URL +"{branchId}")
    Call<UserResponse> getBranchUsers(@Path("branchId") int branchId);

    //get Nozzle for shift report
    @GET(AppUrl.GET_USER_REPORT_NOZZLE +"/{userId}")
    Call<ReportNozzleResponse> getReportNozzle(@Path("userId") int userId);

    //get User for shift report
    @GET(AppUrl.GET_USER_LIST +"/{userId}")
    Call<UserResponse> getShiftUsers(@Path("userId") int userId);

    //Post Shift Report
    @POST(AppUrl.POST_SHIFT_REPORT)
    Call<ShiftReportResponse> postShiftReport(@Body ShiftReportRequest shiftReportRequest);

    /**
     * @param orderRequest
     * @return
     * OrderResponse
     */
    //login client service
    @POST(AppUrl.GET_BRANCH_ORDERS)
    Call<OrderResponse> getBranchOrders(@Body OrderRequest orderRequest);
}
