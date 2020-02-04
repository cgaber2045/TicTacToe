package cpsc2150.extendedTicTacToe;

/**
 * A GameBoard of size Number_Of_Rows x Number_Of_Columns
 * A GameBoard is the area in which players place their markers for the game.
 * @Defines: Board: - Every GameBoard must store the players positions
 *           winAmt: z - The number of pieces needed in a row in order to win
 *           Number_Of_Rows: z - The number of rows in the GameBoard
 *           Number_Of_Columns: z - The number of columns in the GameBoard
 *
 * @Initialization Ensures: An empty (null char) GameBoard of size Number_Of_Rows x Number_Of_Columns is created.
 *
 * @Constraints: WIN_AMT_MIN <= winAmt <= WIN_AMT_MAX and [winAmt <= Number_Of_Row and winAmt <= Number_Of_Columns]
 *              MIN_ROW <= Number_Of_Row <= MAX_ROW
 *              MIN_COL <= Number_Of_Columns <= MAX_COL
 */

public interface IGameBoard {
    int MAX_ROW = 100;
    int MAX_COL = 100;
    int MIN_ROW = 3;
    int MIN_COL = 3;
    int WIN_AMT_MIN = 3;
    int WIN_AMT_MAX = 25;

    /**
     * @param pos a position in the Game Board
     * @return true if the position specified in pos is available, false otherwise
     * @post returns true if the position specified in pos is available, false otherwise. No value changed.
     */
    default boolean checkSpace(BoardPosition pos) {
        //returns true if the position specified in pos is available, false otherwise. If a space is not in bounds, then it is not available
        return pos.getRow() >= 0 && pos.getRow() < getNumRows() &&
                pos.getColumn() >= 0 && pos.getColumn() < getNumColumns()
                && whatsAtPos(pos) == ' '; //
    }

    /**
     * @param marker a position in the Game Board
     * @param player the specific player to go in location
     * @pre 0 <= marker.getRow < getNumRows()
     * @pre 0 <= marker.getCol < getNumColumns()
     * @pre No player at spot
     * @post Marker for player placed at marker BoardPosition
     */
    void placeMarker(BoardPosition marker, char player);

    /**
     * @param lastPos a position in the Game Board
     * @return true if the lastPos placed resulted in a winner, otherwise false
     * @pre 0 <= lastPos.getRow < getNumRows()
     * @pre 0 <= lastPos.getCol < getNumColumns()
     * @pre Player placed marker before this function was called
     * @pre lastPos was the last position placed
     * @post true if the lastPos placed resulted in a winner, otherwise false
     */
    default boolean checkForWinner(BoardPosition lastPos) {
        // this function will check to see if the lastPos placed resulted in a winner. If so it will return true, otherwise false.
        // Passing in the last position will help limit the possible places to check for a win condition, since you can assume that any win condition that did not
        // includes the most recent play made would have been caught earlier.
        if (checkDiagonalWin(lastPos, whatsAtPos(lastPos))) return true;
        if (checkVerticalWin(lastPos, whatsAtPos(lastPos))) return true;
        if (checkHorizontalWin(lastPos, whatsAtPos(lastPos))) return true;

        return false;
    }

    /**
     * @return true if the game is tied, and false otherwise.
     * @pre GameBoard initialized, checkForWinner called
     * @post true if the game is tied, and false otherwise.
     */
    default boolean checkForDraw() {
        //this function will check to see if the game has resulted in a tie.
        // A game is tied if there are no free board positions remaining.
        // Does not check for any potential wins, because we can assume that the players were checking for win conditions as they played the game.
        // It will return true if the game is tied, and false otherwise.
        for (int row = 0; row < getNumRows(); row++) {
            for (int col = 0; col < getNumColumns(); col++) {
                BoardPosition newPos = new BoardPosition(row, col);
                if (whatsAtPos(newPos) == ' ') return false;
            }
        }

        return true;
    }

