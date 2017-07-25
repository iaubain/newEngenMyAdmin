package simplebean.indexnozzles;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ISHIMWE Aubain Consolateur. email: iaubain@yahoo.fr / aubain.c.ishimwe@oltranz.com Tel: +250 785 534 672 / +250 736 864 662 on 7/25/2017.
 */

public class IndexNozzleResponse {
    @JsonProperty("Nozzle")
    private IndexNozzle indexNozzle;
    private String message;
    private int statusCode;

    public IndexNozzleResponse() {
    }

    public IndexNozzleResponse(IndexNozzle indexNozzle, String message, int statusCode) {
        this.setIndexNozzle(indexNozzle);
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public IndexNozzle getIndexNozzle() {
        return indexNozzle;
    }

    public void setIndexNozzle(IndexNozzle indexNozzle) {
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
