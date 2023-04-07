package mainPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class StudentDashboardController {
    @FXML
    protected Button logout;
    @FXML
    public void onLogoutButtonClick(ActionEvent event) throws IOException {
        SceneChanger.changeTo("MainMenu.fxml", event);
    }
}
