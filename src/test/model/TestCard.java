package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCard extends BaseTest {

    private Card testCard5;

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

    @Test
    void testEqualSameObject() {
        assertTrue(testCard2.equals(testCard2));
    }

    @Test
    void testEqualSameCard() {
        testCard5 = new Card("2", "Diamonds");
        assertTrue(testCard2.equals(testCard5));
    }

    @Test
    void testNotEqualDiffCard() {
        assertFalse(testCard2.equals(testCard3));
    }

    @Test
    void testNotEqualDiffObject() {
        assertFalse(testCard7.equals(null));
    }

    @Test
    void testNotEqualDiffSuit() {
        Card cardAD = new Card("A", "Diamonds");
        assertFalse(testCardA.equals(cardAD));
    }

    @Test
    void testNotEqualDiffRank() {
        Card cardKH = new Card("K", "Hearts");
        assertFalse(testCardA.equals(cardKH));
    }

}
