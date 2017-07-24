package simplebean.tankingbean;

/**
 * Created by Hp on 10/3/2016.
 */
public class TankingRespBean {
    private int id;
    private int userId;
    private int tankId;
    private double preTankingMeasuredDip;
    private double preTankingCalculatedDip;
    private String deliveredBy;
    private double theoriticalTanked;
    private String plateNumber;
    private double postTankingMeasuredDip;
    private double postTankingCalculatedDip;
    private double datetime;
    private double waterValue;

    public TankingRespBean(int id, int userId, int tankId, double preTankingMeasuredDip, double preTankingCalculatedDip, String deliveredBy, double theoriticalTanked, String plateNumber, double postTankingMeasuredDip, double postTankingCalculatedDip, double datetime, double waterValue) {
        this.setId(id);
        this.setUserId(userId);
        this.setTankId(tankId);
        this.setPreTankingMeasuredDip(preTankingMeasuredDip);
        this.setPreTankingCalculatedDip(preTankingCalculatedDip);
        this.setDeliveredBy(deliveredBy);
        this.setTheoriticalTanked(theoriticalTanked);
        this.setPlateNumber(plateNumber);
        this.setPostTankingMeasuredDip(postTankingMeasuredDip);
        this.setPostTankingCalculatedDip(postTankingCalculatedDip);
        this.setDatetime(datetime);
        this.setWaterValue(waterValue);
    }

    public TankingRespBean() {

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

    public double getPreTankingMeasuredDip() {
        return preTankingMeasuredDip;
    }

    public void setPreTankingMeasuredDip(double preTankingMeasuredDip) {
        this.preTankingMeasuredDip = preTankingMeasuredDip;
    }

    public double getPreTankingCalculatedDip() {
        return preTankingCalculatedDip;
    }

    public void setPreTankingCalculatedDip(double preTankingCalculatedDip) {
        this.preTankingCalculatedDip = preTankingCalculatedDip;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public double getTheoriticalTanked() {
        return theoriticalTanked;
    }

    public void setTheoriticalTanked(double theoriticalTanked) {
        this.theoriticalTanked = theoriticalTanked;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getPostTankingMeasuredDip() {
        return postTankingMeasuredDip;
    }

    public void setPostTankingMeasuredDip(double postTankingMeasuredDip) {
        this.postTankingMeasuredDip = postTankingMeasuredDip;
    }

    public double getPostTankingCalculatedDip() {
        return postTankingCalculatedDip;
    }

    public void setPostTankingCalculatedDip(double postTankingCalculatedDip) {
        this.postTankingCalculatedDip = postTankingCalculatedDip;
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
