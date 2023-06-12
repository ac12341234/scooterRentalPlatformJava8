package UI_Java8;

import classes.JDialogUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class maintenanceMainFrame extends Pane {

    // 菜單
    MenuBar menuBar = new MenuBar();
    Menu menu = new Menu("基本功能");

    MenuItem menuItem1 = new MenuItem("重新登錄");
    MenuItem menuItem2 = new MenuItem("維修系統");
    MenuItem menuItem3 = new MenuItem("退出系統");

    public maintenanceMainFrame(Stage stage, Scene loginScene) {

        stage.setOnCloseRequest(ev -> {
            ev.consume();
            JDialogUtil.showJDialog("感謝你使用本系統", "期待下次相遇");
            stage.close();
            System.exit(0);
        });

        menu.getItems().addAll(menuItem1, menuItem2, menuItem3);
        menuBar.getMenus().add(menu);

        VBox vBox = new VBox();
        vBox.getChildren().add(menuBar);

        File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
        Image bg = new Image(bgFile.toURI().toString());

        ImageView imageView = new ImageView(bg);
        imageView.setFitWidth(800 * 1.255);
        imageView.setFitHeight(400 * 1.255);

        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().add(imageView);
        borderPane.setLeft(vBox);

        // add event
        {
            menuItem1.setOnAction(e -> {
                JDialogUtil.showJDialog("即將跳轉至登陸介面", "請稍等");
                stage.setScene(loginScene);
                stage.setWidth(750 * 1.255);
                stage.setHeight(450 * 1.255);
                LoginFrameJava8.newCode();
                stage.setTitle("登陸介面");
            });

            menuItem2.setOnAction(e -> {
                Stage newStage = new Stage();
                maintenanceFrame mFrame = null;
                try {
                    mFrame = new maintenanceFrame(LoginFrameJava8.manager.selectAllScooter(), newStage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                newStage.setScene(new Scene(mFrame, 500 * 1.255, 600 * 1.255));
                newStage.setTitle("維修平台");
                newStage.setAlwaysOnTop(true);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.show();
            });

            menuItem3.setOnAction(e -> {
                JDialogUtil.showJDialog("感謝你使用本系統", "期待下次相遇");
                stage.close();
                System.exit(0);
            });
        }

        this.getChildren().add(borderPane);
    }
}
