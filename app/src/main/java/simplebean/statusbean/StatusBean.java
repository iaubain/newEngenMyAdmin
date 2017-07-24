package simplebean.statusbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Owner on 7/10/2016.
 */
public class StatusBean {
    @JsonProperty("statusCode")
    private int statusCode;
    @JsonProperty("message")
    private String message;

    public StatusBean() {
    }

    public StatusBean(String message, int statusCode) {

        this.setMessage(message);
        this.setStatusCode(statusCode);
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