    /**
     * @param lastPos a position in the Game Board
     * @param player  the specific player to check for win
     * @return true if the last marker placed resulted in winAmt in a row horizontally
     * @pre 0 <= lastPos.getRow < getNumRows()
     * @pre 0 <= lastPos.getCol < getNumColumns()
     * @pre lastPos was the last position placed
     * @pre player is a valid char in game
     * @post Returns true if the last marker placed resulted in winAmt in a row horizontally, otherwise false
     */
    default boolean checkHorizontalWin(BoardPosition lastPos, char player) {
        //checks to see if the last marker placed resulted in 5 in a row horizontally. Returns true if it does, otherwise false
        int countRight = 0;
        int countLeft = 0;

        // ---- Checks to the right of the lastPos ----
        BoardPosition newPos = new BoardPosition(lastPos.getRow(),lastPos.getColumn() + countRight);
        while (isPlayerAtPos(newPos, player)) {
            countRight++;
            if (lastPos.getColumn() + countRight >= getNumColumns()) break;
            newPos = new BoardPosition(lastPos.getRow(),lastPos.getColumn() + countRight);
        }

        // ---- Checks to the left of the lastPos ----
        newPos = new BoardPosition(lastPos.getRow(),lastPos.getColumn() - countLeft);
        while (isPlayerAtPos(newPos, player)) {
            countLeft++;
            if (lastPos.getColumn() - countLeft < 0) break;
            newPos = new BoardPosition(lastPos.getRow(),lastPos.getColumn() - countLeft);
        }

        // Return if the number of markers in a row was greater than the amount to win.
        // Subtract 1 since we count the middle twice.
        return countRight + countLeft - 1 >= getNumToWin();
    }

    /**
     * @param lastPos a position in the Game Board
     * @param player  the specific player to check for win
     * @return true if the last marker placed resulted in winAmt in a row vertically, otherwise false
     * @pre player is a valid char in game
     * @pre 0 <= lastPos.getRow < getNumRows()
     * @pre 0 <= lastPos.getCol < getNumColumns()
     * @pre lastPos was the last position placed
     * @post Returns true if the last marker placed resulted in winAmt in a row vertically, otherwise false
     */
    default boolean checkVerticalWin(BoardPosition lastPos, char player) {
        //checks to see if the last marker placed resulted in 5 in a row vertically. Returns true if it does, otherwise false
        int countDown = 0;
        int countUp = 0;

        //  ---- Checks positions below last pos to see if player is there ----
        BoardPosition newPos = new BoardPosition(lastPos.getRow() - countDown,lastPos.getColumn());
        while (isPlayerAtPos(newPos, player)) {
            countDown++;
            if (lastPos.getRow() - countDown < 0) break;
            newPos = new BoardPosition(lastPos.getRow() - countDown,lastPos.getColumn());
        }

        //  ---- Checks positions above the lastPos of the player to see if player is there ----
        newPos = new BoardPosition(lastPos.getRow() + countUp,lastPos.getColumn());
        while (isPlayerAtPos(newPos, player)) {
            countUp++;
            if(lastPos.getRow() + countUp >= getNumRows()) break;
            newPos = new BoardPosition(lastPos.getRow() + countUp,lastPos.getColumn());
        }

        // Return if the number of markers in a row was greater than the amount to win.
        // Subtract 1 since we count the middle twice.
        return countDown + countUp - 1 >= getNumToWin();
    }

