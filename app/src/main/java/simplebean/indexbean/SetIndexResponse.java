package simplebean.indexbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 11/3/2016.
 */
public class SetIndexResponse {
    @JsonProperty("NozzleAdjust")
    private
    SetIndexBean setIndexBean;
    @JsonProperty("message")
    private
    String message;
    @JsonProperty("statusCode")
    private
    int statusCode;

    public SetIndexResponse(SetIndexBean setIndexBean, String message, int statusCode) {
        this.setSetIndexBean(setIndexBean);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public SetIndexResponse() {

    }

    public SetIndexBean getSetIndexBean() {
        return setIndexBean;
    }

    public void setSetIndexBean(SetIndexBean setIndexBean) {
        this.setIndexBean = setIndexBean;
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
