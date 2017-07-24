package simplebean.reportmoneybean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 11/11/2016.
 */
public class ReportResponse {
    @JsonProperty("statusCode")
    private
    int statusCode;
    @JsonProperty("message")
    private
    String message;
    @JsonProperty("UserShiftModel")
    private
    UserShiftBean userShiftBean;

    public ReportResponse(int statusCode, String message, UserShiftBean userShiftBean) {
        this.setStatusCode(statusCode);
        this.setMessage(message);
        this.setUserShiftBean(userShiftBean);
    }

    public ReportResponse() {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserShiftBean getUserShiftBean() {
        return userShiftBean;
    }

    public void setUserShiftBean(UserShiftBean userShiftBean) {
        this.userShiftBean = userShiftBean;
    }
}
