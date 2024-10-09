package ui;

import model.Card;
import model.PokerGame;
import model.PokerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
    // EFFECTS: process user input
    private void runPokerTrackerInput() {
        boolean applicationActive = true;
        String command = null;

        init();
        
        while (applicationActive){
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")){
                applicationActive = false;
            }
            else{
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
        } 
        else if (command.equals("edit")) {
            editPokerGame();
        }
        else if (command.equals("delete")) {
            delPokerGame();
        }
        else if (command.equals("analyze")) {
            handsWithMostLosses();
        }
        else if (command.equals("sortbyamountwon")) {
            sortGamesByAmountWon();
        }
        else if (command.equals("sortbywinloss")) {
            sortGamesByWinLoss();
        }
        else {
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
        System.out.println("Win? (true/false)");
        boolean hasWon = input.nextBoolean();

        System.err.println("Enter amt won(+) or loss(-)");
        int amount = input.nextInt();

        List<Card> hand = new ArrayList<>();

        for (int i = 0; i < 2; i++){
            System.out.println("Enter the rank of your card (A,2,3,...,J,Q,K)");
            String rank = input.next();
            System.out.println("Enter the suit of your card (Spades, Clubs, Hearts, Diamonds)");
            String suit = input.next();

            hand.add(new Card(rank, suit));
        }

        PokerGame pokerGame = new PokerGame(hasWon, amount, hand);
        gameHistory.add(pokerGame);
    }

    // EFFECTS: Display a list of logged poker games
    private void viewPokerGames() {
        int counter = 1;
        for (PokerGame pokergame: gameHistory){
            boolean hasWon = pokergame.getHasWon();
            int amount = pokergame.getAmount();
            List<Card> hand = pokergame.getCards();

            System.out.println("Game " + counter);
            System.out.println("\tOutcome: " + hasWon);
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
    // EFFECTS: Edit an existing poker game
    private void editPokerGame() {
        viewPokerGames();

        System.out.println("Which game do you want to edit? (Enter the game number)");
        int gameNumber = input.nextInt();

        if (gameNumber <=0 || gameHistory.size() < gameNumber){
            System.out.println("Invalid game number...");
        }
        else{
            PokerGame pokergame = gameHistory.get(gameNumber - 1);

            System.out.println("Win? (true/false)");
            boolean newHasWon = input.nextBoolean();

            System.out.println("Enter new amount won(+) or lost(-): ");
            int newAmount = input.nextInt();
            
            List<Card> newHand = new ArrayList<>();
            for (int i = 0; i < 2; i++){
                System.out.println("Enter the new rank of your card (A,2,3,...,J,Q,K)");
                String rank = input.next();
                System.out.println("Enter the new suit of your card (Spades, Clubs, Hearts, Diamonds)");
                String suit = input.next();
                newHand.add(new Card(rank, suit));
            }
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

        if (gameNumber <=0 || gameHistory.size() < gameNumber){
            System.out.println("Invalid game number...");
        }
        else{
            gameHistory.remove(gameNumber - 1);
            System.err.println("Game " + gameNumber + " has been sucessfully deleted");
        }
    }

    // MODIFIES: this
    // EFFECTS: Display hands with the most losses
    private void handsWithMostLosses() {
        Map<List<Card>, Integer> lostHands = pokerManager.analyzeLosingHands(gameHistory); 
        for (Map.Entry<List<Card>, Integer> entry : lostHands.entrySet()){
            int numLoss = entry.getValue();
            List<Card> hand = entry.getKey();

            System.err.println("# of times loss with " + hand.get(0).getRank() + " of " + 
            hand.get(0).getSuit() + "," + hand.get(1).getRank() + " of " + 
            hand.get(1).getSuit());
            System.err.println("\t" + numLoss + "times");
        }
    }

    // MODIFIES: this
    // EFFECTS: display sorted games by amount won
    private void sortGamesByAmountWon() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: display sorted games by amount won
    private void sortGamesByWinLoss() {
        // stub
    }

}
