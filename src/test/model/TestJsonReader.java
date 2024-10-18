package model;

import model.Card;
import model.PokerGame;
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
            PokerGame pg = reader.read();
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

    
}