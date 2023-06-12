package classes;

public class Scooter {
    private int ID;
    private String licensePlate;
    private double lat; // 緯度
    private double lng; // 經度
    private double power;
    private boolean condition;
    private String state;

    public Scooter() {
    }

    public Scooter(String licensePlate, double lat, double lng, double power, boolean condition) {
        this.licensePlate = licensePlate;
        this.lat = lat;
        this.lng = lng;
        this.power = power;
        this.condition = condition;
    }

    /**
     * 获取
     * @return licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * 设置
     * @param licensePlate
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * 获取
     * @return lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * 设置
     * @param lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * 获取
     * @return lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * 设置
     * @param lng
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * 获取
     * @return power
     */
    public double getPower() {
        return power;
    }

    /**
     * 设置
     * @param power
     */
    public void setPower(double power) {
        this.power = power;
    }

    /**
     * 获取
     * @return condition
     */
    public boolean isCondition() {
        return condition;
    }

    /**
     * 设置
     * @param condition
     */
    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setState(boolean condition) {
        if (condition) this.state = "正常";
        else this.state = "故障";
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public String toString() {
        return "Scooter{licensePlate = " + licensePlate + ", lat = " + lat + ", lng = " + lng + ", power = " + power + ", condition = " + condition + "}";
    }
}
