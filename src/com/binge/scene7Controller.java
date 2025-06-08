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

public class scene7Controller implements Initializable {
    @FXML
    private GridPane gridpane7;

    horse horse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 確保 gridpane 已經載入完成
        horse = new horse(4, 5, 10, gridpane7);

        horse.setOnMovedCallback(() -> onKnightMoved()); // 新增：當騎士移動時要做的事
    }

    public void onKnightMoved() {
        if (horse.row == 10 && horse.col == 0) {
            switchToScene8(); // 到達目標位置就轉場
        }
    }

    public void switchToScene8() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scene8.fxml"));
            Stage stage = (Stage) gridpane7.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
