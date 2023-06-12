package UI_Java8;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.sql.ResultSet;

/**
 * 充電介面
 */
public class ChargeFrame extends AnchorPane {
    private double latT;
    private double latB;
    private double lngL;
    private double lngR;
    private double locLat;
    private double locLng;
    private double distance;
    private TextField latInputB;
    private TextField latInputT;
    private TextField lngInputL;
    private TextField lngInputR;
    private TextField targetLngInput;
    private TextField targetLatInput;
    private TextField distanceInput;
    private Button searchByArea;
    private Button searchByDst;
    private Stage newStage;
    private AnchorPane userInfo;
    public static boolean couponNew = false;
    private static int powerCount = 0;

    ChargeFrame(AnchorPane userInfo) {
        this.userInfo = userInfo;
        this.setPrefWidth(250 * 1.255);
        this.setPrefHeight(450 * 1.255);

        // 介面提示
        Label label = new Label("充電介面");
        SetBoundsUtil.setBounds(label, 100 * 1.255, 15 * 1.255, 80 * 1.255, 30 * 1.255);
        label.setStyle("-fx-font-size: 23px");
        this.getChildren().add(label);

        // 輸入框 經緯度
        latInputB = new TextField();
        latInputB.setPromptText("緯度下界");
        SetBoundsUtil.setBounds(latInputB, 25 * 1.255, 50 * 1.255, 100 * 1.255, 30 * 1.255);

        latInputT = new TextField();
        latInputT.setPromptText("緯度上界");
        SetBoundsUtil.setBounds(latInputT, 150 * 1.255, 50 * 1.255, 100 * 1.255, 30 * 1.255);

        lngInputL = new TextField();
        lngInputL.setPromptText("經度左界");
        SetBoundsUtil.setBounds(lngInputL, 25 * 1.255, 100 * 1.255, 100 * 1.255, 30 * 1.255);

        lngInputR = new TextField();
        lngInputR.setPromptText("經度右界");
        SetBoundsUtil.setBounds(lngInputR, 150 * 1.255, 100 * 1.255, 100 * 1.255, 30 * 1.255);

        Label inputHint1 = new Label("依範圍查詢");
        SetBoundsUtil.setBounds(inputHint1, 10 * 1.255, 10 * 1.255, 70 * 1.255, 30 * 1.255);

        targetLngInput = new TextField();
        targetLngInput.setPromptText("請輸入目標經度");

        targetLatInput = new TextField();
        targetLatInput.setPromptText("請輸入目標緯度");

        distanceInput = new TextField();
        distanceInput.setPromptText("請輸入距離  KM");

        SetBoundsUtil.setBounds(targetLatInput, 25 * 1.255, 175 * 1.255, 100 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(targetLngInput, 150 * 1.255, 175 * 1.255, 100 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(distanceInput, 87 * 1.255, 225 * 1.255, 100 * 1.255, 30 * 1.255);

        Label inputHint2 = new Label("依距離查詢");
        SetBoundsUtil.setBounds(inputHint2, 10 * 1.255, 135 * 1.255, 70 * 1.255, 30 * 1.255);

        this.getChildren().addAll(latInputB, latInputT, lngInputL, lngInputR, inputHint1, inputHint2, targetLatInput, targetLngInput, distanceInput);

        // 查詢到的充電站信息表
        ObservableList<Battery> list = FXCollections.observableArrayList();

        // 按鈕
        searchByArea = new Button("依範圍查詢");
        searchByArea.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (isValidInput(searchByArea)) { // 合法的輸入
                    int i = 1;
                    list.clear();

                    ResultSet rs = null;
                    try {
                        rs = LoginFrameJava8.manager.selectBattery();
                        while (rs.next()) {
                            double lat = rs.getDouble("lat");
                            double lng = rs.getDouble("lng");
                            Battery battery = new Battery(i, lat, lng);

                            double dist = MapDistUtil.GetDistance(battery.getLng(), battery.getLat(), SRPMainFrame.userScooter.getLng(), SRPMainFrame.userScooter.getLat());
                            dist = Math.round(dist * 100) / 100.0;
                            if (battery.getLat() <= latT && battery.getLat() >= latB
                                    && battery.getLng() <= lngR && battery.getLng() >= lngL) {
                                if (SRPMainFrame.userScooter.getLicensePlate() == null)
                                    battery.setDistance("無法判斷當前位置");
                                else battery.setDistance(dist);
                                i++;
                                list.add(battery);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    AnchorPane selectPane = new AnchorPane();
                    Pagination pagination = new Pagination((int) Math.ceil(list.size() / 20.0), 0);
                    pagination.setPageFactory(pageIndex -> createPage(pageIndex, list));
                    pagination.setPrefWidth(300 * 1.255);
                    pagination.setPrefHeight(600 * 1.255 - 50);
                    selectPane.getChildren().add(pagination);

                    newStage = new Stage();
                    newStage.setScene(new Scene(selectPane, 300 * 1.255, 600 * 1.255));

                    // 更改圖標
                    File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
                    Image bg = new Image(bgFile.toURI().toString());
                    newStage.getIcons().add(bg);
                    newStage.setResizable(false);

                    newStage.setAlwaysOnTop(true);
                    newStage.setTitle("充電站列表");
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    newStage.show();
                } else {
                    JDialogUtil.showJDialog("輸入範圍有誤", "請重新輸入");
                }

            }
        });

        searchByDst = new Button("依距離查詢");
        searchByDst.setOnAction(e -> {
            if (isValidInput(searchByDst)) { // 合法的輸入
                int i = 1;
                list.clear();

                ResultSet rs = null;
                try {
                    rs = LoginFrameJava8.manager.selectBattery();
                    while (rs.next()) {
                        double lat = rs.getDouble("lat");
                        double lng = rs.getDouble("lng");
                        Battery battery = new Battery(i, lat, lng);

                        double dist = MapDistUtil.GetDistance(battery.getLng(), battery.getLat(), SRPMainFrame.userScooter.getLng(), SRPMainFrame.userScooter.getLat());
                        dist = Math.round(dist * 100) / 100.0;
                        if (dist < distance) {
                            if (SRPMainFrame.userScooter.getLicensePlate() == null)
                                battery.setDistance("無法判斷當前位置");
                            else battery.setDistance(dist);
                            i++;
                            list.add(battery);
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                AnchorPane selectPane = new AnchorPane();
                int pageNum = (int) Math.ceil(list.size() / 20.0);
                pageNum = pageNum == 0 ? 1 : pageNum;

                Pagination pagination = new Pagination(pageNum, 0);
                pagination.setPageFactory(pageIndex -> createPage(pageIndex, list));
                pagination.setPrefWidth(300 * 1.255);
                pagination.setPrefHeight(600 * 1.255 - 50);
                selectPane.getChildren().add(pagination);

                newStage = new Stage();
                newStage.setScene(new Scene(selectPane, 300 * 1.255, 600 * 1.255));

                // 更改圖標
                File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
                Image bg = new Image(bgFile.toURI().toString());
                newStage.getIcons().add(bg);
                newStage.setResizable(false);

                newStage.setAlwaysOnTop(true);
                newStage.setTitle("充電站列表");
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.show();
            } else {
                JDialogUtil.showJDialog("輸入範圍有誤", "請重新輸入");
            }
        });

        searchByArea.setLayoutX(50 * 1.255);
        searchByArea.setLayoutY(280 * 1.255);

        searchByDst.setLayoutX(150 * 1.255);
        searchByDst.setLayoutY(280 * 1.255);
        this.getChildren().addAll(searchByArea, searchByDst);

    }

    /**
     * 判斷輸入是否合法
     * @param button
     * @return
     */
    private boolean isValidInput(Button button) {
        String regex = "[0-9]+\\.[0-9]+";
        double rB = 25.026708, rT = 25.068277, rL = 121.511162, rR = 121.567045;
        if (button == searchByArea) {
            if (latInputB.getText().matches(regex) && latInputT.getText().matches(regex)
                    && lngInputL.getText().matches(regex) && lngInputR.getText().matches(regex)) {
                latB = Double.parseDouble(latInputB.getText());
                latT = Double.parseDouble(latInputT.getText());
                lngL = Double.parseDouble(lngInputL.getText());
                lngR = Double.parseDouble(lngInputR.getText());
                if (latB < rB || latT > rT || lngL < rL || lngR > rR) return false;
                return true;
            }
            return false;
        } else if (button == searchByDst) {
            String regexDst = "[0-9]+(\\.[0-9]+)?";
            if (targetLatInput.getText().matches(regex) && targetLngInput.getText().matches(regex)) {
                locLat = Double.parseDouble(targetLatInput.getText());
                locLng = Double.parseDouble(targetLngInput.getText());
                if (locLat < rB || locLat > rT || locLng < rL || locLng > rR) return false;
                if (distanceInput.getText().matches(regexDst)) {
                    distance = Double.parseDouble(distanceInput.getText());
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 創建分頁
     * @param pageIndex
     * @param list
     * @return
     */
    private BorderPane createPage(int pageIndex, ObservableList<Battery> list) {
        BorderPane pane = new BorderPane();

        int fromIndex = pageIndex * 20;
        int toIndex = Math.min(fromIndex + 20, list.size());

        if (fromIndex > toIndex) {
            int temp = fromIndex;
            fromIndex = toIndex;
            toIndex = temp;
        }

        TableView<Battery> pageTableView = new TableView<>(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

        TableColumn<Battery, Integer> ID = new TableColumn<>("編號");
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Battery, Double> lat = new TableColumn<>("緯度");
        lat.setCellValueFactory(new PropertyValueFactory<>("lat"));

        TableColumn<Battery, Double> lng = new TableColumn<>("經度");
        lng.setCellValueFactory(new PropertyValueFactory<>("lng"));

        TableColumn<Battery, String> dis = new TableColumn<>("距離");
        dis.setCellValueFactory(new PropertyValueFactory<>("distance"));


        HBox hBox = new HBox();

        Button charge = new Button("確認前往充電站");
        charge.setOnAction(e -> {
            if (SRPMainFrame.userScooter.getLicensePlate() != null) {
                JDialogUtil.showJDialog("充電成功", "贈與優惠券可下次使用");
                powerCount++;
                ClearTextFieldUtil.clearTextField(latInputB, latInputT, lngInputL, lngInputR);
                Battery selectedItem = pageTableView.getSelectionModel().getSelectedItem();

                if (canCharge(selectedItem) >= 0) {
                    double temp = canCharge(selectedItem);
                    SRPMainFrame.userScooter.setLat(selectedItem.getLat());
                    SRPMainFrame.userScooter.setLng(selectedItem.getLng());
                    SRPMainFrame.userScooter.setPower(100);
                    SRPMainFrame.setMile(temp);
                    couponNew = true;
                }
                SRPMainFrame.userInfoFlush();
                ReturnScooterFrame.flush();
                SRPMainFrame.setStart(false);
            } else {
                JDialogUtil.showJDialog("電量不足", "無法前往充電站");
                ClearTextFieldUtil.clearTextField(latInputB, latInputT, lngInputL, lngInputR);
            }
            newStage.close();
        });

        hBox.getChildren().add(charge);
        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);

        pageTableView.getColumns().addAll(ID, lat, lng, dis);
        pane.setCenter(pageTableView);

        return pane;
    }

    /**
     * 判斷當前電量是否能夠前往充電站
     * @param battery
     * @return
     */
    private double canCharge(Battery battery) {
        double distance = MapDistUtil.GetDistance(SRPMainFrame.userScooter.getLng(), SRPMainFrame.userScooter.getLat(), battery.getLng(), battery.getLat());
        distance = Math.round(distance * 100) / 100.0;
        if (distance > SRPMainFrame.userScooter.getPower() * 4) return -1;
        return distance;
    }

    public static int getPowerCount() {
        return powerCount;
    }
}
