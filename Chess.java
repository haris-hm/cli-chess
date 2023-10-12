public class Chess {
    public static void main(String[] args) {
        Board board = new Board();

        board.getActivePieces().get(0).movePiece(4, 6);
        board.getActivePieces().get(1).movePiece(3, 6);
        System.out.println(board);
    }
}