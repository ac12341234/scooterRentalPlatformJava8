package UI_Java8;

import classes.Coupon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class couponInfo extends BorderPane {
    AnchorPane anchorPane = new AnchorPane();
    Stage newStage;

    couponInfo(String account, Stage newStage) throws Exception {
        this.newStage = newStage;
        newStage.setResizable(false);

        // 更改圖標
        File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
        Image bg = new Image(bgFile.toURI().toString());
        newStage.getIcons().add(bg);

        Label textAccount = new Label(account);
        textAccount.setLayoutX(35 * 1.255);
        textAccount.setLayoutY(10 * 1.255);
        textAccount.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 20px;-fx-font-weight: bolder");

        Label textCoupon = new Label("當前優惠券數量: " + LoginFrameJava8.manager.SelectCoupon(account));
        textCoupon.setLayoutX(35 * 1.255);
        textCoupon.setLayoutY(40 * 1.255);
        textCoupon.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 20px;-fx-font-weight: bolder");

        anchorPane.getChildren().addAll(textAccount, textCoupon);
        anchorPane.setPrefWidth(240 * 1.255);
        anchorPane.setPrefHeight(80 * 1.255);

        this.setTop(anchorPane);

        ObservableList<Coupon> list = FXCollections.observableArrayList();

        ArrayList<Coupon> coupons = LoginFrameJava8.manager.selectCouponHistory(account);

        for (Coupon cp : coupons) {
            list.add(cp);
        }

        int pageNum = (int) Math.ceil(list.size() / 10.0);
        pageNum = pageNum == 0 ? 1 : pageNum;
        Pagination pagination = new Pagination(pageNum, 0);
        pagination.setPageFactory(pageIndex -> createPage(pageIndex, list));

        this.setPrefWidth(240);
        this.setHeight(500);
        this.setCenter(pagination);
    }

    /**
     * 創建分頁
     * @param pageIndex
     * @param list
     * @return
     */
    private BorderPane createPage(int pageIndex, ObservableList<Coupon> list) {

        int fromIndex = pageIndex * 10;
        int toIndex = Math.min(fromIndex + 10, list.size());

        TableView<Coupon> pageTableView = new TableView<>(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

        TableColumn<Coupon, Integer> count = new TableColumn("獲得次數");
        count.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Coupon, String> getTime = new TableColumn<>("獲得時間");
        getTime.setCellValueFactory(new PropertyValueFactory<>("date"));

        BorderPane pane = new BorderPane();
        HBox hBox = new HBox();

        Button exit = new Button("退出介面");

        exit.setOnAction(e -> {
            newStage.close();
        });

        hBox.getChildren().add(exit);
        hBox.setAlignment(Pos.CENTER);

        pane.setBottom(hBox);

        pageTableView.getColumns().addAll(count, getTime);
        pane.setCenter(pageTableView);

        return pane;
    }
}
