package simplebean.userbean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hp on 11/14/2016.
 */
public class UserResponse {
    @JsonProperty("User")
    private List<UserDetails> userDetailsList;
    @JsonProperty("message")
    private String message;
    @JsonProperty("statusCode")
    private int statusCode;

    public UserResponse(List<UserDetails> userDetailsList, String message, int statusCode) {
        this.setUserDetailsList(userDetailsList);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public UserResponse() {

    }

    public List<UserDetails> getUserDetailsList() {
        return userDetailsList;
    }

    public void setUserDetailsList(List<UserDetails> userDetailsList) {
        this.userDetailsList = userDetailsList;
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
