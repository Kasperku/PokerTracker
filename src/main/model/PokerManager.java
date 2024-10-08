package model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

// represents a PokerManager for calculating statistics and sorting of games played
public class PokerManager {

    // EFFECTS: returns winrate
    public int calculateWinRate(List<PokerGame> pokergames) {
        double numWins = 0;
        int numLoss = 0;

        for (PokerGame pokergame : pokergames) {
            if (pokergame.getHasWon()) {
                numWins += 1;
            } else {
                numLoss += 1;
            }
        }

        return (int) ((numWins / (numLoss + numWins)) * 100);
    }

    // EFFECTS: returns total winnings, - if loss
    public int calculateWinnings(List<PokerGame> pokergames) {
        int winnings = 0;

        for (PokerGame pokergame : pokergames) {
            winnings = winnings + pokergame.getAmount();
        }
        return winnings;
    }

    // EFFECTS: analyze which hand lead to most losses
    public Map<Integer, List<Card>> analyzeLosingHands(List<PokerGame> pokergames) {
        Map<List<Card>, Integer> dummy = new HashMap<>();

        for (PokerGame pokergame : pokergames) {
            if (!pokergame.getHasWon()) {
                if (dummy.containsKey(pokergame.getCards())) {
                    dummy.put(pokergame.getCards(), dummy.get(pokergame.getCards()) + 1);
                } else {
                    dummy.put(pokergame.getCards(), 1);
                }
            }
        }
        // reverse the map so it stores integer as key, hands as value
        Map<Integer, List<Card>> lostHands = new HashMap<>();
        for (Map.Entry<List<Card>, Integer> entry : dummy.entrySet()) {
            lostHands.put(entry.getValue(), entry.getKey());
        }
        return lostHands;
    }

    // EFFECTS: sort List<PokerGame> by amount won, largest win on top
    public List<PokerGame> sortByAmountWon(List<PokerGame> pokergames) {
        for (int i = 0; i < pokergames.size() - 1; i++) {
            for (int j = 0; j < pokergames.size() - i - 1; j++) {
                if (pokergames.get(j).getAmount() < pokergames.get(j + 1).getAmount()) {
                    PokerGame dummy = pokergames.get(j);
                    pokergames.set(j, pokergames.get(j + 1));
                    pokergames.set(j + 1, dummy);
                }
            }
        }

        return pokergames;
    }

    // EFFECTS: sort List<PokerGame> by win/loss, won games on top
    public List<PokerGame> sortByWinLoss(List<PokerGame> pokergames) {
        for (int i = 0; i < pokergames.size() - 1; i++) {
            for (int j = 0; j < pokergames.size() - i - 1; j++) {
                if (!pokergames.get(j).getHasWon() && pokergames.get(j + 1).getHasWon()) {
                    PokerGame dummy = pokergames.get(j);
                    pokergames.set(j, pokergames.get(j + 1));
                    pokergames.set(j + 1, dummy);
                }
            }
        }

        return pokergames;
    }
}
