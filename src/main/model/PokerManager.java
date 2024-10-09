package model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// represents a PokerManager for calculating game statistics and sorting of games played
public class PokerManager {
    // REQUIRES: pokergames.size() > 0
    // EFFECTS: returns winrate as a %
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
    public Map<List<Card>, Integer> analyzeLosingHands(List<PokerGame> pokergames) {
        Map<List<Card>, Integer> lostHands = new HashMap<>();

        for (PokerGame pokergame : pokergames) {
            if (!pokergame.getHasWon()) {
                List<Card> hand = pokergame.getCards();
                List<Card> reverseHand = new ArrayList<>(hand); // the reversed of curr hand
                Collections.reverse(reverseHand);

                if (lostHands.containsKey(hand)) {
                    lostHands.put(hand, lostHands.get(hand) + 1);

                } else if (lostHands.containsKey(reverseHand)) { // reversed hand in key
                    lostHands.put(reverseHand, lostHands.get(reverseHand) + 1);
                } else {
                    lostHands.put(hand, 1);
                }
            }
        }
        return lostHands;
    }
    // MODIFIES: pokergames
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
    // MODIFIES: pokergames
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
