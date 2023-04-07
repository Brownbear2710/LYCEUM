package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardResultsController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        if(Main.accountType.equals("Student")) {
            QuizThumbController.next = "result";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("QuizThumbTitleBar.fxml"));
            try {
                Parent root = loader.load();
                QuizThumbController q = loader.getController();
                q.firstTitle.setText("Start Time");
                q.thirdTitle.setText("Marks");
                int x = 1, y = 1;
                gridPane.add(root, x, y);

            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<Pair<QuizInfo, String>> arrayList = DatabaseConn.getAllPrevQuizForStudent();

            for (int i = 0; i < arrayList.size(); i++) {
                QuizInfo curr = arrayList.get(i).getKey();
                LocalDateTime st = LocalDateTime.parse(curr.start.toString().substring(0, curr.start.toString().length() - 2), formatter);
                LocalDateTime en = LocalDateTime.parse(curr.end.toString().substring(0, curr.end.toString().length() - 2), formatter);
                if (en.compareTo(now) < 0) {
                    try {
                        loader = new FXMLLoader(Main.class.getResource("QuizThumb.fxml"));
                        Parent root = loader.load();
                        QuizThumbController q = loader.getController();
                        q.quizInfo = arrayList.get(i).getKey();
                        q.set(arrayList.get(i).getValue());
                        int x = 1, y = i + 6;
                        gridPane.add(root, x, y);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            QuizThumbController.next = "resultT";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("QuizThumbTitleBar.fxml"));
            try {
                Parent root = loader.load();
                QuizThumbController q = loader.getController();
                q.firstTitle.setText("Start Time");
                q.thirdTitle.setText("Marks");
                int x = 1, y = 1;
                gridPane.add(root, x, y);

            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<QuizInfo> arrayList = DatabaseConn.getAllQuizTeacher(Main.email);

            for (int i = 0; i < arrayList.size(); i++) {
                QuizInfo curr = arrayList.get(i);
                LocalDateTime st = LocalDateTime.parse(curr.start.toString().substring(0, curr.start.toString().length() - 2), formatter);
                LocalDateTime en = LocalDateTime.parse(curr.end.toString().substring(0, curr.end.toString().length() - 2), formatter);
                if (en.compareTo(now) < 0) {
                    try {
                        loader = new FXMLLoader(Main.class.getResource("QuizThumb.fxml"));
                        Parent root = loader.load();
                        QuizThumbController q = loader.getController();
                        q.quizInfo = arrayList.get(i);
                        q.set();
                        int x = 1, y = i + 6;
                        gridPane.add(root, x, y);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
    @FXML
    void onClassroomButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("DashboardClassrooms.fxml", event);
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
    void onQuizButtonClick(ActionEvent event) {

    }
}
