package UI_Java8;

import classes.JDialogUtil;
import classes.SetBoundsUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDateTime;

public class ReturnScooterBTE extends AnchorPane {

    ReturnScooterBTE(int cost, String account) {
        Label label = new Label("當前優惠券: " + SRPMainFrame.getCoupon() + "張");
        SetBoundsUtil.setBounds(label, 30 * 1.255, 20 * 1.255, 200 * 1.255, 30 * 1.255);

        Label totalCost = new Label("租車費用: " + cost + "元");
        SetBoundsUtil.setBounds(totalCost, 30 * 1.255, 80 * 1.255, 200 * 1.255, 30 * 1.255);

        Button cashPay = new Button("現金支付");
        Button cardPay = new Button("信用卡支付");
        Button couponPay = new Button("優惠券支付");

        SetBoundsUtil.setBounds(cashPay, 20 * 1.255, 120 * 1.255);
        SetBoundsUtil.setBounds(cardPay, 100 * 1.255, 120 * 1.255);
        SetBoundsUtil.setBounds(couponPay, 190 * 1.255, 120 * 1.255);

        // event
        cashPay.setOnAction(e -> {
            JDialogUtil.showJDialog("現金付款成功", "還車成功");
            ReturnScooterFrame.newStage.close();
            ReturnScooterFrame.setFinalTime(LocalDateTime.now());
            SRPMainFrame.stopTimePass();
            try {
                LoginFrameJava8.manager.setCoupon(account, LoginFrameJava8.manager.SelectCoupon(account) + 1, ChargeFrame.couponNew);
                LoginFrameJava8.manager.insertCouponInfo(account, ChargeFrame.couponNew);
                LoginFrameJava8.manager.insertUserHistory(cost, SRPMainFrame.getInitLoc(), SRPMainFrame.getFinalLoc(), false);
                LoginFrameJava8.manager.saveScooterEvent(SRPMainFrame.userScooter);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            SRPMainFrame.userInfoFlushBack();
        });

        cardPay.setOnAction(e -> {
            JDialogUtil.showJDialog("信用卡付款成功", "還車成功");
            ReturnScooterFrame.newStage.close();
            ReturnScooterFrame.setFinalTime(LocalDateTime.now());
            SRPMainFrame.stopTimePass();
            try {
                LoginFrameJava8.manager.setCoupon(account, LoginFrameJava8.manager.SelectCoupon(account) + 1, ChargeFrame.couponNew);
                LoginFrameJava8.manager.insertCouponInfo(account, ChargeFrame.couponNew);
                LoginFrameJava8.manager.insertUserHistory(cost, SRPMainFrame.getInitLoc(), SRPMainFrame.getFinalLoc(), false);
                LoginFrameJava8.manager.saveScooterEvent(SRPMainFrame.userScooter);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            SRPMainFrame.userInfoFlushBack();
        });

        couponPay.setOnAction(e -> {
            if (SRPMainFrame.getCoupon() > 0) {
                JDialogUtil.showJDialog("優惠券付款成功", "還車成功");
                SRPMainFrame.stopTimePass();
                try {
                    LoginFrameJava8.manager.setCoupon(account, LoginFrameJava8.manager.SelectCoupon(account), ChargeFrame.couponNew);
                    LoginFrameJava8.manager.insertCouponInfo(account, ChargeFrame.couponNew);
                    LoginFrameJava8.manager.insertUserHistory(cost, SRPMainFrame.getInitLoc(), SRPMainFrame.getFinalLoc(), true);
                    LoginFrameJava8.manager.saveScooterEvent(SRPMainFrame.userScooter);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                SRPMainFrame.userInfoFlushBack();
            } else {
                JDialogUtil.showJDialog("優惠券數量不足", "請更換付款方式");
            }
            ReturnScooterFrame.newStage.close();
        });

        this.getChildren().addAll(label, totalCost, cashPay, cardPay, couponPay);
    }
}
