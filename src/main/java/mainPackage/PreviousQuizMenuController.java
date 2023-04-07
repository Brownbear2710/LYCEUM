package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PreviousQuizMenuController implements Initializable {
    public static String classcode;
    @FXML
    private GridPane gp1;

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Main.accountType.equals("Student")) {
            ArrayList<Pair<QuizInfo, String>> arrayList = DatabaseConn.getPreviousQuiz(classcode);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            System.out.println("Current timestamp: " + formattedDateTime);

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("QuizThumbTitleBar.fxml"));
            try {
                Parent root = loader.load();
                QuizThumbController titleController = loader.getController();
                QuizThumbController.next = "previous";
                titleController.firstTitle.setText("Start Time");
                titleController.secondTitle.setText("Quiz Name");
                titleController.thirdTitle.setText("Marks");
                int x = 1, y = 1;
                gridPane.add(root, x, y);
                VBox baksho = new VBox();
//            baksho.setPrefHeight();

            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println(arrayList.size());///////////////
            for (int i = 0; i < arrayList.size(); i++) {
                QuizInfo curr = arrayList.get(i).getKey();
//            System.out.println(curr.start.toString().substring(0, curr.start.toString().length()-2));///////////////
                LocalDateTime st = LocalDateTime.parse(curr.start.toString().substring(0, curr.start.toString().length() - 2), formatter);
                LocalDateTime en = LocalDateTime.parse(curr.end.toString().substring(0, curr.end.toString().length() - 2), formatter);
                if (en.compareTo(now) < 0) {
                    loader = new FXMLLoader(Main.class.getResource("QuizThumb.fxml"));
                    try {
                        Parent root = loader.load();
                        QuizThumbController quizThumbController = loader.getController();
                        quizThumbController.quizInfo = arrayList.get(i).getKey();
                        quizThumbController.set(arrayList.get(i).getValue());
                        int x = 1, y = i + 6;
                        gp1.add(root, x, y);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            ArrayList<QuizInfo> arrayList = DatabaseConn.getAllQuizOfClassroom(classcode);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            System.out.println("Current timestamp: " + formattedDateTime);

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("QuizThumbTitleBar.fxml"));
            try {
                Parent root = loader.load();
                QuizThumbController titleController = loader.getController();
                QuizThumbController.next = "previousT";
                titleController.firstTitle.setText("Start Time");
                titleController.secondTitle.setText("Quiz Name");
                titleController.thirdTitle.setText("End Time");
                int x = 1, y = 1;
                gridPane.add(root, x, y);
                VBox baksho = new VBox();
//            baksho.setPrefHeight();

            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println(arrayList.size());///////////////
            for (int i = 0; i < arrayList.size(); i++) {
                QuizInfo curr = arrayList.get(i);
//            System.out.println(curr.start.toString().substring(0, curr.start.toString().length()-2));///////////////
                LocalDateTime st = LocalDateTime.parse(curr.start.toString().substring(0, curr.start.toString().length() - 2), formatter);
                LocalDateTime en = LocalDateTime.parse(curr.end.toString().substring(0, curr.end.toString().length() - 2), formatter);
                if (en.compareTo(now) < 0) {
                    loader = new FXMLLoader(Main.class.getResource("QuizThumb.fxml"));
                    try {
                        Parent root = loader.load();
                        QuizThumbController quizThumbController = loader.getController();
                        quizThumbController.quizInfo = arrayList.get(i);
                        quizThumbController.set();
                        int x = 1, y = i + 6;
                        gp1.add(root, x, y);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        if(Main.accountType.equals("Student"))
            SceneChanger.changeTo("ClassroomStudentView.fxml", event);
        else
            SceneChanger.changeTo("ClassroomTeacherView.fxml", event);
    }

}
