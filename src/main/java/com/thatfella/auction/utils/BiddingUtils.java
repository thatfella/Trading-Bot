package com.thatfella.auction.utils;


import com.thatfella.auction.bidder.BidderImpl;

/**
 * This class provides some utility methods, which can be applied to bidding strategies
 **/
public class BiddingUtils {

    /**
     * Checks if the next bid will make a bidder bankrupt
     *
     * @param bid
     * @param bidder
     * @return will this bid make bidder bankrupt
     */

    public static boolean bankrupcyDanger(int bid, BidderImpl bidder) {
        return bid > bidder.getCashLeft();
    }

    /**
     * Checks if last opponent's bid is bigger
     *
     * @param lastOpponentBid - last opponent's bid
     * @param lastOwnBid      - last own bid
     * @return is opponent's bid bigger
     */
    public static boolean isOpponentsBidBigger(int lastOpponentBid, int lastOwnBid) {
        return lastOpponentBid >= lastOwnBid;
    }

    /**
     * Returns amount of QU's required to win auction
     *
     * @param bidder - instance of Bidder
     * @return amount of QU's
     */
    public static int calculateQuantityUnitsToWin(BidderImpl bidder) {
        return bidder.getQuantity() / 2 + 1;
    }
}
