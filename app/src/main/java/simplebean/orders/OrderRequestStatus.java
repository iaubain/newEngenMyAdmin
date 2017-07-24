package simplebean.orders;

/**
 * Created by Hp on 5/22/2017.
 */

public class OrderRequestStatus {
    private long statusCode;
    private String message;

    public OrderRequestStatus() {
    }

    public OrderRequestStatus(long statusCode, String message) {
        this.setStatusCode(statusCode);
        this.setMessage(message);
    }

    public long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
