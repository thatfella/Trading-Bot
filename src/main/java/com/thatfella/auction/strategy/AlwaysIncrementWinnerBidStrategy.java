package com.thatfella.auction.strategy;

import com.thatfella.auction.analyzer.BidAnalyzer;
import com.thatfella.auction.bidder.BidderImpl;

/**
 * The idea of this strategy is always increment last winning bid
 */
public class AlwaysIncrementWinnerBidStrategy implements BiddingStrategy {

    //helps us with analytic function - getLastWinnerBid
    BidAnalyzer bidAnalyzer = new BidAnalyzer();

    @Override
    public int makeBid(BidderImpl bidder) {
        int currentRound = bidder.getCurrentRound();
        int bid = bidAnalyzer.getLastWinnerBid(bidder.getBidHistory()) + 1;
        if (bid <= bidder.getCashLeft()) {
            bidder.setCurrentRound(++currentRound);
            return bid;
        }
        return 0;
    }
}
