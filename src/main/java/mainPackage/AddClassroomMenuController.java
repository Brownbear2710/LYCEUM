package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddClassroomMenuController {
    @FXML
    private Label label;

    @FXML
    private TextField subject;

    public static String generateClasscode() {
        String ret = new String();
        while(ret.length() < 7) {
            int i = (int) (Math.random() * 100) % 36;
            if(i < 10)
                ret += Integer.toString(i);
            else
                ret += Character.toString('a' + (i-10));
        }
        return  ret;
    }
    @FXML
    void onCreateButtonClick(ActionEvent event) throws IOException {
        String Subject = subject.getText();
        if(Subject.length() == 0)
        {
            label.setText("Please enter a subject");
        }
        else
        {
            String classcode = generateClasscode();
            while(DatabaseConn.classcodeExists(classcode)) {
                classcode = generateClasscode();
            }
            DatabaseConn.addNewClassroom(Subject, classcode, Main.email);
            SceneChanger.changeTo("DashboardClassrooms.fxml", event);
        }
    }
    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("DashboardClassrooms.fxml", event);
    }
}
