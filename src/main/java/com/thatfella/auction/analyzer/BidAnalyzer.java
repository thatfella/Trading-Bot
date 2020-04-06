package com.thatfella.auction.analyzer;

import com.thatfella.auction.bidder.BidderImpl;
import javafx.util.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is responsible for some analytical functions and help strategies (MyStrategy and some other) to plan the next bid
 */

@NoArgsConstructor
public class BidAnalyzer {

    /**
     * The following method is used to determine whether our gainedQuantity is bigger
     *
     * @param bidder - instance of bidder
     * @return is our gained QU's amount bigger
     */
    public boolean isOwnQuantityBigger(BidderImpl bidder) {
        return bidder.getBidHistory() != null &&
                bidder.getGainedQuantityUnits() > calculateOpponentQuantity(bidder.getBidHistory());
    }

    /**
     * Method that is used to count opponent Quantity
     *
     * @param bidHistory - history of bids
     * @return counter - amount of opponent QU's
     */
    private int calculateOpponentQuantity(List<Pair<Integer, Integer>> bidHistory) {
        if (!bidHistoryValid(bidHistory)) {
            return 0;
        }
        int counter = 0;
        for (Pair<Integer, Integer> amount : bidHistory) {
            if (amount.getValue() > amount.getKey()) {
                counter += 2;
            }
            if (amount.getValue().equals(amount.getKey())) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Determines how much cash has the opponent
     *
     * @param bidder - instance of Bidder
     * @return amount of opponent's left cash
     */
    public int getOpponentCashLeft(BidderImpl bidder) {
        return (int) (bidder.getInitialCash() - bidder.getBidHistory().stream().mapToInt(Pair::getValue).summaryStatistics().getSum());
    }

    /**
     * Checks if opponent bids are the same in a couple of last rounds
     *
     * @param bidHistory - history of bids
     * @return
     */
    public boolean lastOpponentBidsAreSameInLastRounds(List<Pair<Integer, Integer>> bidHistory) {
        if (!bidHistoryValid(bidHistory) || bidHistory.size() < 2) {
            return false;
        }
        int lastBid = getLastOpponentBid(bidHistory);
        int preLastBid = bidHistory.get(bidHistory.size() - 2).getValue();
        return lastBid == preLastBid;
    }


    /**
     * Gets our last bid from the bidding history
     *
     * @param bidHistory - bidding history
     * @return our last bid
     */
    public int getLastOwnBid(List<Pair<Integer, Integer>> bidHistory) {
        return bidHistoryValid(bidHistory) ? bidHistory.get(bidHistory.size() - 1).getKey() : 0;
    }

    /**
     * Gets opponent's last bid from the bidding history
     *
     * @param bidHistory - bidding history
     * @return opponent's last bid
     */
    public int getLastOpponentBid(List<Pair<Integer, Integer>> bidHistory) {
        return bidHistoryValid(bidHistory) ? bidHistory.get(bidHistory.size() - 1).getValue() : 0;
    }

    /**
     * Analyze whether opponent always raises his bid
     *
     * @param bidHistory - history of bids
     * @return is opponent raising bids
     */

    public boolean opponentBidsAlwaysRaised(List<Pair<Integer, Integer>> bidHistory) {
        if (!bidHistoryValid(bidHistory)) {
            return false;
        }
        List<Integer> lastOpponentBids = bidHistory.stream().map(Pair::getValue).sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
        return IntStream.range(1, lastOpponentBids.size()).allMatch(e -> lastOpponentBids.get(e) < lastOpponentBids.get(e - 1));
    }

    /**
     * Determines winner bid in previous turn
     *
     * @param bidHistory - history of bids
     * @return last winner bid
     */
    public int getLastWinnerBid(List<Pair<Integer, Integer>> bidHistory) {
        if (!bidHistoryValid(bidHistory)) {
            return 0;
        }
        int lastOwnBid = getLastOwnBid(bidHistory);
        int lastOpponentBid = getLastOpponentBid(bidHistory);
        return Math.max(lastOwnBid, lastOpponentBid);
    }

    /**
     * Checks that bidHistory is valid  - not null and not empty
     *
     * @param bidHistory - bidding history
     * @return is bid history not null and not empty
     */
    private boolean bidHistoryValid(List<Pair<Integer, Integer>> bidHistory) {
        return bidHistory != null && !bidHistory.isEmpty();
    }

}
