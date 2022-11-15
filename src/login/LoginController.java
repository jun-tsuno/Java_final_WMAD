package login;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbStatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Label loginStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (loginModel.isDatabaseConnected()) {
            this.dbStatus.setText("Connected");
        } else {
            this.dbStatus.setText("Not Connected");
        }
    }

    @FXML
    public void Login(ActionEvent event) {

        if (this.loginModel.isLogin(this.username.getText(), this.password.getText())) {
            Stage stage = (Stage) this.loginBtn.getScene().getWindow();
            stage.close();

            homepage();

        } else {
            this.loginStatus.setText("Wrong credentials.");
        }
    }

        @FXML
        public void signUpPage() {

            Stage stage = (Stage) this.loginBtn.getScene().getWindow();
            stage.close();

            Stage signUpStage = new Stage();
            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/signup/SignUp.fxml")));

                signUpStage.setScene(scene);
                signUpStage.setTitle("Register Page");
                signUpStage.setResizable(false);
                signUpStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void homepage() {

        Stage homeStage = new Stage();
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../home/Home.fxml")));
            homeStage.setScene(scene);
            homeStage.setTitle("Home");
            homeStage.setResizable(false);
            homeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
