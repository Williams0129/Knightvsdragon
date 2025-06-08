package com.binge;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * 直線滑行角色：從目前位置往上下左右任一方向「一路滑到底」，
 * 直到碰到灰色牆（或邊界）為止再停下。
 */
public class horse {

    public int row; // 縱軸
    public int col; // 橫軸
    private int size;               // 地圖大小
    private Pane[][] mypane;        // 參照到畫面上的 Pane
    private int[][] paneproperty;   // 0:牆、1:通路、2:玩家、3:終點、4:可走
    private Runnable onMovedCallback;

    public horse(int inirow, int inicol, int size, GridPane gridpane) {
        this.size = size;
        mypane = new Pane[size + 1][size + 1];
        paneproperty = new int[size + 1][size + 1];
        row = inirow;
        col = inicol;

        /* 1. 從 GridPane 讀出所有 Pane → 存到陣列，並解析顏色決定屬性值 */
        for (Node node : gridpane.getChildren()) {
            if (node instanceof Pane pane) {
                int theCol = GridPane.getColumnIndex(pane) == null ? 0 : GridPane.getColumnIndex(pane);
                int theRow = GridPane.getRowIndex(pane) == null ? 0 : GridPane.getRowIndex(pane);
                mypane[theRow][theCol] = pane;

                String style = pane.getStyle();
                if (style.contains("grey"))      paneproperty[theRow][theCol] = 0; // 牆
                else if (style.contains("black")) paneproperty[theRow][theCol] = 2; // 起點 / 玩家
                else if (style.contains("yellow")) paneproperty[theRow][theCol] = 3; // 終點
                else                               paneproperty[theRow][theCol] = 1; // 通路

                /* 2. 點擊事件：只有綠色格子 (4) 能觸發移動 */
                pane.setOnMouseClicked(e -> {
                    int clickedRow = GridPane.getRowIndex(pane) == null ? 0 : GridPane.getRowIndex(pane);
                    int clickedCol = GridPane.getColumnIndex(pane) == null ? 0 : GridPane.getColumnIndex(pane);

                    if (paneproperty[clickedRow][clickedCol] == 4) {
                        setPosition(row, col, clickedRow, clickedCol); // 角色滑到終點
                        clearGreen();
                        detection(); // 重新標出下一輪可滑終點
                        setColor();
                    }
                });
            }
        }

        detection();
        setColor();
    }

    /** 供外部註冊：「角色移動完後要做什麼」的 callback */
    public void setOnMovedCallback(Runnable callback) {
        this.onMovedCallback = callback;
    }

    /** 把陣列裡殘存的 4 清掉（移動後才呼叫） */
    private void clearGreen() {
        for (int r = 0; r <= size; r++) {
            for (int c = 0; c <= size; c++) {
                if (paneproperty[r][c] == 4) paneproperty[r][c] = 1;
            }
        }
    }

    /**
     * 偵測：由當前座標向四個方向直走，
     * 找到「能抵達的最後一格」→ 標記為 4
     */
    public void detection() {

        // ↑ 往上：row-1, row-2, ...
        int r = row - 1;
        while (r >= 0 && paneproperty[r][col] != 0) r--;
        // r 現在在牆(0)或 -1，停在 r+1
        if (r + 1 != row && r + 1 >= 0) markIfWalkable(r + 1, col);

        // ↓ 往下
        r = row + 1;
        while (r <= size && paneproperty[r][col] != 0) r++;
        if (r - 1 != row && r - 1 <= size) markIfWalkable(r - 1, col);

        // ← 往左
        int c = col - 1;
        while (c >= 0 && paneproperty[row][c] != 0) c--;
        if (c + 1 != col && c + 1 >= 0) markIfWalkable(row, c + 1);

        // → 往右
        c = col + 1;
        while (c <= size && paneproperty[row][c] != 0) c++;
        if (c - 1 != col && c - 1 <= size) markIfWalkable(row, c - 1);
    }

    /** 把 (r,c) 標記成綠色 4──前提是該格是「路」或「終點」 */
    private void markIfWalkable(int r, int c) {
        if (paneproperty[r][c] == 1 || paneproperty[r][c] == 3) {
            paneproperty[r][c] = 4;
        }
    }

    /** 重新依照 paneproperty 塗顏色 */
    public void setColor() {
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
            }
        }
    }

    /** 將玩家從 (prerow,precol) 移到 (nextrow,nextcol) */
    public void setPosition(int prerow, int precol, int nextrow, int nextcol) {
        paneproperty[prerow][precol] = 1;
        paneproperty[nextrow][nextcol] = 2;
        row = nextrow;
        col = nextcol;

        if (onMovedCallback != null) onMovedCallback.run();
    }
}
