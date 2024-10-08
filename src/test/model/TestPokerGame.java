package model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPokerGame {
    private PokerGame testPokerGame;
    private List<Card> testCards;
    private Card testCardA;
    private Card testCardK;

    @BeforeEach
    void runBefore() {
        testCardA = new Card("A", "Heart");
        testCardK = new Card("K", "Diamonds");

        testCards = new ArrayList<>();
        testCards.add(testCardA);
        testCards.add(testCardK);

        testPokerGame = new PokerGame(true, 3000, testCards);
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
}
