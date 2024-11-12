package ui;

/*
 * Represents the main window in which the pokerTracker
 * is displayed
 */
import javax.swing.*;

import model.Card;
import model.PokerGame;
import model.PokerManager;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class PokerTrackerGUI extends JFrame {
    private int frameWidth = 600;
    private int frameHeight = 400;
    private Map<String, JButton> buttons;

    private List<PokerGame> gameHistory;
    private PokerManager pokerManager;

    // EFFECTS: sets up window in which pokerTracker
    // will be displayed
    public PokerTrackerGUI() {
        super("Poker Tracker GUI");
        gameHistory = new ArrayList<>();
        pokerManager = new PokerManager();

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
        if (gameHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No poker games logged yet", "Game Log",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder gameLog = new StringBuilder();
        int counter = 1;

        for (PokerGame pokerGame : gameHistory) {
            boolean hasWon = pokerGame.getHasWon();
            int amount = pokerGame.getAmount();
            List<Card> hand = pokerGame.getCards();

            gameLog.append("Game ").append(counter).append(":\n");
            gameLog.append("\tWin?: ").append(hasWon ? "Yes" : "No").append("\n");
            gameLog.append("\tAmount Won: ").append(amount).append("\n");
            gameLog.append("\tCards Held: ").append(hand.get(0).getRank()).append(" of ").append(hand.get(0).getSuit())
                    .append(", ").append(hand.get(1).getRank()).append(" of ").append(hand.get(1).getSuit())
                    .append("\n\n");

            counter++;
        }

        JTextArea textArea = new JTextArea(gameLog.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

        JOptionPane.showMessageDialog(this, scrollPane, "Poker Game Log", JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: display statistics across all recorded games
    private void checkStatsSummary() {
        int totalGamesPlayed = gameHistory.size();
        int winRate = pokerManager.calculateWinRate(gameHistory);
        int totalWinnings = pokerManager.calculateWinnings(gameHistory);

        String statsSummary = "Total games played: " + totalGamesPlayed + "\n"
                + "Total amount won: " + totalWinnings + "\n"
                + "Win Rate: " + winRate
                + " %";

        JOptionPane.showMessageDialog(this, statsSummary, "Poker Game Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: opens a pop up window to allow the user to edit details of a
    // selected poker game in the log
    private void editPokerGame() {
        String title = "Edit Poker Game";
        if (gameHistory.isEmpty()) {
            showMessage("No games to edit", title);
        }

        String[] gameDescriptions = getGameDescription();
        String selectedGame = showDropdownList(gameDescriptions, "Select a game to edit", title);

        if (selectedGame == null) {
            return; // User canceled the selection
        }

        int gameIndex = getSelectedGameIndex(gameDescriptions, selectedGame);
        PokerGame pokerGame = gameHistory.get(gameIndex);

        // Step 2: Gather new details using existing helper methods
        Boolean hasWon = getHasWon();
        Integer amount = getAmount(hasWon);
        List<Card> hand = getHand();

        if (hand == null || amount == null || hasWon == null) {
            return; // User canceled
        }

        pokerGame.setHasWon(hasWon);
        pokerGame.setAmount(amount);
        pokerGame.setCards(hand);

        showMessage("Successfully Updated!", title);
    }

    // REQUIRES: gameIndex is valid in gameHistory
    // MODIFIES: this
    // EFFECTS: opens pop up window to gather user input
    // and remove game from log
    private void delPokerGame() {
        if (gameHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No poker games to delete", "Delete Game", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] gameDescription = getGameDescription();
        String selectedGame = showDropdownList(gameDescription, "Select a game to delete", "Delete Poker Game");

        if (selectedGame == null) {
            return;
        }

        int gameIndex = getSelectedGameIndex(gameDescription, selectedGame);
        int confirm = getConfirmToDelete(selectedGame);
        deleteGameIfConfirm(gameIndex, confirm);
    }

    // REQUIRES: gameIndex is a valid in gameHistory
    // MODIFIES: this
    // EFFECTS: remove game from log at gameIndex if confirm is YES_OPTION
    private void deleteGameIfConfirm(int gameIndex, int confirm) {
        if (confirm == JOptionPane.YES_OPTION) {
            gameHistory.remove(gameIndex);
            showMessage("Game successfully deleted", "Delete Game");
        }
    }

    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: pop up window asking for confirmation to delete pokerGame
    private int getConfirmToDelete(String selectedGame) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + selectedGame + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
        return confirm;
    }

    // EFFECTS: returns the index of selectedGame within gameDescription; returns -1
    // if not found
    private int getSelectedGameIndex(String[] gameDescription, String selectedGame) {
        int gameIndex = -1;
        for (int i = 0; i < gameDescription.length; i++) {
            if (gameDescription[i].equals(selectedGame)) {
                gameIndex = i;
                break;
            }
        }
        return gameIndex;
    }

    // EFFECTS: displays a dropdown list containing gameDescription elements and
    // returns the selected game as a String, or null if user clicks canceled
    private String showDropdownList(String[] gameDescription, String message, String title) {
        String selectedGame = (String) JOptionPane.showInputDialog(
                this,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                gameDescription,
                gameDescription[0]);
        return selectedGame;
    }

    // EFFECTS: returns an array of descriptions for each game in gameHistory
    private String[] getGameDescription() {
        String[] gameDescription = new String[gameHistory.size()];
        for (int i = 0; i < gameHistory.size(); i++) {
            PokerGame pokerGame = gameHistory.get(i);
            List<Card> hand = pokerGame.getCards();

            gameDescription[i] = "Game " + (i + 1) + ": " + (pokerGame.getHasWon() ? "Win, " : "Loss, ")
                    + pokerGame.getAmount() + ", " + hand.get(0).getRank() + " of " + hand.get(0).getSuit()
                    + ", " + hand.get(1).getRank() + " of " + hand.get(1).getSuit();
        }
        return gameDescription;
    }

    // EFFECTS: displays hands with the most losses
    private void handsWithMostLosses() {
        Map<List<Card>, Integer> lostHands = pokerManager.analyzeLosingHands(gameHistory);
        String output = "Times lost with hand:\n";

        for (Map.Entry<List<Card>, Integer> entry : lostHands.entrySet()) {
            int numLoss = entry.getValue();
            List<Card> hand = entry.getKey();

            output += "# of times loss with ";

            for (int i = 0; i < 2; i++) {
                Card card = hand.get(i);
                output += card.getRank() + " of " + card.getSuit();
                if (i < 1) {
                    output += ", ";
                }
            }
            output += ": " + numLoss + " times\n";
        }

        // Show the output in a dialog box
        JOptionPane.showMessageDialog(this, output, "Times Lost With Hand", JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: displays the sorted list by amountwon
    private void sortGamesByAmountWon() {

        gameHistory = pokerManager.sortByAmountWon(gameHistory);
        String[] gameDescriptions = getGameDescription();
        String output = "Poker Games Sorted by Amount Won:\n";

        for (String description : gameDescriptions) {
            output += description + "\n";
        }

        // Display the sorted games in a dialog box
        JOptionPane.showMessageDialog(this, output, "Sorted Poker Games by Amount Won",
                JOptionPane.INFORMATION_MESSAGE);
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
            if (winInput != null
                    && (winInput.equalsIgnoreCase("true")
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
