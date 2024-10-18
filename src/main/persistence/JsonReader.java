package persistence;

import model.Card;
import model.PokerGame;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {
    // code adapted from The University Of British Columbia,
    // CPSC210 Winter 2024, jsonSerializationDemo

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads PokerGame from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PokerGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePokerGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PokerGame from JSON object and returns it
    private PokerGame parsePokerGame(JSONObject jsonObject) {
        boolean hasWon = jsonObject.getBoolean("haswon");
        int amount = jsonObject.getInt("amount");
        List<Card> cards = parseCards(jsonObject.getJSONArray("cards"));
        return new PokerGame(hasWon, amount, cards);
    }


    // EFFECTS: parses list of Cards from JSON array and returns them
    private List<Card> parseCards(JSONArray jsonArray) {
        List<Card> cards = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject card = (JSONObject) json;
            String rank = card.getString("rank");
            String suit = card.getString("suit");
            Card pokerCard = new Card(rank, suit);
            cards.add(pokerCard);
        }
        return cards;
    }
}
