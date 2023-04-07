package mainPackage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClassroomStudentViewController implements Initializable {

    public static String sub, classcode;
    @FXML
    private Label Subject;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Subject.setText(sub);
    }
    @FXML
    void onGobackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("DashboardClassrooms.fxml", event);
    }

    @FXML
    void onLeaveButtonClick(ActionEvent event) throws IOException {
        DatabaseConn.leaveClassroom(classcode, Main.email);
        System.out.println(classcode + " " + Main.email);
        onGobackButtonClick(event);
    }

    @FXML
    void onPreviousButtonClick(ActionEvent event) throws IOException {
        PreviousQuizMenuController.classcode = classcode;
        SceneChanger.changeTo("PreviousQuizMenu.fxml", event);
    }

    @FXML
    void onUpcomingButtonClick(ActionEvent event) throws IOException {
        UpComingQuizMenuController.classcode = classcode;
        SceneChanger.changeTo("UpComingQuizMenu.fxml", event);
    }

}
