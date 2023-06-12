package UI_Java8;

import classes.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * 登陸介面
 */
public class LoginFrameJava8 extends Application {

    public static loginManager manager;

    public static String path = "scooterRentalPlatformJava8\\src";
    RegisterFrame registerFrame;
    Scene registerScene;
    private TextField accountInput;
    private PasswordField passwordInput;
    private static TextField randomCodeInput;
    private String userHint = "用戶名  長度為 6 ~ 16 位英文數字";
    private String passwordHint = "密碼     長度為 6 ~ 16 位英文數字";
    private String randomCodeHint = "驗證碼   可換";
    private ImageView loginButton;
    private ImageView registerButton;
    private ImageView bar;
    private ImageView user;
    private ImageView password;
    private ImageView randomCode;
    private ImageView alter;
    private Label title;
    private static Label verify;
    private Label findPassword;
    private static String rCode;
    private Scene loginScene;
    private SRPMainFrame MainFrame;
    private Scene MainScene;
    private maintenanceMainFrame MTFrame;
    private String account;
    public static ArrayList<Scooter> scooterList = new ArrayList<>();
    private static ArrayList<Battery> batteryList = new ArrayList<>();

    private AnchorPane pane = new AnchorPane();

    @Override
    public void start(Stage stage) throws Exception {

        initFX(stage);
        initImage(stage);
        initButton(stage);
        initTextField();

        loadJSONScooter(scooterList);
        loadJSONBattery(batteryList);
        manager.createTotalScooterTAB(scooterList);
        manager.createTotalBatteryTAB(batteryList);
        manager.createUserHistory();
        registerFrame = new RegisterFrame(stage, loginScene);
        registerScene = new Scene(registerFrame, 750, 450);
        MainFrame = new SRPMainFrame(stage, loginScene, registerScene);
        MTFrame = new maintenanceMainFrame(stage, loginScene);
        MainScene = new Scene(MainFrame, 800, 400);
    }

    public static void main(String[] args) {
        launch();
    }

    private void initFX(Stage stage) throws Exception {
        // 更改圖標
        File bgFile = new File(path + "\\pictures\\background.png");
        Image bg = new Image(bgFile.toURI().toString());
        stage.getIcons().add(bg);

        manager = new loginManager();

        stage.setWidth(750 * 1.255);
        stage.setHeight(450 * 1.255);
        // set Title
        stage.setTitle("登陸介面");
        // set UI on Top
        stage.setAlwaysOnTop(true);
        // setResizable
        stage.setResizable(false);

        stage.setOnCloseRequest(ex ->
        {
            ex.consume();
            Platform.exit();
            System.exit(0);
        });
    }

