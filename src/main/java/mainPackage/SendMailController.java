package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class SendMailController {
    static String receiver;
    @FXML
    private Label exception;

    @FXML
    private TextArea text;

    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("StudentList.fxml", event);
    }

    @FXML
    void onSendButtonClick(ActionEvent event) throws IOException{
        if(text.getText().isEmpty() || text.getText().length() == 0) {
            exception.setText("Field is empty");
            return;
        }
        MailService.sendMail(receiver, text.getText(), "Lyceum mail from \'" + Main.firstName + " " + Main.lastName + '\'');
        SceneChanger.changeTo("StudentList.fxml", event);
    }

}