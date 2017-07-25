package simplebean.indexnozzles;

/**
 * Created by ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr / aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662 on 7/25/2017.
 */

public class IndexNozzle {
    private String id;
    private String name;
    private double indexCount;
    private String preCalibration;
    private String nextCalibration;
    private String pumpId;
    private String pumpName;
    private String tankId;
    private String tankName;
    private String branchId;
    private String branchName;
    private String lastUsedTime;
    private String creationTime;
    private String createdBy;

    public IndexNozzle() {
    }

    public IndexNozzle(String id, String name, double indexCount, String preCalibration, String nextCalibration, String pumpId, String pumpName, String tankId, String tankName, String branchId, String branchName, String lastUsedTime, String creationTime, String createdBy) {
        this.setId(id);
        this.setName(name);
        this.setIndexCount(indexCount);
        this.setPreCalibration(preCalibration);
        this.setNextCalibration(nextCalibration);
        this.setPumpId(pumpId);
        this.setPumpName(pumpName);
        this.setTankId(tankId);
        this.setTankName(tankName);
        this.setBranchId(branchId);
        this.setBranchName(branchName);
        this.setLastUsedTime(lastUsedTime);
        this.setCreationTime(creationTime);
        this.setCreatedBy(createdBy);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(double indexCount) {
        this.indexCount = indexCount;
    }

    public String getPreCalibration() {
        return preCalibration;
    }

    public void setPreCalibration(String preCalibration) {
        this.preCalibration = preCalibration;
    }

    public String getNextCalibration() {
        return nextCalibration;
    }

    public void setNextCalibration(String nextCalibration) {
        this.nextCalibration = nextCalibration;
    }

    public String getPumpId() {
        return pumpId;
    }

    public void setPumpId(String pumpId) {
        this.pumpId = pumpId;
    }

    public String getPumpName() {
        return pumpName;
    }

    public void setPumpName(String pumpName) {
        this.pumpName = pumpName;
    }

    public String getTankId() {
        return tankId;
    }

    public void setTankId(String tankId) {
        this.tankId = tankId;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(String lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
