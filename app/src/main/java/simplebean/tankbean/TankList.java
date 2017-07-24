package simplebean.tankbean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hp on 9/26/2016.
 */
public class TankList {
    @JsonProperty("Tank")
    private List<TankBean> mTanks;
    @JsonProperty("message")
    private String message;
    @JsonProperty("statusCode")
    private int statusCode;

    public TankList(List<TankBean> mTanks, String message, int statusCode) {
        this.setmTanks(mTanks);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public TankList() {

    }

    public List<TankBean> getmTanks() {
        return mTanks;
    }

    public void setmTanks(List<TankBean> mTanks) {
        this.mTanks = mTanks;
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
