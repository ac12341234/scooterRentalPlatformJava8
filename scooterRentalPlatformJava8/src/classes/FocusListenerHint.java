package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FocusListenerHint implements FocusListener {

    private String hint;
    private JTextField textField;
    private JPasswordField passwordField;
    private Font font = new Font("微軟正黑體", Font.BOLD, 14);

    public FocusListenerHint(JTextField textField, String hint) {
        this.textField = textField;
        this.hint = hint;
        textField.setText(hint);
        textField.setFont(font);
        textField.setForeground(Color.gray);
    }

    public FocusListenerHint(JPasswordField passwordField, String hint) {
        this.passwordField = passwordField;
        this.hint = hint;
        passwordField.setText(hint);
        passwordField.setFont(font);
        passwordField.setForeground(Color.gray);
        passwordField.setEchoChar((char) 0);
    }

    @Override
    public void focusGained(FocusEvent e) {
        // 獲得焦點
        if (textField != null) {
            String text = textField.getText();
            if (text.equals(hint)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        } else {
            String text = passwordField.getText();
            if (text.equals(hint)) {
                passwordField.setText("");
                passwordField.setForeground(Color.BLACK);
                passwordField.setEchoChar('。');
            }
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        // 失去焦點
        if (textField != null) {
            String text = textField.getText();
            if (text.equals("")) {
                textField.setText(hint);
                textField.setForeground(Color.gray);
            }
        } else {
            String text = passwordField.getText();
            if (text.equals("")) {
                passwordField.setText(hint);
                passwordField.setForeground(Color.gray);
                passwordField.setEchoChar((char) 0);
            }
        }

    }
}
