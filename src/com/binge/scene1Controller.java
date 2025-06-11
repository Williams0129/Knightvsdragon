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

public class scene1Controller implements Initializable {
    @FXML
    private GridPane gridpane1;
    @FXML
    Text timer;

    Knight knight;
    public static time time = MenuController.time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        knight = new Knight(6, 0, 6, gridpane1);
        knight.setOnMovedCallback(() -> onKnightMoved());

        // 只更新畫面，不再呼叫 oneSecondPassed()
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
        if (knight.row == 0 && knight.col == 6) {
            switchToScene2();
        }
    }

    public void switchToScene2() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
            Stage stage = (Stage) gridpane1.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
