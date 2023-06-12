package UI_Java8;

import classes.ClearTextFieldUtil;
import classes.JDialogUtil;
import classes.SetBoundsUtil;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;

/**
 * 註冊介面
 */
public class RegisterFrame extends AnchorPane {

    public static registerManager manager;
    private TextField accountInput;
    private PasswordField passwordInput;
    private PasswordField checkInput;
    private TextField emailInput;
    private TextField creditCardInput;
    private TextField nameInput;
    private TextField phoneInput;
    private String userHint = "用戶名  長度為 6 ~ 16 位英文數字";
    private String passwordHint = "密碼     長度為 6 ~ 16 位英文數字";
    private String checkHint = "確認密碼";
    private String emailHint = "電子信箱 (Gmail Only)";
    private String creditCardHint = "請輸入信用卡資訊";
    private String nameHint = "請輸入姓名";
    private String phoneHint = "請輸入電話號碼";
    private ImageView loginButton;
    private ImageView registerButton;
    private ImageView bar;
    private ImageView user;
    private ImageView password;
    private ImageView check;
    private ImageView email;
    private ImageView name;
    private ImageView phone;
    private ImageView creditCard;

    public RegisterFrame(Stage stage, Scene loginScene) throws Exception {
        initFX();
        initImage();
        initButton(stage, loginScene);
        initTextField();
    }

    private void initFX() throws Exception {

        manager = new registerManager();

        this.setPrefWidth(750 * 1.255);
        this.setPrefHeight(450 * 1.255);
    }

