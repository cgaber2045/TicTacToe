package cpsc2150.extendedTicTacToe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.*;
import java.util.List;

/**
 * This class is the view of our tic tac toe game
 * our view has a message area, and a list of buttons
 * the buttons will be arranges in a ROWS_IN_BUTTON_PANEL x COLUMNS_IN_BUTTON_PANEL Gris
 * Players will use the buttons to make their next move.
 * All events will be passed to the controller
 *
 * You do not need to make any changes to this code, but you do need to understand much of what is happening
 */

public final class TicTacToeView extends JFrame implements ActionListener {

    private TicTacToeController controller;

    private final JTextArea message;
    private final List<JButton>  buttons;

    private static final int LINES_IN_TEXT = 4, LINE_TEXT_LENGTHS = 20;
    private static int ROWS_IN_BUTTON_PANEL;

    private static int COLUMNS_IN_BUTTON_PANEL;
    private float fontSize = 40;



    /**
     *
     * @param row the number of rows you want in the tic tac toe grid
     * @param col the number of columns you want in the tic tac toe grid
     * @pre row > 0 and col > 0
     * @post a functional screen with a row x col grid of buttons will be created
     */
    public TicTacToeView(int row, int col)
    {
        //call super constructor to make JFrame
        super("Tic Tac Toe Game");
        ROWS_IN_BUTTON_PANEL = row;
        COLUMNS_IN_BUTTON_PANEL = col;





        //create the widgets
        this.message = new JTextArea("It is X\'s turn. ", LINES_IN_TEXT, LINE_TEXT_LENGTHS);
        //read only
        this.message.setEditable(false);
        this.message.setLineWrap(true);
        this.message.setWrapStyleWord(true);
        message.setFont(message.getFont().deriveFont(fontSize));




        //Add buttons to our list of buttons
        buttons = new ArrayList<JButton>();
        for(int i=0; i < ROWS_IN_BUTTON_PANEL * COLUMNS_IN_BUTTON_PANEL; i++)
        {
            JButton button = new JButton("");
            button.setPreferredSize(new Dimension(50, 50));
            button.setMinimumSize(new Dimension(50, 50));
            button.setMaximumSize(new Dimension(50, 50));
            button.setFont(message.getFont().deriveFont(fontSize));
            buttons.add(button);

        }




        //create layout for the screen
        this.setLayout(new GridLayout(ROWS_IN_BUTTON_PANEL + 1, 1));
        //add our message box on top
        this.add(message);



        //add our rows of buttons
        for(int i=0; i<ROWS_IN_BUTTON_PANEL; i++)
        {
            //create a panel to hold a row of buttons
            JPanel buttonPanel = new JPanel(new GridLayout(1, COLUMNS_IN_BUTTON_PANEL));
            for(int j=0; j<COLUMNS_IN_BUTTON_PANEL; j++)
            {
                //add a button in each column
                int index = (i*COLUMNS_IN_BUTTON_PANEL) + j;
                //add the screen as a listener
                buttons.get(index).addActionListener(this);
                buttonPanel.add(buttons.get(index));


            }
            //row is complete, add it to out screen
            this.add(buttonPanel);

        }




        // Start the main application window --------------------------------

        /*
         * Make sure the main window is appropriately sized for the widgets in
         * it, that it exits this program when closed, and that it becomes
         * visible to the user now
         */
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * Register argument as observer/listener of this; this must be done first,
     * before any other methods of this class are called.
     *
     * @param c
     *            controller to register
     * @pre c is a valid controller for this view
     * @post this.controller = c
     */
    public void registerObserver(TicTacToeController c) {
        this.controller = c;
    }

    /**
     *
     * @param event the event on the screen that is observed
     * @post button events will be sent to the controller
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going; this matters only if
         * processing the event might take a noticeable amount of time as seen
         * by the user
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press; in each case,
         * tell the controller to do whatever is needed to update the model and
         * to refresh the view
         */
        Object source = event.getSource();

        //is the source one of our buttons?
        if(buttons.contains(source))
        {
            //get row and columns
            int index =  buttons.indexOf(source);

            //index integer divided by number of columns gives you the row, and modulus division gives you the column
            int r = index/ COLUMNS_IN_BUTTON_PANEL;
            int c =  index % COLUMNS_IN_BUTTON_PANEL;
            //call the controller event
            controller.processButtonClick(r, c);



        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     *
     * @param m the message to display in our text area
     * @ensures the this.message text = m
     */
    public void setMessage(String m)
    {
        message.setText(m);
    }

    /**
     *
     * @param row the row of the button in our grid
     * @param col the column of the button in our grid
     * @param player the player who will now claim that button
     * @pre 0<= row < ROWS_IN_BUTTON_PANEL and 0<= col <= COLUMNS_IN_BUTTON_PANEL
     * @post the button at row, col will display the character for the player
     *
     */
    public void setMarker(int row, int col, char player)
    {
        //find index
        int index = (row * COLUMNS_IN_BUTTON_PANEL) + col;
        //set the text of the button
        buttons.get(index).setText(Character.toString(player));

    }

}
