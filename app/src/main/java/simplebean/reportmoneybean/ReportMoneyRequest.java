package simplebean.reportmoneybean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hp on 11/10/2016.
 */
public class ReportMoneyRequest {
    @JsonProperty("userId")
    private int userId;
    @JsonProperty("paymentReport")
    private List<MoneyReportBean> moneyReportBeenList;

    public ReportMoneyRequest(int userId, List<MoneyReportBean> moneyReportBeenList) {
        this.setUserId(userId);
        this.setMoneyReportBeenList(moneyReportBeenList);
    }

    public ReportMoneyRequest() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<MoneyReportBean> getMoneyReportBeenList() {
        return moneyReportBeenList;
    }

    public void setMoneyReportBeenList(List<MoneyReportBean> moneyReportBeenList) {
        this.moneyReportBeenList = moneyReportBeenList;
    }
}
