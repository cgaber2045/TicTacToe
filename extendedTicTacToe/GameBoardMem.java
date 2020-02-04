package cpsc2150.extendedTicTacToe;

import java.util.*;

/**
 * @invariant MIN_ROW <= numRows <= MAX_ROW
 * @invariant MIN_COL <= numCols <= MAX_COL
 * @invariant [winAmt_MIN <= winAmt <= winAmt_MAX] and [winAmt <= numRows and winAmt <= numCols]
 * Correspondence Board = BoardMap
 * Correspondence winAmt = winAmt
 * Correspondence numRows = Number_Of_Rows
 * Correspondence numCols = Number_Of_Cols
 */

public class GameBoardMem extends AbsGameBoard implements IGameBoard {
    // Private Data
    private Map<Character, List<BoardPosition>> BoardMap;
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
     * @post a map with each player as keys and a list of board positions as its pair is created
     * @post amount of rows, columns, and amount of pieces in a row needed to win set.
     */
    public GameBoardMem(int rows, int cols, int winAmt) {
        this.numRows = rows;
        this.numCols = cols;
        this.winAmt = winAmt;
        BoardMap = new HashMap<>();
    }

    // Primary
    public void placeMarker(BoardPosition marker, char player) {
        // if player not found in map, add it
        if (!BoardMap.containsKey(player)) BoardMap.put(player, new ArrayList<>());
        //places the character in marker on the position specified by marker, and should not be called if the space is not available.
        BoardMap.get(player).add(marker);
    }

    // Primary
    public char whatsAtPos(BoardPosition pos) {
        // returns what is in the GameBoard at position pos.
        // If no marker is there it returns a blank space char ‘ ‘.

        // Iterate through each map entry
        for(Map.Entry currentListPos: BoardMap.entrySet()) {
            // Iterates through the list at each pair to find a matching board position
            if (((List<BoardPosition>) currentListPos.getValue()).contains(pos)) return (char) currentListPos.getKey();
        }

        return ' '; // No value found
    }

    // Overrided Secondary Method
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        if (BoardMap.get(player) == null) return false;
        return BoardMap.get(player).contains(pos);
    }

    // Primary
    public int getNumRows() { return numRows; }
    // Primary
    public int getNumColumns() { return numCols; }
    // Primary
    public int getNumToWin() { return winAmt; }
}
