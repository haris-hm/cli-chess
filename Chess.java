import java.util.InputMismatchException;
import java.util.Scanner;

public class Chess {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner keyboard = new Scanner(System.in);

        //System.out.println('D'-'A'+1);
        

        board.getActivePieces().get(0).movePiece(4, 6);
        board.getActivePieces().get(1).movePiece(3, 6);
        System.out.println(board);

        while (true) {
            System.out.println(board);
            System.out.println("Please select a piece by typing its board coordinate (i.e. 'E4'):");
            String selectedCoord = keyboard.nextLine();

            try {
                board.play(selectedCoord, false);
            }
            catch (InputMismatchException e) {
                System.out.println(e);
            }
        }        
    }
}