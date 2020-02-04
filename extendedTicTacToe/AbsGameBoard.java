package cpsc2150.extendedTicTacToe;

public abstract class AbsGameBoard implements IGameBoard{
    /**
     * @return a representation of the Game Board as a string
     * @pre GameBoard initialized with values
     * @post returns an output of the Game Board as a string
     */
    @Override
    public String toString() {
        String output = "";
        int TwoDigits = 10;

        for (int row = 0; row <= getNumRows(); row++) {
            for (int col = 0; col <= getNumColumns(); col++) {
                if (row == 0 && col == 0) output += "   ";
                else if (row == 0 && col-1 < TwoDigits) output += " " + (col-1) + "|";
                else if (row == 0) output += col-1 + "|";
                else if (col == 0 && row-1 < TwoDigits) output += " " + (row-1) + "|";
                else if (col == 0) output += row-1 + "|";
                else output += whatsAtPos(new BoardPosition(row-1,col-1)) + " |";
            }

            output += "\n";
        }

        return output;
    }
}
