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
                String color = switch (judge) {
                    case 0 -> "grey";
                    case 1 -> "white";
                    case 2 -> "black";
                    case 3 -> "yellow";
                    case 4 -> "green";
                    default -> "white";
                };

                mypane[x][y].setStyle("-fx-background-color: " + color + "; -fx-border-color: black;");
                if (color.equals("white")) {
                    mypane[x][y].setOpacity(0.01); // 白色格子透明 (看得到背景)
                } else {
                    mypane[x][y].setOpacity(1.0); // 其他格子不透明 (完全蓋住背景)
                }

                // 只有當前格子為玩家 (黑色 / 2) 才放圖片
                mypane[x][y].getChildren().clear(); // 先清掉 Pane 裡原本的內容
                if (judge == 2) {
                    // 取得 Pane 寬高
                    double paneWidth = mypane[x][y].getWidth();
                    double paneHeight = mypane[x][y].getHeight();

                    // 若寬高還未初始化（第一次載入），綁定它們
                    int finalX = x;
                    int finalY = y;
                    mypane[x][y].widthProperty().addListener((obs, oldVal, newVal) -> {
                        updateKnightImageSize(mypane[finalX][finalY]);
                    });
                    int finalX1 = x;
                    int finalY1 = y;
                    mypane[x][y].heightProperty().addListener((obs, oldVal, newVal) -> {
                        updateKnightImageSize(mypane[finalX1][finalY1]);
                    });

                    // 設定 knightImageView 大小
                    updateKnightImageSize(mypane[x][y]);

                    mypane[x][y].getChildren().add(knightImageView);
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