package model;

import java.util.List;

// Represents a single poker game having a outcome, amt won/loss, cards held.

public class PokerGame {

    private boolean haswon; // true for win, false for lose
    private int amount; // + for amt won, - for loss
    private List<Card> cards; // a list consisting of 2 cards

    /*
     * REQUIRES: cards must be of length 2, card cannot be duplicate
     * EFFECTS: new PokerGame with outcome, amount won, and cards held.
     */

    public PokerGame() {
        //stub
    }
}
