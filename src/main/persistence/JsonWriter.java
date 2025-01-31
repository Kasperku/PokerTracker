package persistence;

import model.PokerGame;

import org.json.JSONArray;

import java.io.*;
import java.util.List;

// Represents a writer that writes JSON representation of pokerlog to file

public class JsonWriter {
    // code adapted from The University Of British Columbia,
    // CPSC210 Winter 2024, jsonSerializationDemo

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of PokerGame to file
    public void write(List<PokerGame> gameHistory) {
        JSONArray jsonArray = new JSONArray();
        for (PokerGame pg : gameHistory) {
            jsonArray.put(pg.toJson());
        }
        saveToFile(jsonArray.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
