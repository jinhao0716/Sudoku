import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board {
    int[][] board;
    Random rand = new Random();

    public Board(){
        this.board = new int[9][9];
    }



    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                temp += board[i][j] + " ";
            }
            temp.trim();
            temp += "\n";
        }
        return temp;
    }
}
