package model;

import persistence.JsonReader;
import persistence.JsonWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonWriter {
    List<Card> hand;

    @BeforeEach
    void runBefore() {
        Card cardK = new Card("K", "Hearts");
        Card cardA = new Card("A", "Diamonds");
        hand = new ArrayList<>();
        hand.add(cardK);
        hand.add(cardA);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPokerGame() {
        try {
            ArrayList<Card> dummy = new ArrayList<>();

            PokerGame pg = new PokerGame(false, 0, dummy);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPokerGame.json");
            writer.open();
            writer.write(pg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPokerGame.json");
            pg = reader.read();
            assertFalse(pg.getHasWon());
            assertEquals(0, pg.getAmount());
            assertEquals(0, pg.getCards().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPokerGame() {
        try {
            PokerGame pg = new PokerGame(true, 999, hand);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPokerGame.json");
            writer.open();
            writer.write(pg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPokerGame.json");
            pg = reader.read();
            assertTrue(pg.getHasWon());
            assertEquals(999, pg.getAmount());
            List<Card> hand = pg.getCards();
            assertEquals(2, hand.size());
            assertEquals("K", hand.get(0).getRank());
            assertEquals("Hearts", hand.get(0).getSuit());
            assertEquals("A", hand.get(1).getRank());
            assertEquals("Spades", hand.get(1).getSuit());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
