package simplebean.tankbean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Hp on 9/26/2016.
 */
public class TankBean {
    /*
    "id":"123456",
"name":"PMS",
"maxQty":"1000",
"curQty":"500",
"theoQty":"500",
"lastDipped":"YYYY-MM-DD HH:MM",
"preCalibration":"YYYY-MM-DD HH:MM",
"nextCalibration":"YYYY-MM-DD HH:MM",
"status":"7",
"branchId":"1",
"productId":"1",
"branchName":"Lagos",
"productName":"Super",
"creationTime":"YYYY-MM-DD HH:MM",
"createdBy":"John",
     */
    @JsonProperty("id")
    private int tankId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("maxQty")
    private Double maxCapacity;
    @JsonProperty("curQty")
    private Double currentCapacity;
    @JsonProperty("preCalibration")
    private String preCalibrationDate;
    @JsonProperty("nextCalibration")
    private String nextCalibrationDate;
    @JsonProperty("status")
    private long status;
    @JsonProperty("branchId")
    private int branchId;
    @JsonProperty("productId")
    private int productId;

    public TankBean() {
    }

    public TankBean(int tankId, String name, Double maxCapacity, Double currentCapacity, String preCalibrationDate, String nextCalibrationDate, long status, int branchId, int productId) {
        this.setTankId(tankId);
        this.setName(name);
        this.setMaxCapacity(maxCapacity);
        this.setCurrentCapacity(currentCapacity);
        this.setPreCalibrationDate(preCalibrationDate);
        this.setNextCalibrationDate(nextCalibrationDate);
        this.setStatus(status);
        this.setBranchId(branchId);
        this.setProductId(productId);
    }


    public int getTankId() {
        return tankId;
    }

    public void setTankId(int tankId) {
        this.tankId = tankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Double getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(Double currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public String getPreCalibrationDate() {
        return preCalibrationDate;
    }

    public void setPreCalibrationDate(String preCalibrationDate) {
        this.preCalibrationDate = preCalibrationDate;
    }

    public String getNextCalibrationDate() {
        return nextCalibrationDate;
    }

    public void setNextCalibrationDate(String nextCalibrationDate) {
        this.nextCalibrationDate = nextCalibrationDate;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
