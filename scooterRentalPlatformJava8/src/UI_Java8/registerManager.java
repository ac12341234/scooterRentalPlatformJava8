package UI_Java8;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class registerManager {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pst;

    // sql
    // private String deleteTAB_SQL = "drop table if exists User";
    private String createDB_SQL = "create database if not exists itcast";
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
    private String createTAB_SQL2 = "create table if not exists couponInfo(" +
            "    ID int primary key auto_increment," +
            "    account varchar(20) comment '帳號'," +
            "    date datetime comment '獲得時間'" +
            ")";
    private String insert_SQL = "insert into scooterUser(account,password,email, name, phone, creditCard, coupon) values(?, ?, ?, ?, ?, ?, 0)";
    private String select_SQL = "select * from scooterUser";

    public registerManager() throws Exception {

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

    public void createTable() throws Exception {
        try {
            // deleteTable();
            pst = conn.prepareStatement(createTAB_SQL);
            PreparedStatement pst2 = conn.prepareStatement(createTAB_SQL2);
            pst2.executeUpdate();
            pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            System.out.println("Create Table Exception :" + e.toString());
            conn.rollback();
        } finally {
            close();
        }
    }

    public int insertTable(String Name, String Password, String email, String name, String phone, String creditCard) throws Exception {
        boolean confirm = SelectTable(Name);
        try {
            if (confirm) {
                pst = conn.prepareStatement(insert_SQL);
                pst.setString(1, Name);
                pst.setString(2, Password);
                pst.setString(3, email);
                pst.setString(4, name);
                pst.setString(5, phone);
                pst.setString(6, creditCard);
                pst.executeUpdate();
                conn.commit();
                return 1; // 成功註冊
            } else {
                return 2; // 用戶名已存在
            }
        } catch (Exception e) {
            System.out.println("Insert Exception :" + e.toString());
            conn.rollback();
        } finally {
            close();
        }
        return 0;
    }

    public boolean SelectTable(String account) {

        try {
            pst = conn.prepareStatement(select_SQL);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("account").equals(account))
                    return false;
            }
        } catch (SQLException e) {
            System.out.println("Selection Exception :" + e.toString());
        } finally {
            close();
        }
        return true;
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

    public int updateTable(String oldAccount, String newAccount, String password, String email, String name, String phone, String creditCard, int id) throws SQLException {
        String sql = "update scooteruser set account = ?, password = ?, email = ?, name = ?, phone = ?, creditCard = ? where ID = ?";
        boolean flag = oldAccount.equals(newAccount);
        boolean confirm = SelectTable(newAccount);
        if (flag || confirm) {
            // 未更改account
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, oldAccount);
                pst.setString(2, password);
                pst.setString(3, email);
                pst.setString(4, name);
                pst.setString(5, phone);
                pst.setString(6, creditCard);
                pst.setInt(7, id);
                pst.executeUpdate();
                conn.commit();
                return 1;
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                close();
            }
        }
        return 2;
    }
}
