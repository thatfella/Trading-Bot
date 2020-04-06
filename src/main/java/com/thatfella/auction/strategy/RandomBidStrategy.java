package com.thatfella.auction.strategy;

import com.thatfella.auction.bidder.BidderImpl;

import java.util.Random;

/**
 * This strategy makes random bid every round
 */
public class RandomBidStrategy implements BiddingStrategy {

    @Override
    public int makeBid(BidderImpl bidder) {
        Random random = new Random();
        int currentRound = bidder.getCurrentRound();
        int bid = random.nextInt(bidder.getCashLeft());
        bidder.setCurrentRound(++currentRound);
        System.out.println("Opponent's random bid " + bid);
        return bid;
    }
}
