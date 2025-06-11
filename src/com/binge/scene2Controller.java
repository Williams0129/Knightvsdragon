package com.binge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class scene2Controller implements Initializable {
    @FXML
    private GridPane gridpane2;
    @FXML
    Text timer;
    Knight knight;
    public static time time = scene1Controller.time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        knight = new Knight(10, 0, 10, gridpane2);
        knight.setOnMovedCallback(() -> onKnightMoved());

        timer.setText(time.getCurrentTime());

        Timeline updateDisplay = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timer.setText(time.getCurrentTime());
                })
        );
        updateDisplay.setCycleCount(Timeline.INDEFINITE);
        updateDisplay.play();
    }

    public void onKnightMoved() {
        if (knight.row == 0 && knight.col == 10) {
            switchToScene3();
        }
    }

    public void switchToScene3() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scene3.fxml"));
            Stage stage = (Stage) gridpane2.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
