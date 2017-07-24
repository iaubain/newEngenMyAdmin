package simplebean.shiftreport;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import simplebean.reportmoneybean.MoneyReportBean;
import simplebean.reportnozzle.NozzleReportBean;

/**
 * Created by Hp on 1/3/2017.
 */

public class ShiftReportRequest {
    @JsonProperty("adminId")
    private
    int adminId;
    @JsonProperty("userId")
    private
    int userId;
    @JsonProperty("nozzleReportList")
    private
    List<NozzleReportBean> nozzleReportList;
    @JsonProperty("paymentReportList")
    private
    List<MoneyReportBean> paymentReportList;

    public ShiftReportRequest(int adminId, int userId, List<NozzleReportBean> nozzleReportList, List<MoneyReportBean> paymentReportList) {
        this.setAdminId(adminId);
        this.setUserId(userId);
        this.setNozzleReportList(nozzleReportList);
        this.setPaymentReportList(paymentReportList);
    }

    public ShiftReportRequest() {

    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<NozzleReportBean> getNozzleReportList() {
        return nozzleReportList;
    }

    public void setNozzleReportList(List<NozzleReportBean> nozzleReportList) {
        this.nozzleReportList = nozzleReportList;
    }

    public List<MoneyReportBean> getPaymentReportList() {
        return paymentReportList;
    }

    public void setPaymentReportList(List<MoneyReportBean> paymentReportList) {
        this.paymentReportList = paymentReportList;
    }
}
