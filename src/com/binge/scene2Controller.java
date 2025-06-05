package com.binge;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class scene2Controller implements Initializable {
    @FXML
    private GridPane gridpane2;

    Knight knight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 確保 gridpane 已經載入完成
        knight = new Knight(10, 0, 10, gridpane2);
    }
}
