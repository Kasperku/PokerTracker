package ui;

import model.PokerGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// PokerTracker application
public class PokerTracker {

    private List<PokerGame> gameHistory;
    private Scanner input;

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
        System.out.println("\tviewLog -> view your poker games!");
        System.out.println("\tviewSummary -> view a statistical summary of all your games!");
        System.out.println("\tedit -> edit a logged game record");
        System.out.println("\tdelete -> delete a game record");
        System.out.println("\tanalyze -> view the hands you lose with the most!");
        System.out.println("\tquit -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("add")) {
            // add poker game to log
        } else if (command.equals("viewLog")) {
            // view game logs
        } else if (command.equals("viewSummary")) {
            // view statistical summary
        } 
        else if (command.equals("edit")) {
            // edit pokergame from log
        }
        else if (command.equals("delete")) {
            // delete pokergame from log
        }
        else if (command.equals("analyze")) {
            // show hands with the most loss
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
    }

    // MODIFIES: this
    // EFFECTS: adds a pokerGame to gameHistory
    private void addNewPokerGame() {
        // stub
    }

    // EFFECTS: Display a list of logged poker games
    private void viewPokerGames() {
        // stub
    }

    // EFFECTS: Display game statistics
    private void checkStatsSummary() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Edit an existing poker game
    private void editPokerGame() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Delete an existing poker game
    private void delPokerGame() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sort the games in gameHistory by given criteria
    private void sortGames(String crit) {
        // stub
    }

}
