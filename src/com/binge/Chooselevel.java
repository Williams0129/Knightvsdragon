package com.binge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Chooselevel implements Initializable {

    @FXML
    private AnchorPane gridpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void switchScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) gridpane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void switchToScene1() { switchScene("scene1.fxml"); }
    @FXML public void switchToScene2() { switchScene("scene2.fxml"); }
    @FXML public void switchToScene3() { switchScene("scene3.fxml"); }
    @FXML public void switchToScene4() { switchScene("scene4.fxml"); }
    @FXML public void switchToScene5() { switchScene("scene5.fxml"); }
    @FXML public void switchToScene6() { switchScene("scene6.fxml"); }
    @FXML public void switchToScene7() { switchScene("scene7.fxml"); }
    @FXML public void switchToScene8() { switchScene("scene8.fxml"); }
    @FXML public void switchToScene9() { switchScene("scene9.fxml"); }
    @FXML public void switchToScene10() { switchScene("scene10.fxml"); }
}