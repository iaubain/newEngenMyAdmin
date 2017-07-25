package simplebean.indexnozzles;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr / aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662 on 7/25/2017.
 */

public class IndexNozzleResult {
    @JsonProperty("Nozzle")
    private List<IndexNozzle> indexNozzle;
    private String message;
    private int statusCode;

    public IndexNozzleResult() {
    }

    public IndexNozzleResult(List<IndexNozzle> indexNozzle, String message, int statusCode) {
        this.setIndexNozzle(indexNozzle);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public List<IndexNozzle> getIndexNozzle() {
        return indexNozzle;
    }

    public void setIndexNozzle(List<IndexNozzle> indexNozzle) {
        this.indexNozzle = indexNozzle;
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
