package simplebean.orders;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hp on 5/22/2017.
 */

public class OrderResponse {
    @JsonProperty("Order")
    private List<OrderBean> orderBeanList;
    private long statusCode;
    private String message;

    public OrderResponse() {

    }

    public OrderResponse(List<OrderBean> orderBeanList, long statusCode, String message) {
        this.setOrderBeanList(orderBeanList);
        this.setStatusCode(statusCode);
        this.setMessage(message);
    }


    public List<OrderBean> getOrderBeanList() {
        return orderBeanList;
    }

    public void setOrderBeanList(List<OrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
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
