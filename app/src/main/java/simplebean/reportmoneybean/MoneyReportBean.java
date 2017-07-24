package simplebean.reportmoneybean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Hp on 11/10/2016.
 */
public class MoneyReportBean {
    @JsonProperty("paymentModeId")
    private int paymentId;
    @JsonProperty("paymentMode")
    private String paymentName;
    @JsonProperty("amount")
    private BigDecimal totalValue;

    public MoneyReportBean(int paymentId, String paymentName, BigDecimal totalValue) {
        this.setPaymentId(paymentId);
        this.setPaymentName(paymentName);
        this.setTotalValue(totalValue);
    }

    public MoneyReportBean() {

    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
}
