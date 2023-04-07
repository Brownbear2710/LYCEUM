package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeacherResultViewController implements Initializable {

    @FXML
    Button mailButton;
    @FXML
    Label exceptionLabel;
    @FXML
    Label quizNameLabel;
    @FXML
    Label timeLabel;

    public static  String previous;
    public static ClassQuizResult r;
    public static QuizInfo quizInfo;
    public static ArrayList<String> emailList;
    public static ArrayList<String> nameList;

    public static ArrayList<String> markList;
    @FXML
    private GridPane realG;
    @FXML
    private GridPane titleGridpane;
    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        if(previous.equals("resultT"))
        {
            SceneChanger.changeTo("DashboardResults.fxml",event);
        }
        else if(previous.equals("previousT"))
        {
            SceneChanger.changeTo("PreviousQuizMenu.fxml",event);
        }
    }

    @FXML
    void mailResultClick(ActionEvent event) {
        if(!MailService.InternetAccess()) {
            exceptionLabel.setText("Check your internet connection");
            return;
        }
        mailButton.setDisable(true);
        exceptionLabel.setText("Marks mailed to All Students");
        PublishResult pr = new PublishResult(emailList,markList, quizInfo);
        Thread t = new Thread(pr);
        t.start();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mailButton.setDisable(false);
        exceptionLabel.setText("");
        quizNameLabel.setText("Quiz Name :     " + quizInfo.name);
        /////////////////////////////////////////////////////////////////////////////////
        // Initialize the marks name and email using database fucntion
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime st = LocalDateTime.parse(quizInfo.start.toString().substring(0, quizInfo.start.toString().length()-2), formatter);
        String s = QuizThumbController.getForamttedDateAndTime(st.toString());
        timeLabel.setText("Started       :     " + s);
        try {
            r =  DatabaseConn.getResultInfo(quizInfo);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        nameList = r.nameList;
        markList = r.markList;
        emailList = r.emailList;
        /////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////////////////////////
        // Load the titlebar into upper gridpane
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("TeacherResultViewTitleBar.fxml"));
        try {

            Parent root = loader.load();
            titleGridpane.add(root,1,1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        /////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////
        // sz is equal for email, name and marklist
        int sz = emailList.size();
        /////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////
        // load all info into bottom gridpane
        for (int i = 0; i<sz; i++)
        {
            try {
                loader = new FXMLLoader(Main.class.getResource("TeacherResultViewThumb.fxml"));
                Parent root = loader.load();

                TeacherResultViewThumbController t = loader.getController();
                AccountInfo accountInfo = DatabaseConn.getAccountInfo(emailList.get(i));
                if(accountInfo.dp != null)
                    t.userImage.setImage(accountInfo.dp);
                t.set(nameList.get(i), markList.get(i));
                realG.add(root,1,i+2);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
    }
}
