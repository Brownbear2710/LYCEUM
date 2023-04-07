package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class OTPController {
    @FXML protected Label label;
    @FXML protected TextField OTP;
    @FXML public void onConfirmButtonClick(ActionEvent event) throws IOException
    {
        try {
            if (Integer.valueOf(OTP.getText()) == Main.otp) {
                DatabaseConn.registerUser(Main.firstName, Main.lastName, Main.email, Main.password, Main.accountType);
                SceneChanger.changeTo("RegisterSuccess.fxml", event);
            } else {
                label.setText("OTP doesn't match");
            }
        }
        catch (Exception e) {
            label.setText("OTP doesn't match");
        }
    }
    @FXML public void onBackButtonClick(ActionEvent event) throws IOException
    {
        SceneChanger.changeTo("SignUpMenu.fxml", event);
    }
}
