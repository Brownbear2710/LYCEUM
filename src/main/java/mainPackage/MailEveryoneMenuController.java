package mainPackage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;

import static javafx.util.Duration.seconds;

public class MailEveryoneMenuController {
    public static ArrayList<StudentInfo> arrayList;
    @FXML
    private Label exception;

    @FXML
    private TextArea text;

    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("StudentList.fxml", event);
    }
    Timeline timeline;

    @FXML
    void onSendButtonClick(ActionEvent event) throws IOException {
        if(text.getText().isEmpty() || text.getText().length() == 0) {
            exception.setText("Field is empty");
            return;
        }
        if(!MailService.InternetAccess()) {
            exception.setText("No Internet Access.");
            return;
        }

        ArrayList<StudentInfo> arrayList = MailEveryoneMenuController.arrayList;
        SceneChanger.changeTo("StudentList.fxml", event);
        class Task implements Runnable{
            @Override
            public void run() {
                for(int i = 0; i < arrayList.size(); i++) {
                    MailService.sendMail(arrayList.get(i).email, text.getText() , "Mail from " + Main.firstName + " " + Main.lastName);
                }
            }
        }
        Task t = new Task();
        Thread thread = new Thread(t);
        thread.start();
    }
}
