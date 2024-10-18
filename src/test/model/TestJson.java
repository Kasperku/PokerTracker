package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class TestJson {

    protected void checkPokerGame(boolean hasWon, int amount, List<Card> expectedHand, PokerGame pokerGame) {
        assertEquals(hasWon, pokerGame.getHasWon());
        assertEquals(amount, pokerGame.getAmount());
        assertEquals(expectedHand, pokerGame.getCards());
    }
}
