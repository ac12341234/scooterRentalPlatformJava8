package classes;

import javafx.scene.control.TextField;

public class ClearTextFieldUtil {
    public static void clearTextField(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText("");
        }
    }
}
