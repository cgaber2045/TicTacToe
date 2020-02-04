package cpsc2150.extendedTicTacToe;

/**
 * The TicTacToe controller class will handle communication between our TicTacToeView and our Model (IGameBoard and BoardPosition)
 *
 * This is where you will write code
 *
 * You will need to include your BoardPosition class, the IGameBoard interface
 * and the implementations from previous homework
 * If your code was correct you will not need to make any changes to your IGameBoard classes
 */
public class TicTacToeController{
    //our current game that is being played
    private IGameBoard curGame;

    //The screen that provides our view
    private TicTacToeView screen;

    // Private variables to be used
    private static int numberPlayers;
    private static int roundNumber = 0;
    private boolean gameOver;

    // List of the possible characters
    private static Character[] charList = {'X', 'O', 'A', 'C', 'Z', 'L', 'N', 'F', 'S', 'H'};

    /**
     *
     * @param model the board implementation
     * @param view the screen that is shown
     * @param np The number of players for the game
     * @post the controller will respond to actions on the view using the model.
     */
    TicTacToeController(IGameBoard model, TicTacToeView view, int np){
        this.curGame = model;
        this.screen = view;
        this.numberPlayers = np;
    }

    /**
     *
     * @param row the row of the activated button
     * @param col the column of the activated button
     * @pre row and col are in the bounds of the game represented by the view
     * @post The button pressed will show the right token and check if a player has won.
     */
    public void processButtonClick(int row, int col) {
        // Checks if game was over
        if (gameOver) {
            // Reset vars
            gameOver = false;
            roundNumber = 0;
            newGame(); // Makes new game
            return; // Leaves method
        }

        // Gets the current board position and player
        BoardPosition newPos = new BoardPosition(row, col);
        char curPlayer = charList[roundNumber % numberPlayers];

        // Validates position
        if(curGame.checkSpace(newPos)) {

            // Place the marker
            curGame.placeMarker(newPos, curPlayer);
            screen.setMarker(row, col, curPlayer);

            // Gets next player
            roundNumber++;
            char nextPlayer = charList[roundNumber % numberPlayers];
            screen.setMessage("It is " + nextPlayer + "\'s turn!");

            // Checks for a win or draw
            if(curGame.checkForWinner(newPos)) {
                screen.setMessage("Player " + curPlayer + " wins! Click any button to continue...");
                gameOver = true;
            } else if (curGame.checkForDraw()) {
                screen.setMessage("Draw! Click any button to continue...");
                gameOver = true;
            }
        }

        // Error Message
        else {
            screen.setMessage("Spot not available!");
        }
    }

    private void newGame()
    {
        screen.dispose();
        GameSetupScreen screen = new GameSetupScreen();
        GameSetupController controller = new GameSetupController(screen);
        screen.registerObserver(controller);
    }
}
