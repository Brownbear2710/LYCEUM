package mainPackage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class QuizThumbController {

    static String next;
    @FXML
    public Label thirdTitle;

    @FXML
    public Label firstTitle;

    @FXML
    public Label secondTitle;

    QuizInfo quizInfo;
    @FXML
    private HBox quizThumbHBox;

    @FXML
    private Label dateBox;

    @FXML
    private Label markBox;

    @FXML
    private Label timeBox;

    @FXML
    private Label titleBox;

    private String IDofQuiz;

    public void set(String date, String title, String time, String mark, String quizID)
    {
        dateBox.setText(date);
        titleBox.setText(title);
        timeBox.setText(time);
        if(!mark.equals("")) {
            markBox.setText(mark);
        }
        IDofQuiz = quizID;
    }

    public static String getForamttedDateAndTime(String s)
    {
        String date = new String(), time = new String();
        int i = 0;
        while(s.charAt(i) != 'T')
        {
            date += s.charAt(i);
            i++;
        }
        i++;
        while (i < s.length())
        {
            time += s.charAt(i);
            i++;
        }
        return date + " " + time;
    }

    public void set() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime st = LocalDateTime.parse(quizInfo.start.toString().substring(0, quizInfo.start.toString().length()-2), formatter);
        LocalDateTime en = LocalDateTime.parse(quizInfo.end.toString().substring(0, quizInfo.end.toString().length()-2), formatter);
        String s = getForamttedDateAndTime(st.toString());
        String e = getForamttedDateAndTime(en.toString());
        dateBox.setText(s);
        titleBox.setText(quizInfo.name);
        timeBox.setText(e);
        IDofQuiz = quizInfo.id;
    }

    public void set(String Marks)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime st = LocalDateTime.parse(quizInfo.start.toString().substring(0, quizInfo.start.toString().length()-2), formatter);
        LocalDateTime en = LocalDateTime.parse(quizInfo.end.toString().substring(0, quizInfo.end.toString().length()-2), formatter);
        String s = getForamttedDateAndTime(st.toString());
        String e = getForamttedDateAndTime(en.toString());
        dateBox.setText(s);
        titleBox.setText(quizInfo.name);
        ArrayList<Question> arrayList = DatabaseConn.getQuizQuestions(quizInfo.id);
        timeBox.setText(Marks + "/" + Integer.toString(arrayList.size()));
        IDofQuiz = quizInfo.id;
    }

    public long getTimeToEnd() {
        long duration;
        Timestamp newTS = Timestamp.valueOf(quizInfo.end.toLocalDateTime());
        Instant isnt = newTS.toInstant();
        Instant current = Instant.now();
        Duration d = Duration.between(current,isnt);
        duration = d.getSeconds();
        return duration;
    }
    public long getTimeToStart() {
        long duration;
        Timestamp newTS = Timestamp.valueOf(quizInfo.start.toLocalDateTime());
        Instant isnt = newTS.toInstant();
        Instant current = Instant.now();
        Duration d = Duration.between(current,isnt);
        duration = d.getSeconds();
        return duration;
    }

    @FXML
    public void quizSelected(MouseEvent mouseEvent) throws IOException {
//        SceneChanger.changeTo("MainMenu.fxml",baksho);
        if(next.equals("upcoming")) {
            WaitingRoomController.qInfo = quizInfo;
            System.out.println(getTimeToStart());/////////////
            System.out.println(getTimeToEnd());//////////////
            if (getTimeToEnd() > 0) {
                if (getTimeToStart() <= 0) {
                    QuizAnswerController.quizInfo = quizInfo;
                    SceneChanger.changeTo("QuizAnswer.fxml", quizThumbHBox);
                }
                else
                    WaitingRoomController.qInfo = quizInfo;
                    SceneChanger.changeTo("WaitingRoom.fxml", quizThumbHBox);
            }
            else ;
        }
        else if(next.equals("previous") || next.equals("result")) {
            QuizViewAnswerController.quizInfo = quizInfo;
            QuizViewAnswerController.whereBack = next;
            SceneChanger.changeTo("QuizViewAnswer.fxml", quizThumbHBox);
        }
        else if(next.equals("upcomingT")) {

        }
        else if(next.equals("previousT")||next.equals("resultT")) {
            TeacherResultViewController.quizInfo = quizInfo;
            TeacherResultViewController.previous = next;
            SceneChanger.changeTo("TeacherResultView.fxml", quizThumbHBox);
        }
    }
}

