package UI_Java8;

import classes.*;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class loginManager {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pst;

    // sql
    private String createDB_SQL = "create database if not exists itcast";
    private String select_SQL = "select * from scooterUser";
    private String select_SQL2 = "select ID, date from couponInfo where account = ?";
    private String CreateTAB_SQL2 = "create table if not exists totalScooter(" +
            "    ID int primary key auto_increment," +
            "    license varchar(15)," +
            "    lat double," +
            "    lng double," +
            "    power int," +
            "    status boolean," +
            "    isUsing boolean" +
            ") comment '車輛總表'";
    private String CreateTAB_SQL3 = "create table if not exists totalBattery(" +
            "    ID int primary key auto_increment," +
            "    lat double," +
            "    lng double" +
            ") comment '充電站總表'";
    private String findPassword_SQL = "select password from scooteruser where account = ? && email = ?";
    private String createTAB_SQL = "CREATE TABLE if not exists scooterUser (" +
            "ID int primary key auto_increment, " +
            "account VARCHAR(20), " +
            "password VARCHAR(20), " +
            "email varchar(30), " +
            "name varchar(15), " +
            "phone varchar(20), " +
            "creditCard varchar(20)," +
            "coupon int" +
            ")";
    private String createUserHistory = "create table if not exists scooteruserHistory(" +
            "    ID int primary key auto_increment," +
            "    account varchar(16)," +
            "    date date," +
            "    money int," +
            "    initLoc varchar(50)," +
            "    finalLoc varchar(50)," +
            "    distance double," +
            "    initTime timestamp," +
            "    finalTime timestamp," +
            "    duringTime int," +
            "    chargeCount int," +
            "    couponUse boolean" +
            ") comment '租車歷史紀錄'";

    public loginManager() throws Exception {

        Properties prop1 = new Properties();
        prop1.load(new FileInputStream("scooterRentalPlatformJava8\\src\\druid2.properties"));

        Connection connection = DruidDataSourceFactory.createDataSource(prop1).getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(createDB_SQL);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

        Properties prop2 = new Properties();
        prop2.load(new FileInputStream("scooterRentalPlatformJava8\\src\\druid.properties"));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop2);

        conn = dataSource.getConnection();
        conn.setAutoCommit(false);

        createTable();

    }

    /**
     * 判斷輸入是否合法
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    public int isValidLogin(String account, String password) throws Exception {

        // select data
        conn.setAutoCommit(true);
        try {
            pst = conn.prepareStatement(select_SQL);
            rs = pst.executeQuery();
            ArrayList<ScooterUser> users = new ArrayList<>();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String uAccount = rs.getString("account");
                String uPassword = rs.getString("password");
                String uEmail = rs.getString("email");
                String uName = rs.getString("name");
                String uPhone = rs.getString("phone");
                String uCreditCard = rs.getString("creditCard");
                int uCoupon = rs.getInt("coupon");
                users.add(new ScooterUser(ID, uAccount, uPassword, uEmail, uName, uPhone, uCreditCard, uCoupon));
            }
            conn.setAutoCommit(false);
            for (ScooterUser user : users) {
                if (user.getAccount().equals(account)) {
                    if (user.getPassword().equals(password)) {
                        return 1; // 成功登入
                    } else {
                        return 2; // 用戶名存在, 但密碼錯誤
                    }
                }
            }
            return 3; // 用戶名不存在
        } catch (SQLException e) {
            System.out.println("Selection Exception :" + e.toString());
        } finally {
            close();
        }
        return 0;
    }

    public void createTable() throws Exception {
        try {
            pst = conn.prepareStatement(createTAB_SQL);
            pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            System.out.println("Create Table Exception :" + e.toString());
            conn.rollback();
        } finally {
            close();
        }
    }

    public void createUserHistory() throws Exception {
        try {
            pst = conn.prepareStatement(createUserHistory);
            pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    /**
     * 讀取coupon
     * @param account
     * @return
     */
    public int SelectCoupon(String account) {
        try {
            pst = conn.prepareStatement(select_SQL);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("account").equals(account)) {
                    return rs.getInt("coupon");
                }
            }
        } catch (SQLException e) {
            System.out.println("Selection Exception :" + e.toString());
        } finally {
            close();
        }
        return 0;
    }

    public void setCoupon(String account, int coupon, boolean couponNew) throws Exception {
        String sql = "update scooteruser set coupon = ? where account = ?";
        try {
            pst = conn.prepareStatement(sql);
            if (couponNew) {
                pst.setInt(1, coupon);
            } else {
                pst.setInt(1, coupon - 1);
            }
            pst.setString(2, account);
            pst.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println("Selection Exception :" + e.toString());
            conn.rollback();
        } finally {
            close();
        }

    }

    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            System.out.println("Close Exception :" + e.toString());
        }
    }

    /**
     * 找回密碼
     * @param username
     * @param email
     * @return
     * @throws Exception
     */
    public String findPassword(String username, String email) throws Exception {
        pst = conn.prepareStatement(findPassword_SQL);
        pst.setString(1, username);
        pst.setString(2, email);
        rs = pst.executeQuery();
        String password = null;
        while (rs.next()) {
            password = rs.getString("password");
        }
        close();
        return password;
    }

    /**
     * 查優惠券資訊
     * @param account
     * @return
     * @throws Exception
     */
    public ArrayList<Coupon> selectCouponHistory(String account) throws Exception {
        pst = conn.prepareStatement(select_SQL2);
        pst.setString(1, account);
        rs = pst.executeQuery();

        ArrayList<Coupon> list = new ArrayList<>();
        int i = 1;
        while (rs.next()) {
            int id = i;
            i++;
            Date date = rs.getDate("date");
            Time time = rs.getTime("date");
            list.add(new Coupon(id, date + " " + time));
        }
        return list;
    }

    public void createTotalScooterTAB(ArrayList<Scooter> list) throws Exception {
        try {
            pst = conn.prepareStatement(CreateTAB_SQL2);
            pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            System.out.println("Create Table Exception :" + e.toString());
            conn.rollback();
        } finally {
            insertTotalScooterData(list);
            close();
        }
    }

    public boolean insertTotalScooterData(ArrayList<Scooter> list) throws Exception {
        String sql2 = "select count(*) as count from totalScooter";
        pst = conn.prepareStatement(sql2);
        rs = pst.executeQuery();
        if (rs.next()) {
            if (rs.getInt("count") == 5198) {
                return true;
            }
            String sql3 = "TRUNCATE TABLE totalScooter";
            pst = conn.prepareStatement(sql3);
            pst.executeUpdate();
            String sql = "insert into totalscooter (license, lat, lng, power, status, isUsing) values (?, ?, ?, ?, ?, false)";
            for (Scooter scooter : list) {
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, scooter.getLicensePlate());
                    pst.setDouble(2, scooter.getLat());
                    pst.setDouble(3, scooter.getLng());
                    pst.setDouble(4, scooter.getPower());
                    pst.setBoolean(5, scooter.isCondition());
                    pst.executeUpdate();
                    conn.commit();
                } catch (Exception e) {
                    conn.rollback();
                }
            }
            close();
        }
        return true;
    }

    public void createTotalBatteryTAB(ArrayList<Battery> list) throws Exception {
        try {
            pst = conn.prepareStatement(CreateTAB_SQL3);
            pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            System.out.println("Create Table Exception :" + e.toString());
            conn.rollback();
        } finally {
            insertTotalBatteryData(list);
            close();
        }
    }

    public boolean insertTotalBatteryData(ArrayList<Battery> list) throws Exception {
        String sql2 = "select count(*) as count from totalBattery";
        pst = conn.prepareStatement(sql2);
        rs = pst.executeQuery();
        if (rs.next()) {
            if (rs.getInt("count") == 375) {
                return true;
            }
            String sql3 = "TRUNCATE TABLE totalBattery";
            pst = conn.prepareStatement(sql3);
            pst.executeUpdate();
            String sql = "insert into totalbattery (lat, lng) values (?, ?)";
            for (Battery battery : list) {
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setDouble(1, battery.getLat());
                    pst.setDouble(2, battery.getLng());
                    pst.executeUpdate();
                    conn.commit();
                } catch (Exception e) {
                    conn.rollback();
                }
            }
            close();
        }
        return true;
    }

    public void setUsing(Scooter scooter) throws Exception {
        String sql = "update totalScooter set isUsing = true where license = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, scooter.getLicensePlate());
            pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
        } finally {
            close();
        }
    }

    public ResultSet selectScooter() throws Exception {
        String sql = "select license, lat, lng, power from totalScooter where isUsing = false and status = true";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
        } finally {
            return rs;
        }
    }

    public boolean scooterUsing(Scooter scooter) throws Exception {
        String sql = "select isUsing from totalScooter where license = ?";
        boolean isUsing = false;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, scooter.getLicensePlate());
            rs = pst.executeQuery();
            conn.commit();
            while (rs.next()) {
                isUsing =  rs.getBoolean("license");
            }
        } catch (Exception e) {
            conn.rollback();
        } finally {
            close();
            return isUsing;
        }
    }

    public ArrayList<Scooter> selectAllScooter() throws Exception {
        String sql = "select license, lat, lng, power, status from totalScooter where isUsing = false";
        ArrayList<Scooter> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String license = rs.getString("license");
                double lat = rs.getDouble("lat");
                double lng = rs.getDouble("lng");
                double power = rs.getDouble("power");
                boolean status = rs.getBoolean("status");
                list.add(new Scooter(license, lat, lng, power, status));
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
        } finally {
            close();
        }
        return list;
    }

    public ResultSet selectBattery() throws Exception {
        String sql = "select lat, lng from totalbattery";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            conn.rollback();
        } finally {
            return rs;
        }
    }

    public void insertCouponInfo(String account, boolean couponNew) throws SQLException {
        if (couponNew) {
            String sql = "insert into couponInfo (account, date) values (?, current_timestamp)";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, account);
                pst.executeUpdate();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                close();
            }
        }
    }

    public void insertUserHistory(int money, double[] initLoc, double[] finalLoc, boolean couponUse) throws Exception {
        String sql = "insert into scooteruserhistory (account, date, money, initLoc, finalLoc, distance, initTime, finalTime, duringTime, chargeCount, couponUse) " +
                "values (?, current_date, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, SRPMainFrame.getAccount());
            pst.setInt(2, money);
            String initLocStr = "緯度: " + initLoc[0] + "\n" + "經度: " + initLoc[1];
            String finalLocStr = "緯度: " + finalLoc[0] + "\n" + "經度: " + finalLoc[1];
            pst.setString(3, initLocStr);
            pst.setString(4, finalLocStr);
            pst.setDouble(5, SRPMainFrame.getMile());
            pst.setTimestamp(6, Timestamp.valueOf(SearchFrame.getInitTime()));
            pst.setTimestamp(7, Timestamp.valueOf(ReturnScooterFrame.getFinalTime()));
            pst.setInt(8, SRPMainFrame.getDuringTimeInt());
            pst.setInt(9, ChargeFrame.getPowerCount());
            pst.setBoolean(10, couponUse);
            pst.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查用戶歷史紀錄
     * @return
     * @throws Exception
     */
    public ArrayList<UserHistory> selectUserHistory() throws Exception {
        String sql = "select * from scooteruserhistory where account = ?";
        pst = conn.prepareStatement(sql);
        pst.setString(1, SRPMainFrame.getAccount());
        rs = pst.executeQuery();
        ArrayList<UserHistory> list = new ArrayList<>();
        while (rs.next()) {
            Date date = rs.getDate("date");
            int money = rs.getInt("money");
            String initLoc = rs.getString("initLoc");
            String finalLoc = rs.getString("finalLoc");
            double distance = rs.getDouble("distance");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            String initTime = rs.getTimestamp("initTime").toLocalDateTime().format(formatter);
            String finalTime = rs.getTimestamp("finalTime").toLocalDateTime().format(formatter);
            int duringTime = rs.getInt("duringTime");
            int chargeCount = rs.getInt("chargeCount");
            boolean couponUse = rs.getBoolean("couponUse");
            list.add(new UserHistory(date, money, initLoc, finalLoc, distance, initTime, finalTime, duringTime, chargeCount, couponUse));
        }
        close();
        return list;
    }

    /**
     * 保存用戶使用的過程
     * @param scooter
     * @throws Exception
     */
    public void saveScooterEvent(Scooter scooter) throws Exception {
        String sql = "update totalscooter set lat = ?, lng = ?, power = ?, status = ?, isUsing = 0 where license = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setDouble(1, scooter.getLat());
            pst.setDouble(2, scooter.getLng());
            pst.setInt(3, (int) Math.round(scooter.getPower()));
            pst.setBoolean(4, scooter.isCondition());
            pst.setString(5, scooter.getLicensePlate());
            pst.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    /**
     * 查用戶資訊
     * @return
     * @throws Exception
     */
    public ScooterUser selectUserInfo() throws Exception {
        String sql = "select * from scooteruser where account = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, SRPMainFrame.getAccount());
            rs = pst.executeQuery();
            conn.commit();
            while (rs.next()) {
                if (rs.getString("account").equals(SRPMainFrame.getAccount())) {
                    int id = rs.getInt("ID");
                    String account = rs.getString("account");
                    String password = rs.getString("password");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    String name = rs.getString("name");
                    String creditCard = rs.getString("creditCard");
                    return new ScooterUser(id, account, password, phone, email, name, creditCard, 0);
                }

            }
        } catch (Exception e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            close();
        }
        return null;
    }

}
