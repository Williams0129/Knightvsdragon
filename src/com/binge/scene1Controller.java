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

public class scene1Controller implements Initializable {
    @FXML
    private GridPane gridpane1;

    Knight knight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 確保 gridpane 已經載入完成
        knight = new Knight(6, 0, 6, gridpane1);

        knight.setOnMovedCallback(() -> onKnightMoved()); // 新增：當騎士移動時要做的事
    }

    public void onKnightMoved() {
        if (knight.row == 0 && knight.col == 6) {
            switchToScene2(); // 到達目標位置就轉場
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
