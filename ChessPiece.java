public abstract class ChessPiece{
    private String name;
    private String symbol;
    private boolean black;

    private int rankPosition;
    private int filePosition;

    /**
     * Creates a new chess piece
     * @param name The name of the piece.
     * @param symbol The symbol to represent the piece on the board printed to the console.
     * @param black Whether or not the piece is black.
     * @param rankPosition The horizontal (x) coordinate of the piece on the board grid.
     * @param filePosition The vertical (y) coordinate of the piece on the board grid.
     */
    public ChessPiece(String name, String symbol, boolean black, int rankPosition, int filePosition) {
        this.name = name;
        this.symbol = symbol;
        this.black = black;
        this.rankPosition = rankPosition;
        this.filePosition = filePosition;
    }

    /**
     * Calculates the valid moves for the piece.
     * @param currentBoard The current chess board. Used to figure out the positions of other pieces in case of capture.
     * @return
     */
    public abstract String calculateValidBoardMoves(Board currentBoard);

    /**
     * @return The symbol of the piece. If the piece is black, it colors its output using ANSI codes, if it's white, it returns just the symbol.
     */
    public String toString() {
        if (black) {
            return "\u001B[35m" + symbol + "\u001B[0m"; // "\u001B[35m" is the code for purple, "\u001B[0m" is the reset code
        }
        else {
            return symbol;
        }
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public int getRankPosition() {
        return rankPosition;
    }

    public int getFilePosition() {
        return filePosition;
    }

    public void movePiece(int rankPosition, int filePosition) {
        this.rankPosition = rankPosition;
        this.filePosition = filePosition;
    }
}
