package mainPackage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;

import static javafx.util.Duration.seconds;

public class WaitingRoomController implements Initializable {

    public static QuizInfo qInfo;

    @FXML
    VBox myVBox;

    @FXML
    Label quizName;

    private Timeline timeline;
    @FXML
    private Label timerLabel;

    public static long getDuration() {
        long duration;
        Timestamp newTS = Timestamp.valueOf(qInfo.start.toLocalDateTime());
        Instant instant = newTS.toInstant();
        Instant current = Instant.now();
        Duration d = Duration.between(current,instant);
        duration = d.getSeconds();
        duration = 20;////////////////////
        return duration;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizName.setText( "Quiz Name: " + qInfo.name);
        timeline = new Timeline(new KeyFrame(seconds(1), new EventHandler<ActionEvent>() {

            long timeRemaining= getDuration();


            @Override
            public void handle(ActionEvent event) {
                // Decrement Time by 1 second
                timeRemaining--;
                int hours = (int) (timeRemaining / 3600);
                int minutes = (int) ((timeRemaining / 60) % 60);
                int seconds = (int) (timeRemaining % 60);
                System.out.println(timeRemaining);/////////////////////////////////////
                if(timeRemaining <= 0)
                {
                    try {
                        QuizAnswerController.quizInfo = qInfo;
                        SceneChanger.changeTo("QuizAnswer.fxml", myVBox);
                        timeline.stop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                timerLabel.setText(String.format("%02d:%02d:%02d",hours, minutes, seconds));
            }
        }));
        timeline.setCycleCount((int) getDuration());
        timeline.play();
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        timeline.stop();
        SceneChanger.changeTo("UpComingQuizMenu.fxml", event);
    }
}
