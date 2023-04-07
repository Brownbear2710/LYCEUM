package mainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static String email, firstName, lastName, password, accountType;
    public static int otp;
    public static void run(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader  root = new FXMLLoader(Main.class.getResource("EditProfileMenu.fxml"));
        FXMLLoader  root = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(root.load());
        Image icon = new Image("Asset_1.png");
        stage.getIcons().add(icon);
        stage.setTitle("Lyceum");
        stage.setScene(scene);
        stage.setMinHeight(700);
        stage.setMinWidth(1000);
        stage.show();
    }

}