package classes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SetBoundsUtil {

    public static void setBounds(ImageView imageView, double x, double y, double width, double height) {
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public static void setBounds(TextField textField, double x, double y, double width, double height) {
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefWidth(width);
        textField.setPrefHeight(height);
    }

    public static void setBounds(Label label, double x, double y, double width, double height) {
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
    }

    public static void setBounds(Button button, double x, double y, double width, double height) {
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
    }

    public static void setBounds(Button button, double x, double y) {
        button.setLayoutX(x);
        button.setLayoutY(y);
    }
}
