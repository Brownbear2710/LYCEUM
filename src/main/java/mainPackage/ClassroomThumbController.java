package mainPackage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.IOException;

public class ClassroomThumbController{
    private String Classcode, Subject, cc;

    @FXML
    public ImageView teacherDP;
    @FXML
    VBox vbox;
    @FXML
    private TextField subject;

    @FXML
    private TextField classroomCode;

    public void setLabel(String Subject,String Code) {
        subject.setText(Subject);
        this.Subject = Subject;
        Classcode = Code;
        if(Main.accountType.equals("Teacher")) classroomCode.setText("Classcode: " + Code);
        else classroomCode.setText("Teacher: " + Code);
    }
    public void setcc(String _cc) {
        cc = _cc;
    }

    @FXML
    void onClassClick(MouseEvent event) throws IOException {
        if(Main.accountType.equals("Teacher")) {
            ClassroomTeacherViewController.Subject = Subject;
            ClassroomTeacherViewController.Classcode = Classcode;
            SceneChanger.changeTo("ClassroomTeacherView.fxml", vbox);
        }
        else {
            ClassroomStudentViewController.sub = Subject;
            ClassroomStudentViewController.classcode = cc;
            SceneChanger.changeTo("ClassroomStudentView.fxml", vbox);
        }
    }
}
