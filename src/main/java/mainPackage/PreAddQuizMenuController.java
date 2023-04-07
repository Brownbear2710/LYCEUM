package mainPackage;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PreAddQuizMenuController implements Initializable {
    private static String quizID;

    @FXML
    private DatePicker endingDate;
    @FXML
    private ComboBox<String> endingHour;

    @FXML
    private ComboBox<String> endingMinute;

    @FXML
    private Label exceptionLabel;

    @FXML
    private TextField quizName;

    @FXML
    private DatePicker startingDate;

    @FXML
    private ComboBox<String> startingHour;

    @FXML
    private ComboBox<String> startingMinute;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        endingDate.setEditable(false);
        startingDate.setEditable(false);
        String [] hr = new String[24];
        String [] mnt = new String[60];
        for(int i = 0; i < 24; i++) {
            hr[i] = Integer.toString(i);
            if(i < 10) hr[i] = "0" + hr[i];
        }
        for(int i = 0;  i< 60; i++) {
            mnt[i] = Integer.toString(i);
            if(i < 10) mnt[i] = "0" + mnt[i];
        }
        startingHour.setItems(FXCollections.observableArrayList(hr));
        endingHour.setItems(FXCollections.observableArrayList(hr));
        startingMinute.setItems(FXCollections.observableArrayList(mnt));
        endingMinute.setItems(FXCollections.observableArrayList(mnt));
    }

    private boolean isEmpty()
    {
        if(
           quizName.getText().length() == 0 ||
           startingHour.getValue() == null ||
           endingHour.getValue() == null ||
           startingMinute.getValue() == null ||
           endingMinute.getValue() == null ||
           startingDate.getValue() == null||
           endingDate.getValue() == null) return true;
        return false;
    }

    private String gibMeTheDate(Date dateObject) {
        LocalDate localDate = dateObject.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // replace with your desired format
        String dateString = ((LocalDate) localDate).format(formatter);
        return dateString;
    }
    @FXML
    void onProceedButtonClick(ActionEvent event) throws IOException {
        if(isEmpty()) exceptionLabel.setText("No field can be empty!!");
        else
        {
            // Set quizID and goto quiz menu
            Date sd = Date.valueOf(startingDate.getValue());
            String SD = gibMeTheDate(sd);
            String ST = startingHour.getValue() + ":" + startingMinute.getValue() + ":00";
            String S = SD + " " + ST;
            System.out.println(S);///////////////////////////////////////////

            Date ed = Date.valueOf(endingDate.getValue());
            String ED = gibMeTheDate(ed);
            String ET = endingHour.getValue() + ":" + endingMinute.getValue() + ":00";
            String E = ED + " " + ET;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime LDTStart = LocalDateTime.parse(S, formatter);
            LocalDateTime LDTEnd = LocalDateTime.parse(E, formatter);


            System.out.println(E);///////////////////////////////

            if(LDTStart.compareTo(LDTEnd) >= 0)
            {
                exceptionLabel.setText("Start time must be before end time");
                return;
            }
            else if(LDTStart.compareTo(LocalDateTime.now()) <= 0)
            {
                exceptionLabel.setText("Quiz cannot start in the past!!");
                return;
            }
            else exceptionLabel.setText("");
            String QuizName = quizName.getText();
            /// Continue From Here !!!
            quizID = AddClassroomMenuController.generateClasscode();
            QuestionCreateController.quizID = quizID;
            while(DatabaseConn.quizIDFound(quizID)) {
                quizID = AddClassroomMenuController.generateClasscode();
            }
            System.out.println(quizID);
            QuestionCreateController.quizID = quizID;
            QuestionCreateController.quizName = QuizName;
            QuestionCreateController.start = LDTStart;
            QuestionCreateController.end = LDTEnd;
            SceneChanger.changeTo("QuizCreate.fxml", event);
//            SceneChanger.changeTo("QuizCreate.fxml", event);
        }
    }
    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("ClassroomTeacherView.fxml", event);
    }
}

