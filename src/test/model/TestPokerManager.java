package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPokerManager extends BaseTest {

    @BeforeEach
    void runBefore() {
        pokerManager = new PokerManager();
        initializedPokerGame(); 

        pokerManager.addPokerGame(testPokerGame1); // +3000
        pokerManager.addPokerGame(testPokerGame2); // -200
        pokerManager.addPokerGame(testPokerGame3); // +200
        pokerManager.addPokerGame(testPokerGame4); // -1000
    }

    @Test
    void testCalculateWinRate() {
        assertEquals(50, pokerManager.calculateWinRate());
    }

    @Test
    void testCalculateWinnings() {
        assertEquals(2000, pokerManager.calculateWinnings());
    }

    @Test
    void testAnalyzeLosingHands() {
        List<Card> sortedTestCards2 = createSortedHand(testCard3, testCard7); 
        List<Card> sortedTestCards3 = createSortedHand(testCardJ, testCard2);

        Map<List<Card>, Integer> expectedLosingHands = new HashMap<>();
        expectedLosingHands.put(sortedTestCards2, 1);
        expectedLosingHands.put(sortedTestCards3, 1);

        Map<List<Card>, Integer> actualLostHands = pokerManager.analyzeLosingHands();
        assertEquals(expectedLosingHands, actualLostHands);
    }

    @Test
    void testSortByAmountWon() {
        pokerManager.sortByAmountWon();
        List<PokerGame> sortedGames = pokerManager.getGameHistory();

        assertEquals(testPokerGame1, sortedGames.get(0)); // +3000
        assertEquals(testPokerGame3, sortedGames.get(1)); // +200
        assertEquals(testPokerGame2, sortedGames.get(2)); // -200
        assertEquals(testPokerGame4, sortedGames.get(3)); // -1000
    }

    @Test
    void testSortByWinLoss() {
        pokerManager.sortByWinLoss();
        List<PokerGame> sortedGames = pokerManager.getGameHistory();

        assertEquals(true, sortedGames.get(0).getHasWon()); 
        assertEquals(true, sortedGames.get(1).getHasWon()); 
        assertEquals(false, sortedGames.get(2).getHasWon()); 
        assertEquals(false, sortedGames.get(3).getHasWon());
    }

    // Helper method to sort the hand
    private List<Card> createSortedHand(Card card1, Card card2) {
        List<Card> hand = new ArrayList<>();
        hand.add(card1);
        hand.add(card2);
        hand.sort(Comparator.comparing(Card::getRank).thenComparing(Card::getSuit));
        return hand;
    }
}
