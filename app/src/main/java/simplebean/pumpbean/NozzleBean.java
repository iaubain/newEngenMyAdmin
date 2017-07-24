package simplebean.pumpbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 11/1/2016.
 */
public class NozzleBean {
    @JsonProperty("nozzleId")
    private int nozzleId;
    @JsonProperty("nozzleName")
    private String nozzleName;
    @JsonProperty("nozzleIndex")
    private Double nozzleIndex;
    @JsonProperty("productId")
    private int productId;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("unitPrice")
    private int unitPrice;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("status")
    private int status;

    public NozzleBean(int nozzleId, String nozzleName, Double nozzleIndex, int productId, String productName, int unitPrice, String userName, int status) {
        this.setNozzleId(nozzleId);
        this.setNozzleName(nozzleName);
        this.setNozzleIndex(nozzleIndex);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setUnitPrice(unitPrice);
        this.setUserName(userName);
        this.setStatus(status);
    }

    public NozzleBean() {

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

    public Double getNozzleIndex() {
        return nozzleIndex;
    }

    public void setNozzleIndex(Double nozzleIndex) {
        this.nozzleIndex = nozzleIndex;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
