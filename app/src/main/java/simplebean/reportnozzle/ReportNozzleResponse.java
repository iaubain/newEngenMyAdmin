package simplebean.reportnozzle;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hp on 1/3/2017.
 */

public class ReportNozzleResponse {
    @JsonProperty("ReportNozzle")
    private List<NozzleReportBean> ReportNozzle;
    @JsonProperty("message")
    private String message;
    @JsonProperty("statusCode")
    private int statusCode;

    public ReportNozzleResponse(List<NozzleReportBean> reportNozzle, String message, int statusCode) {
        setReportNozzle(reportNozzle);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public ReportNozzleResponse() {

    }

    public List<NozzleReportBean> getReportNozzle() {
        return ReportNozzle;
    }

    public void setReportNozzle(List<NozzleReportBean> reportNozzle) {
        ReportNozzle = reportNozzle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
