package com.binge;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class scene10Controller implements Initializable {
    @FXML
    private GridPane gridpane10;

    horse horse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 確保 gridpane 已經載入完成
        horse = new horse(10, 0, 10, gridpane10);

        horse.setOnMovedCallback(() -> onKnightMoved()); // 新增：當騎士移動時要做的事
    }

    public void onKnightMoved() {
        if (horse.row == 0 && horse.col == 10) {
            switchToScene11(); // 到達目標位置就轉場
        }
    }

    public void switchToScene11() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scene11.fxml"));
            Stage stage = (Stage) gridpane10.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
