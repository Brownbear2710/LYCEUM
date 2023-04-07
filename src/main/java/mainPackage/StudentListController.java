package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentListController implements Initializable {
    public static String classcode;
    ArrayList<StudentInfo> arrayList;
    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        arrayList = DatabaseConn.getAllStudents(classcode);
        for(int i = 0; i < arrayList.size(); i++) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("StudentThumb.fxml"));
            try {
                Parent root = loader.load();
                StudentThumbController q = loader.getController();
                q.setInfo(arrayList.get(i));
                int x = 1, y = 1 + i;
                gridPane.add(root, x, y);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onMailEveryoneButtonClick(ActionEvent event) throws IOException{
        MailEveryoneMenuController.arrayList = arrayList;
        SceneChanger.changeTo("MailEveryoneMenu.fxml", event);
    }
    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("ClassroomTeacherView.fxml", event);
    }

}
