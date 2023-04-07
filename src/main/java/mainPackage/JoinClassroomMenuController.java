package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class JoinClassroomMenuController {
    @FXML
    private Label label;
    @FXML
    private TextField classcode;

    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("DashboardClassrooms.fxml", event);
    }

    @FXML
    void onJoinButtonClick(ActionEvent event) throws IOException {
        String Classcode = classcode.getText();
        System.out.println(Classcode);
        if(!DatabaseConn.classcodeExists(Classcode))
            label.setText("Invalid classroom code");
        else if(DatabaseConn.alreadyJoined(Classcode))
            label.setText("You are already joined");
        else
        {
            DatabaseConn.joinClassRoom(Classcode);
            SceneChanger.changeTo("DashboardClassrooms.fxml", event);
        }
    }

}
