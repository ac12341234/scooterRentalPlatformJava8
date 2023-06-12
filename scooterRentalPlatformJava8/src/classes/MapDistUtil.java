package classes;

public class MapDistUtil {
    /**
     * 默認地球半徑
     */
    private static double EARTH_RADIUS = 6371;//赤道半徑(单位Km)

    /**
     * 轉化为弧度(rad)
     * */
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }
    /**
     * @param lng1 第一點的經度
     * @param lat1 第一點的緯度
     * @param lng2 第二點的經度
     * @param lat2 第二點的緯度
     * @return 返回的距離，單位 Km
     * */
    public static double GetDistance(double lng1,double lat1,double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000.0;
        return s;
    }
}
