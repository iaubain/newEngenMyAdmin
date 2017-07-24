package simplebean.loginbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 9/26/2016.
 */
public class AuthenticationBean {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public AuthenticationBean(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public AuthenticationBean() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
