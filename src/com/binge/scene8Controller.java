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


public class scene8Controller implements Initializable {
    @FXML
    private GridPane gridpane8;

    horse horse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 確保 gridpane 已經載入完成
        horse = new horse(9, 8, 10, gridpane8);

        horse.setOnMovedCallback(() -> onKnightMoved()); // 新增：當騎士移動時要做的事
    }

    public void onKnightMoved() {
        if (horse.row == 9 && horse.col == 10) {
            switchToScene9(); // 到達目標位置就轉場
        }
    }

    public void switchToScene9() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scene9.fxml"));
            Stage stage = (Stage) gridpane8.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}