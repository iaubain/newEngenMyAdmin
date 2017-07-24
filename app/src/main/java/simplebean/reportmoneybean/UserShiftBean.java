package simplebean.reportmoneybean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hp on 11/10/2016.
 */
public class UserShiftBean {
    @JsonProperty("userId")
    private
    int userId;
    @JsonProperty("paymentReport")
    private
    List<MoneyReportBean> moneyReportBeanList;

    public UserShiftBean(int userId, List<MoneyReportBean> moneyReportBeanList) {
        this.setUserId(userId);
        this.setMoneyReportBeanList(moneyReportBeanList);
    }

    public UserShiftBean() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<MoneyReportBean> getMoneyReportBeanList() {
        return moneyReportBeanList;
    }

    public void setMoneyReportBeanList(List<MoneyReportBean> moneyReportBeanList) {
        this.moneyReportBeanList = moneyReportBeanList;
    }
}
