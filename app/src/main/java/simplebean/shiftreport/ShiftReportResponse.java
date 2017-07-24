package simplebean.shiftreport;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 1/3/2017.
 */

public class ShiftReportResponse {
    @JsonProperty("statusCode")
    private
    int statusCode;
    @JsonProperty("message")
    private
    String message;
    @JsonProperty("ReportOutput")
    private
    ShiftReportRequest shiftReportRequest;

    public ShiftReportResponse(int statusCode, String message, ShiftReportRequest shiftReportRequest) {
        this.setStatusCode(statusCode);
        this.setMessage(message);
        this.setShiftReportRequest(shiftReportRequest);
    }

    public ShiftReportResponse() {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ShiftReportRequest getShiftReportRequest() {
        return shiftReportRequest;
    }

    public void setShiftReportRequest(ShiftReportRequest shiftReportRequest) {
        this.shiftReportRequest = shiftReportRequest;
    }
}
