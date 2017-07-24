package simplebean.tankingbean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 9/26/2016.
 */
public class TankingBean {
    @JsonProperty("id")
    private String id;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("tankId")
    private String tankId;
    @JsonProperty("orderNo")
    private String orderNo;
    @JsonProperty("preCalculatedDip")
    private String preCalculatedDip;
    @JsonProperty("postCalculatedDip")
    private String postCalculatedDip;
    @JsonProperty("waterValue")
    private String waterValue;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonIgnore
    private String tankName;
    public TankingBean() {

    }

    public TankingBean(String id, String userId, String tankId, String orderNo, String preCalculatedDip, String postCalculatedDip, String waterValue, String createdBy, String tankName) {
        this.id = id;
        this.userId = userId;
        this.tankId = tankId;
        this.orderNo = orderNo;
        this.preCalculatedDip = preCalculatedDip;
        this.postCalculatedDip = postCalculatedDip;
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

    public String getPreCalculatedDip() {
        return preCalculatedDip;
    }

    public void setPreCalculatedDip(String preCalculatedDip) {
        this.preCalculatedDip = preCalculatedDip;
    }

    public String getPostCalculatedDip() {
        return postCalculatedDip;
    }

    public void setPostCalculatedDip(String postCalculatedDip) {
        this.postCalculatedDip = postCalculatedDip;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }
}
