package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ForgotPasswordController {
    @FXML protected TextField email;
    @FXML protected Label label;

    public void onGoBackBAckButtonCLick(ActionEvent event) throws IOException
    {
        SceneChanger.changeTo("LoginMenu.fxml", event);
    }
    public void onSendButtonClick(ActionEvent event) throws IOException
    {
        String Email = email.getText();
        if(!DatabaseConn.accountAlreadyExists(Email))
        {
            label.setText("No such account exists!");
        }
        else
        {
            int otp = (int) (Math.random() * (999999-100000) + 1000000);
            if(MailService.sendMail(Email, "OTP: " + String.valueOf(otp), "Change Password"))
            {
                Main.otp = otp;
                Main.email = Email;
                SceneChanger.changeTo("EmailConfirmation.fxml", event);
            }
            else
            {
                label.setText("Invalid Email address");
            }
        }
    }
}
