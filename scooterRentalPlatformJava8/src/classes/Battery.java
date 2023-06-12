package classes;

public class Battery {
    private int ID;
    private double lat; // 緯度
    private double lng; // 經度
    private String distance;

    public Battery() {
    }

    public Battery(int ID, double lat, double lng) {
        this.ID = ID;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * 获取
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * 设置
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
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

    public void setDistance(double distance) {
        this.distance = distance + " 公里";
    }

    public void setDistance(String text) {
        this.distance = text;
    }

    public String getDistance() {
        return this.distance;
    }

    public String toString() {
        return "Battery{ID = " + ID + ", lat = " + lat + ", lng = " + lng + "}";
    }
}
