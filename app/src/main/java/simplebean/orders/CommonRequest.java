package simplebean.orders;

/**
 * Created by Hp on 5/22/2017.
 */

public class CommonRequest {
    private String branchId;

    public CommonRequest(String branchId) {
        this.setBranchId(branchId);
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
