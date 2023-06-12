package classes;

public class Coupon {
    private int ID;
    private String date;

    public Coupon() {
    }

    public Coupon(int ID, String date) {
        this.ID = ID;
        this.date = date;
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
    public String getDate() {
        return date;
    }

    /**
     * 设置
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "Coupon{ID = " + ID + ", date = " + date + "}";
    }
}
