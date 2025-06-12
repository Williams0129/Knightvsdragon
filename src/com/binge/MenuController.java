package com.binge;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    Button gameStartButton, gameQuitButton;
    @FXML
    Text timer;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static time time = new time("00:00:00");
    public static Timeline timeline; // 改為 static

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        timer.setText(time.getCurrentTime());

        if (timeline == null) { // 確保只建立一次
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            e -> {
                                time.oneSecondPassed();
                            }
                    )
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
        }
    }

    public void gameStart(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1" +
                ".fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        timeline.play();
    }

    public void gameQuit(javafx.event.ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
