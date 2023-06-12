package classes;

import java.time.LocalDateTime;
import java.util.Date;

public class UserHistory {
    private int ID;
    private Date date;
    private int money;
    private String initLoc;
    private String finalLoc;
    private double distance;
    private String initTime;
    private String finalTime;
    private int duringTime;
    private int chargeCount;
    private boolean couponUse;
    private String couponStr;

    public UserHistory() {
    }

    public UserHistory(Date date, int money, String initLoc, String finalLoc, double distance, String initTime, String finalTime, int duringTime, int chargeCount, boolean couponUse) {
        this.date = date;
        this.money = money;
        this.initLoc = initLoc;
        this.finalLoc = finalLoc;
        this.distance = distance;
        this.initTime = initTime;
        this.finalTime = finalTime;
        this.duringTime = duringTime;
        this.chargeCount = chargeCount;
        this.couponUse = couponUse;
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
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 获取
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * 设置
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * 获取
     * @return initLoc
     */
    public String getInitLoc() {
        return initLoc;
    }

    /**
     * 设置
     * @param initLoc
     */
    public void setInitLoc(String initLoc) {
        this.initLoc = initLoc;
    }

    /**
     * 获取
     * @return finalLoc
     */
    public String getFinalLoc() {
        return finalLoc;
    }

    /**
     * 设置
     * @param finalLoc
     */
    public void setFinalLoc(String finalLoc) {
        this.finalLoc = finalLoc;
    }

    /**
     * 获取
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * 设置
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * 获取
     * @return initTime
     */
    public String getInitTime() {
        return initTime;
    }

    /**
     * 设置
     * @param initTime
     */
    public void setInitTime(String initTime) {
        this.initTime = initTime;
    }

    /**
     * 获取
     * @return finalTime
     */
    public String getFinalTime() {
        return finalTime;
    }

    /**
     * 设置
     * @param finalTime
     */
    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    /**
     * 获取
     * @return duringTime
     */
    public int getDuringTime() {
        return duringTime;
    }

    /**
     * 设置
     * @param duringTime
     */
    public void setDuringTime(int duringTime) {
        this.duringTime = duringTime;
    }

    /**
     * 获取
     * @return chargeCount
     */
    public int getChargeCount() {
        return chargeCount;
    }

    /**
     * 设置
     * @param chargeCount
     */
    public void setChargeCount(int chargeCount) {
        this.chargeCount = chargeCount;
    }

    /**
     * 获取
     * @return couponUse
     */
    public boolean isCouponUse() {
        return couponUse;
    }

    /**
     * 设置
     * @param couponUse
     */
    public void setCouponUse(boolean couponUse) {
        this.couponUse = couponUse;
    }

    public String getCouponStr() {
        return couponStr;
    }

    public void setCouponStr(boolean couponUse) {
        if (couponUse) couponStr = "使用";
        else couponStr = "未使用";
    }

    public String toString() {
        return "UserHistory{ID = " + ID + ", date = " + date + ", money = " + money + ", initLoc = " + initLoc + ", finalLoc = " + finalLoc + ", distance = " + distance + ", initTime = " + initTime + ", finalTime = " + finalTime + ", duringTime = " + duringTime + ", chargeCount = " + chargeCount + ", couponUse = " + couponUse + "}";
    }
}
