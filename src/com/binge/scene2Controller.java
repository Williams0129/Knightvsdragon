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
    time time = scene1Controller.time;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 確保 gridpane 已經載入完成
        knight = new Knight(10, 0, 10, gridpane2);

        knight.setOnMovedCallback(() -> onKnightMoved()); // 新增：當騎士移動時要做的事

        timer.setText(time.getCurrentTime()); // 取得時間並開始
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void onKnightMoved() {
        if (knight.row == 0 && knight.col == 10) {
            switchToScene3(); // 到達目標位置就轉場
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
    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e -> {
                        time.oneSecondPassed();
                        timer.setText(time.getCurrentTime());
                    }
            ));
}
