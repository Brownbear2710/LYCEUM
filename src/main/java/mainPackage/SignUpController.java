package mainPackage;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label emptyField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        comboBox.setItems(FXCollections.observableArrayList("Student", "Teacher"));
    }
    @FXML
    protected void onSignupButtonClick(ActionEvent event) throws IOException
    {
        // Register
        String Email = email.getText();
        Main.email = Email;
        String Password = DatabaseConn.hash(password.getText());
        String ConfirmPassword = DatabaseConn.hash(confirmPassword.getText());
        Main.password = Password;
        String FirstName = firstName.getText();
        Main.firstName = FirstName;
        String LastName = lastName.getText();
        Main.lastName = LastName;
        String AccountType = comboBox.getValue();
        Main.accountType = AccountType;
        int otp = (int) (Math.random() * (999999-100000) + 1000000);
        if(FirstName.length() == 0 || LastName.length() == 0 || Email.length() == 0 || AccountType == null)
        {
            emptyField.setText("No field can be empty");
            return;
        }
        else emptyField.setText("");

        if(Password.length() < 8)
        {
            emptyField.setText("Password should be at least 8 character");
            return;
        }
        else if(!Objects.equals(ConfirmPassword, Password))
        {
            emptyField.setText("Passwords don't match");
            return;
        }
        else if(DatabaseConn.accountAlreadyExists(Email))
        {
            emptyField.setText("Seems like you are already registered");
        }
        else if(MailService.sendMail(Email,"OTP: " + String.valueOf(otp), "Account Verification"))
        {
            Main.otp = otp;
            SceneChanger.changeTo("SignUpVerification.fxml", event);
        }
        else
        {
            emptyField.setText("Invalid Email address");
        }
    }
    @FXML
    protected void onGoBackButtonClick(ActionEvent event) throws IOException
    {
        SceneChanger.changeTo("MainMenu.fxml", event);
    }
}
