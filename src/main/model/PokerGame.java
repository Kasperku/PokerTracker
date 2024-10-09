package model;

import java.util.List;

// Represents a single poker game having a outcome, amt won/loss, cards held.

public class PokerGame {

    private boolean haswon; // true for win, false for lose
    private int amount; // + for amt won, - for loss
    private List<Card> cards; // a list consisting of 2 cards

    /*
     * REQUIRES: cards must be of length 2, cards cannot contain duplicate cards
     * EFFECTS: new PokerGame with outcome, amount won, and cards held.
     */

    public PokerGame(boolean haswon, int amount, List<Card> cards) {
        this.haswon = haswon;
        this.amount = amount;
        this.cards = cards;
    }

    public boolean getHasWon() {
        return this.haswon;
    }

    public int getAmount() {
        return this.amount;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void setHasWon(boolean hasWon) {
        this.haswon = hasWon;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
