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

    public PokerGame() {
        //stub
    }

    public boolean getHasWon(){
        // stub
        return false;
    }

    public int getAmount(){
        // stub
        return 0;
    }

    public List<Card> getCards(){
        //stub
        return null;
    }

    public void setHasWon(boolean hasWon){
        //stub
    }

    public void setAmount(int amount){
        //stub
    }

    public void setCards(List<Card> cards){
        //stub
    }
}
