package UI_Java8;

import classes.JDialogUtil;
import classes.Scooter;
import classes.SetBoundsUtil;
import classes.Distance;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用戶主介面
 */
public class SRPMainFrame extends Pane {

    private static String account;
    private static int coupon;
    private Label username;
    public static Scooter userScooter = new Scooter(null, 0, 0, 0, true);
    private static AnchorPane userInfo = new AnchorPane();
    private static Label licenseText;
    private static Label powerText;
    private static Label locationText;
    private static Label mileAgeText;
    private static Label DuringTimeText;
    private static double mile = 0;
    private static String duringTime = "0秒";
    private static int duringTimeInt = 0;
    private static Timeline timePass;
    private static Timeline locationModify;
    private static SearchFrame searchFrame;
    private static ChargeFrame chargeFrame;
    private static ReturnScooterFrame returnScooterFrame;
    private static Button stop;
    private static Button speed;
    private static Button intervalChange;
    private static double currentSpeed = 23;
    private static int currentInterval = 1;
    private static TextField speedInput;
    private static TextField intervalInput;
    private static double[] initLoc;
    private static double[] finalLoc;

    // 菜單
    MenuBar menuBar = new MenuBar();
    Menu menu = new Menu("基本功能");

    MenuItem menuItem1 = new MenuItem("重新登錄");
    MenuItem menuItem2 = new MenuItem("註冊帳號");
    MenuItem menuItem3 = new MenuItem("歷史紀錄");
    MenuItem menuItem4 = new MenuItem("用戶信息");
    MenuItem menuItem5 = new MenuItem("優惠券");
    MenuItem menuItem6 = new MenuItem("退出系統");

