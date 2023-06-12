package UI_Java8;

import classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
import java.time.LocalDateTime;

/**
 * 借車介面
 */
public class SearchFrame extends AnchorPane {

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
    private static LocalDateTime initTime;

    SearchFrame(AnchorPane userInfo) {
        this.userInfo = userInfo;
        this.setPrefWidth(250);
        this.setPrefHeight(450);

        // 介面提示
        Label label = new Label("租車介面");
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

        // 查詢到的車輛信息表
        ObservableList<Scooter> list = FXCollections.observableArrayList();

        // 按鈕
        searchByArea = new Button("依範圍查詢");

        searchByArea.setOnAction(ev -> {
            if (isValidInput(searchByArea)) { // 合法的輸入
                int i = 1;
                list.clear();

                ResultSet rs = null;
                try {
                    rs = LoginFrameJava8.manager.selectScooter();
                    while (rs.next()) {
                        String license = rs.getString("license");
                        double lat = rs.getDouble("lat");
                        double lng = rs.getDouble("lng");
                        int power = rs.getInt("power");
                        Scooter scooter = new Scooter(license, lat, lng, power, true);

                        if (scooter.getLat() <= latT && scooter.getLat() >= latB
                                && scooter.getLng() <= lngR && scooter.getLng() >= lngL) {
                            scooter.setID(i);
                            i++;
                            list.add(scooter);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                AnchorPane selectPane = new AnchorPane();
                selectPane.setPadding(new Insets(10, 10, 10, 10));
                Pagination pagination = new Pagination((int) Math.ceil(list.size() / 20.0), 0);
                pagination.setPageFactory(pageIndex -> createPage(pageIndex, list));
                pagination.setPrefWidth(440 * 1.255);
                pagination.setPrefHeight(600 * 1.255 - 50);
                selectPane.getChildren().add(pagination);

                newStage = new Stage();
                newStage.setScene(new Scene(selectPane, 440 * 1.255, 600 * 1.255 - 40));

                // 更改圖標
                File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
                Image bg = new Image(bgFile.toURI().toString());

                newStage.getIcons().add(bg);
                newStage.setResizable(false);

                newStage.setAlwaysOnTop(true);
                newStage.setTitle("可租車列表");
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.show();
            } else {
                JDialogUtil.showJDialog("輸入範圍有誤", "請重新輸入");
            }
        });

        searchByDst = new Button("依距離查詢");
        searchByDst.setOnAction(ev -> {
            if (isValidInput(searchByDst)) { // 合法的輸入
                int i = 1;
                list.clear();
                ResultSet rs = null;
                try {
                    rs = LoginFrameJava8.manager.selectScooter();
                    while (rs.next()) {
                        String license = rs.getString("license");
                        double lat = rs.getDouble("lat");
                        double lng = rs.getDouble("lng");
                        int power = rs.getInt("power");
                        Scooter scooter = new Scooter(license, lat, lng, power, true);

                        double dist = MapDistUtil.GetDistance(scooter.getLng(), scooter.getLat(), locLng, locLat);
                        if (dist < distance) {
                            scooter.setID(i);
                            i++;
                            list.add(scooter);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                AnchorPane selectPane = new AnchorPane();

                int pageNum = (int) Math.ceil(list.size() / 20.0);
                pageNum = pageNum == 0 ? 1 : pageNum;

                Pagination pagination = new Pagination(pageNum, 0);
                pagination.setPageFactory(pageIndex -> createPage(pageIndex, list));
                pagination.setPrefWidth(440 * 1.255);
                pagination.setPrefHeight(600 * 1.255 - 50);
                selectPane.getChildren().add(pagination);

                newStage = new Stage();
                newStage.setScene(new Scene(selectPane, 440 * 1.255, 600 * 1.255 - 40));

                // 更改圖標
                File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
                Image bg = new Image(bgFile.toURI().toString());

                newStage.getIcons().add(bg);
                newStage.setResizable(false);

                newStage.setAlwaysOnTop(true);
                newStage.setTitle("可租車列表");
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
     *
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
     *
     * @param pageIndex
     * @param list
     * @return
     */
    private BorderPane createPage(int pageIndex, ObservableList<Scooter> list) {

        int fromIndex = pageIndex * 20;
        int toIndex = Math.min(fromIndex + 20, list.size());

        TableView<Scooter> pageTableView = new TableView<>(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));


        TableColumn<Scooter, Integer> ID = new TableColumn<>("編號");
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Scooter, String> license = new TableColumn<>("車牌號碼");
        license.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));

        TableColumn<Scooter, Double> lat = new TableColumn<>("緯度");
        lat.setCellValueFactory(new PropertyValueFactory<>("lat"));

        TableColumn<Scooter, Double> lng = new TableColumn<>("經度");
        lng.setCellValueFactory(new PropertyValueFactory<>("lng"));

        TableColumn<Scooter, Integer> power = new TableColumn<>("電量");
        power.setCellValueFactory(new PropertyValueFactory<>("power"));

        BorderPane pane = new BorderPane();
        HBox hBox = new HBox();

        // 租車按鈕
        Button rent = new Button("確認租車");
        rent.setOnAction(e -> {
            if (SRPMainFrame.userScooter.getLicensePlate() == null) {
                Scooter selectedItem = pageTableView.getSelectionModel().getSelectedItem();
                try {
                    if (!LoginFrameJava8.manager.scooterUsing(selectedItem)) { // 二次檢測 避免重複借用
                        JDialogUtil.showJDialog("租車成功", "祝你愉快");
                        ClearTextFieldUtil.clearTextField(latInputB, latInputT, lngInputL, lngInputR);

                        SRPMainFrame.userScooter = selectedItem;
                        SRPMainFrame.setStopText();
                        SRPMainFrame.setInitLoc(SRPMainFrame.userScooter.getLat(), SRPMainFrame.userScooter.getLng());
                        initTime = LocalDateTime.now();

                        LoginFrameJava8.manager.setUsing(selectedItem);

                        SRPMainFrame.userInfoFlush();
                        SRPMainFrame.setStart(true);
                    } else {
                        JDialogUtil.showJDialog("該車已被租借", "請重新嘗試");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JDialogUtil.showJDialog("你已有使用中的車", "若有需要請還車後繼續");
                ClearTextFieldUtil.clearTextField(latInputB, latInputT, lngInputL, lngInputR);
            }
            newStage.close();
        });

        hBox.getChildren().add(rent);
        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);

        pageTableView.getColumns().addAll(ID, license, lat, lng, power);
        pane.setCenter(pageTableView);

        return pane;
    }

    public static LocalDateTime getInitTime() {
        return initTime;
    }
}
