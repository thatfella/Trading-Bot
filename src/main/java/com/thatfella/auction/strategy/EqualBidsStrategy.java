package com.thatfella.auction.strategy;

import com.thatfella.auction.bidder.BidderImpl;

/**
 * Make equal bids every round
 */
public class EqualBidsStrategy implements BiddingStrategy {

    @Override
    public int makeBid(BidderImpl bidder) {
        int totalRounds = bidder.getQuantity() / 2;
        int currentRound = bidder.getCurrentRound();
        int bid = bidder.getInitialCash() / totalRounds;

        bidder.setCurrentRound(++currentRound);
        return bid;
    }
}
