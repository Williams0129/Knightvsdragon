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


public class scene10Controller implements Initializable {
    @FXML
    private GridPane gridpane10;
    @FXML
    Text timer;

    horse horse;
    public static time time = scene9Controller.time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 確保 gridpane 已經載入完成
        horse = new horse(10, 0, 10, gridpane10);

        horse.setOnMovedCallback(this::onKnightMoved); // 新增：當騎士移動時要做的事
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
        if (horse.row == 0 && horse.col == 10) {
            switchToScene11(); // 到達目標位置就轉場
        }
    }

    public void switchToScene11() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("finishscreen.fxml"));
            Stage stage = (Stage) gridpane10.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}