package ui;

/*
 * Represents the main window in which the pokerTracker
 * is displayed
 */
import javax.swing.*;

import model.Card;
import model.PokerGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class PokerTrackerGUI extends JFrame {
    private int frameWidth = 600;
    private int frameHeight = 400;
    private Map<String, JButton> buttons;

    private ArrayList<PokerGame> gameHistory;

    // EFFECTS: sets up window in which pokerTracker
    // will be displayed
    public PokerTrackerGUI() {
        super("Poker Tracker GUI");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(frameWidth, frameHeight));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        initializeButtons(mainPanel);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: opens pop up window to gather user input
    // and adds game to log
    private void addPokerGame() {
        Boolean hasWon = getHasWon();
        if (hasWon == null) {
            return;
        }
        Integer amount = getAmount(hasWon);
        if (amount == null) {
            return;
        }
        ArrayList<Card> hand = getHand();
        if (hand == null) {
            return;
        }

        PokerGame pokerGame = new PokerGame(hasWon, amount, hand);
        gameHistory.add(pokerGame);
        JOptionPane.showMessageDialog(this, "Poker game added successfully!");
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
    private void initializeButtons(JPanel mainPanel) {

        createButtons();
        linkButtonToActions();

        for (JButton button : buttons.values()) {
            mainPanel.add(button);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize buttons and stores them in map
    private void createButtons() {
        buttons = new LinkedHashMap<>();

        buttons.put("Add Game", new JButton("Add game"));
        buttons.put("View Games", new JButton("View log"));
        buttons.put("Check Stats Summary", new JButton("Check your stats"));
        buttons.put("Edit Game", new JButton("Edit log"));
        buttons.put("Delete Game", new JButton("Delete game"));
        buttons.put("Sort Games by Amount Won", new JButton("Sort log by amount aon"));
        buttons.put("Analyze Hands with Most Losses", new JButton("Analyze Hands with Most Losses"));
    }

    // MODIFIES: this
    // EFFECTS: links button to its corresponding action listener
    private void linkButtonToActions() {
        buttons.get("Add Game").addActionListener(e -> addPokerGame());
        buttons.get("View Games").addActionListener(e -> viewPokerGame());
        buttons.get("Check Stats Summary").addActionListener(e -> checkStatsSummary());
        buttons.get("Edit Game").addActionListener(e -> editPokerGame());
        buttons.get("Delete Game").addActionListener(e -> delPokerGame());
        buttons.get("Sort Games by Amount Won").addActionListener(e -> sortGamesByAmountWon());
        buttons.get("Analyze Hands with Most Losses").addActionListener(e -> handsWithMostLosses());
    }

    // EFFECTS: prompts the user for hasWon and return if valid, contiune prompt
    // otherwise.
    private Boolean getHasWon() {
        while (true) {
            String winInput = JOptionPane.showInputDialog(this, "Did you win? (true/false)");
            if (winInput != null &&
                    (winInput.equalsIgnoreCase("true")
                            || winInput.equalsIgnoreCase("false"))) {
                return Boolean.parseBoolean(winInput.toLowerCase());
            } else if (winInput == null) {
                return null;
            }
            showInvalidInputMessage();
            ;
        }
    }

    // EFFECTS: prompts the user for amount and return if valid, contiune prompt
    // otherwise.
    private Integer getAmount(boolean hasWon) {
        while (true) {
            String message = hasWon ? "Enter amount won:" : "Enter amount lost as a negative number:";
            String amountInput = JOptionPane.showInputDialog(this, message);

            if (amountInput == null) {
                return null;
            }

            try {
                int amount = Integer.parseInt(amountInput);

                if ((hasWon && amount > 0) || (!hasWon && amount < 0)) {
                    return amount;
                } else {
                    showInvalidInputMessage();
                }
            } catch (NumberFormatException e) {
                showInvalidInputMessage();
            }
        }
    }

    // EFFECTS: prompts the user for hand and return if valid, contiune prompt
    // otherwise.
    private ArrayList<Card> getHand() {
        ArrayList<Card> hand = new ArrayList<>();

        String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] suits = { "Spades", "Clubs", "Hearts", "Diamonds" };

        for (int i = 0; i < 2; i++) {
            String rank = null;
            String suit = null;

            while (rank == null) {
                String inputRank = JOptionPane.showInputDialog(this,
                        "Enter the rank of card " + (i + 1) + " (A, 2, 3, ..., J, Q, K):");
                if (inputRank == null) {
                    return null;
                }
                if (Arrays.asList(ranks).contains(inputRank)) {
                    rank = inputRank;
                } else {
                    showInvalidInputMessage();
                }
            }

            while (suit == null) {
                String inputSuit = JOptionPane.showInputDialog(this,
                        "Enter the suit of card " + (i + 1) + " (Spades, Clubs, Hearts, Diamonds):");
                if (inputSuit == null) {
                    return null;
                }
                if (Arrays.asList(suits).contains(inputSuit)) {
                    suit = inputSuit;
                } else {
                    showInvalidInputMessage();
                }
            }

            i = addCardToHandIfNoDuplicate(hand, i, rank, suit);
        }
        return hand;
    }

    // EFFECTS: adds a card to hand if its not a duplicate
    // returns index i if no duplicate, or index i - 1 if duplicate

    private int addCardToHandIfNoDuplicate(ArrayList<Card> hand, int i, String rank, String suit) {
        Card card = new Card(rank, suit);
        if (hand.contains(card)) {
            JOptionPane.showMessageDialog(this, "Duplicate card detected. Please enter a different card.");
            return i - 1;
        } else {
            hand.add(card);
        }
        return i;
    }

    // EFFECTS: show invalid input message
    private void showInvalidInputMessage() {
        JOptionPane.showMessageDialog(this, "Invalid input, please try again...");
    }

}
