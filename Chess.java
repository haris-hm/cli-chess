import java.util.Scanner;

public class Chess {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner keyboard = new Scanner(System.in);

        boolean gameActive = true;
        boolean isBlacksTurn = false;

        while (gameActive) {
            System.out.println(board);
            System.out.println("Please select a piece by typing its board coordinate (i.e. 'E4') or type \"Q\" to quit the game:");

            String selectedCoord = keyboard.nextLine();

            if (selectedCoord.equals("Q")) {
                keyboard.close();
                gameActive = false;
                break;
            }

            try {
                board.play(selectedCoord, isBlacksTurn);
            }
            catch (IncorrectChessInputException e) {
                System.out.println(e.getMessage());
            }

            isBlacksTurn = !isBlacksTurn;
        }
    }
}