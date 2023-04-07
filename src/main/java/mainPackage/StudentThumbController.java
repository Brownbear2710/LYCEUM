package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class StudentThumbController {

    StudentInfo studentInfo;
    @FXML
    ImageView studentImage;

    public void setInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
        name.setText(studentInfo.name);
        AccountInfo accountInfo = DatabaseConn.getAccountInfo(studentInfo.email);
        if(accountInfo.dp != null) {
            Circle circle = new Circle(35,35,35);
            studentImage.setClip(circle);
            studentImage.setImage(accountInfo.dp);
        }
    }
    @FXML
    private TextField name;

    @FXML
    void onMailButtonClick(ActionEvent event) throws IOException {
        SendMailController.receiver = studentInfo.email;
        SceneChanger.changeTo("SendMail.fxml", event);
    }

    @FXML
    void onRemoveButtonClick(ActionEvent event) throws IOException {
        DatabaseConn.leaveClassroom(StudentListController.classcode,studentInfo.email);
        SceneChanger.changeTo("StudentList.fxml", event);
    }
}