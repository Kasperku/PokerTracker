package ui;

import model.Card;
import model.PokerGame;
import model.PokerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Arrays;

// PokerTracker application
public class PokerTracker {

    private List<PokerGame> gameHistory;
    private Scanner input;
    private PokerManager pokerManager;

    // EFFECTS: run the PokerTracker application
    public PokerTracker() {
        runPokerTrackerInput();
    }

    // MODIFIES: this
    // EFFECTS: process user input and runs the application until input == "quit"
    private void runPokerTrackerInput() {
        boolean applicationActive = true;
        String command = null;

        init();

        while (applicationActive) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                applicationActive = false;
            } else {
                processCommand(command);
            }
        }
    }

    // EFFECTS: Display user menu
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add your poker games to log!");
        System.out.println("\tviewlog -> view your poker games!");
        System.out.println("\tviewsummary -> view a statistical summary of all your games!");
        System.out.println("\tedit -> edit a logged game record");
        System.out.println("\tdelete -> delete a game record");
        System.out.println("\tanalyze -> view the hands you lose with the most!");
        System.out.println("\tsortbyamountwon -> view a log of pokergames by AmountWon");
        System.out.println("\tsortbywinloss -> view a log of pokergames by AmountWon");
        System.out.println("\tquit -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("add")) {
            addNewPokerGame();
        } else if (command.equals("viewlog")) {
            viewPokerGames();
        } else if (command.equals("viewsummary")) {
            checkStatsSummary();
        } else if (command.equals("edit")) {
            editPokerGame();
        } else if (command.equals("delete")) {
            delPokerGame();
        } else if (command.equals("analyze")) {
            handsWithMostLosses();
        } else if (command.equals("sortbyamountwon")) {
            sortGamesByAmountWon();
        } else if (command.equals("sortbywinloss")) {
            sortGamesByWinLoss();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes gameHistory
    private void init() {
        gameHistory = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");

        pokerManager = new PokerManager();
    }

    // MODIFIES: this
    // EFFECTS: adds a pokerGame to gameHistory
    private void addNewPokerGame() {
        int amount;
        boolean hasWon = winLossInput();

        System.err.println("Enter amt won(+) or loss(-)");
        if (input.hasNextInt()) {
            amount = input.nextInt();
        } else {
            System.err.println("Invalid input ...");
            input.next();
            return;
        }

        List<Card> hand = cardInput();

        PokerGame pokerGame = new PokerGame(hasWon, amount, hand);
        gameHistory.add(pokerGame);
    }

    // EFFECTS: Display a list of logged poker games with their stats
    private void viewPokerGames() {
        int counter = 1;
        for (PokerGame pokergame : gameHistory) {
            boolean hasWon = pokergame.getHasWon();
            int amount = pokergame.getAmount();
            List<Card> hand = pokergame.getCards();

            System.out.println("Game " + counter);
            System.out.println("\tWin?: " + hasWon);
            System.out.println("\tAmount Won: " + amount);
            System.out.println("\tCards Held: " + hand.get(0).getRank() + " of " + hand.get(0).getSuit()
                    + ", " + hand.get(1).getRank() + " of " + hand.get(1).getSuit());

            counter += 1;
        }
    }

    // EFFECTS: Display game statistics
    private void checkStatsSummary() {
        int totalGamesPlayed = gameHistory.size();
        int winRate = pokerManager.calculateWinRate(gameHistory);
        int totalWinnings = pokerManager.calculateWinnings(gameHistory);

        System.err.println("Total games played: " + totalGamesPlayed);
        System.err.println("Total amount won : " + totalWinnings);
        System.err.println("Win Rate: " + winRate + " %");
    }

    // MODIFIES: this
    // EFFECTS: Edit the log of an existing poker game
    private void editPokerGame() {
        viewPokerGames();

        System.out.println("Which game do you want to edit? (Enter the game number)");
        int gameNumber = input.nextInt();

        if (gameNumber <= 0 || gameHistory.size() < gameNumber) {
            System.out.println("Invalid game number...");
        } else {
            PokerGame pokergame = gameHistory.get(gameNumber - 1);

            System.out.println("Win? (true/false)");
            boolean newHasWon = input.nextBoolean();

            System.out.println("Enter new amount won(+) or lost(-): ");
            int newAmount = input.nextInt();

            List<Card> newHand = cardInput();

            pokergame.setCards((newHand));
            pokergame.setAmount(newAmount);
            pokergame.setHasWon(newHasWon);
            System.out.println("Game " + gameNumber + " has been successfully updated");
        }
    }

    // MODIFIES: this
    // EFFECTS: Delete an existing poker game
    private void delPokerGame() {
        viewPokerGames();

        System.out.println("Which game do you want to delete? (Enter the game number)");
        int gameNumber = input.nextInt();

        if (gameNumber <= 0 || gameHistory.size() < gameNumber) {
            System.out.println("Invalid game number...");
        } else {
            gameHistory.remove(gameNumber - 1);
            System.err.println("Game " + gameNumber + " has been sucessfully deleted");
        }
    }

    // EFFECTS: Display hands with the most losses
    private void handsWithMostLosses() {
        Map<List<Card>, Integer> lostHands = pokerManager.analyzeLosingHands(gameHistory);
        for (Map.Entry<List<Card>, Integer> entry : lostHands.entrySet()) {
            int numLoss = entry.getValue();
            List<Card> hand = entry.getKey();

            System.err.println("# of times loss with " + hand.get(0).getRank() + " of "
                    + hand.get(0).getSuit() + "," + hand.get(1).getRank() + " of "
                    + hand.get(1).getSuit());
            System.err.println("\t" + numLoss + "times");
        }
    }

    // MODIFIES: this
    // EFFECTS: display sorted games by amount won
    private void sortGamesByAmountWon() {
        gameHistory = pokerManager.sortByAmountWon(gameHistory);
        viewPokerGames();
    }

    // MODIFIES: this
    // EFFECTS: display sorted games by amount won
    private void sortGamesByWinLoss() {
        gameHistory = pokerManager.sortByWinLoss(gameHistory);
        viewPokerGames();
    }

    // EFFECTS: return users hand after user input
    private List<Card> cardInput() {
        List<Card> hand = new ArrayList<>();

        String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] suits = { "Spades", "Clubs", "Hearts", "Diamonds" };

        for (int i = 0; i < 2; i++) {
            System.out.println("Enter the rank of your card (A,2,3,...,J,Q,K)");
            String rank = input.next();
            if (!Arrays.asList(ranks).contains(rank)) {
                System.out.println("Invalid Input ...");
                i -= 1;
                continue;
            }

            System.out.println("Enter the suit of your card (Spades, Clubs, Hearts, Diamonds)");
            String suit = input.next();

            if (!Arrays.asList(suits).contains(suit)) {
                System.out.println("Invalid Input, please enter the entire card again");
                i -= 1;
                continue;
            }

            hand.add(new Card(rank, suit));

        }
        return hand;
    }

    // EFFECTS: return users win/loss after user input
    private boolean winLossInput() {
        boolean hasWon = false;

        for (int i = 0; i < 1; i++) {
            System.out.println("Win? (true/false)");

            String winInput = input.next();

            if (winInput.equals("true") || winInput.equals("false")) {
                hasWon = Boolean.parseBoolean(winInput);
            } else {
                System.out.println("Invalid input, please try again...");
                i--; 
            }
        }
        return hasWon;
    }

}
