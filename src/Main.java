import Board.Board;
import Graphic.Graphic;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        new Graphic(board);
        System.out.println(board);
    }
}