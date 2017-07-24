package simplebean.dippingbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 9/26/2016.
 */
public class DippingResp {
    @JsonProperty("Diping")
    private
    DippingRespBean dippingRespBean;
    @JsonProperty("message")
    private String message;
    @JsonProperty("statusCode")
    private int statusCode;

    public DippingResp(DippingRespBean dippingRespBean, String message, int statusCode) {
        this.setDippingRespBean(dippingRespBean);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public DippingResp() {

    }

    public DippingRespBean getDippingRespBean() {
        return dippingRespBean;
    }

    public void setDippingRespBean(DippingRespBean dippingRespBean) {
        this.dippingRespBean = dippingRespBean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
