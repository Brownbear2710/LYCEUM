package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuestionCreateController implements Initializable {
    static String quizID, quizName;
    static LocalDateTime start, end;
    private RadioButton[] radioButton;
    private ArrayList<Question> questions;
    private TextArea[] option;
    private int indx;
    @FXML
    Button prevButton;
    @FXML
    private Label exceptionLabel;

    @FXML
    private Button backButton;

    @FXML
    private TextArea opt1;

    @FXML
    private TextArea opt2;

    @FXML
    private TextArea opt3;

    @FXML
    private TextArea opt4;

    @FXML
    private ToggleGroup optionssss;

    @FXML
    private TextArea questionBox;

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
        radioButton = new RadioButton[]{rButton1, rButton2, rButton3, rButton4};
        questions = new ArrayList<>();
        questions.add(new Question());
        prevButton.setDisable(true);
        indx = 0;
        option = new TextArea[]{opt1, opt2, opt3, opt4};
    }

    boolean isEmpty()
    {
        if(
            opt1.getText().isEmpty() ||
            opt2.getText().isEmpty() ||
            opt3.getText().isEmpty() ||
            opt4.getText().isEmpty() ||
            questionBox.getText().isEmpty() ||
            !(rButton1.isSelected()||
            rButton2.isSelected()||
            rButton3.isSelected()||
            rButton4.isSelected())
        )
            return true;
        else
            return false;
    }
    
    private Question getQuestionAnsOptions()
    {
        Question q = new Question();
        q.option[0] = opt1.getText();
        q.option[1] = opt2.getText();
        q.option[2] = opt3.getText();
        q.option[3] = opt4.getText();
        q.question = questionBox.getText();
        if(rButton1.isSelected()) q.answer = q.option[0];
        if(rButton2.isSelected()) q.answer = q.option[1];
        if(rButton3.isSelected()) q.answer = q.option[2];
        if(rButton4.isSelected()) q.answer = q.option[3];
        return q;
    }

    private void setCurrentIndex()
    {
        Question q = questions.get(indx);
        questionBox.setText(q.question);
        opt1.setText(q.option[0]);
        opt2.setText(q.option[1]);
        opt3.setText(q.option[2]);
        opt4.setText(q.option[3]);
        if(q.answer.isEmpty())
        {
            rButton1.setSelected(false);
            rButton2.setSelected(false);
            rButton3.setSelected(false);
            rButton4.setSelected(false);
        }
        else {
            if (q.answer.equals(opt1.getText())) rButton1.setSelected(true);
            if (q.answer.equals(opt2.getText())) rButton2.setSelected(true);
            if (q.answer.equals(opt3.getText())) rButton3.setSelected(true);
            if (q.answer.equals(opt4.getText())) rButton4.setSelected(true);
        }
    }

    private boolean saveCurrentIndex()
    {
        if(isEmpty())
        {
            exceptionLabel.setText("No field can be empty");
            return false;
        }
        Question q = getQuestionAnsOptions();
        questions.set(indx, q);
        return true;
    }
    @FXML
    void nextButtonClick(ActionEvent event) {
        exceptionLabel.setText("");
        if(!saveCurrentIndex()) return;
        System.out.println("Here");
        if(indx == questions.size()-1) {
            questions.add(new Question());
            indx++;
            setCurrentIndex();
        }
        else
        {
            indx++;
            setCurrentIndex();
        }
        if(indx > 0) prevButton.setDisable(false);
    }

    @FXML
    void prevButtonClick(ActionEvent event) {
        exceptionLabel.setText("");
        if(indx == 0) ;// Do some random stuff
        else
        {
            if(indx == questions.size()-1)
            {
                if(!isEmpty())
                {
                    saveCurrentIndex();
                    questions.add(new Question());
                }
            }
            else if(!saveCurrentIndex()) return;
            indx--;
            setCurrentIndex();
        }
        if(indx == 0)  prevButton.setDisable(true);
    }

    @FXML
    public void onClearButtonClick(ActionEvent event)
    {
        if(indx != questions.size()-1)
        {
            questionBox.setText("");
            opt1.setText("");
            opt2.setText("");
            opt3.setText("");
            opt4.setText("");
            rButton1.setSelected(false);
            rButton2.setSelected(false);
            rButton3.setSelected(false);
            rButton4.setSelected(false);
            exceptionLabel.setText("");
            questions.remove(indx);
            setCurrentIndex();
        }
    }
    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("DashboardClassrooms.fxml", event);
    }
    void print(int i)
    {
        Question q = questions.get(i);
        System.out.println("Size: " + questions.size());
        System.out.println("Q: " + q.question);
        System.out.println("1: " + q.option[0]);
        System.out.println("1: " + q.option[1]);
        System.out.println("1: " + q.option[2]);
        System.out.println("1: " + q.option[3]);
        System.out.println("Answer:" + q.answer);
    }

    boolean noQuestion() {
        if(questions.size() > 1)
            return false;
        return isEmpty();
    }
    @FXML
    void onFinishButtonClick(ActionEvent event) throws IOException {
        if(noQuestion()) {
            exceptionLabel.setText("At least 1 question required");
            return;
        }
        DatabaseConn.addQuiz(quizID, quizName, ClassroomTeacherViewController.Classcode, start, end);
        for(int i = 0; i < questions.size(); i++)
        {
            Question q = questions.get(i);
            if(!(q.answer == null || q.answer.length() == 0))
                DatabaseConn.addQuestion(q.question, q.option[0], q.option[1], q.option[2], q.option[3], q.answer, quizID);
        }
        Question q = getQuestionAnsOptions();
        if(indx == questions.size()-1 && !(q.answer == null || q.answer.length() == 0)) {
            DatabaseConn.addQuestion(q.question, q.option[0], q.option[1], q.option[2], q.option[3], q.answer, quizID);
        }
        SceneChanger.changeTo("ClassroomTeacherView.fxml", event);
    }
}
