package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginMenuController
{
    @FXML
    public TextField email;
    @FXML
    public TextField password;
    @FXML
    Label wrongPassword;
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException
    {
        // Register
        String Email = email.getText();
        String Password = DatabaseConn.hash(password.getText());
//        String Password = password.getText();
        AccountInfo accountInfo = DatabaseConn.getAccountInfo(Email);
        Main.email = Email;
        Main.firstName = accountInfo.firstName;
        Main.lastName = accountInfo.lastName;
        Main.accountType = accountInfo.accountType;

        int loginVal = DatabaseConn.loginValidation(Email, Password);
        if(loginVal == 0)
        {
            wrongPassword.setText("Incorrect Email / Password");
            return;
        }
        else if(loginVal == 1 || loginVal == 2)
        {
            SceneChanger.changeTo("Dashboard.fxml", event);
        }
    }
    @FXML
    protected void onGoBackButtonClick(ActionEvent event) throws IOException
    {
        SceneChanger.changeTo("MainMenu.fxml", event);
    }
    @FXML
    public void onForgotPasswordButtonClick(ActionEvent event) throws IOException
    {
        SceneChanger.changeTo("ForgotPasswordMenu.fxml", event);
    }
}
