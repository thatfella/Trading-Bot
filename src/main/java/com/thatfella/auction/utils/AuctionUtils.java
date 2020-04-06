package com.thatfella.auction.utils;


import com.thatfella.auction.bidder.BidderImpl;
import com.thatfella.auction.enums.AuctionResult;

import static com.thatfella.auction.enums.AuctionResult.DRAW;
import static com.thatfella.auction.enums.AuctionResult.MY_STRATEGY_WON;
import static com.thatfella.auction.enums.AuctionResult.OPPONENT_WON;

/**
 * This class provides some methods for determining a winner of auction
 */
public class AuctionUtils {
    /**
     * determines winner of auction.
     *
     * @param my       - my instance of Bidder
     * @param opponent - opponent's instance of bidder
     * @return result according to amount of gained QU's
     */
    public static AuctionResult determineWinner(BidderImpl my, BidderImpl opponent) {
        if (my.getGainedQuantityUnits() > opponent.getGainedQuantityUnits()) {
            return MY_STRATEGY_WON;
        } else if (my.getGainedQuantityUnits() == opponent.getGainedQuantityUnits()) {
            return whoSavedMoreCash(my, opponent);
        }
        return AuctionResult.OPPONENT_WON;
    }

    /**
     * determines winner of auction by comparing saved cash in case when amount of gained QU's is equal
     *
     * @param my       - my instance of Bidder
     * @param opponent - opponent's instance of bidder
     * @return result according to amount of saved cache
     */
    private static AuctionResult whoSavedMoreCash(BidderImpl my, BidderImpl opponent) {
        if (my.getCashLeft() > opponent.getCashLeft()) {
            return MY_STRATEGY_WON;
        } else if (opponent.getCashLeft() > my.getCashLeft()) {
            return OPPONENT_WON;
        }
        return DRAW;
    }
}
