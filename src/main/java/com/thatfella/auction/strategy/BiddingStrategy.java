package com.thatfella.auction.strategy;

import com.thatfella.auction.bidder.BidderImpl;

/**
 * Interface which describes the bidding strategy
 */
public interface BiddingStrategy {

    int makeBid(BidderImpl bidder);
}
