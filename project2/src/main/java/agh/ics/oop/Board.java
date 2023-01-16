package agh.ics.oop;

import java.util.Random;

public class Board {
    private final int rows;
    private final int cols;
    private final int[][] board;
    private final Random random = new Random();

    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.board = new int[rows][cols];
        generateSquare();
    }

    public int[] generateSquare(){
        int counter = 0;
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                if (board[i][j] == 0) counter++;
            }
        }
        if (counter == 0) return null;

        int index = random.nextInt(counter);
        int x = random.nextInt(10);
        int value;
        if (x == 0) value = 4;
        else value = 2;
//        System.out.println(value);

        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                if (board[i][j] == 0){
                    if (index == 0){
                        board[i][j] = value;
                        return new int[]{i, j};
                    }
                    index--;
                }
            }
        }
        return null;
    }

    public boolean right(){
        boolean flag = false;

        for (int i=0; i<rows; i++){
            boolean[] merged = new boolean[cols];
            for (int k=cols-2; k>=0; k--){
                if (board[i][k] == 0) continue;
                for (int j=k; j<cols-1; j++){
                    if (board[i][j+1] == 0){
                        board[i][j+1] = board[i][j];
                        board[i][j] = 0;
                        flag = true;
                    }
                    else if (board[i][j] == board[i][j+1] && !merged[j+1]) {
                        board[i][j] = 0;
                        board[i][j+1] *= 2;
                        merged[j+1] = true;
                        flag = true;
                        break;
                    }
                    else break;
                }
            }
        }

        return flag;
    }

    public boolean left(){
        boolean flag = false;

        for (int i=0; i<rows; i++){
            boolean[] merged = new boolean[cols];
            for (int k=1; k<cols; k++){
                if (board[i][k] == 0) continue;
                for (int j=k; j>=1; j--){
                    if (board[i][j-1] == 0){
                        board[i][j-1] = board[i][j];
                        board[i][j] = 0;
                        flag = true;
                    }
                    else if (board[i][j] == board[i][j-1] && !merged[j-1]) {
                        board[i][j] = 0;
                        board[i][j-1] *= 2;
                        merged[j-1] = true;
                        flag = true;
                        break;
                    }
                    else break;
                }
            }
        }
        return flag;
    }

    public boolean down(){
        boolean flag = false;

        for (int j=0; j<rows; j++){
            boolean[] merged = new boolean[rows];
            for (int k=rows-2; k>=0; k--){
                if (board[k][j] == 0) continue;
                for (int i=k; i<rows-1; i++){
                    if (board[i+1][j] == 0){
                        board[i+1][j] = board[i][j];
                        board[i][j] = 0;
                        flag = true;
                    }
                    else if (board[i][j] == board[i+1][j] && !merged[i+1]) {
                        board[i][j] = 0;
                        board[i+1][j] *= 2;
                        merged[i+1] = true;
                        flag = true;
                        break;
                    }
                    else break;
                }
            }
        }
        return flag;
    }

    public boolean up(){
        boolean flag = false;

        for (int j=0; j<cols; j++){
            boolean[] merged = new boolean[rows];
            for (int k=1; k<rows; k++){
                if (board[k][j] == 0) continue;
                for (int i=k; i>=1; i--){
                    if (board[i-1][j] == 0){
                        board[i-1][j] = board[i][j];
                        board[i][j] = 0;
                        flag = true;
                    }
                    else if (board[i][j] == board[i-1][j] && !merged[i-1]) {
                        board[i][j] = 0;
                        board[i-1][j] *= 2;
                        merged[i-1] = true;
                        flag = true;
                        break;
                    }
                    else break;
                }
            }
        }
        return flag;
    }

    public int[][] getBoard(){
        return board;
    }

    public boolean isGameOver(){
        int counter = 0;
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                if (board[i][j] == 0) counter++;
            }
        }
        if (counter > 0) return false;
        for (int i=0; i<rows; i++) {
            for (int j=0; j <cols-1; j++) {
                if (board[i][j] == board[i][j+1]) return false;
            }
        }

        for (int j=0; j<cols; j++) {
            for (int i=0; i<rows-1; i++) {
                if (board[i][j] == board[i+1][j]) return false;
            }
        }


        return true;
    }
}
