package com.thatfella.auction.strategy;

import com.thatfella.auction.bidder.BidderImpl;

/**
 * This strategy skips first round. And then... Right - it makes equal bids :=)
 */
public class EqualBidsWithSkippingFirstTurn implements BiddingStrategy {

    @Override
    public int makeBid(BidderImpl bidder) {
        int totalRounds = bidder.getQuantity() / 2;
        int currentRound = bidder.getCurrentRound();
        if (currentRound == 0) {
            bidder.setCurrentRound(++currentRound);
            return 0;
        } else {
            bidder.setCurrentRound(++currentRound);
            return bidder.getInitialCash() / (totalRounds - 1);
        }
    }
}
