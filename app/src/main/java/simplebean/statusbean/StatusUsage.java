package simplebean.statusbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 9/19/2016.
 */
public class StatusUsage {
    @JsonProperty("status")
    private
    StatusBean status;

    public StatusUsage(StatusBean status) {
        this.setStatus(status);
    }

    public StatusUsage() {

    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }
}
