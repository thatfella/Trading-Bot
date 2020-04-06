package com.thatfella.auction.strategy;

import com.thatfella.auction.bidder.BidderImpl;

import static com.thatfella.auction.utils.BiddingUtils.calculateQuantityUnitsToWin;

/**
 * This one is aggressive strategy - the idea is to win (or at least draw) the auction as fast as possible - by making biggest bids until it has enough QU's to win
 */
public class AggressiveStrategy implements BiddingStrategy {
    @Override
    public int makeBid(BidderImpl bidder) {
        int roundsToWin = calculateQuantityUnitsToWin(bidder) / 2;
        int currentRound = bidder.getCurrentRound();
        int agressiveBid = bidder.getInitialCash() / roundsToWin;
        bidder.setCurrentRound(++currentRound);
        return agressiveBid;
    }
}
