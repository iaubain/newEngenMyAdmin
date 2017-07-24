package simplebean.indexbean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 11/2/2016.
 */
public class SetIndexBean {
    @JsonProperty("userId")
    private int userId;
    @JsonIgnore
    private int branchId;
    @JsonIgnore
    private int pumpId;
    @JsonIgnore
    private String pumpName;
    @JsonProperty("nozzleId")
    private int nozzleId;
    @JsonIgnore
    private String nozzleName;
    @JsonProperty("index")
    private double index;

    public SetIndexBean(int userId, int branchId, int pumpId, String pumpName, int nozzleId, String nozzleName, double index) {
        this.setUserId(userId);
        this.setBranchId(branchId);
        this.setPumpId(pumpId);
        this.setPumpName(pumpName);
        this.setNozzleId(nozzleId);
        this.setNozzleName(nozzleName);
        this.setIndex(index);
    }

    public SetIndexBean() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getPumpId() {
        return pumpId;
    }

    public void setPumpId(int pumpId) {
        this.pumpId = pumpId;
    }

    public String getPumpName() {
        return pumpName;
    }

    public void setPumpName(String pumpName) {
        this.pumpName = pumpName;
    }

    public int getNozzleId() {
        return nozzleId;
    }

    public void setNozzleId(int nozzleId) {
        this.nozzleId = nozzleId;
    }

    public String getNozzleName() {
        return nozzleName;
    }

    public void setNozzleName(String nozzleName) {
        this.nozzleName = nozzleName;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }
}
