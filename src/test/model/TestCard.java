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
        testCardA.SetRank("3");
        assertEquals("3", testCardA.getRank());

        testCardA.SetRank("7");
        assertEquals("7", testCardA.getRank());
    }

    @Test
    void testSetSuit() {
        testCardA.SetSuit("Spades");
        assertEquals("Spades", testCardA.getSuit());  

        testCardA.SetSuit("Diamonds");
        assertEquals("Diamonds", testCardA.getSuit());  

        testCardA.SetSuit("Diamonds");
        assertEquals("Diamonds", testCardA.getSuit()); 
    }
}

