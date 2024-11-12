package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a card with suit and rank

public class Card implements Writable {

    private String rank;
    private String suit;

    /*
     * REQUIRES: rank is one of "A", "2", "3", "4", ... "10", "J", "Q", "K"
     * suit is one of "Spades", "Clubs", "Diamond", "Hearts"
     * EFFECTS: new card with rank and suit
     */

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return this.rank;
    }

    public String getSuit() {
        return this.suit;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (rank == null) {
            if (other.rank != null)
                return false;
        } else if (!rank.equals(other.rank))
            return false;
        if (suit == null) {
            if (other.suit != null)
                return false;
        } else if (!suit.equals(other.suit))
            return false;
        return true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("rank", rank);
        json.put("suit", suit);
        return json;
    }
}
