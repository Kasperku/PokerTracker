package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
