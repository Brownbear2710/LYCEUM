package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileMenuController implements Initializable {

    File file;
    int cf;

    @FXML
    ImageView userimage;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label label;
    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField oldPassword;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountInfo accountInfo = DatabaseConn.getAccountInfo(Main.email);
        firstname.setText(accountInfo.firstName);
        lastname.setText(accountInfo.lastName);
        if(accountInfo.dp != null) {
            Circle clip = new Circle(50,50,50);
            userimage.setFitWidth(100);
            userimage.setFitHeight(100);
            userimage.setClip(clip);
            userimage.setImage(accountInfo.dp);
        }
    }
    @FXML
    void OnSaveButtonClick(ActionEvent event) throws FileNotFoundException {
        String FirstName = firstname.getText();
        String LastName = lastname.getText();
        String NewPassword = newPassword.getText();
        String ConfirmPassword = confirmPassword.getText();
        String OldPassword = oldPassword.getText();
        if(FirstName.length() == 0 || LastName.length() == 0)
        {
            label.setText("First Name and Last Name cannot be empty");
        }
        else if((NewPassword.length() == 0 && ConfirmPassword.length() != 0) ||( NewPassword.length() != 0 && ConfirmPassword.length() == 0) )
        {
            label.setText("New Passwords don't match");
        }
        else if(NewPassword.length() != 0 && ConfirmPassword.length() != 0 && !NewPassword.equals(ConfirmPassword))
        {
            label.setText("New Passwords don't match");
        }
        else if(NewPassword.length() != 0 && ConfirmPassword.length() != 0 && NewPassword.length() < 8)
        {
            label.setText("Password should be at least 8 characters");
        }
        else if(DatabaseConn.loginValidation(Main.email, DatabaseConn.hash(OldPassword)) == 0)
        {
            label.setText("Password is incorrect");
        }
        else {
            DatabaseConn.updateName(FirstName, LastName, Main.email);
            if(NewPassword.length() != 0) DatabaseConn.updatePassword(NewPassword);
            AccountInfo accountInfo = DatabaseConn.getAccountInfo(Main.email);
            Main.firstName = accountInfo.firstName;
            Main.lastName = accountInfo.lastName;
            if(file != null) {
                DatabaseConn.updateImage(file);
            }
            label.setText("Update Successful!");
            newPassword.setText("");
            oldPassword.setText("");
            confirmPassword.setText("");
        }
    }

    @FXML
    void onBackButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("Dashboard.fxml", event);
    }

    @FXML
    void onImageClicked(MouseEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            Circle clip = new Circle(50,50,50);
            userimage.setFitHeight(100);
            userimage.setFitWidth(100);
            userimage.setClip(clip);
            userimage.setImage(image);
            cf = 1;
        } else {
            cf = 0;
        }
    }
}
