package mainPackage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class ClassroomAddController {
    @FXML
    Label label;
    @FXML
    void onAddButtonClick(MouseEvent event) throws IOException {
        if(Objects.equals(Main.accountType, "Teacher"))
            SceneChanger.changeTo("AddClassroomMenu.fxml", label);
        else
            SceneChanger.changeTo("JoinClassroomMenu.fxml", label);
    }
}
