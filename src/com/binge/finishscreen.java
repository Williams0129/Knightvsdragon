package com.binge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class finishscreen implements Initializable {
    @FXML
    Button chooselevel, exit;
    @FXML
    AnchorPane panee;
    @FXML
    Text timer;

    public static time time = scene10Controller.time;

    public void initialize(URL location, ResourceBundle resources) {
        timer.setText(time.getCurrentTime());

        Timeline updateDisplay = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timer.setText(time.getCurrentTime());
                })
        );
        updateDisplay.setCycleCount(Timeline.INDEFINITE);
        updateDisplay.stop();
    }

    public void Chooselevel(javafx.event.ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Chooselevel.fxml"));
            Stage stage = (Stage) panee.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void gameQuit(javafx.event.ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}