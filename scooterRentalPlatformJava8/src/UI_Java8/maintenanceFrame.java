package UI_Java8;

import classes.JDialogUtil;
import classes.Scooter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

public class maintenanceFrame extends BorderPane {
    private Stage stage;
    Button repairButton;

    maintenanceFrame(ArrayList<Scooter> originList, Stage newStage) {

        this.stage = newStage;
        File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
        Image bg = new Image(bgFile.toURI().toString());
        stage.getIcons().add(bg);

        ObservableList<Scooter> list = FXCollections.observableList(originList);

        int i = 1;
        for (Scooter scooter : list) {
            scooter.setID(i);
            scooter.setState(scooter.isCondition());
            i++;
        }
        int pageNum = (int) Math.ceil(list.size() / 20.0);
        pageNum = pageNum == 0 ? 1 : pageNum;
        Pagination pagination = new Pagination(pageNum, 0);
        pagination.setPageFactory(pageIndex -> createPage(pageIndex, list));

        this.setPrefWidth(400 * 1.255);
        this.setHeight(600 * 1.255);
        this.setCenter(pagination);
    }


    private BorderPane createPage(int pageIndex, ObservableList<Scooter> list) {
        int fromIndex = pageIndex * 20;
        int toIndex = Math.min(fromIndex + 20, list.size());

        TableView<Scooter> pageTableView = new TableView<>(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

        TableColumn<Scooter, Integer> ID = new TableColumn<>("編號");
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Scooter, String> license = new TableColumn<>("車牌號碼");
        license.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));

        TableColumn<Scooter, Double> lng = new TableColumn<>("經度");
        lng.setCellValueFactory(new PropertyValueFactory<>("lng"));

        TableColumn<Scooter, Double> lat = new TableColumn<>("緯度");
        lat.setCellValueFactory(new PropertyValueFactory<>("lat"));

        TableColumn<Scooter, Integer> power = new TableColumn<>("電量");
        power.setCellValueFactory(new PropertyValueFactory<>("power"));

        TableColumn<Scooter, String> state = new TableColumn<>("  狀態  ");
        state.setCellValueFactory(new PropertyValueFactory<>("state"));

        BorderPane pane = new BorderPane();

        /*
         * 設定按鈕間距
         */
        VBox vBox = new VBox();

        HBox hBox1 = new HBox(10);
        hBox1.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox(10);
        hBox2.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(hBox2, hBox1);

        pageTableView.getColumns().addAll(ID, license, lng, lat, power, state);

        for (TableColumn column : pageTableView.getColumns()) {
            column.setCellFactory(p -> new CenteredTableCell<>());
        }

        Button exitButton = new Button("退出介面");
        exitButton.setOnAction(e -> stage.close());

        hBox1.getChildren().add(exitButton);
        pane.setBottom(vBox);

        repairButton = new Button("維修");
        repairButton.setOnAction(e -> {
            Scooter scooter = pageTableView.getSelectionModel().getSelectedItem();
            if (scooter != null && !scooter.isCondition()) {
                repairEvent(scooter, pageTableView);
                JDialogUtil.showJDialog("維修中", "");
            } else {
                JDialogUtil.showJDialog("車輛狀態為正常", "無須維修");
            }

        });

        /*
         * 用generateRandomNumber隨機生成經緯度
         */
        Button chargeButton = new Button("充電");
        chargeButton.setOnAction(e -> {
            Scooter scooter = pageTableView.getSelectionModel().getSelectedItem();
            if (scooter != null && scooter.getPower() <= 20) {
                scooter.setPower(100);
                scooter.setLng(generateRandomNumber(121.511162, 121.567045));
                scooter.setLat(generateRandomNumber(25.026708, 25.068277));
                try {
                    LoginFrameJava8.manager.saveScooterEvent(scooter);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                JDialogUtil.showJDialog("充電完畢", "");
                pageTableView.refresh();
            } else {
                JDialogUtil.showJDialog("電量大於20%", "無須充電");
            }
        });

        Button repairAllButton = new Button("全體維修");
        repairAllButton.setOnAction(e -> {
            for (Scooter scooter : list) {
                if (!scooter.isCondition()) {
                    repairEvent(scooter, pageTableView);
                }
            }
            JDialogUtil.showJDialog("維修中", "");
        });

        Button chargeAllButton = new Button("全體充電");
        chargeAllButton.setOnAction(e -> {
            for (Scooter scooter : list) {
                if (scooter.getPower() <= 20) {
                    scooter.setPower(100);
                    scooter.setLng(generateRandomNumber(121.511162, 121.567045));
                    scooter.setLat(generateRandomNumber(25.026708, 25.068277));
                    try {
                        LoginFrameJava8.manager.saveScooterEvent(scooter);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            JDialogUtil.showJDialog("全體充電完畢", "");
            pageTableView.refresh();
        });

        for (TableColumn column : pageTableView.getColumns()) {
            column.setCellFactory(p -> new CenteredTableCell<>());
        }

        hBox2.getChildren().addAll(repairButton, repairAllButton, chargeButton, chargeAllButton);

        pane.setCenter(pageTableView);

        return pane;

    }


    public class CenteredTableCell<S, T> extends TableCell<S, T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
                // setGraphic(null);
                setStyle("");
            } else {
                setText(item.toString());
                setStyle("-fx-alignment: CENTER;");
                // setGraphic(repairButton);
            }
        }
    }

    /*
     * 生成亂數
     */
    public static double generateRandomNumber(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public void repairEvent(Scooter scooter, TableView pageTableView) {
        scooter.setCondition(true);
        scooter.setState("維修中");
        pageTableView.refresh();
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scooter.setState(scooter.isCondition());
                pageTableView.refresh();
            }
        };
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(5), eventHandler);
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();

        try {
            LoginFrameJava8.manager.saveScooterEvent(scooter);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
