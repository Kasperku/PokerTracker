package model;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTest {

    protected PokerManager pokerManager;

    protected List<Card> testCards1;
    protected List<Card> testCards2;
    protected Card testCardA;
    protected Card testCardK;
    protected Card testCard3;
    protected Card testCard7;

    protected PokerGame testPokerGame1;
    protected PokerGame testPokerGame2;

    protected List<PokerGame> testPokerGames;

    protected void initializedPokerGame(){
        testCardA = new Card("A", "Heart");
        testCardK = new Card("K", "Diamonds");
        testCard3 = new Card("3", "Spades");
        testCard7 = new Card("7", "Clubs");

        testCards1 = new ArrayList<>();
        testCards1.add(testCardA);
        testCards1.add(testCardK);
        testCards2 = new ArrayList<>();
        testCards2.add(testCard3);
        testCards2.add(testCard7);

        testPokerGame1 = new PokerGame(true, 3000, testCards1);
        testPokerGame2 = new PokerGame(false, -200, testCards2);
    }
}
