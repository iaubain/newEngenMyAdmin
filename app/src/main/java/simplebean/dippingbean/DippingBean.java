package simplebean.dippingbean;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Hp on 9/26/2016.
 */
public class DippingBean {
    private String id;
    private String userId;
    private String tankId;
    private String dippingQty;
    private String dippingTime;
    private String waterValue;
    private String createdBy;
    @JsonIgnore
    private String tankName;

    public DippingBean() {

    }

    public DippingBean(String id, String userId, String tankId, String dippingQty, String dippingTime, String waterValue, String createdBy, String tankName) {
        this.id = id;
        this.userId = userId;
        this.tankId = tankId;
        this.dippingQty = dippingQty;
        this.dippingTime = dippingTime;
        this.waterValue = waterValue;
        this.createdBy = createdBy;
        this.tankName = tankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTankId() {
        return tankId;
    }

    public void setTankId(String tankId) {
        this.tankId = tankId;
    }

    public String getDippingQty() {
        return dippingQty;
    }

    public void setDippingQty(String dippingQty) {
        this.dippingQty = dippingQty;
    }

    public String getWaterValue() {
        return waterValue;
    }

    public void setWaterValue(String waterValue) {
        this.waterValue = waterValue;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDippingTime() {
        return dippingTime;
    }

    public void setDippingTime(String dippingTime) {
        this.dippingTime = dippingTime;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }
}
