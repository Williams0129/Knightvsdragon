package com.binge;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Knight {

    public int row;
    public int col;
    private int size;
    private Pane[][] mypane;
    private int[][] paneproperty;

    private Runnable onMovedCallback;

    // 用來顯示圖片
    private ImageView knightImageView;

    public Knight(int inirow, int inicol, int size, GridPane gridpane) {
        mypane = new Pane[size + 1][size + 1];
        paneproperty = new int[size + 1][size + 1];
        row = inirow;
        col = inicol;
        this.size = size;

        // 建立角色圖示
        Image knightImage = new Image(getClass().getResource("knight.png").toExternalForm()); // 請確認路徑正確
        knightImageView = new ImageView(knightImage);
        knightImageView.setFitWidth(75); // 可以根據格子大小微調
        knightImageView.setFitHeight(75);
        knightImageView.setPreserveRatio(true);

        for (Node node : gridpane.getChildren()) {
            if (node instanceof Pane pane) {
                int thecol = GridPane.getColumnIndex(pane) == null ? 0 : GridPane.getColumnIndex(pane);
                int therow = GridPane.getRowIndex(pane) == null ? 0 : GridPane.getRowIndex(pane);
                mypane[therow][thecol] = pane;

                String style = pane.getStyle();
                if (style.contains("grey")) {
                    paneproperty[therow][thecol] = 0;
                } else if (style.contains("black")) {
                    paneproperty[therow][thecol] = 2;
                } else if (style.contains("yellow")) {
                    paneproperty[therow][thecol] = 3;
                } else {
                    paneproperty[therow][thecol] = 1;
                }

                pane.setOnMouseClicked(e -> {
                    int clickedRow = GridPane.getRowIndex(pane) == null ? 0 : GridPane.getRowIndex(pane);
                    int clickedCol = GridPane.getColumnIndex(pane) == null ? 0 : GridPane.getColumnIndex(pane);

                    if (paneproperty[clickedRow][clickedCol] == 4) {
                        setposition(row, col, clickedRow, clickedCol);
                        for (int x = 0; x <= size; x++) {
                            for (int y = 0; y <= size; y++) {
                                if (paneproperty[x][y] == 4) {
                                    paneproperty[x][y] = 1;
                                }
                            }
                        }
                        detection();
                        setcolor();
                    }
                });
            }
        }
        detection();
        setcolor();
        updateImagePosition(); // 初始化時放上圖
    }

    public void setOnMovedCallback(Runnable callback) {
        this.onMovedCallback = callback;
    }

    public void detection() {
        int[][] move = {
                {+1, -2}, {-1, -2}, {-2, -1}, {-2, +1},
                {-1, +2}, {+1, +2}, {+2, +1}, {+2, -1}
        };
        for (int[] m : move) {
            int r = row + m[0], c = col + m[1];
            if (r >= 0 && r <= size && c >= 0 && c <= size &&
                    (paneproperty[r][c] == 1 || paneproperty[r][c] == 3)) {
                paneproperty[r][c] = 4;
            }
        }
    }

    public void setcolor() {
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
                    mypane[x][y].getChildren().add(knightImageView);

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
        knightImageView.setFitWidth(pane.getWidth());
        knightImageView.setFitHeight(pane.getHeight());
    }

    public void setposition(int prerow, int precol, int nextrow, int nextcol) {
        paneproperty[prerow][precol] = 1;
        paneproperty[nextrow][nextcol] = 2;
        row = nextrow;
        col = nextcol;

        updateImagePosition(); // 每次移動更新圖片

        if (onMovedCallback != null) {
            onMovedCallback.run();
        }
    }

    private void updateImagePosition() {
        // 移除舊位置的圖
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                mypane[i][j].getChildren().remove(knightImageView);
            }
        }
        // 放到目前位置的 Pane 中
        mypane[row][col].getChildren().add(knightImageView);
    }
}