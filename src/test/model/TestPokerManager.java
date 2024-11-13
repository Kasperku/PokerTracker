package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPokerManager extends BaseTest {

    private PokerManager pokerManager;
    private List<PokerGame> testPokerGames;
    private List<PokerGame> testMultipleLosses;

    @BeforeEach
    void runBefore() {
        pokerManager = new PokerManager();
        initializedPokerGame();

        testPokerGames = new ArrayList<>();
        testPokerGames.add(testPokerGame1); // win +3000
        testPokerGames.add(testPokerGame2); // loss -200
        testPokerGames.add(testPokerGame3); // win +200
        testPokerGames.add(testPokerGame4); // loss -1000

        testMultipleLosses = new ArrayList<>();
        testMultipleLosses.add(testPokerGame2); // 3 of Spades, 7 of Clubs
        testMultipleLosses.add(testPokerGame2); // 3 of Spades, 7 of Clubs
        testMultipleLosses.add(testPokerGame2); // 3 of Spades, 7 of Clubs
        testMultipleLosses.add(testPokerGame4); // J of Clubs, 2 of Diamonds
    }

    @Test
    void testCalculateWinRate() {
        assertEquals(50, pokerManager.calculateWinRate(testPokerGames));
    }

    @Test
    void testCalculateWinnings() {
        assertEquals(2000, pokerManager.calculateWinnings(testPokerGames));
    }

    @Test
    void testAnalyzeLosingHands() {

        // Create expected losing hand entries to match testPokerGames setup
        List<Card> threeOfSpadesSevenOfClubs = createSortedHand(testCard3, testCard7);
        List<Card> jackOfClubsTwoOfDiamonds = createSortedHand(testCardJ, testCard2);

        // Expected map of losing hands and their counts
        Map<List<Card>, Integer> expectedLosingHands = new HashMap<>();
        expectedLosingHands.put(threeOfSpadesSevenOfClubs, 1);
        expectedLosingHands.put(jackOfClubsTwoOfDiamonds, 1);

        // Run analyzeLosingHands using testPokerGames, which includes both wins and
        // losses
        Map<List<Card>, Integer> actualLostHands = pokerManager.analyzeLosingHands(testPokerGames);

        // Assert that the results match expected outcomes
        assertEquals(expectedLosingHands, actualLostHands);
    }

    // Helper method to create and sort a hand for consistency with the
    // implementation
    private List<Card> createSortedHand(Card card1, Card card2) {
        List<Card> hand = new ArrayList<>();
        hand.add(card1);
        hand.add(card2);
        hand.sort(Comparator.comparing(Card::getRank).thenComparing(Card::getSuit));
        return hand;
    }

    @Test
    void testSortByAmountWon() {
        List<PokerGame> pokerGamesSortedByAmountWon = pokerManager.sortByAmountWon(testPokerGames);
        assertEquals(testPokerGame1, pokerGamesSortedByAmountWon.get(0)); // +3000
        assertEquals(testPokerGame3, pokerGamesSortedByAmountWon.get(1)); // +200
        assertEquals(testPokerGame2, pokerGamesSortedByAmountWon.get(2)); // -200
        assertEquals(testPokerGame4, pokerGamesSortedByAmountWon.get(3)); // -1000
    }

    @Test
    void testSortByWinLoss() {
        List<PokerGame> pokerGamesSortedByWinLoss = pokerManager.sortByWinLoss(testPokerGames);
        assertEquals(testPokerGame1, pokerGamesSortedByWinLoss.get(0)); // win
        assertEquals(testPokerGame3, pokerGamesSortedByWinLoss.get(1)); // win
        assertEquals(testPokerGame2, pokerGamesSortedByWinLoss.get(2)); // loss
        assertEquals(testPokerGame4, pokerGamesSortedByWinLoss.get(3)); // loss
    }
}
