package ui;

/*
 * Represents the main window in which the pokerTracker
 * is displayed
 */
import javax.swing.*;

import model.Card;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PokerTrackerGUI extends JFrame {
    private int frameWidth = 600;
    private int frameHeight = 400;

    // EFFECTS: sets up window in which pokerTracker
    // will be displayed
    public PokerTrackerGUI() {
        super("Poker Tracker GUI");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setPreferredSize(new Dimension(frameWidth, frameHeight)); 

        pack(); 
        setLocationRelativeTo(null); 
        setVisible(true); 
    }


    // MODIFIES: this
    // EFFECTS: opens pop up window to gather user input
    // and adds game to log
    private void handleAddPokerGame() {
        // stub
    }

    // EFFECTS: display a list of all logged poker games with
    // the individual game statistics
    private void handleViewPokerGame() {
        // stub
    }

    // EFFECTS: display statistics across all recorded games
    private void handleCheckStatsSummary() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens a pop up window to allow the user to edit details of a
    // selected poker game in the log
    private void handleEditPokerGame() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens pop up window to gather user input
    // and remove game from log
    private void handleDelPokerGame() {
        // stub
    }

    // EFFECTS: displays hands with the most losses
    private void handleHandsWithMostLosses() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: displays the sorted list by amountwon
    private void handleSortGamesByAmountWon() {
        // stub
    }

}