    /**
     * @param lastPos a position in the Game Board
     * @param player  the specific player to check for win
     * @return true if the last marker placed resulted in winAmt in a row diagonally, otherwise false
     * @pre player is a valid char in game
     * @pre 0 <= lastPos.getRow < getNumRows()
     * @pre 0 <= lastPos.getCol < getNumColumns()
     * @pre lastPos was the last position placed
     * @post Returns true if the last marker placed resulted in winAmt in a row diagonally, otherwise false
     */
    default boolean checkDiagonalWin(BoardPosition lastPos, char player) {
        //checks to see if the last marker placed resulted in 5 in a row diagonally. Returns true if it does, otherwise false
        // Note: there are two diagonals to check
        //checks to see if the last marker placed resulted in 5 in a row vertically. Returns true if it does, otherwise false
        int countDownLeft = 0;
        int countUpRight = 0;

        int countDownRight = 0;
        int countUpLeft = 0;

        // ---- Checks diagonals that look like this '/' ----

            // ---- Checks to the bottom left of the lastPos ----
        BoardPosition newPos = new BoardPosition(lastPos.getRow() - countDownLeft,lastPos.getColumn() - countDownLeft);
        while (isPlayerAtPos(newPos, player)) {
            countDownLeft++;
            if (lastPos.getRow() - countDownLeft < 0 || lastPos.getColumn() - countDownLeft < 0) break;
            newPos = new BoardPosition(lastPos.getRow() - countDownLeft,lastPos.getColumn() - countDownLeft);
        }

            // ---- Checks to the top right of lastPos ----
        newPos = new BoardPosition(lastPos.getRow() + countUpRight,lastPos.getColumn() + countUpRight);
        while (isPlayerAtPos(newPos, player)) {
            countUpRight++;
            if(lastPos.getRow() + countUpRight >= getNumRows() || lastPos.getColumn() + countUpRight >= getNumColumns()) break;
            newPos = new BoardPosition(lastPos.getRow() + countUpRight,lastPos.getColumn() + countUpRight);
        }

        // Subtract 1 since we count the middle twice.
        if(countDownLeft + countUpRight - 1 >= getNumToWin()) return true;

        // ---- Checks diagonals that look like this '\' ----

            // ---- Checks to the bottom right of lastPos ----
        newPos = new BoardPosition(lastPos.getRow() - countDownRight,lastPos.getColumn() + countDownRight);
        while (isPlayerAtPos(newPos, player)) {
            countDownRight++;
            if (lastPos.getRow() - countDownRight < 0 || lastPos.getColumn() + countDownRight >= getNumColumns()) break;
            newPos = new BoardPosition(lastPos.getRow() - countDownRight,lastPos.getColumn() + countDownRight);
        }

            // ---- Checks to the top left of lastPos ----
        newPos = new BoardPosition(lastPos.getRow() + countUpLeft,lastPos.getColumn() - countUpLeft);
        while (isPlayerAtPos(newPos, player)) {
            countUpLeft++;
            if(lastPos.getRow() + countUpLeft >= getNumRows() || lastPos.getColumn() - countUpLeft < 0) break;
            newPos = new BoardPosition(lastPos.getRow() + countUpLeft,lastPos.getColumn() - countUpLeft);
        }

        // Return if the number of markers in a row was greater than the amount to win.
        // Subtract 1 since we count the middle twice.
        if(countDownRight + countUpLeft - 1 >= getNumToWin()) return true;

        return false;
    }

    /**
     * @param pos a position in the Game Board
     * @return what is in the GameBoard at position pos, ' ' if nothing there.
     * @pre 0 <= pos.getRow < getNumRows()
     * @pre 0 <= pos.getCol < getNumColumns()
     * @post what is in the GameBoard at position pos, ' ' if nothing there.
     */
    char whatsAtPos(BoardPosition pos);

    /**
     * @param pos a position in the Game Board
     * @param player the specific player to check for win
     * @return true if the position specified in pos is available, false otherwise
     * @pre 0 <= pos.getRow < getNumRows()
     * @pre 0 <= pos.getCol < getNumColumns()
     * @pre player is a valid char in game
     * @post true if the position specified in pos is available, false otherwise 
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player) {
        return whatsAtPos(pos) == player;
    }

    /**
     * @post getNumRows() = Number_Of_Row
     * @return number of rows for the GameBoard
     */
    int getNumRows();

    /**
     * @post getNumColumns() = Number_Of_Cols
     * @return number of columns for the GameBoard
     */
    int getNumColumns();

    /**
     * @post getNumToWin() = winAmt
     * @return number of markers in a row needed to win
     */
    int getNumToWin();
}
