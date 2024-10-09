package model;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTest {

    protected PokerManager pokerManager;

    protected List<Card> testCards1;
    protected List<Card> testCards2;
    protected List<Card> testCards3;
    protected Card testCardA;
    protected Card testCardK;
    protected Card testCard3;
    protected Card testCard7;
    protected Card testCardJ;
    protected Card testCard2;

    protected PokerGame testPokerGame1;
    protected PokerGame testPokerGame2;
    protected PokerGame testPokerGame3;
    protected PokerGame testPokerGame4;

    protected void initializedPokerGame() {
        testCardA = new Card("A", "Hearts");
        testCardK = new Card("K", "Diamonds");
        testCard3 = new Card("3", "Spades");
        testCard7 = new Card("7", "Clubs");
        testCardJ = new Card("J", "Clubs");
        testCard2 = new Card("2", "Diamonds");

        testCards1 = new ArrayList<>();
        testCards1.add(testCardA);
        testCards1.add(testCardK);
        testCards2 = new ArrayList<>();
        testCards2.add(testCard3);
        testCards2.add(testCard7);
        testCards3 = new ArrayList<>();
        testCards3.add(testCardJ);
        testCards3.add(testCard2);

        testPokerGame1 = new PokerGame(true, 3000, testCards1);
        testPokerGame2 = new PokerGame(false, -200, testCards2);
        testPokerGame3 = new PokerGame(true, 200, testCards3);
        testPokerGame4 = new PokerGame(false, -1000, testCards3);
    }
}
