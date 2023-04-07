package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException { // New Scene in old window
        SceneChanger.changeTo("LoginMenu.fxml", event);
    }
    @FXML
    protected void onSignUpButtonClick(ActionEvent event) throws IOException
    {
        SceneChanger.changeTo("SignUpMenu.fxml", event);
    }
    @FXML void onExitButtonClick(ActionEvent event) throws  IOException
    {
        Node oldButton = (Node) event.getSource();
        Stage myStage = (Stage) oldButton.getScene().getWindow();
        myStage.close();
    }
}
