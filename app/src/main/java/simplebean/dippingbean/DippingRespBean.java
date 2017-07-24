package simplebean.dippingbean;

/**
 * Created by Hp on 10/3/2016.
 */
public class DippingRespBean {
    private int id;
    private int userId;
    private int tankId;
    private double measuredDip;
    private double calculatedDip;
    private double datetime;
    private double waterValue;

    public DippingRespBean(int id, int userId, int tankId, double measuredDip, double calculatedDip, double datetime, double waterValue) {
        this.setId(id);
        this.setUserId(userId);
        this.setTankId(tankId);
        this.setMeasuredDip(measuredDip);
        this.setCalculatedDip(calculatedDip);
        this.setDatetime(datetime);
        this.setWaterValue(waterValue);
    }

    public DippingRespBean() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTankId() {
        return tankId;
    }

    public void setTankId(int tankId) {
        this.tankId = tankId;
    }

    public double getMeasuredDip() {
        return measuredDip;
    }

    public void setMeasuredDip(double measuredDip) {
        this.measuredDip = measuredDip;
    }

    public double getCalculatedDip() {
        return calculatedDip;
    }

    public void setCalculatedDip(double calculatedDip) {
        this.calculatedDip = calculatedDip;
    }

    public double getDatetime() {
        return datetime;
    }

    public void setDatetime(double datetime) {
        this.datetime = datetime;
    }

    public double getWaterValue() {
        return waterValue;
    }

    public void setWaterValue(double waterValue) {
        this.waterValue = waterValue;
    }
}
