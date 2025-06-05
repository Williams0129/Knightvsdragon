package com.binge;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Knight {

    private int row; //縱軸
    private int col; //橫軸
    private int size;//地圖大小
    private Pane[][] mypane;//將scene的格子複製下來
    private int[][] paneproperty;//紀錄格子的屬性

    public Knight(int inirow, int inicol,int size, GridPane gridpane){//size是地圖大小(最大的row數)
        mypane = new Pane[size + 1][size + 1];
        paneproperty = new int[size + 1][size + 1];
        row = inirow;
        col = inicol;
        this.size = size;
        for (Node node : gridpane.getChildren()){//將scene的pane複製到mypane中，並記錄屬性到paneproperty中
            if (node instanceof Pane){//判斷是不是Pane
                Pane pane = (Pane) node;
                int thecol = GridPane.getColumnIndex(pane) == null ? 0 : GridPane.getColumnIndex(pane);
                int therow = GridPane.getRowIndex(pane) == null ? 0 : GridPane.getRowIndex(pane);
                mypane[therow][thecol] = pane;

                String style = pane.getStyle();//得到類似"-fx-background-color: gray; -fx-border-color: black;"
                if (style.contains("grey")){
                    paneproperty[therow][thecol] = 0;//代表牆
                }
                else if (style.contains("black")){
                    paneproperty[therow][thecol] = 2;//代表起點、玩家
                }
                else if (style.contains("yellow")){
                    paneproperty[therow][thecol] = 3;//代表終點
                }
                else{
                    paneproperty[therow][thecol] = 1;//代表通路
                }
                pane.setOnMouseClicked(e -> {
                    int clickedRow = GridPane.getRowIndex(pane) == null ? 0 : GridPane.getRowIndex(pane);
                    int clickedCol = GridPane.getColumnIndex(pane) == null ? 0 : GridPane.getColumnIndex(pane);

                    if (paneproperty[clickedRow][clickedCol] == 4) { // 如果是綠色格子
                        setposition(row, col, clickedRow, clickedCol); // 移動
                        for (int x = 0; x <= size; x++){
                            for (int y = 0; y <=size; y++){
                                if (paneproperty[x][y] == 4){
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
    }


    public void detection(){//偵測四周能走的格子，若可以走就標示成4，size是地圖大小(最大的row數)
        if (row + 1 <= size && col - 2 >= 0 && paneproperty[row + 1][col - 2] == 1){
            paneproperty[row + 1][col - 2] = 4;//綠色，代表可以走
        }
        if (row - 1 >= 0 && col - 2 >= 0 && paneproperty[row - 1][col - 2] == 1){
            paneproperty[row - 1][col - 2] = 4;//綠色，代表可以走
        }
        if (row - 2 >= 0 && col - 1 >= 0 && paneproperty[row - 2][col - 1] == 1){
            paneproperty[row - 2][col - 1] = 4;//綠色，代表可以走
        }
        if (row - 2 >= 0 && col + 1 <= size && paneproperty[row - 2][col + 1] == 1){
            paneproperty[row - 2][col + 1] = 4;//綠色，代表可以走
        }
        if (row - 1 >= 0 && col + 2 <= size && paneproperty[row - 1][col + 2] == 1){
            paneproperty[row - 1][col + 2] = 4;//綠色，代表可以走
        }
        if (row + 1 <= size && col + 2 <= size && paneproperty[row + 1][col + 2] == 1){
            paneproperty[row + 1][col + 2] = 4;//綠色，代表可以走
        }
        if (row + 2 <= size && col + 1 <= size && paneproperty[row + 2][col + 1] == 1){
            paneproperty[row + 2][col + 1] = 4;//綠色，代表可以走
        }
        if (row + 2 <= size && col - 1 >= 0 && paneproperty[row + 2][col - 1] == 1){
            paneproperty[row + 2][col - 1] = 4;//綠色，代表可以走
        }
    }

    public void setcolor(){//剛開始跟每次移動玩都要重製顏色
        for (int x = 0; x <= size; x++){
            for (int y = 0; y <= size; y++){
                int judge = paneproperty[x][y];
                String color = switch(judge){
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

    public void setposition(int prerow, int precol, int nextrow, int nextcol){//移動完後要重新設定位置
        paneproperty[prerow][precol] = 1;
        paneproperty[nextrow][nextcol] = 2;
        row = nextrow;
        col = nextcol;
    }
}
