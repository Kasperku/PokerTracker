package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a PokerManager for managing poker games, calculating statistics,
 * and sorting games
 */
public class PokerManager {
    private List<PokerGame> gameHistory;

    public PokerManager() {
        this.gameHistory = new ArrayList<>();
    }

    // REQUIRES: pokerGame is not null
    // MODIFIES: this
    // EFFECTS: adds a poker game to the game history and logs the event
    public void addPokerGame(PokerGame pokerGame) {
        gameHistory.add(pokerGame);
        EventLog.getInstance().logEvent(new Event("Poker game added"));
    }

    // REQUIRES: 0 <= index and newGame is not null
    // MODIFIES: this
    // EFFECTS: replaces the poker game at the specified index with newGame
    // and logs the event

    public void editPokerGame(int index, PokerGame newGame) {
        gameHistory.set(index, newGame);
        EventLog.getInstance().logEvent(
                new Event("Poker game edited"));
    }

    // REQUIRES: 0 <= index
    // MODIFIES: this
    // EFFECTS: removes the poker game at the specified index and logs the event

    public void delPokerGame(int index) {
        gameHistory.remove(index);
        EventLog.getInstance().logEvent(new Event("Poker game deleted"));
    }

    // EFFECTS: return gameHisotyr
    public List<PokerGame> getGameHistory() {
        return gameHistory;
    }

    // EFFECTS: calculates the win rate as a percentage

    public int calculateWinRate() {
        double numWins = 0;

        for (PokerGame pokerGame : gameHistory) {
            if (pokerGame.getHasWon()) {
                numWins++;
            }
        }
        return (int) ((numWins / gameHistory.size()) * 100);
    }

    // EFFECTS: calculates the total winnings. Losses are negative amounts.

    public int calculateWinnings() {
        int winnings = 0;

        for (PokerGame pokerGame : gameHistory) {
            winnings += pokerGame.getAmount();
        }
        return winnings;
    }

    // EFFECTS: analyzes which hands lead to the most losses

    public Map<List<Card>, Integer> analyzeLosingHands() {
        Map<List<Card>, Integer> lostHands = new HashMap<>();

        for (PokerGame pokerGame : gameHistory) {
            if (!pokerGame.getHasWon()) {
                List<Card> hand = new ArrayList<>(pokerGame.getCards());
                hand.sort(Comparator.comparing(Card::getRank).thenComparing(Card::getSuit));
                lostHands.put(hand, lostHands.getOrDefault(hand, 0) + 1);
            }
        }
        return lostHands;
    }

    // MODIFIES: this
    // EFFECTS: sorts the game history by amount won, with the largest wins on topW
    public void sortByAmountWon() {
        gameHistory.sort(new Comparator<PokerGame>() {
            @Override
            public int compare(PokerGame game1, PokerGame game2) {
                return Integer.compare(game2.getAmount(), game1.getAmount());
            }
        });
        EventLog.getInstance().logEvent(new Event("Game history sorted by amountwon"));
    }

    // MODIFIES: this
    // EFFECTS: sorts the game history by win/loss, with won games on top
    public void sortByWinLoss() {
        gameHistory.sort(new Comparator<PokerGame>() {
            @Override
            public int compare(PokerGame game1, PokerGame game2) {
                return Boolean.compare(!game1.getHasWon(), !game2.getHasWon());
            }
        });
        EventLog.getInstance().logEvent(new Event("Game history sorted by winloss"));
    }

    // MODIFIES: this
    // EFFECTS: replaces the current game history with newGameHistory
    public void setGameHistory(List<PokerGame> newGameHistory) {
        gameHistory = new ArrayList<>(newGameHistory);
        EventLog.getInstance().logEvent(new Event("saved poker log"));
    }
}
