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

public class scene4Controller implements Initializable {
    @FXML
    private GridPane gridpane4;

    Knight knight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 確保 gridpane 已經載入完成
        knight = new Knight(10, 0, 10, gridpane4);

        knight.setOnMovedCallback(() -> onKnightMoved()); // 新增：當騎士移動時要做的事
    }

    public void onKnightMoved() {
        if (knight.row == 4 && knight.col == 10) {
            switchToScene5(); // 到達目標位置就轉場
        }
    }

    public void switchToScene5() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scene5.fxml"));
            Stage stage = (Stage) gridpane4.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}