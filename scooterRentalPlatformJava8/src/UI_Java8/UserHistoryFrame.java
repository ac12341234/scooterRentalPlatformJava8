package UI_Java8;

import classes.UserHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * 用戶歷史紀錄介面
 */
public class UserHistoryFrame extends BorderPane {
    Stage stage;

    UserHistoryFrame(ArrayList<UserHistory> originList, Stage newStage) {
        this.stage = newStage;
        File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
        Image bg = new Image(bgFile.toURI().toString());
        stage.getIcons().add(bg);

        ObservableList<UserHistory> list = FXCollections.observableList(originList);

        int i = 1;
        for (UserHistory userHistory : list) {
            userHistory.setID(i);
            userHistory.setCouponStr(userHistory.isCouponUse());
            i++;
        }
        int pageNum = (int) Math.ceil(list.size() / 10.0);
        pageNum = pageNum == 0 ? 1 : pageNum;
        Pagination pagination = new Pagination(pageNum, 0);
        pagination.setPageFactory(pageIndex -> createPage(pageIndex, list));

        this.setPrefWidth(600 * 1.255);
        this.setHeight(400 * 1.255);
        this.setCenter(pagination);
    }

    private BorderPane createPage(int pageIndex, ObservableList<UserHistory> list) {

        int fromIndex = pageIndex * 10;
        int toIndex = Math.min(fromIndex + 10, list.size());

        TableView<UserHistory> pageTableView = new TableView<>(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

        TableColumn<UserHistory, Integer> ID = new TableColumn<>("編號");
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<UserHistory, Date> date = new TableColumn<>("租車日期");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<UserHistory, Integer> money = new TableColumn<>("金額 (元)");
        money.setCellValueFactory(new PropertyValueFactory<>("money"));

        TableColumn<UserHistory, String> initLoc = new TableColumn<>("初始位置");
        initLoc.setCellValueFactory(new PropertyValueFactory<>("initLoc"));

        TableColumn<UserHistory, String> finalLoc = new TableColumn<>("終點位置");
        finalLoc.setCellValueFactory(new PropertyValueFactory<>("finalLoc"));

        TableColumn<UserHistory, Double> distance = new TableColumn<>("距離 (Km)");
        distance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        TableColumn<UserHistory, LocalDateTime> initTime = new TableColumn<>("借車時間");
        initTime.setCellValueFactory(new PropertyValueFactory<>("initTime"));

        TableColumn<UserHistory, LocalDateTime> finalTime = new TableColumn<>("還車時間");
        finalTime.setCellValueFactory(new PropertyValueFactory<>("finalTime"));

        TableColumn<UserHistory, Integer> duringTime = new TableColumn<>("總時間 (秒)");
        duringTime.setCellValueFactory(new PropertyValueFactory<>("duringTime"));

        TableColumn<UserHistory, Integer> chargeCount = new TableColumn<>("充電次數");
        chargeCount.setCellValueFactory(new PropertyValueFactory<>("chargeCount"));

        TableColumn<UserHistory, String> couponUse = new TableColumn<>("優惠券");
        couponUse.setCellValueFactory(new PropertyValueFactory<>("couponStr"));

        BorderPane pane = new BorderPane();

        HBox hBox = new HBox();

        Button exit = new Button("退出介面");
        exit.setOnAction(e -> stage.close());

        hBox.getChildren().add(exit);
        hBox.setAlignment(Pos.CENTER);

        pane.setBottom(hBox);

        pageTableView.getColumns().addAll(ID, date, money, initLoc, finalLoc, distance, initTime, finalTime, duringTime, chargeCount, couponUse);

        for (TableColumn column : pageTableView.getColumns()) {
            column.setCellFactory(p -> new CenteredTableCell<>());
        }

        pane.setCenter(pageTableView);
        return pane;
    }

    public class CenteredTableCell<S, T> extends TableCell<S, T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                setText(item.toString());
                setStyle("-fx-alignment: CENTER;");
            }
        }
    }
}
