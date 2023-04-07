package mainPackage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class TeacherResultViewThumbController {

    public static String previous;
    @FXML
    public ImageView userImage;
    @FXML Label nameTitle;
    @FXML Label markTitle;
    @FXML
    private Label markLabel;
    @FXML
    private Label nameLabel;
    void set(String name, String marks) {

        nameLabel.setText(name);
        markLabel.setText(marks);
        Circle circle = new Circle(35,35,35);
        userImage.setClip(circle);
    }

    void setTitle(String Name, String Mark)
    {
        nameTitle.setText(Name);
        markTitle.setText(Mark);
    }


}
