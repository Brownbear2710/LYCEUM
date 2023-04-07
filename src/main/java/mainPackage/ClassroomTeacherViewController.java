package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClassroomTeacherViewController implements Initializable {

    public static String Classcode, Subject;
    @FXML
    private  TextField classcode;
    @FXML
    private TextField subject;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classcode.setText("Classcode: " + Classcode);
        subject.setText("Subject: " + Subject);
    }
    @FXML
    void onAddQuizButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("PreAddQuizMenu.fxml", event);
    }
    @FXML
    void onDeleteButtonClick(ActionEvent event) throws IOException {
        DatabaseConn.deleteClassroom(Classcode);
        SceneChanger.changeTo("DashboardClassrooms.fxml", event);
    }
    @FXML
    void onGoBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("DashboardClassrooms.fxml", event);
    }
    @FXML
    void onPreviousButtonClick(ActionEvent event)  throws IOException{
        PreviousQuizMenuController.classcode = Classcode;
        SceneChanger.changeTo("PreviousQuizMenu.fxml",event);

    }
    @FXML
    void onStudentListButtonClick(ActionEvent event) throws IOException{
        StudentListController.classcode = Classcode;
        SceneChanger.changeTo("StudentList.fxml", event);
    }
    @FXML
    void onUpcomingButtonClick(ActionEvent event) throws IOException{
        UpComingQuizMenuController.classcode = Classcode;
        SceneChanger.changeTo("UpComingQuizMenu.fxml", event);
    }

}