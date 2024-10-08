package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCard extends BaseTest {


    @BeforeEach
    void runBefore() {
        initializedPokerGame();
    }

    @Test
    void testConstructor() {
        assertEquals("A", testCardA.getRank());
        assertEquals("Hearts", testCardA.getSuit());
    }

    @Test
    void testSetRank() {
        testCardA.setRank("3");
        assertEquals("3", testCardA.getRank());

        testCardA.setRank("7");
        assertEquals("7", testCardA.getRank());
    }

    @Test
    void testSetSuit() {
        testCardA.setSuit("Spades");
        assertEquals("Spades", testCardA.getSuit());  

        testCardA.setSuit("Diamonds");
        assertEquals("Diamonds", testCardA.getSuit());  

        testCardA.setSuit("Diamonds");
        assertEquals("Diamonds", testCardA.getSuit()); 
    }
}

