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
            PokerGame pg = reader.read();
            assertFalse(pg.getHasWon());
            assertEquals(0, pg.getAmount());
            assertEquals(0, pg.getCards().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPokerGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPokerGame.json");
        try {
            PokerGame pg = reader.read();
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

}