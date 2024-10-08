package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestPokerManager extends BaseTest{

    private PokerManager pokerManager;
    private List<PokerGame> testPokerGames;

    @BeforeEach
    void runBefore(){
        pokerManager = new PokerManager();
        initializedPokerGame();

        testPokerGames = new ArrayList<>();
        testPokerGames.add(testPokerGame1);
        testPokerGames.add(testPokerGame2);
        testPokerGames.add(testPokerGame3);
    }

    @Test
    void testCalculateWinRate(){
        assertEquals(50, pokerManager.calculateWinRate(testPokerGames));
    }

    @Test
    void testCalculateWinnings(){
        assertEquals(2800, pokerManager.calculateWinnings(testPokerGames));
    }

    @Test
    void testSortByAmountWon(){
        List<PokerGame> pokerGamesSortedByAmountWon = pokerManager.sortByAmountWon(testPokerGames);

        assertEquals(testPokerGame1, pokerGamesSortedByAmountWon.get(0)); // +3000
        assertEquals(testPokerGame3, pokerGamesSortedByAmountWon.get(1)); // +200
        assertEquals(testPokerGame2, pokerGamesSortedByAmountWon.get(2)); // -200
    }

    @Test
    void testSortByWinLoss() {
        List<PokerGame> pokerGamesSortedByWinLoss = pokerManager.sortByWinLoss(testPokerGames);

        assertEquals(testPokerGame1, pokerGamesSortedByWinLoss.get(1)); // win
        assertEquals(testPokerGame3, pokerGamesSortedByWinLoss.get(2)); // win
        assertEquals(testPokerGame2, pokerGamesSortedByWinLoss.get(0)); // loss
    }
}
