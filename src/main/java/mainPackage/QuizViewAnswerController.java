package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuizViewAnswerController implements Initializable {

    int indx;
    int sz;
    ArrayList<Question> qArr;
    public static QuizInfo quizInfo;
    public static String whereBack;
    @FXML
    private Button backButton;

    @FXML
    private ToggleGroup buttonToggleGroup;

    @FXML
    private Button nextButton;

    @FXML
    private TextArea opt1;

    @FXML
    private TextArea opt2;

    @FXML
    private TextArea opt3;

    @FXML
    private TextArea opt4;

    @FXML
    private Button prevButton;

    @FXML
    private TextArea qBox;

    @FXML
    private RadioButton rButton1;

    @FXML
    private RadioButton rButton2;

    @FXML
    private RadioButton rButton3;

    @FXML
    private RadioButton rButton4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        qArr = DatabaseConn.getQuizQuestions(quizInfo.id);
        sz = qArr.size();

        prevButton.setDisable(true);
        if(sz == 1)
            nextButton.setDisable(true);

        loadCurrentIndx();
    }

    @FXML
    void nexButtonClicked(ActionEvent event) {
        prevButton.setDisable(false);
        indx++;
        loadCurrentIndx();
        if (indx == sz - 1)
            nextButton.setDisable(true);
    }

    @FXML
    void prevButtonClicked(ActionEvent event) {
        nextButton.setDisable(false);
        indx--;
        loadCurrentIndx();
        if(indx==0)
            prevButton.setDisable(true);
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

        opt1.getStyleClass().clear();
        opt2.getStyleClass().clear();
        opt3.getStyleClass().clear();
        opt4.getStyleClass().clear();
        opt1.getStyleClass().add("text-area");
        opt2.getStyleClass().add("text-area");
        opt3.getStyleClass().add("text-area");
        opt4.getStyleClass().add("text-area");


        if(qArr.get(indx).answer.equals(opt1.getText())) opt1.getStyleClass().add("AnswerTextArea");
        else if(qArr.get(indx).answer.equals(opt2.getText())) opt2.getStyleClass().add("AnswerTextArea");
        else if(qArr.get(indx).answer.equals(opt3.getText())) opt3.getStyleClass().add("AnswerTextArea");
        else if(qArr.get(indx).answer.equals(opt4.getText())) opt4.getStyleClass().add("AnswerTextArea");
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        if(whereBack.equals("previous"))
            SceneChanger.changeTo("PreviousQuizMenu.fxml", event);
        else if(whereBack.equals("result"))
            SceneChanger.changeTo("DashboardResults.fxml", event);
    }
}