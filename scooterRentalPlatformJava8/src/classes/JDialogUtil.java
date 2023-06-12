package classes;

import javax.swing.*;
import java.awt.*;

public class JDialogUtil {
    public static void showJDialog(String text1, String text2) {

        Font font = new Font("微軟正黑體", Font.BOLD, 14);

        JLabel jLabel1 = new JLabel(text1);
        JLabel jLabel2 = new JLabel(text2);

        jLabel1.setFont(font);
        jLabel2.setFont(font);

        JDialog jDialog = new JDialog();
        jDialog.getContentPane().add(jLabel1);
        jDialog.getContentPane().add(jLabel2);

        jDialog.setTitle("系統提示");

        jLabel1.setBounds(0, 20, 180, 30);
        jLabel2.setSize(150, 30);

        jLabel1.setVerticalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel2.setVerticalAlignment(SwingConstants.CENTER);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        jDialog.setSize(200, 150);
        jDialog.setAlwaysOnTop(true);
        jDialog.setModal(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
    }
}
