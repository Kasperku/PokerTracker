package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a single poker game having a outcome, amt won/loss, cards held.

public class PokerGame implements Writable{

    private boolean haswon; // true for win, false for lose
    private int amount; // + for amt won, - for loss
    private List<Card> cards; // represents a hand, a list consisting of 2 cards

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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("haswon", haswon);
        json.put("amount", amount);
        json.put("cards", cardsToJson());
        return json;
    }
    // EFFECTS: returns cards in pokerGame as a JSON array 
    private JSONArray cardsToJson(){
        JSONArray jsonArray = new JSONArray();

        for (Card card : cards){
            jsonArray.put(card.toJson());
        }
        return jsonArray;
    }
}
