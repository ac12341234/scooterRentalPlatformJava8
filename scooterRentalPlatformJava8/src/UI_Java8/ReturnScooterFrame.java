package UI_Java8;

import classes.JDialogUtil;
import classes.SetBoundsUtil;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;

public class ReturnScooterFrame extends AnchorPane {

    private static Label licenseText;
    private static Label powerText;
    private static Label locationText;
    private static Label mileAgeText;
    private static Label costText;
    public static Stage newStage;
    private static LocalDateTime finalTime;

    ReturnScooterFrame() {
        Label label = new Label("還車界面");
        SetBoundsUtil.setBounds(label, 100 * 1.255, 15 * 1.255, 80 * 1.255, 30 * 1.255);
        label.setStyle("-fx-font-size: 23px");

        this.setPrefWidth(250 * 1.255);
        this.setPrefHeight(450 * 1.255);
        this.setWidth(200 * 1.255);
        this.setHeight(450 * 1.255);
        // 里程數 費用 顯示目前位置

        // 車種
        File licenseFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\license.png");

        ImageView license = new ImageView(licenseFile.toURI().toString());
        licenseText = new Label("尚未租車  無法確定");

        SetBoundsUtil.setBounds(license, 30 * 1.255, 70 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(licenseText, 70 * 1.255, 70 * 1.255, 160 * 1.255, 35 * 1.255);

        // 電量
        File powerFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\battery.png");

        ImageView power = new ImageView(powerFile.toURI().toString());
        powerText = new Label("尚未租車  無法確定");

        SetBoundsUtil.setBounds(power, 30 * 1.255, 115 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(powerText, 70 * 1.255, 115 * 1.255, 160 * 1.255, 35 * 1.255);

        // 位置
        File locationFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\location.png");

        ImageView location = new ImageView(locationFile.toURI().toString());
        locationText = new Label("尚未租車  無法確定");

        SetBoundsUtil.setBounds(location, 30 * 1.255, 160 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(locationText, 70 * 1.255, 140 * 1.255, 160 * 1.255, 70 * 1.255);

        // 里程數
        File mileAgeFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\mileage.png");

        ImageView mileAge = new ImageView(mileAgeFile.toURI().toString());
        mileAgeText = new Label("已行駛: " + SRPMainFrame.getMile() + "公里");

        SetBoundsUtil.setBounds(mileAge, 30 * 1.255, 205 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(mileAgeText, 70 * 1.255, 205 * 1.255, 160 * 1.255, 35 * 1.255);

        // 費用
        File costFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\cost.png");

        ImageView cost = new ImageView(costFile.toURI().toString());
        costText = new Label("租車費用: " + 0 + "元");

        SetBoundsUtil.setBounds(cost, 30 * 1.255, 250 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(costText, 70 * 1.255, 250 * 1.255, 160 * 1.255, 35 * 1.255);

        // 還車按鈕
        Button returnScooter = new Button("確認還車");
        returnScooter.setOnAction(e -> {
            if (SRPMainFrame.userScooter.getLicensePlate() != null) {
                SRPMainFrame.setFinalLoc(SRPMainFrame.userScooter.getLat(), SRPMainFrame.userScooter.getLng());
                newStage = new Stage();
                newStage.setTitle("付款介面");
                newStage.setScene(new Scene(new ReturnScooterBTE(getCost(), SRPMainFrame.getAccount()), 300 * 1.255, 200 * 1.255));
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setAlwaysOnTop(true);
                newStage.show();
            } else {
                JDialogUtil.showJDialog("尚未租車", "請租車後再操作");
            }
        });

        SetBoundsUtil.setBounds(returnScooter, 90 * 1.255, 300 * 1.255);

        this.getChildren().addAll(label, licenseText, license, powerText, power, locationText, location, mileAgeText, mileAge, costText, cost, returnScooter);
    }

    /**
     * 表格刷新
     */
    public static void flush() {
        if (SRPMainFrame.userScooter.getLicensePlate() != null) {
            licenseText.setText(SRPMainFrame.userScooter.getLicensePlate());
            locationText.setText("經度: " + SRPMainFrame.userScooter.getLng() + "\n" + "緯度: " + SRPMainFrame.userScooter.getLat());
            powerText.setText(SRPMainFrame.userScooter.getPower() + " %");
            mileAgeText.setText("已行駛: " + SRPMainFrame.getMile() + "公里");
            costText.setText("租車費用: " + getCost() + "元");
        } else {
            licenseText.setText("尚未租車  無法確定");
            locationText.setText("尚未租車  無法確定");
            powerText.setText("尚未租車  無法確定");
            mileAgeText.setText("已行駛: " + SRPMainFrame.getMile() + "公里");
            costText.setText("租車費用: " + getCost() + "元");
        }
    }

    private static int getCost() {
        return (int) Math.round(SRPMainFrame.getMile() * 10);
    }

    public static void setFinalTime(LocalDateTime finTime) {
        finalTime = finTime;
    }

    public static LocalDateTime getFinalTime() {
        return finalTime;
    }

}
