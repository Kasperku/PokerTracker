package model;

import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonReader extends TestJson {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptyPokerGame() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPokerGame.json");
        try {
            List<PokerGame> pokerGames = reader.read();
            assertEquals(0, pokerGames.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPokerGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPokerGame.json");
        try {
            List<PokerGame> pokerGames = reader.read();
            assertEquals(1, pokerGames.size());
            PokerGame pg = pokerGames.get(0);

            assertTrue(pg.getHasWon());
            assertEquals(999, pg.getAmount());
            List<Card> cards = pg.getCards();

            assertEquals(2, cards.size());
            assertEquals("A", cards.get(0).getRank());
            assertEquals("Spades", cards.get(0).getSuit());
            assertEquals("A", cards.get(1).getRank());
            assertEquals("Hearts", cards.get(1).getSuit());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMultiplePokerGames() {
        JsonReader reader = new JsonReader("./data/testReaderMultiplePokerGames.json");
        try {
            List<PokerGame> pokerGames = reader.read();
            assertEquals(2, pokerGames.size());

            PokerGame pg1 = pokerGames.get(0);
            assertTrue(pg1.getHasWon());
            assertEquals(3000, pg1.getAmount());
            assertEquals(2, pg1.getCards().size());
            assertEquals("9", pg1.getCards().get(0).getRank());
            assertEquals("Spades", pg1.getCards().get(0).getSuit());
            assertEquals("3", pg1.getCards().get(1).getRank());
            assertEquals("Hearts", pg1.getCards().get(1).getSuit());

            PokerGame pg2 = pokerGames.get(1);
            assertFalse(pg2.getHasWon());
            assertEquals(-111, pg2.getAmount());
            assertEquals(2, pg2.getCards().size());
            assertEquals("Q", pg2.getCards().get(0).getRank());
            assertEquals("Diamonds", pg2.getCards().get(0).getSuit());
            assertEquals("J", pg2.getCards().get(1).getRank());
            assertEquals("Clubs", pg2.getCards().get(1).getSuit());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}