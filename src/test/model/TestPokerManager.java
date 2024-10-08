package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
        testMultipleLosses = new ArrayList<>();
        testMultipleLosses.add(testPokerGame2); // 3 of Spades, 7 of Clubs
        testMultipleLosses.add(testPokerGame2); // 3 of Spades, 7 of Clubs
        testMultipleLosses.add(testPokerGame2); // 3 of Spades, 7 of Clubs
        testMultipleLosses.add(testPokerGame4); // J of Clubs, 2 of Diamonds

        // represents the hand (3 of Spades, 7 of Clubs)
        List<Card> threeOfSpadesSevenOfClubs = new ArrayList<>();
        threeOfSpadesSevenOfClubs.add(testCard3);
        threeOfSpadesSevenOfClubs.add(testCard7);

        // represents the hand (J of Clubs, 2 of Diamonds)
        List<Card> jackOfClubsTwoOfDiamonds = new ArrayList<>();
        jackOfClubsTwoOfDiamonds.add(testCardJ);
        jackOfClubsTwoOfDiamonds.add(testCard2);

        Map<Integer, List<Card>> expectedLosingHands = new HashMap<>();
        expectedLosingHands.put(3, threeOfSpadesSevenOfClubs);
        expectedLosingHands.put(1, jackOfClubsTwoOfDiamonds);

        Map<Integer, List<Card>> actualLostHands = pokerManager.analyzeLosingHands(testMultipleLosses);
        assertEquals(expectedLosingHands, actualLostHands);
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