    private void initImage() {
        javafx.scene.control.Label title = new javafx.scene.control.Label("電動車租借平台註冊系統");
        title.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 45px;-fx-font-weight: bolder");

        File bgFile = new File(LoginFrameJava8.path + "\\pictures\\background.png");
        Image bg = new Image(bgFile.toURI().toString());

        ImageView background = new ImageView(bg);

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
        check = new ImageView(checkFile.toURI().toString());
        creditCard = new ImageView(creditCardFile.toURI().toString());
        phone = new ImageView(phoneFile.toURI().toString());
        name = new ImageView(nameFile.toURI().toString());


        SetBoundsUtil.setBounds(title, 0, 35 * 1.255, 750 * 1.255, 50 * 1.255);
        title.setAlignment(Pos.CENTER);

        background.setFitWidth(750 * 1.255);
        background.setFitHeight(450 * 1.255);

        SetBoundsUtil.setBounds(user, 60 * 1.255, 100 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(password, 60 * 1.255, 150 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(check, 60 * 1.255, 200 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(email, 60 * 1.255, 250 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(name, 390 * 1.255, 100 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(phone, 390 * 1.255, 150 * 1.255, 32, 32);
        SetBoundsUtil.setBounds(creditCard, 390 * 1.255, 200 * 1.255, 32, 32);

        this.getChildren().addAll(background, title, user, password, check, email, name, phone, creditCard);

    }

    private void initTextField() {
        accountInput = new TextField();
        passwordInput = new PasswordField();
        checkInput = new PasswordField();
        emailInput = new TextField();
        creditCardInput = new TextField();
        phoneInput = new TextField();
        nameInput = new TextField();

        SetBoundsUtil.setBounds(accountInput, 115 * 1.255, 100 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(passwordInput, 115 * 1.255, 150 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(checkInput, 115 * 1.255, 200 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(emailInput, 115 * 1.255, 250 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(nameInput, 450 * 1.255, 100 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(phoneInput, 450 * 1.255, 150 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(creditCardInput, 450 * 1.255, 200 * 1.255, 250 * 1.255, 30 * 1.255);

        accountInput.setPromptText(userHint);
        passwordInput.setPromptText(passwordHint);
        checkInput.setPromptText(checkHint);
        emailInput.setPromptText(emailHint);
        creditCardInput.setPromptText(creditCardHint);
        nameInput.setPromptText(nameHint);
        phoneInput.setPromptText(phoneHint);

        javafx.scene.text.Font font = javafx.scene.text.Font.font("Microsoft JhengHei", FontWeight.BOLD, 18);
        accountInput.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 18px;-fx-font-weight: bolder");
        passwordInput.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 18px;-fx-font-weight: bolder");
        checkInput.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 18px;-fx-font-weight: bolder");
        emailInput.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 18px;-fx-font-weight: bolder");
        nameInput.setFont(font);
        phoneInput.setFont(font);
        creditCardInput.setFont(font);

        this.getChildren().addAll(accountInput, passwordInput, checkInput, emailInput, creditCardInput, nameInput, phoneInput);
    }

    private void initButton(Stage stage, Scene loginScene) {

        File registerFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\register.png");
        registerButton = new ImageView(registerFile.toURI().toString());

        File loginFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\login.png");
        loginButton = new ImageView(loginFile.toURI().toString());

        File barFile = new File(LoginFrameJava8.path + "\\pictures\\buttonIcon\\bar.png");
        bar = new ImageView(barFile.toURI().toString());

        SetBoundsUtil.setBounds(registerButton, 335 * 1.255, 315 * 1.255, 35 * 1.255, 35 * 1.255);
        SetBoundsUtil.setBounds(loginButton, 245 * 1.255, 315 * 1.255, 35 * 1.255, 35 * 1.255);
        SetBoundsUtil.setBounds(bar, 210 * 1.255, 292 * 1.255, 200 * 1.255, 75 * 1.255);

        // add event
        registerButton.setOnMouseReleased(e -> {
            // 開始註冊
            String account = accountInput.getText();
            String password = passwordInput.getText();
            String check = checkInput.getText();
            String email = emailInput.getText();
            String name = nameInput.getText();
            String phone = phoneInput.getText();
            String creditCard = creditCardInput.getText();
            int condition = isValidInput(account, password, check, email, name, phone, creditCard);
            if (condition == 1) {
                JDialogUtil.showJDialog("帳號密碼不符合規範", "請重新註冊");
                ClearTextFieldUtil.clearTextField(accountInput, passwordInput, checkInput);
            } else if (condition == 3) {
                JDialogUtil.showJDialog("兩次密碼輸入不一致", "請重新註冊");
                ClearTextFieldUtil.clearTextField(accountInput, passwordInput, checkInput);
            } else if (condition == 4) {
                JDialogUtil.showJDialog("電子信箱不符合規範", "請重新輸入");
                ClearTextFieldUtil.clearTextField(emailInput);
            } else if (condition == 5) {
                JDialogUtil.showJDialog("使用者姓名不符合規範", "請重新輸入");
                ClearTextFieldUtil.clearTextField(nameInput);
            } else if (condition == 6) {
                JDialogUtil.showJDialog("電話號碼不符合規範", "請重新輸入");
                ClearTextFieldUtil.clearTextField(phoneInput);
            } else if (condition == 7) {
                JDialogUtil.showJDialog("信用卡卡號不符合規範", "請重新輸入");
                ClearTextFieldUtil.clearTextField(creditCardInput);
            } else if (condition == 2) {
                try {
                    int reaction = manager.insertTable(account, password, email, name, phone, creditCard);
                    // 根據reaction操作
                    if (reaction == 1) {
                        JDialogUtil.showJDialog("恭喜用戶", "帳號註冊成功");
                        ClearTextFieldUtil.clearTextField(accountInput, passwordInput, checkInput, emailInput, nameInput, phoneInput, creditCardInput);
                    } else if (reaction == 2) {
                        JDialogUtil.showJDialog("用戶名已存在", "請重新輸入");
                        ClearTextFieldUtil.clearTextField(accountInput, passwordInput, checkInput);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        loginButton.setOnMouseReleased(e -> {
            stage.setScene(loginScene);
            stage.setTitle("登陸介面");
        });

        this.getChildren().addAll(bar, loginButton, registerButton);

    }

    // 判斷輸入是否合法
    private int isValidInput(String account, String password, String check, String email, String name, String phone, String creditCard) {
        String regex = "[a-zA-Z0-9]{6,16}";

        if (account.matches(regex) && password.matches(regex) && check.matches(regex)) {
            if (password.equals(check)) {
                if (email.matches("[a-zA-z0-9]+@gmail.com")) {
                    if (name.matches("[^0-9]+")) {
                        if (phone.matches("[0-9]{8,}")) {
                            if (creditCard.matches("[0-9]{16}")) return 2; // 合格
                            return 7; // 信用卡不規範
                        }
                        return 6; // 電話號碼不規範
                    }
                    return 5; // 名稱不規範
                }
                return 4; // email不合格
            } else return 3; // 確認密碼失敗
        } else {
            return 1; // 不符合規範
        }
    }

}