    private void initImage(Stage stage) {

        title = new Label("電動車租借平台登錄系統");

        title.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 45px;-fx-font-weight: bolder");

        File bgFile = new File(path + "\\pictures\\background.png");
        Image bg = new Image(bgFile.toURI().toString());

        ImageView background = new ImageView(bg);

        File userFile = new File(path + "\\pictures\\buttonIcon\\user.png");
        user = new ImageView(userFile.toURI().toString());

        File passwordFile = new File(path + "\\pictures\\buttonIcon\\password.png");
        password = new ImageView(passwordFile.toURI().toString());

        File randomFile = new File(path + "\\pictures\\buttonIcon\\verification.png");
        randomCode = new ImageView(randomFile.toURI().toString());

        title.setPrefWidth(750 * 1.255);

        title.setLayoutY(50 * 1.255);
        title.setAlignment(Pos.CENTER);

        background.setFitWidth(750 * 1.255);
        background.setFitHeight(450 * 1.255);

        SetBoundsUtil.setBounds(user, 175 * 1.255, 150 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(password, 175 * 1.255, 200 * 1.255, 32 * 1.255, 32 * 1.255);
        SetBoundsUtil.setBounds(randomCode, 175 * 1.255, 250 * 1.255, 32 * 1.255, 32 * 1.255);

        pane.getChildren().addAll(background, title, user, password, randomCode);

        loginScene = new Scene(pane, 750 * 1.255, 450 * 1.255);
        stage.setScene(loginScene);
        stage.setTitle("登陸介面");
        stage.show();
    }

    private void initTextField() {
        accountInput = new TextField();
        passwordInput = new PasswordField();
        randomCodeInput = new TextField();

        SetBoundsUtil.setBounds(accountInput, 230 * 1.255, 150 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(passwordInput, 230 * 1.255, 200 * 1.255, 250 * 1.255, 30 * 1.255);
        SetBoundsUtil.setBounds(randomCodeInput, 230 * 1.255, 250 * 1.255, 110 * 1.255, 30 * 1.255);

        accountInput.setPromptText(userHint);
        passwordInput.setPromptText(passwordHint);
        randomCodeInput.setPromptText(randomCodeHint);

        accountInput.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 18px;-fx-font-weight: bolder");
        passwordInput.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 18px;-fx-font-weight: bolder");
        randomCodeInput.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 18px;-fx-font-weight: bolder");

        pane.getChildren().addAll(accountInput, passwordInput, randomCodeInput);
    }

    private void initButton(Stage stage) {
        // code
        {
            rCode = code();
            verify = new Label(rCode);
            verify.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 20px;-fx-font-weight: bolder");
            verify.setTextFill(javafx.scene.paint.Color.RED);
            verify.setStyle("-fx-background-color: white");
            verify.setAlignment(Pos.CENTER);
            SetBoundsUtil.setBounds(verify, 350 * 1.255, 250 * 1.255, 55 * 1.255, 30 * 1.255);
        }

        // findPassword
        {
            findPassword = new Label("忘記密碼");
            findPassword.setStyle("-fx-font-family: 'Microsoft JhengHei';-fx-font-size: 20px;-fx-font-weight: bolder");
            findPassword.setStyle("-fx-background-color: white");
            findPassword.setAlignment(Pos.CENTER);
            SetBoundsUtil.setBounds(findPassword, 420 * 1.255, 250 * 1.255, 60 * 1.255, 30 * 1.255);

        }

        File registerFile = new File(path + "\\pictures\\buttonIcon\\register.png");
        registerButton = new ImageView(registerFile.toURI().toString());

        File loginFile = new File(path + "\\pictures\\buttonIcon\\login.png");
        loginButton = new ImageView(loginFile.toURI().toString());

        File barFile = new File(path + "\\pictures\\buttonIcon\\bar.png");
        bar = new ImageView(barFile.toURI().toString());

        File alterFile = new File(path + "\\pictures\\buttonIcon\\alter.png");
        alter = new ImageView(alterFile.toURI().toString());

        SetBoundsUtil.setBounds(registerButton, 335 * 1.255, 315 * 1.255, 35 * 1.255, 35 * 1.255);
        SetBoundsUtil.setBounds(loginButton, 245 * 1.255, 315 * 1.255, 35 * 1.255, 35 * 1.255);
        SetBoundsUtil.setBounds(bar, 210 * 1.255, 292 * 1.255, 200 * 1.255, 75 * 1.255);
        SetBoundsUtil.setBounds(alter, 690 * 1.255, 10 * 1.255, 32 * 1.255, 32 * 1.255);

        pane.getChildren().addAll(verify, findPassword, alter);

        // add event
        {

            // 開始註冊
            registerButton.setOnMouseReleased(e -> {
                if (title.getText().equals("電動車租借平台登錄系統")) {
                    stage.setScene(registerScene);
                    stage.setTitle("註冊介面");
                    LoginFrameJava8.newCode();
                } else {
                    JDialogUtil.showJDialog("管理員系統不支持該功能", "請更改操作");
                }
            });

            // 開始登陸
            loginButton.setOnMouseReleased(e -> {
                if (title.getText().equals("電動車租借平台登錄系統")) {
                    loginEvent(stage);
                } else {
                    MTLoginEvent(stage);
                }
            });

            // 切換驗證碼
            verify.setOnMouseReleased(e -> {
                rCode = code();
                verify.setText(rCode);
            });

            // 忘記密碼
            findPassword.setOnMouseReleased(e -> {
                if (title.getText().equals("電動車租借平台登錄系統")) {
                    try {
                        new findPasswordFrame();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JDialogUtil.showJDialog("管理員系統不支持該功能", "請更改操作");
                }
            });

            alter.setOnMouseReleased(e -> {
                if (title.getText().equals("電動車租借平台登錄系統")) {
                    newCode();
                    title.setText("電動車維修人員登錄系統");
                } else {
                    newCode();
                    title.setText("電動車租借平台登錄系統");
                }
            });
        }

        pane.getChildren().addAll(bar, registerButton, loginButton);
    }

    // 判斷輸入是否合理
    private int isValidInput(String name, String password, String code) {
        String regex = "[a-zA-Z0-9]{6,16}";
        if (name.matches(regex) && password.matches(regex)) {
            if (code.equals(rCode)) return 2; // 合格
            else return 3; // 驗證碼錯誤
        } else {
            return 1; // 不符合規範
        }
    }

    // 生成驗證碼
    public static String code() {
        // 生成驗證碼 4位英文 1位數字 數字位置隨機
        char[] arr = new char[52];
        for (int i = 0; i < arr.length; i++) {
            if (i < arr.length / 2) {
                char c = (char) ('a' + i);
                arr[i] = c;
            } else {
                char c = (char) ('A' + i - arr.length / 2);
                arr[i] = c;
            }
        }
        Random r = new Random();
        int number = r.nextInt(10);
        int index = r.nextInt(5);
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i == index) {
                code.append(number);
            } else {
                int indexEN = r.nextInt(52);
                code.append(arr[indexEN]);
            }
        }
        return code.toString();
    }

    public static loginManager getManager() {
        return manager;
    }

    /**
     * 用戶登入
     * @param stage
     */
    public void loginEvent(Stage stage) {

        // 開始登陸
        String account = accountInput.getText();
        String password = passwordInput.getText();
        String check = randomCodeInput.getText();
        int condition = isValidInput(account, password, check);
        if (condition == 1) {
            JDialogUtil.showJDialog("帳號密碼不符合規範", "請重新輸入");
            ClearTextFieldUtil.clearTextField(accountInput, passwordInput);
            newCode();
        } else if (condition == 3) {
            JDialogUtil.showJDialog("驗證碼錯誤", "請重新輸入");
            newCode();
        } else if (condition == 2) {
            try {
                int reaction = manager.isValidLogin(account, password);
                // 根據reaction操作
                if (reaction == 3) { // 用戶不存在
                    ClearTextFieldUtil.clearTextField(accountInput, passwordInput, randomCodeInput);
                    JDialogUtil.showJDialog("用戶不存在", "請重新輸入或註冊");
                } else if (reaction == 2) { // 密碼錯誤
                    ClearTextFieldUtil.clearTextField(passwordInput, randomCodeInput);
                    JDialogUtil.showJDialog("密碼錯誤", "請重新輸入");
                } else if (reaction == 1) { // 成功登陸
                    ClearTextFieldUtil.clearTextField(accountInput, passwordInput, randomCodeInput);
                    this.account = account;
                    JDialogUtil.showJDialog("登入成功", "即將跳轉至主系統");
                    // 開啟新的javaFX
                    MainFrame.setAccount(this.account);
                    MainFrame.setCoupon(manager.SelectCoupon(this.account));

                    stage.setScene(MainScene);

                    stage.setWidth(800 * 1.255);
                    stage.setHeight(400 * 1.255);
                    stage.setTitle("電動車租借平台");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 管理者登陸
     * @param stage
     */
    public void MTLoginEvent(Stage stage) {
        // 限定一個管理員帳號
        if (accountInput.getText().equals("manager") && passwordInput.getText().equals("manager") && randomCodeInput.getText().equals(rCode)) {
            JDialogUtil.showJDialog("登入成功", "即將跳轉至主系統");
            ClearTextFieldUtil.clearTextField(accountInput, passwordInput, randomCodeInput);
            stage.setWidth(800 * 1.255);
            stage.setHeight(400 * 1.255);
            stage.setScene(new Scene(MTFrame, 800 * 1.255, 400 * 1.255));
            stage.setTitle("電動車維修平台");
        } else {
            System.out.println(rCode);
            ClearTextFieldUtil.clearTextField(accountInput, passwordInput, randomCodeInput);
            JDialogUtil.showJDialog("帳號密碼錯誤", "請重新輸入");
        }
    }

    /**
     * 從json中讀數據scooter
     */
    private void loadJSONScooter(ArrayList<Scooter> list) throws FileNotFoundException {
        File scooter = new File("scooterRentalPlatformJava8\\src\\json\\scooter_detail.json");
        JsonReader reader = Json.createReader(new InputStreamReader(new FileInputStream(scooter)));

        JsonArray jsonArray = reader.readArray();
        for (JsonValue jsonValue : jsonArray) {
            if (jsonValue.getValueType() == JsonValue.ValueType.OBJECT) {
                JsonObject jsonObject = (JsonObject) jsonValue;
                String licence = jsonObject.getString("no");
                double lat = jsonObject.getJsonNumber("lat").doubleValue();
                double lng = jsonObject.getJsonNumber("lng").doubleValue();
                int power = jsonObject.getInt("power");
                boolean condition = randomCondition();
                list.add(new Scooter(licence, lat, lng, power, condition));
            }
        }
    }

    /**
     * 從json中讀battery
     */
    private void loadJSONBattery(ArrayList<Battery> list) throws FileNotFoundException {
        File battery = new File("scooterRentalPlatformJava8\\src\\json\\battery.json");
        JsonReader reader = Json.createReader(new InputStreamReader(new FileInputStream(battery)));

        JsonArray jsonArray = reader.readArray();
        for (JsonValue jsonValue : jsonArray) {
            if (jsonValue.getValueType() == JsonValue.ValueType.OBJECT) {
                JsonObject jsonObject = (JsonObject) jsonValue;
                int no = jsonObject.getInt("no");
                double lat = jsonObject.getJsonNumber("lat").doubleValue();
                double lng = jsonObject.getJsonNumber("lng").doubleValue();
                list.add(new Battery(no, lat, lng));
            }
        }
    }

    /**
     * 隨機給予車種狀態模擬故障情況
     */
    private boolean randomCondition() {
        Random r = new Random();
        int i = r.nextInt(10);
        if (i >= 8) return false;
        return true;
    }

    /**
     * 修改驗證碼
     */
    public static void newCode() {
        rCode = code();
        verify.setText(rCode);
        randomCodeInput.setText("");
    }
}
