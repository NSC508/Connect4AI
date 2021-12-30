public class main{
    public static void main(String[] args) {
        Board connect4Board = new Board(5, 6);
        connect4Board.makeMove(0, true);
        connect4Board.makeMove(0, true);
        System.out.println(connect4Board);
    }
}