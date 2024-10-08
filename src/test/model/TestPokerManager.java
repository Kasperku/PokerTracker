package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestPokerManager {

    private PokerManager pokerManager;

    private List<Card> testCards1;
    private List<Card> testCards2;
    private Card testCardA;
    private Card testCardK;
    private Card testCard3;
    private Card testCard7;

    private PokerGame testPokerGame1;
    private PokerGame testPokerGame2;

    private List<PokerGame> testPokerGames;

    @BeforeEach
    void runBefore(){
        pokerManager = new PokerManager();
        
        // TODO: Make an abstract class

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

    @Test
    void testCalculateWinRate(){
        assertEquals(50, pokerManager.calculateWinRate(testPokerGames));
    }

    @Test
    void testCalculateWinnings(){
        assertEquals(2800, pokerManager.calculateWinnings(testPokerGames));
    }

}
