package model;

import java.util.List;

// Represents a single poker game having a outcome, amt won/loss, cards held.

public class PokerGame {
    private boolean haswon;
    int amount;
    private List<Card> cards;

    /*
     * REQUIRES: cards must be of length 2
     * EFFECTS: new PokerGame with outcome, amount won, and cards held.
     */

    public PokerGame(boolean haswon, int amount, List<Card> cards) {
        this.haswon = haswon;
        this.amount = amount;
        this.cards = cards;
    }
}
