package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.util.Objects;

public class ChangePasswordMenuController {
    @FXML
    protected PasswordField password;
    @FXML
    protected PasswordField confirmPassword;
    @FXML
    protected Label label;

    @FXML
    public void onGoBackButtonClick(ActionEvent event) throws IOException
    {
        SceneChanger.changeTo("LoginMenu.fxml", event);
    }
    public void onChangePasswordButtonClick(ActionEvent event) throws IOException
    {
        String Password = password.getText();
        String ConfirmPassword = confirmPassword.getText();
        if(!Objects.equals(Password, ConfirmPassword))
        {
            label.setText("Passwords don't match");
        }
        else if(Password.length() < 8)
        {
            label.setText("Password should be at least 8 characters");
        }
        else
        {
            DatabaseConn.updatePassword(Password);
            onGoBackButtonClick(event);
        }
    }
}
