package model;

import java.util.List;
import java.util.Map;

// represents a PokerManager for calculating statistics and sorting of games played
public class PokerManager {
    
    // EFFECTS: returns winrate
    public int calculateWinRate(List<PokerGame> pokergames){
        // stub
        return 0;
    }

    // EFFECTS: returns total winnings, - if loss 
    public int calculateWinnings(List<PokerGame> pokergames){
        // stub
        return 0;
    }

    // EFFECTS: analyze which hand lead to most losses 
    public Map<List<Card>, Integer> analyzeLosingHands(List<PokerGame> pokergames){
        // stubb
        return null;
    }

    // EFFECTS: sort List<PokerGame> by amount won, largest win on top
    public List<PokerGame> sortByAmountWon(List<PokerGame> pokergames){
        // stub
        return null;
    }

    // EFFECTS: sort List<PokerGame> by win/loss, won games on top
    public List<PokerGame> sortByWinLoss(List<PokerGame> pokergames){
        // stub
        return null;
    }
}
