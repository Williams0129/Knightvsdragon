package com.binge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {//啟動時，系統自動建立一個主要的Stage並傳入start中
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml")); // 載入 FXML 文件;getClass獲得本地class，getResource以本類別為根目錄尋照資料夾內的fxml的網址
        primaryStage.setTitle("Knight vs dragon");
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene(); // 設定視窗大小
        primaryStage.show(); // 顯示視窗
        // 播放背景音樂，只會播放一次
        String musicPath = "src/com/binge/medievalSong.wav"; // 請根據你的實際路徑調整
        BGMPlayer.play(musicPath);
    }

    public static void main(String[] args) {
        launch(args); // 啟動應用程式
    }


}
