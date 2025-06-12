package com.binge;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class horse {

    public int row;
    public int col;
    private int size;
    private Pane[][] mypane;
    private int[][] paneproperty;
    private Runnable onMovedCallback;

    // 圖片
    private ImageView horseImageView;

    public horse(int inirow, int inicol, int size, GridPane gridpane) {
        this.size = size;
        mypane = new Pane[size + 1][size + 1];
        paneproperty = new int[size + 1][size + 1];
        row = inirow;
        col = inicol;

        // 讀取圖片 (注意路徑)
        Image horseImage = new Image(getClass().getResource("knight.png").toExternalForm());
        horseImageView = new ImageView(horseImage);
        horseImageView.setPreserveRatio(false); // 完全填滿 Pane

        for (Node node : gridpane.getChildren()) {
            if (node instanceof Pane pane) {
                int theCol = GridPane.getColumnIndex(pane) == null ? 0 : GridPane.getColumnIndex(pane);
                int theRow = GridPane.getRowIndex(pane) == null ? 0 : GridPane.getRowIndex(pane);

                mypane[theRow][theCol] = pane;

                String style = pane.getStyle();
                if (style.contains("grey"))      paneproperty[theRow][theCol] = 0;
                else if (style.contains("black")) paneproperty[theRow][theCol] = 2;
                else if (style.contains("yellow")) paneproperty[theRow][theCol] = 3;
                else                               paneproperty[theRow][theCol] = 1;

                final int clickedRow = theRow;
                final int clickedCol = theCol;

                pane.setOnMouseClicked(e -> {
                    if (paneproperty[clickedRow][clickedCol] == 4) {
                        setPosition(row, col, clickedRow, clickedCol);
                        clearGreen();
                        detection();
                        setColor();
                    }
                });
            }
        }

        detection();
        setColor();
    }

    public void setOnMovedCallback(Runnable callback) {
        this.onMovedCallback = callback;
    }

    private void clearGreen() {
        for (int r = 0; r <= size; r++) {
            for (int c = 0; c <= size; c++) {
                if (paneproperty[r][c] == 4) paneproperty[r][c] = 1;
            }
        }
    }

    public void detection() {
        int r = row - 1;
        while (r >= 0 && paneproperty[r][col] != 0) r--;
        if (r + 1 != row && r + 1 >= 0) markIfWalkable(r + 1, col);

        r = row + 1;
        while (r <= size && paneproperty[r][col] != 0) r++;
        if (r - 1 != row && r - 1 <= size) markIfWalkable(r - 1, col);

        int c = col - 1;
        while (c >= 0 && paneproperty[row][c] != 0) c--;
        if (c + 1 != col && c + 1 >= 0) markIfWalkable(row, c + 1);

        c = col + 1;
        while (c <= size && paneproperty[row][c] != 0) c++;
        if (c - 1 != col && c - 1 <= size) markIfWalkable(row, c - 1);
    }

    private void markIfWalkable(int r, int c) {
        if ((paneproperty[r][c] == 1 || paneproperty[r][c] == 3)) {
            paneproperty[r][c] = 4;
        }
    }

    public void setColor() {
        for (int x = 0; x <= size; x++) {
            for (int y = 0; y <= size; y++) {
                int judge = paneproperty[x][y];

                // 根據屬性決定背景色（透明度在這裡調整）
                String backgroundStyle = switch (judge) {
                    case 0 -> "-fx-background-color: rgba(128,128,128,0.2);"; // grey 半透明
                    case 1 -> "-fx-background-color: rgba(255,255,255,0.01);"; // white 幾乎全透明
                    case 2 -> "-fx-background-color: rgba(0,0,0,0.01);";       // black 幾乎全透明，但不會影響圖
                    case 3 -> "-fx-background-color: yellow;";
                    case 4 -> "-fx-background-color: green;";
                    default -> "-fx-background-color: white;";
                };

                mypane[x][y].setStyle(backgroundStyle + " -fx-border-color: black;");

                // 清除原有內容（避免重複）
                mypane[x][y].getChildren().clear();

                // 如果這是玩家（黑色 / 2）的位置，就放圖片
                if (judge == 2) {
                    // 設定圖片尺寸並加入
                    updateKnightImageSize(mypane[x][y]);
                    mypane[x][y].getChildren().add( horseImageView);

                    // 綁定 Pane 大小（確保縮放時圖片跟著變）
                    int finalX = x;
                    int finalY = y;
                    mypane[x][y].widthProperty().addListener((obs, oldVal, newVal) -> {
                        updateKnightImageSize(mypane[finalX][finalY]);
                    });
                    mypane[x][y].heightProperty().addListener((obs, oldVal, newVal) -> {
                        updateKnightImageSize(mypane[finalX][finalY]);
                    });
                }
            }
        }
    }

    private void updateKnightImageSize(Pane pane) {
        // 綁定圖片寬高到 pane 大小
        horseImageView.fitWidthProperty().bind(pane.widthProperty());
        horseImageView.fitHeightProperty().bind(pane.heightProperty());
    }

    public void setPosition(int prerow, int precol, int nextrow, int nextcol) {
        paneproperty[prerow][precol] = 1;
        paneproperty[nextrow][nextcol] = 2;
        row = nextrow;
        col = nextcol;

        if (onMovedCallback != null) onMovedCallback.run();
    }
}
