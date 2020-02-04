package cpsc2150.extendedTicTacToe;

public class BoardPosition {
    /**
     * @invariant
     * 0 <= rowNum < MAX_ROW
     * 0 <= colNum < MAX_COL
     */
    private int rowNum;
    private int colNum;

    /**
     * @param row a row in the Game Board
     * @param col a column in the Game Board
     * @pre GameBoard.checkSpace() called
     * @post rowNum = row and colNum = col
     */
    public BoardPosition(int row, int col) {
        rowNum = row;
        colNum = col;
    }

    /**
     * @return the row number
     * @pre BoardPosition initialized
     * @post 0 <= rowNum < GameBoard.MAX_ROW
     */
    public int getRow() {
        //returns the row
        return rowNum;
    }

    /**
     * @return the column number
     * @pre BoardPosition initialized
     * @post 0 <= colNum < GameBoard.MAX_COL 
     */
    public int getColumn() {
        //returns the column
        return colNum;
    }

    /**
     * @return the BoardPosition in the format “<rowNum>,<colNum>”. Example “3,5”
     * @pre BoardPosition initialized
     * @post string returned in format <rowNum>,<colNum> 
     */
    @Override
    public String toString() {
        return rowNum + "," + colNum;
    }

    /**
     * @return the BoardPosition in the format “<rowNum>,<colNum>”. Example “3,5”
     * @pre board initialized
     * @post returns true if Board Position's rows and columns are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BoardPosition)) return false;
        BoardPosition newPos = (BoardPosition) obj;
        if (rowNum == newPos.rowNum && colNum == newPos.colNum) return true;
        return false;
    }
}
