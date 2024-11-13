package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCard extends BaseTest {

    private Card testCard5;
    private Card cardWithNullRank;
    private Card cardWithNullSuit;

    @BeforeEach
    void runBefore() {
        initializedPokerGame();
        cardWithNullRank = new Card(null, "Spades");
        cardWithNullSuit = new Card("A", null);
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

    @Test
    void testHashCodeEqualForSameCard() {
        testCard5 = new Card("2", "Diamonds");
        assertEquals(testCard2.hashCode(), testCard5.hashCode());
    }

    @Test
    void testHashCodeNotEqualForDiffCard() {
        assertNotEquals(testCardA.hashCode(), testCard2.hashCode());
    }

    @Test
    void testHashCodeWithRankAndSuit() {
        int expectedHash = 31 * (31 + "A".hashCode()) + "Hearts".hashCode();
        assertEquals(expectedHash, testCardA.hashCode());
    }

    @Test
    void testHashCodeWithNullRank() {
        int expectedHash = 31 * (31 + 0) + "Spades".hashCode(); // Rank is null, so we use 0
        assertEquals(expectedHash, cardWithNullRank.hashCode());
    }

    @Test
    void testHashCodeWithNullSuit() {
        int expectedHash = 31 * (31 + "A".hashCode()) + 0; // Suit is null, so we use 0
        assertEquals(expectedHash, cardWithNullSuit.hashCode());
    }


    @Test
    void testToJson() {
        JSONObject json = testCardA.toJson();

        assertEquals("A", json.getString("rank"));
        assertEquals("Hearts", json.getString("suit"));

        assertTrue(json.has("rank"));
        assertTrue(json.has("suit"));
        assertEquals(2, json.length());

    }
}
