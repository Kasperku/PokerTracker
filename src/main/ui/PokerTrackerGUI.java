package ui;

/*
 * Represents the main window in which the pokerTracker
 * is displayed
 */
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PokerTrackerGUI extends JFrame {
    private int frameWidth = 600;
    private int frameHeight = 400;
    private Map<String, JButton> buttons;

    // EFFECTS: sets up window in which pokerTracker
    // will be displayed
    public PokerTrackerGUI() {
        super("Poker Tracker GUI");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setPreferredSize(new Dimension(frameWidth, frameHeight)); 

        initializeButtons();

        pack(); 
        setLocationRelativeTo(null); 
        setVisible(true); 
    }


    // MODIFIES: this
    // EFFECTS: opens pop up window to gather user input
    // and adds game to log
    private void addPokerGame() {
        // stub
    }

    // EFFECTS: display a list of all logged poker games with
    // the individual game statistics
    private void viewPokerGame() {
        // stub
    }

    // EFFECTS: display statistics across all recorded games
    private void checkStatsSummary() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens a pop up window to allow the user to edit details of a
    // selected poker game in the log
    private void editPokerGame() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens pop up window to gather user input
    // and remove game from log
    private void delPokerGame() {
        // stub
    }

    // EFFECTS: displays hands with the most losses
    private void handsWithMostLosses() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: displays the sorted list by amountwon
    private void sortGamesByAmountWon() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: add buttons to main panel
    private void initializeButtons(){
        // stub
    }

    // MODIFIES: this
    // EFFECTS: initialize buttons and stores them in map
    private void createButtons(){
        // stub
    }

    // MODIFIES: this
    // EFFECTS: links button to its corresponding action listener
    private void linkButtonToActions(){
        // stub
    }

}
