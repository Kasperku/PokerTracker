package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPokerGame extends BaseTest {
    @BeforeEach
    void runBefore() {
        initializedPokerGame();
    }

    @Test
    void testConstructor() {
        assertTrue(testPokerGame1.getHasWon());
        assertEquals(testPokerGame1.getAmount(), 3000);
        assertEquals(testPokerGame1.getCards(), testCards1);
    } 

    @Test
    void testGetHasWon(){
        assertTrue(testPokerGame1.getHasWon());
        assertFalse(testPokerGame2.getHasWon());
    }

    @Test
    void testGetAmount(){
        assertEquals(testPokerGame1.getAmount(), 3000);
        assertEquals(testPokerGame2.getAmount(), -200);
    }

    @Test
    void testGetCards(){
        assertEquals(testCards1, testPokerGame1.getCards());
        assertEquals(testCards2, testPokerGame2.getCards());
    }

    @Test
    void testSetHasWon(){
        testPokerGame1.setHasWon(false);
        assertFalse(testPokerGame1.getHasWon());

        testPokerGame2.setHasWon(true);
        assertTrue(testPokerGame2.getHasWon());
    }

    @Test
    void testSetAmount() {
        testPokerGame1.setAmount(1000);
        assertEquals(1000, testPokerGame1.getAmount());

        testPokerGame4.setAmount(-300);
        assertEquals(-300, testPokerGame4.getAmount());
    }

    @Test
    void testSetCards() {
        List<Card> testCards = new ArrayList<>();
        testCards.add(new Card("2", "Clubs"));
        testCards.add(new Card("2", "Spades"));

        testPokerGame2.setCards(testCards);
        assertEquals(testCards, testPokerGame2.getCards());
    }


}
