package mainPackage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static javafx.util.Duration.seconds;

public class QuizAnswerController implements Initializable {


    @FXML
    private Label timerLabel;
    Timeline timeline;
    @FXML
    private Text quizNameBox;
    @FXML
    TextArea qBox;
    @FXML TextArea opt1;
    @FXML TextArea opt2;
    @FXML TextArea opt3;
    @FXML TextArea opt4;

    @FXML RadioButton rButton1;
    @FXML RadioButton rButton2;
    @FXML RadioButton rButton3;
    @FXML RadioButton rButton4;
    @FXML
    Button prevButton;
    @FXML
    Button nextButton;

    @FXML
    VBox myVbox;
    @FXML
    HBox myHBox;

    int indx;
    int sz;
    String[] answerList;
    ArrayList<Question> qArr;
    public static QuizInfo quizInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConn.updateMark(quizInfo.id, Main.email, "0");
        quizNameBox.setText(quizInfo.name);
        qArr = DatabaseConn.getQuizQuestions(quizInfo.id);
        for(Question q : qArr)
            q.shuffle();
        shuffleQuestions();
        sz = qArr.size();
        answerList = new String[sz];

        prevButton.setDisable(true);
        if(sz == 1)
            nextButton.setDisable(true);

        loadCurrentIndx();

        timeline = new Timeline(new KeyFrame(seconds(1), new EventHandler<ActionEvent>() {

            long timeRemaining= getDuration();


            @Override
            public void handle(ActionEvent event) {
                // Decrement Time by 1 second
                timeRemaining--;
                int hours = (int) (timeRemaining / 3600);
                int minutes = (int) ((timeRemaining / 60) % 60);
                int seconds = (int) (timeRemaining % 60);
                timerLabel.setText( "Time Remaining: " + String.format("%02d:%02d:%02d",hours, minutes, seconds));
                if(timeRemaining <= 0)
                {
                    try {
                        submitButtonClick(event);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @FXML
    private void prevButtonClicked(ActionEvent e)
    {
        nextButton.setDisable(false);
        getAns();
        indx--;
        loadCurrentIndx();
        if(indx==0)
            prevButton.setDisable(true);
    }

    @FXML
    private void nexButtonClicked(ActionEvent e)
    {
        prevButton.setDisable(false);
        getAns();
        indx++;
        loadCurrentIndx();
        if (indx == sz - 1)
            nextButton.setDisable(true);
    }
    void loadCurrentIndx()
    {
        Question q = qArr.get(indx);
        qBox.setText(q.question);
        opt1.setText(q.option[0]);
        opt2.setText(q.option[1]);
        opt3.setText(q.option[2]);
        opt4.setText(q.option[3]);

        rButton1.setSelected(false);
        rButton2.setSelected(false);
        rButton3.setSelected(false);
        rButton4.setSelected(false);

        if(answerList[indx] != null)
        {
            if(answerList[indx].equals(opt1.getText())) rButton1.setSelected(true);
            if(answerList[indx].equals(opt2.getText())) rButton2.setSelected(true);
            if(answerList[indx].equals(opt3.getText())) rButton3.setSelected(true);
            if(answerList[indx].equals(opt4.getText())) rButton4.setSelected(true);
        }
    }

    public static long getDuration() {
        long duration;
        Timestamp newTS = Timestamp.valueOf(quizInfo.end.toLocalDateTime());
        Instant instant = newTS.toInstant();
        Instant current = Instant.now();
        Duration d = Duration.between(current,instant);
        duration = d.getSeconds();
//        duration = 20;////////////////////
        return duration;
    }
    private void getAns()
    {
        if(rButton1.isSelected()) answerList[indx] = opt1.getText();
        if(rButton2.isSelected()) answerList[indx] = opt2.getText();
        if(rButton3.isSelected()) answerList[indx] = opt3.getText();
        if(rButton4.isSelected()) answerList[indx] = opt4.getText();
    }

    @FXML
    void submitButtonClick(ActionEvent e)throws IOException {
        timeline.stop();
        getAns();
        for (String ans: answerList)
        {
            System.out.println(ans);
        }

        int marks = 0;

        for (int i = 0; i< sz; i++)
        {
            if( answerList[i]!=null && answerList[i].equals(qArr.get(i).answer))
                marks++;
        }

        DatabaseConn.updateMark(quizInfo.id, Main.email, Integer.toString(marks));
        UpComingQuizMenuController.classcode = quizInfo.classcode;
        SceneChanger.changeTo("UpComingQuizMenu.fxml", timerLabel);
    }
    void shuffleQuestions()
    {
        Collections.shuffle(qArr);
    }

}