    SRPMainFrame(Stage stage, Scene loginScene, Scene registerScene) {

        stage.setOnCloseRequest(ev -> {
            ev.consume();
            if (userScooter.getLicensePlate() != null) {
                try {
                    LoginFrameJava8.manager.saveScooterEvent(userScooter);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            JDialogUtil.showJDialog("感謝你使用本系統", "期待下次相遇");
            stage.close();
            System.exit(0);
        });

        // 左側菜單
        menuBar.getMenus().add(menu);

        VBox vBox = new VBox();
        vBox.getChildren().add(menuBar);

        File bgFile = new File(LoginFrameJava8.path + "\\pictures\\backgroundMain.png");
        Image bg = new Image(bgFile.toURI().toString());

        ImageView imageView = new ImageView(bg);
        imageView.setFitWidth(800 * 1.255);
        imageView.setFitHeight(400 * 1.255);

        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().add(imageView);
        borderPane.setLeft(vBox);

        // 右側用戶當前資訊
        userInfo.setPrefWidth(360 * 1.255);
        userInfo.setPrefHeight(60 * 1.255);
        userInfo.setMaxWidth(340 * 1.255);
        userInfo.setMaxWidth(60 * 1.255);

        username = new Label(account);
        username.setStyle("-fx-font-size: 18px");
        SetBoundsUtil.setBounds(username, 60 * 1.255, 10 * 1.255, 100 * 1.255, 35 * 1.255);
        File userFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\user.png");
        ImageView userIcon = new ImageView(userFile.toURI().toString());
        SetBoundsUtil.setBounds(userIcon, 20 * 1.255, 15 * 1.255, 32 * 1.255, 32 * 1.255);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Label time = new Label();
        time.setStyle("-fx-font-size: 18px");
        SetBoundsUtil.setBounds(time, 205 * 1.255, 10 * 1.255, 140 * 1.255, 35 * 1.255);
        File timeFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\time.png");
        ImageView timeIcon = new ImageView(timeFile.toURI().toString());
        SetBoundsUtil.setBounds(timeIcon, 165 * 1.255, 15 * 1.255, 32 * 1.255, 32 * 1.255);

        // 租車信息
        File rentDurTimeFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\DuringTime.png");
        ImageView rentDurTime = new ImageView(rentDurTimeFile.toURI().toString());

        DuringTimeText = new Label("以租車: " + duringTime);

        SetBoundsUtil.setBounds(rentDurTime, 180 * 1.255, 70 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(DuringTimeText, 220 * 1.255, 70 * 1.255, 120 * 1.255, 35 * 1.255);

        EventHandler<ActionEvent> duringTimeEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                duringTimeInt++;
                timing();
            }
        };

        timePass = new Timeline(new KeyFrame(Duration.seconds(1), duringTimeEvent));
        timePass.setCycleCount(Animation.INDEFINITE);

        // 車種
        File licenseFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\license.png");

        ImageView license = new ImageView(licenseFile.toURI().toString());
        licenseText = new Label("尚未租車  無法確定");

        SetBoundsUtil.setBounds(license, 20 * 1.255, 70 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(licenseText, 60 * 1.255, 70 * 1.255, 200 * 1.255, 35 * 1.255);

        // 電量
        File powerFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\battery.png");

        ImageView power = new ImageView(powerFile.toURI().toString());
        powerText = new Label("尚未租車  無法確定");

        SetBoundsUtil.setBounds(power, 20 * 1.255, 115 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(powerText, 60 * 1.255, 115 * 1.255, 200 * 1.255, 35 * 1.255);

        // 位置
        File locationFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\location.png");

        ImageView location = new ImageView(locationFile.toURI().toString());
        locationText = new Label("尚未租車  無法確定");

        SetBoundsUtil.setBounds(location, 20 * 1.255, 160 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(locationText, 60 * 1.255, 140 * 1.255, 200 * 1.255, 70 * 1.255);

        // 里程數
        File mileAgeFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\mileage.png");

        ImageView mileAge = new ImageView(mileAgeFile.toURI().toString());
        mileAgeText = new Label("已行駛: " + mile + "公里");

        SetBoundsUtil.setBounds(mileAge, 180 * 1.255, 115 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(mileAgeText, 220 * 1.255, 115 * 1.255, 200 * 1.255, 35 * 1.255);

        userInfo.getChildren().addAll(licenseText, license, powerText, power, locationText, location,
                rentDurTime, DuringTimeText, mileAge, mileAgeText);

        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                time.setText(df.format(new Date()));
            }
        };

        userInfo.getChildren().addAll(username, time, userIcon, timeIcon);

        Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), eventHandler));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        // 三大介面
        searchFrame = new SearchFrame(userInfo);
        chargeFrame = new ChargeFrame(userInfo);
        returnScooterFrame = new ReturnScooterFrame();

        // 位置變化
        // 預設值
        EventHandler<ActionEvent> locationModifyEvent = newEventHandler(-1);
        KeyFrame[] keyFrames = new KeyFrame[1];
        keyFrames[0] = new KeyFrame(Duration.seconds(10), locationModifyEvent);

        locationModify = new Timeline(keyFrames[0]);
        locationModify.setCycleCount(Animation.INDEFINITE);

        // 停止駕駛按鈕
        stop = new Button("開始駕駛");
        stop.setLayoutX(145 * 1.255);
        stop.setLayoutY(210 * 1.255);
        stop.setOnAction(e -> {
            if (SRPMainFrame.userScooter.getLicensePlate() != null) {
                if (stop.getText().equals("停止駕駛")) {
                    setStart(false);
                    stop.setText("開始駕駛");
                } else {
                    setStart(true);
                    stop.setText("停止駕駛");
                }
            }
        });

        // 調整速度按鈕
        speed = new Button("調整行車速度");
        SetBoundsUtil.setBounds(speed, 65 * 1.255, 250 * 1.255);

        speedInput = new TextField();
        SetBoundsUtil.setBounds(speedInput, 165 * 1.255, 250 * 1.255, 120 * 1.255, 30 * 1.255);
        speedInput.setPromptText("更改速度 km/hr");
        speedInput.setEditable(false);
        speed.setOnAction(e -> {
            if (speedInput.getText().equals("")) {
                speedInput.setEditable(true);
            } else {
                if (speedInput.getText().matches("\\d+(\\.\\d+)?")) {
                    speedInput.setEditable(false);
                    double newSpeed = Double.parseDouble(speedInput.getText());
                    currentSpeed = newSpeed;
                    speedInput.setText("");
                    keyFrames[0] = new KeyFrame(Duration.seconds(currentInterval), newEventHandler(newSpeed));
                    locationModify.stop();
                    locationModify = new Timeline(keyFrames[0]);
                    locationModify.setCycleCount(Animation.INDEFINITE);
                    locationModify.play();
                } else {
                    JDialogUtil.showJDialog("輸入格式錯誤", "請重新輸入");
                }
            }
        });

        // 調整位置變換的間隔
        intervalChange = new Button("調整時間間隔");
        SetBoundsUtil.setBounds(intervalChange, 65 * 1.255, 290 * 1.255);

        intervalInput = new TextField();
        intervalInput.setPromptText("更改間隔 s");
        SetBoundsUtil.setBounds(intervalInput, 165 * 1.255, 290 * 1.255, 120 * 1.255, 30 * 1.255);
        intervalInput.setEditable(false);

        intervalChange.setOnAction(e -> {
            if (intervalInput.getText().equals("")) {
                intervalInput.setEditable(true);
            } else {
                if (intervalInput.getText().matches("\\d+")) {
                    intervalInput.setEditable(false);
                    int newInterval = Integer.parseInt(intervalInput.getText());
                    currentInterval = newInterval;
                    intervalInput.setText("");
                    keyFrames[0] = new KeyFrame(Duration.seconds(newInterval), newEventHandler(currentSpeed));
                    locationModify.stop();
                    locationModify = new Timeline(keyFrames[0]);
                    locationModify.setCycleCount(Animation.INDEFINITE);
                    locationModify.play();
                } else {
                    JDialogUtil.showJDialog("輸入格式錯誤", "請重新輸入");
                }
            }
        });

        userInfo.getChildren().addAll(stop, speed, speedInput, intervalChange, intervalInput);

        borderPane.setRight(userInfo);

        // 查詢按鈕
        Button search = new Button("開始查詢");
        search.setLayoutX(16);
        search.setLayoutY(100 * 1.255);

        // 充電按鈕
        Button charge = new Button("開始充電");
        charge.setLayoutX(16);
        charge.setLayoutY(175 * 1.255);

        // 還車按鈕
        Button returnCar = new Button("開始還車");
        returnCar.setLayoutX(16);
        returnCar.setLayoutY(250 * 1.255);

        // 主介面
        BorderPane mainPane = new BorderPane();
        borderPane.setCenter(mainPane);

        AnchorPane buttonPane = new AnchorPane();
        mainPane.setLeft(buttonPane);

        buttonPane.getChildren().addAll(search, charge, returnCar);

        mainPane.setCenter(searchFrame);

        this.getChildren().add(borderPane);

        // 添加事件
        menuItem1.setOnAction(e -> {
            JDialogUtil.showJDialog("即將跳轉至登陸介面", "請稍等");
            stage.setScene(loginScene);
            stage.setWidth(750 * 1.255);
            stage.setHeight(450 * 1.255);
            LoginFrameJava8.newCode();
            stage.setTitle("登陸介面");
        });

        menuItem2.setOnAction(e -> {
            JDialogUtil.showJDialog("即將跳轉至註冊介面", "請稍等");
            stage.setScene(registerScene);
            stage.setWidth(750 * 1.255);
            stage.setHeight(450 * 1.255);
            stage.setTitle("註冊介面");
        });

        menuItem3.setOnAction(e -> { // 歷史紀錄

            Stage newStage = new Stage();
            UserHistoryFrame userHistoryFrame = null;
            try {
                userHistoryFrame = new UserHistoryFrame(LoginFrameJava8.manager.selectUserHistory(), newStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            newStage.setScene(new Scene(userHistoryFrame, 600 * 1.255, 400 * 1.255));
            newStage.setTitle("歷史紀錄");
            newStage.setAlwaysOnTop(true);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        });

        menuItem4.setOnAction(e -> { // 用戶信息
            Stage newStage = new Stage();
            try {
                newStage.setScene(new Scene(new UserInfoFrame(newStage), 750 * 1.255, 350 * 1.255));
                newStage.show();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        menuItem5.setOnAction(e -> { // 優惠券
            Stage newStage = new Stage();
            couponInfo couponInfo = null;
            try {
                couponInfo = new couponInfo(account, newStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            newStage.setScene(new Scene(couponInfo, 240 * 1.255, 420 * 1.255));
            newStage.setTitle("優惠券");
            newStage.setAlwaysOnTop(true);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        });

        menuItem6.setOnAction(e -> {
            JDialogUtil.showJDialog("感謝你使用本系統", "期待下次相遇");
            stage.close();
            System.exit(0);
        });

        menu.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6);

        search.setOnAction(e -> mainPane.setCenter(searchFrame));

        charge.setOnAction(e -> {
            mainPane.setCenter(chargeFrame);
            stop.setText("開始駕駛");
            setStart(false);
        });

        returnCar.setOnAction(e -> {
            mainPane.setCenter(returnScooterFrame);
            stop.setText("開始駕駛");
            setStart(false);
        });
    }

    public void setAccount(String account) {
        this.account = account;
        username.setText(account);
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public static int getCoupon() {
        return coupon;
    }

    /**
     * 資料刷新
     */
    public static void userInfoFlush() {
        licenseText.setText(userScooter.getLicensePlate());
        powerText.setText(userScooter.getPower() + " %");
        locationText.setText("經度: " + userScooter.getLng() + "\n" + "緯度: " + userScooter.getLat());
        mileAgeText.setText("已行駛: " + mile + "公里");
    }

    /**
     * 資料重置
     */
    public static void userInfoFlushBack() {
        mile = 0;
        licenseText.setText("尚未租車  無法確定");
        powerText.setText("尚未租車  無法確定");
        locationText.setText("尚未租車  無法確定");
        mileAgeText.setText("已行駛: " + mile + "公里");
        duringTime = "0秒";
        duringTimeInt = 0;
        DuringTimeText.setText(duringTime);
        userScooter.setLicensePlate(null);
        userScooter.setLat(0);
        userScooter.setLng(0);
        userScooter.setPower(0);
        returnScooterFrame.flush();
    }

    /**
     * 計算駕駛時間
     */
    private void timing() {
        if (duringTimeInt < 60) {
            duringTime = duringTimeInt + "秒";
        } else if (duringTimeInt < 3600 && duringTimeInt >= 60) {
            int min = duringTimeInt / 60;
            int sec = duringTimeInt % 60;
            duringTime = min + "分" + sec + "秒";
        } else {
            int hr = duringTimeInt / 3600;
            int min = (duringTimeInt % 3600) / 60;
            int sec = (duringTimeInt % 3600) % 60;
            duringTime = hr + "時" + min + "分" + sec + "秒";
        }
        DuringTimeText.setText("以租車: " + duringTime);
    }

    /**
     * 控制電量
     * @param distance
     */
    private void batteryDecrease(double distance) {
        double newPower = userScooter.getPower() - distance * 0.25;
        if (newPower >= 1.0) {
            userScooter.setPower(Math.round(newPower * 100.0) / 100.0);
        } else {
            setStart(false);
            JDialogUtil.showJDialog("電量不足", "已停止駕駛");
        }

    }

    // 控制駕駛
    public static void setStart(boolean bool) {
        if (bool) {
            if (userScooter.getPower() >= 1.0) {
                timePass.play();
                locationModify.play();
            } else {
                JDialogUtil.showJDialog("電量不足", "已停止駕駛");
            }
        } else {
            locationModify.stop();
        }
    }

    // 停止駕駛
    public static void stopTimePass() {
        timePass.stop();
        stop.setText("開始駕駛");
    }

    public static double getMile() {
        return mile;
    }

    public static void setMile(double distance) {
        mile = mile + distance;
        mile = Math.round(mile * 100) / 100.0;
    }

    public static String getAccount() {
        return account;
    }

    public static void setInitLoc(double initLat, double initLng) {
        initLoc = new double[]{initLat, initLng};
    }

    public static double[] getInitLoc() {
        return initLoc;
    }

    public static void setFinalLoc(double finalLat, double finalLng) {
        finalLoc = new double[]{finalLat, finalLng};
    }

    public static double[] getFinalLoc() {
        return finalLoc;
    }

    public static int getDuringTimeInt() {
        return duringTimeInt;
    }

    public static void setStopText() {
        stop.setText("停止駕駛");
    }

    /**
     * 調整速度
     * @param speed
     * @return
     */
    public EventHandler<ActionEvent> newEventHandler(double speed) {
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Double[] newLocation = new Double[0];
                try {
                    newLocation = Distance.generate(userScooter.getLat(), userScooter.getLng(), speed);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                userScooter.setLat(newLocation[0]);
                userScooter.setLng(newLocation[1]);
                mile += newLocation[2];
                mile = Math.round(mile * 100) / 100.0;
                batteryDecrease(newLocation[2]);
                userInfoFlush();
                returnScooterFrame.flush();
            }
        };
        return eventHandler;
    }
}
