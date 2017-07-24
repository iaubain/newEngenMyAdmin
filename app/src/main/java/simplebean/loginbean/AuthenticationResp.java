package simplebean.loginbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 9/26/2016.
 */
public class AuthenticationResp {
    @JsonProperty("LoginOpModel")
    private User user;
    @JsonProperty("message")
    private String message;
    @JsonProperty("statusCode")
    private int statusCode;

    public AuthenticationResp(User user, String message, int statusCode) {
        this.setUser(user);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public AuthenticationResp() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
