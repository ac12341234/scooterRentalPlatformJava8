package UI_Java8;

import classes.ClearTextFieldUtil;
import classes.JDialogUtil;
import classes.ScooterUser;
import classes.SetBoundsUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

/**
 * 用戶資訊界面
 */
public class UserInfoFrame extends AnchorPane {
    private TextField accountInput;
    private PasswordField passwordInput;
    private TextField emailInput;
    private TextField creditCardInput;
    private TextField nameInput;
    private TextField phoneInput;
    private ImageView editButton;
    private ImageView confirmButton;
    private ImageView bar;
    private ImageView user;
    private ImageView password;
    private ImageView email;
    private ImageView name;
    private ImageView phone;
    private ImageView creditCard;
    private ScooterUser scooterUser = LoginFrameJava8.manager.selectUserInfo();
    private boolean canModify = false;

    public UserInfoFrame(Stage newStage) throws Exception {
        newStage.setTitle("用戶基本信息");
        newStage.setAlwaysOnTop(true);
        newStage.initModality(Modality.APPLICATION_MODAL);

        // 更改圖標
        File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
        Image bg = new Image(bgFile.toURI().toString());
        newStage.getIcons().add(bg);

        initFX();
        initImage();
        initButton(newStage);
        initTextField();
    }

    private void initFX() {
        this.setPrefWidth(750 * 1.255);
        this.setPrefHeight(350 * 1.255);
    }

