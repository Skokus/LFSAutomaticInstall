import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("Linux From Scratch Installation Script Creator");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getIcons().add(new Image("linuxlogo.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
