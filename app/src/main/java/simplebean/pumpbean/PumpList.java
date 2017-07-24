package simplebean.pumpbean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hp on 11/1/2016.
 */
public class PumpList {
    @JsonProperty("PumpDash")
    private
    List<PumpBean> pumps;
    @JsonProperty("message")
    private
    String message;
    @JsonProperty("statusCode")
    private
    int statusCode;

    public PumpList(List<PumpBean> pumps, String message, int statusCode) {
        this.setPumps(pumps);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public PumpList() {

    }

    public List<PumpBean> getPumps() {
        return pumps;
    }

    public void setPumps(List<PumpBean> pumps) {
        this.pumps = pumps;
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