    private void initImage() {
        Label title = new Label("用戶基本信息");
        title.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 45px;-fx-font-weight: bolder");

        File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
        ImageView background = new ImageView(bgFile.toURI().toString());

        File userFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\user.png");
        File passwordFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\password.png");
        File emailFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\email.png");
        File checkFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\password.png");
        File creditCardFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\credit-card.png");
        File phoneFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\smartphone.png");
        File nameFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\signature.png");

        user = new ImageView(userFile.toURI().toString());
        password = new ImageView(passwordFile.toURI().toString());
        email = new ImageView(emailFile.toURI().toString());
        creditCard = new ImageView(creditCardFile.toURI().toString());
        phone = new ImageView(phoneFile.toURI().toString());
        name = new ImageView(nameFile.toURI().toString());

        SetBoundsUtil.setBounds(title, 0, 25 * 1.255, 750 * 1.255, 50 * 1.255);
        title.setAlignment(Pos.CENTER);

        background.setFitWidth(750 * 1.255);
        background.setFitHeight(350 * 1.255);

        SetBoundsUtil.setBounds(user, 60 * 1.255, 110 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(password, 60 * 1.255, 160 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(email, 60 * 1.255, 210 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(name, 390 * 1.255, 110 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(phone, 390 * 1.255, 160 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(creditCard, 390 * 1.255, 210 * 1.255, 32, 32);

        this.getChildren().addAll(background, title, user, password, email, name, phone, creditCard);

    }

    private void initTextField() {
        accountInput = new TextField();
        passwordInput = new PasswordField();
        emailInput = new TextField();
        creditCardInput = new TextField();
        phoneInput = new TextField();
        nameInput = new TextField();

        SetBoundsUtil.setBounds(accountInput, 115 * 1.255, 110 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(passwordInput, 115 * 1.255, 160 * 1.255, 250, 30 * 1.255);
        SetBoundsUtil.setBounds(emailInput, 115 * 1.255, 210 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(nameInput, 450 * 1.255, 110 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(phoneInput, 450 * 1.255, 160 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(creditCardInput, 450 * 1.255, 210 * 1.255, 250, 30 * 1.255);

        accountInput.setText(scooterUser.getAccount());
        passwordInput.setText(scooterUser.getPassword());
        emailInput.setText(scooterUser.getEmail());

        creditCardInput.setText(scooterUser.getCreditCard());
        nameInput.setText(scooterUser.getName());

        phoneInput.setText(scooterUser.getPhone());

        accountInput.setEditable(false);
        passwordInput.setEditable(false);
        emailInput.setEditable(false);
        creditCardInput.setEditable(false);
        nameInput.setEditable(false);
        phoneInput.setEditable(false);

        Font font = Font.font("Microsoft JhengHei", FontWeight.BOLD, 14);
        accountInput.setFont(font);
        passwordInput.setFont(font);
        emailInput.setFont(font);
        nameInput.setFont(font);
        phoneInput.setFont(font);
        creditCardInput.setFont(font);

        this.getChildren().addAll(accountInput, passwordInput, emailInput, creditCardInput, nameInput, phoneInput);
    }

    private void initButton(Stage newStage) {
        File confirmFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\confirm.png");
        confirmButton = new ImageView(confirmFile.toURI().toString());

        File editFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\edit.png");
        editButton = new ImageView(editFile.toURI().toString());

        File barFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\bar.png");
        bar = new ImageView(barFile.toURI().toString());

        SetBoundsUtil.setBounds(confirmButton, 385 * 1.255, 270 * 1.255, 40 * 1.255, 40 * 1.255);
        SetBoundsUtil.setBounds(editButton, 295 * 1.255, 270 * 1.255, 40 * 1.255, 40 * 1.255);
        SetBoundsUtil.setBounds(bar, 257 * 1.255, 250 * 1.255, 200 * 1.255, 75 * 1.255);

        // add event
        confirmButton.setOnMouseReleased(e -> {
            // 開始修改
            String oldAccount = scooterUser.getAccount();
            String newAccount = accountInput.getText();
            String password = passwordInput.getText();
            String email = emailInput.getText();
            String name = nameInput.getText();
            String phone = phoneInput.getText();
            String creditCard = creditCardInput.getText();
            int condition = isValidInput(newAccount, password, email, name, phone, creditCard);
            if (condition == 1) {
                JDialogUtil.showJDialog("帳號密碼不符合規範", "請重新註冊");
                ClearTextFieldUtil.clearTextField(accountInput, passwordInput);
            } else if (condition == 3) {
                JDialogUtil.showJDialog("電子信箱不符合規範", "請重新輸入");
                ClearTextFieldUtil.clearTextField(emailInput);
            } else if (condition == 4) {
                JDialogUtil.showJDialog("使用者姓名不符合規範", "請重新輸入");
                ClearTextFieldUtil.clearTextField(nameInput);
            } else if (condition == 5) {
                JDialogUtil.showJDialog("電話號碼不符合規範", "請重新輸入");
                ClearTextFieldUtil.clearTextField(phoneInput);
            } else if (condition == 6) {
                JDialogUtil.showJDialog("信用卡卡號不符合規範", "請重新輸入");
                ClearTextFieldUtil.clearTextField(creditCardInput);
            } else if (condition == 2) {
                try {
                    int reaction = RegisterFrame.manager.updateTable(oldAccount, newAccount, password, email, name, phone, creditCard, scooterUser.getID());
                    // 根據reaction操作
                    if (reaction == 1) {
                        JDialogUtil.showJDialog("恭喜用戶", "用戶信息修改成功");
                        ClearTextFieldUtil.clearTextField(accountInput, passwordInput, emailInput, nameInput, phoneInput, creditCardInput);
                        newStage.close();
                    } else if (reaction == 2) {
                        JDialogUtil.showJDialog("用戶名已存在", "請重新修改");
                        ClearTextFieldUtil.clearTextField(accountInput);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        editButton.setOnMouseReleased(e ->
        {
            if (!canModify) JDialogUtil.showJDialog("用戶信息開放修改", "請更新資料");
            else JDialogUtil.showJDialog("用戶信息停止修改", "請確認資料");
            accountInput.setEditable(!canModify);
            passwordInput.setEditable(!canModify);
            emailInput.setEditable(!canModify);
            creditCardInput.setEditable(!canModify);
            nameInput.setEditable(!canModify);
            phoneInput.setEditable(!canModify);
            canModify = !canModify;
        });

        this.getChildren().addAll(bar, editButton, confirmButton);

    }

    /**
     * 判斷輸入是否合法
     * @param account
     * @param password
     * @param email
     * @param name
     * @param phone
     * @param creditCard
     * @return
     */
    private int isValidInput(String account, String password, String email, String name, String phone, String creditCard) {
        String regex = "[a-zA-Z0-9]{6,16}";

        if (account.matches(regex) && password.matches(regex)) {
            if (email.matches("[a-zA-z0-9]+@gmail.com")) {
                if (name.matches("[^0-9]+")) {
                    if (phone.matches("[0-9]{8,}")) {
                        if (creditCard.matches("[0-9]{16}")) return 2; // 合格
                        return 6; // 信用卡不規範
                    }
                    return 5; // 電話號碼不規範
                }
                return 4; // 名稱不規範
            }
            return 3; // email不合格
        } else {
            return 1; // 不符合規範
        }
    }
}
