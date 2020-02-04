package cpsc2150.extendedTicTacToe;

/**
 * @invariant MIN_ROW <= numRows <= MAX_ROW
 * @invariant MIN_COL <= numCols <= MAX_COL
 * @invariant winAmt_MIN <= winAmt <= winAmt_MAX and [winAmt <= numRows and winAmt <= numCols]
 * Correspondence Board = Board
 * Correspondence winAmt = winAmt
 * Correspondence numRows = Number_Of_Rows
 * Correspondence numCols = Number_Of_Cols
 */

public class GameBoard extends AbsGameBoard implements IGameBoard {
    // Private Data
    private char[][] Board;
    private int winAmt;
    private int numRows;
    private int numCols;

    /**
     * @pre MIN_ROW <= rows <= MAX_ROW
     * @pre MIN_COL <= cols <= MAX_COL
     * @pre WIN_AMT_MIN <= winAmt <= WIN_AMT_MAX
     * @param rows amount of rows for GameBoard
     * @param cols amount of columns for GameBoard
     * @param winAmt the number of tokens needed to win
     * @post empty Board of size numRows by numCols created
     * @post amount of rows, columns, and amount of pieces in a row needed to win set.
     */
    public GameBoard(int rows, int cols, int winAmt) {
        this.numRows = rows;
        this.numCols = cols;
        this.winAmt = winAmt;
        Board = new char[numRows][numCols];
    }

    // Primary
    public void placeMarker(BoardPosition marker, char player) {
        //places the character in marker on the position specified by marker, and should not be called if the space is not available.
        Board[marker.getRow()][marker.getColumn()] = player;
    }

    // Primary
    public char whatsAtPos(BoardPosition pos) {
        // returns what is in the GameBoard at position pos.
        // If no marker is there it returns a blank space char ‘ ‘.
        if (Board[pos.getRow()][pos.getColumn()] == 0) return ' ';
        else return Board[pos.getRow()][pos.getColumn()];
    }

    // Primary
    public int getNumRows() { return numRows; }
    // Primary
    public int getNumColumns() { return numCols; }
    // Primary
    public int getNumToWin() { return winAmt; }
}
