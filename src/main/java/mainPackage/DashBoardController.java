package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    ImageView userimage;
    @FXML
    private Text accountType;

    @FXML
    private Text email;

    @FXML
    private Text name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        AccountInfo accountInfo = DatabaseConn.getAccountInfo(Main.email);
        accountType.setText(accountInfo.accountType);
        email.setText(accountInfo.email);
        name.setText(accountInfo.firstName + " " + accountInfo.lastName);
        Circle clip = new Circle(50,50,50);
        userimage.setClip(clip);
        if(accountInfo.dp != null) userimage.setImage(accountInfo.dp);
    }
    @FXML
    void onClassroomButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("DashboardClassrooms.fxml", event);
    }

    @FXML
    void onEditButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("EditProfileMenu.fxml", event);
    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("MainMenu.fxml", event);
    }

    @FXML
    void onResultButtonClick(ActionEvent event) throws IOException {
//        QuizThumbController.next = "result";
        SceneChanger.changeTo("DashboardResults.fxml", event);
    }
}
