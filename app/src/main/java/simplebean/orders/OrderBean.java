package simplebean.orders;

/**
 * Created by Hp on 5/22/2017.
 */

public class OrderBean {
    private String orderNo;
    private String branchId;
    private String branchName;
    private String productId;
    private String productName;
    private String qty;
    private String plateNo;
    private String transportCompanyName;
    private String driverName;
    private String estimateTime;
    private String status;
    private String creationTime;
    private String createdBy;

    public OrderBean() {
    }

    public OrderBean(String orderNo, String branchId, String branchName, String productId, String productName, String qty, String plateNo, String transportCompanyName, String driverName, String estimateTime, String status, String creationTime, String createdBy) {
        this.setOrderNo(orderNo);
        this.setBranchId(branchId);
        this.setBranchName(branchName);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setQty(qty);
        this.setPlateNo(plateNo);
        this.setTransportCompanyName(transportCompanyName);
        this.setDriverName(driverName);
        this.setEstimateTime(estimateTime);
        this.setStatus(status);
        this.setCreationTime(creationTime);
        this.setCreatedBy(createdBy);
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getTransportCompanyName() {
        return transportCompanyName;
    }

    public void setTransportCompanyName(String transportCompanyName) {
        this.transportCompanyName = transportCompanyName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
