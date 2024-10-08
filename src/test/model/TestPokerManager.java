package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestPokerManager extends BaseTest{

    private PokerManager pokerManager;

    @BeforeEach
    void runBefore(){
        pokerManager = new PokerManager();
        initializedPokerGame();
    }

    @Test
    void testCalculateWinRate(){
        assertEquals(50, pokerManager.calculateWinRate(testPokerGames));
    }

    @Test
    void testCalculateWinnings(){
        assertEquals(2800, pokerManager.calculateWinnings(testPokerGames));
    }
}
