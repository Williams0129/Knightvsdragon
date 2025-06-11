package com.binge;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class BGMPlayer {
    private static MediaPlayer mediaPlayer;

    public static void play(String filePath) {
        if (mediaPlayer == null) {
            Media media = new Media(new File(filePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 無限循環播放
            mediaPlayer.setVolume(0.05);
            mediaPlayer.play();
        }
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }
}