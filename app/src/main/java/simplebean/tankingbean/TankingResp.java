package simplebean.tankingbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 9/26/2016.
 */
public class TankingResp {
    @JsonProperty("Tanking")
    private TankingRespBean tankingRespBean;
    @JsonProperty("message")
    private String message;
    @JsonProperty("statusCode")
    private int statusCode;

    public TankingResp(TankingRespBean tankingRespBean, String message, int statusCode) {
        this.setTankingRespBean(tankingRespBean);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public TankingResp() {

    }

    public TankingRespBean getTankingRespBean() {
        return tankingRespBean;
    }

    public void setTankingRespBean(TankingRespBean tankingRespBean) {
        this.tankingRespBean = tankingRespBean;
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
