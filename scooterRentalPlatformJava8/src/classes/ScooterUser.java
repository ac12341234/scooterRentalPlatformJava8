package classes;

public class ScooterUser {
    private int ID;
    private String account;
    private String password;
    private String phone;
    private String email;
    private String name;
    private String creditCard;
    private int coupon;

    public ScooterUser() {
    }

    public ScooterUser(int ID, String account, String password, String phone, String email, String name, String creditCard, int coupon) {
        this.ID = ID;
        this.account = account;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.creditCard = creditCard;
        this.coupon = coupon;
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
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return creditCard
     */
    public String getCreditCard() {
        return creditCard;
    }

    /**
     * 设置
     * @param creditCard
     */
    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * 获取
     * @return coupon
     */
    public int getCoupon() {
        return coupon;
    }

    /**
     * 设置
     * @param coupon
     */
    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public String toString() {
        return "ScooterUser{ID = " + ID + ", account = " + account + ", password = " + password + ", phone = " + phone + ", email = " + email + ", name = " + name + ", creditCard = " + creditCard + ", coupon = " + coupon + "}";
    }
}
