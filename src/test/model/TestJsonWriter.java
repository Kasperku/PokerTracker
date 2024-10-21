package model;

import model.Card;
import model.PokerGame;
import persistence.JsonWriter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonWriter {

    @Test
    void testWriterInvalidFile() {
        try {

            Card cardK = new Card("K", "Hearts");
            Card cardA = new Card("A", "Diamonds");
            List<Card> hand = new ArrayList<>();
            hand.add(cardK);
            hand.add(cardA);

            PokerGame pg = new PokerGame(false, 999, hand);

            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }
}
