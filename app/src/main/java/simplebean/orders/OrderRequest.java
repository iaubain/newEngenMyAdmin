package simplebean.orders;

/**
 * Created by ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr / aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662 on 7/14/2017.
 */

public class OrderRequest {
    private int statusCode;
    private String branchId;

    public OrderRequest() {
    }

    public OrderRequest(int statusCode, String branchId) {
        this.setStatusCode(statusCode);
        this.setBranchId(branchId);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
