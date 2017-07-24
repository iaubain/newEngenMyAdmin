package simplebean.reportnozzle;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Hp on 1/3/2017.
 */

public class NozzleReportBean {
    @JsonProperty("nozzleId")
    private int nozzleId;
    @JsonProperty("nozzleName")
    private String nozzleName;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("oldIndex")
    private BigDecimal oldIndex;
    @JsonProperty("newIndex")
    private BigDecimal newIndex;

    public NozzleReportBean(int nozzleId, String nozzleName, String productName, BigDecimal oldIndex, BigDecimal newIndex) {
        this.setNozzleId(nozzleId);
        this.setNozzleName(nozzleName);
        this.setProductName(productName);
        this.setOldIndex(oldIndex);
        this.setNewIndex(newIndex);
    }

    public NozzleReportBean() {

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getOldIndex() {
        return oldIndex;
    }

    public void setOldIndex(BigDecimal oldIndex) {
        this.oldIndex = oldIndex;
    }

    public BigDecimal getNewIndex() {
        return newIndex;
    }

    public void setNewIndex(BigDecimal newIndex) {
        this.newIndex = newIndex;
    }
}
