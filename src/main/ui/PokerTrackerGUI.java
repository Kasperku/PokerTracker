package ui;

import javax.swing.*;

import model.Card;
import model.EventLog;
import model.PokerGame;
import model.PokerManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Represents the main window in which the pokerTracker
 * is displayed
 */

public class PokerTrackerGUI extends JFrame {
    private int frameWidth = 600;
    private int frameHeight = 400;
    private Map<String, JButton> buttons;

    private PokerManager pokerManager;

    private String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
    private String[] suits = { "Spades", "Clubs", "Hearts", "Diamonds" };

    private static final String JSON_STORE = "./data/pokergame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: sets up window in which pokerTracker
    // will be displayed
    public PokerTrackerGUI() {
        super("Poker Tracker GUI");
        pokerManager = new PokerManager();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(frameWidth, frameHeight));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        initializeButtons(mainPanel);
        addImageToPanel(mainPanel);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Add a window listener to print the event log when the application exits
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
    }

    private void printLog(EventLog el) {
        for (model.Event next : el) {
            System.out.println(next); 
        }
    }

    // MODIFIES: mainPanel
    // EFFECTS: Adds a resized image to the top area of the main panel
    private void addImageToPanel(JPanel mainPanel) {
        // reference:
        // https://www.freepik.com/free-vector/poker-background-with-golden-cards-realistic-dice_15717693.htm#fromView=keyword&page=23&position=20&uuid=edf0c290-071e-454f-a591-8eeb0a4f0580
        int width = 275;
        int height = 225;
        ImageIcon pokerIcon = new ImageIcon("src/main/resources/pokerimage.jpg");
        Image pokerImage = pokerIcon.getImage();

        Image resizedPokerImage = pokerImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedPokerIcon = new ImageIcon(resizedPokerImage);

        JLabel imageLabel = new JLabel(resizedPokerIcon);
        mainPanel.add(imageLabel, BorderLayout.NORTH);

    }

    // MODIFIES: this
    // EFFECTS: opens pop up window to gather user input
    // and adds game to log
    private void addPokerGame(ActionEvent e) {
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
        pokerManager.addPokerGame(pokerGame);
        JOptionPane.showMessageDialog(this, "Poker game added successfully!");
    }

    // EFFECTS: display a list of all logged poker games with
    // the individual game statistics
    private void viewPokerGame(ActionEvent e) {
        String title = "Poker Game Log";

        if (pokerManager.getGameHistory().isEmpty()) {
            showMessage("No poker games logged yet", title);
            return;
        }

        String gameLog = String.join("\n\n", getGameDescription());

        JTextArea textArea = new JTextArea(gameLog);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: display statistics across all recorded games
    private void checkStatsSummary(ActionEvent e) {
        String title = "Poker Game Statistic Summary";
        int totalGamesPlayed = pokerManager.getGameHistory().size();
        int winRate = pokerManager.calculateWinRate();
        int totalWinnings = pokerManager.calculateWinnings();

        String statsSummary = "Total games played: " + totalGamesPlayed + "\n"
                + "Total amount won: " + totalWinnings + "\n"
                + "Win Rate: " + winRate
                + " %";

        JOptionPane.showMessageDialog(this, statsSummary, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: opens a pop up window to allow the user to edit details of a
    // selected poker game in the log
    private void editPokerGame(ActionEvent e) {
        String title = "Edit Poker Game";

        if (pokerManager.getGameHistory().isEmpty()) {
            showMessage("No games to edit", title);
        }

        String[] gameDescriptions = getGameDescription();
        String selectedGame = showDropdownList(gameDescriptions, "Select a game to edit", title);

        if (selectedGame == null) {
            return;
        }

        int gameIndex = getSelectedGameIndex(gameDescriptions, selectedGame);

        Boolean hasWon = getHasWon();
        Integer amount = getAmount(hasWon);
        List<Card> hand = getHand();

        if (hand == null || amount == null || hasWon == null) {
            return;
        }

        PokerGame newGame = new PokerGame(hasWon, amount, hand);
        pokerManager.editPokerGame(gameIndex, newGame);

        showMessage("Successfully Updated!", title);
    }

    // REQUIRES: gameIndex is valid in gameHistory
    // MODIFIES: this
    // EFFECTS: opens pop up window to gather user input
    // and remove game from log
    private void delPokerGame(ActionEvent e) {
        String title = "Delete Game";
        if (pokerManager.getGameHistory().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No poker games to delete", title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] gameDescription = getGameDescription();
        String selectedGame = showDropdownList(gameDescription, "Select a game to delete", title);

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
        String title = "Delete Game";
        if (confirm == JOptionPane.YES_OPTION) {
            pokerManager.delPokerGame(gameIndex);
            showMessage("Game successfully deleted", title);
        }
    }

    // EFFECTS: show message to GUI
    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: pop up window asking for confirmation to delete pokerGame
    private int getConfirmToDelete(String selectedGame) {
        String title = "Confirm Deleteion";
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + selectedGame + "?",
                title,
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
        String selectedGame = (String) JOptionPane.showInputDialog(this, message, title,
                JOptionPane.QUESTION_MESSAGE, null, gameDescription, gameDescription[0]);
        return selectedGame;
    }

    // EFFECTS: returns an array of descriptions for each game in gameHistory
    private String[] getGameDescription() {
        List<PokerGame> gameHistory = pokerManager.getGameHistory();
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
    private void handsWithMostLosses(ActionEvent e) {
        Map<List<Card>, Integer> lostHands = pokerManager.analyzeLosingHands();
        String output = "Times lost with hand:\n";
        String title = "Times lost with Hand";

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

        JOptionPane.showMessageDialog(this, output, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: displays the sorted list by amountwon
    private void sortGamesByAmountWon(ActionEvent e) {

        pokerManager.sortByAmountWon();
        String[] gameDescriptions = getGameDescription();
        String output = "Poker Games Sorted by Amount Won:\n";
        String title = "Sorted Poker Games by Amount Won";

        for (String description : gameDescriptions) {
            output += description + "\n";
        }

        // Display the sorted games in a dialog box
        JOptionPane.showMessageDialog(this, output, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: saves the poker log to file and shows a confirmation dialog
    private void savePokerLog(ActionEvent evt) {
        try {
            jsonWriter.open();
            jsonWriter.write(pokerManager.getGameHistory());
            jsonWriter.close();
            showMessage("Saved poker log to " + JSON_STORE, "Save Successfully!");
        } catch (FileNotFoundException e) {
            showMessage("Unable to save to " + JSON_STORE, "Saved Failed");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the poker log from file and shows a confirmation dialog
    private void loadPokerLog(ActionEvent evt) {
        try {
            List<PokerGame> loadedGames = jsonReader.read();
            pokerManager.setGameHistory(loadedGames);
            showMessage("Poker log loaded successfully from " + JSON_STORE, "Load Successful");

        } catch (IOException e) {
            showMessage("Unable to read from file: " + JSON_STORE, "Load Failed");
        }
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
        buttons.put("Save Poker Log", new JButton("Save Poker Log"));
        buttons.put("Load Poker Log", new JButton("Load Poker Log"));

    }

    // MODIFIES: this
    // EFFECTS: links button to its corresponding action listener
    private void linkButtonToActions() {
        buttons.get("Add Game").addActionListener(this::addPokerGame);
        buttons.get("View Games").addActionListener(this::viewPokerGame);
        buttons.get("Check Stats Summary").addActionListener(this::checkStatsSummary);
        buttons.get("Edit Game").addActionListener(this::editPokerGame);
        buttons.get("Delete Game").addActionListener(this::delPokerGame);
        buttons.get("Sort Games by Amount Won").addActionListener(this::sortGamesByAmountWon);
        buttons.get("Analyze Hands with Most Losses").addActionListener(this::handsWithMostLosses);
        buttons.get("Save Poker Log").addActionListener(this::savePokerLog);
        buttons.get("Load Poker Log").addActionListener(this::loadPokerLog);

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

        for (int i = 0; i < 2; i++) {
            String rank = getRank(i);
            String suit = getSuit(i);

            if (rank == null || suit == null) {
                return null;
            }

            i = addCardToHandIfNoDuplicate(hand, i, rank, suit);
        }
        return hand;
    }

    // EFFECTS: prompts user for, and return valid rank, null if user cancels
    private String getRank(int i) {
        while (true) {
            String inputRank = JOptionPane.showInputDialog(this,
                    "Enter the rank of card " + (i + 1) + " (A, 2, 3, ..., J, Q, K):");
            if (inputRank == null) {
                return null;
            }
            if (Arrays.asList(ranks).contains(inputRank)) {
                return inputRank;
            } else {
                showInvalidInputMessage();
            }
        }
    }

    // EFFECTS: prompts user for, and return valid suit, null if user cancels
    private String getSuit(int i) {
        while (true) {
            String inputSuit = JOptionPane.showInputDialog(this,
                    "Enter the suit of card " + (i + 1) + " (Spades, Clubs, Hearts, Diamonds):");
            if (inputSuit == null) {
                return null;
            }
            if (Arrays.asList(suits).contains(inputSuit)) {
                return inputSuit;
            } else {
                showInvalidInputMessage();
            }
        }
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
