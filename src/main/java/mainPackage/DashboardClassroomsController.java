package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardClassroomsController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        int i;
        if(Main.accountType.equals("Teacher")) {
            ArrayList<Pair<String, String>> arrayList = DatabaseConn.getAllClassroomsForTeacher();
            for (i = 0; i < arrayList.size(); i++) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("ClassroomThumb.fxml"));
                try {
                    Parent root = loader.load();
                    ClassroomThumbController c = loader.getController();
                    c.setLabel(arrayList.get(i).getValue(), arrayList.get(i).getKey());
                    int x = i % 4 + 1, y = i / 4 + 1;
                    gridPane.add(root, x, y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            //<Classcode, <Teacher, Subject>>
            ArrayList<Pair<String, Pair<String, String>>> arrayList = DatabaseConn.getAllClassForStudent();
            for (i = 0; i < arrayList.size(); i++) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("ClassroomThumb.fxml"));
                try {
                    Parent root = loader.load();
                    ClassroomThumbController c = loader.getController();
                    Image dp = DatabaseConn.getTeacherImage(arrayList.get(i).getKey());
                    Circle circle = new Circle(38,38,38);
                    c.teacherDP.setImage(dp);
                    c.teacherDP.setClip(circle);

                    c.setLabel(arrayList.get(i).getValue().getValue(), arrayList.get(i).getValue().getKey());
                    c.setcc(arrayList.get(i).getKey());
                    int x = i % 4 + 1, y = i / 4 + 1;
                    gridPane.add(root, x, y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("ClassroomAdd.fxml"));
            Parent root = loader.load();
            gridPane.add(root, (i%4) + 1, (i/4) + 1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onClassroomButtonClick(ActionEvent event) {

    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("MainMenu.fxml", event);
    }

    @FXML
    void onProfileButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("Dashboard.fxml", event);
    }

    @FXML
    void onResultButtonClick(ActionEvent event) throws IOException {
//        QuizThumbController.next = "previous";
        SceneChanger.changeTo("DashboardResults.fxml", event);
    }
}
