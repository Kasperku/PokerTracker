package model;

// Represents a card with suit and rank

public class Card {

    private String rank;
    private String suit;

    /*
     * REQUIRES: rank is one of "A", "2", "3", "4", ... "10", "J", "Q", "K"
     *           suit is one of "Spades", "Clubs", "Diamond", "Hearts"
     * EFFECTS: new card with rank and suit
     */

     public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank(){
        return this.rank;
    }

    public String getSuit(){
        return this.suit;
    }

    public void setRank(String rank){
        this.rank = rank;
    }

    public void setSuit(String suit){
        this.suit = suit;
    }
}


