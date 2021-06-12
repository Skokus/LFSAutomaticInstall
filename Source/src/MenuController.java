import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    Pane pane;
    @FXML
    TextField ch3text;
    @FXML
    TextField ch4text;
    @FXML
    TextField ch5text;
    @FXML
    TextField ch6text;
    @FXML
    TextField ch7text;
    @FXML
    TextField ch8text;

    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    public void createScript() throws MalformedURLException, InterruptedException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to save the script");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            CreateChapterThree testthree = new CreateChapterThree(Integer.parseInt(ch3text.getText()));
            CreateChapterFour testfour = new CreateChapterFour(Integer.parseInt(ch4text.getText()));
            CreateChapterFive testfive = new CreateChapterFive(Integer.parseInt(ch5text.getText()));
            CreateChapterSix testsix = new CreateChapterSix(Integer.parseInt(ch6text.getText()));
            CreateChapterSeven testseven = new CreateChapterSeven(Integer.parseInt(ch7text.getText()));
            CreateChapterEight testeight = new CreateChapterEight(Integer.parseInt(ch8text.getText()));
            ArrayList<String> instalka = testthree.createchapterthreescript();
            instalka.addAll(testfour.createchapterfourscript());
            instalka.addAll(testfive.createchapterfivescript());
            instalka.addAll(testsix.createchaptersixscript());
            instalka.addAll(testseven.createchaptersevenscript());
            instalka.addAll(testeight.createchaptereightscript());
            try {
                FileWriter myWriter = new FileWriter(file);
                for(int i = 0; i < instalka.size(); i++) {
                    myWriter.write(instalka.get(i) + "\n");
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Script Created");
            alert.setHeaderText("The Script was successfully created in the chosen destination!");
            alert.setContentText("Have fun with LFS!");
            alert.showAndWait();
        }
    }
}
