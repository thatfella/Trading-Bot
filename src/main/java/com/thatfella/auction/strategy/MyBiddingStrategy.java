package com.thatfella.auction.strategy;

import com.thatfella.auction.analyzer.BidAnalyzer;
import com.thatfella.auction.bidder.BidderImpl;
import javafx.util.Pair;

import java.util.List;

import static com.thatfella.auction.utils.BiddingUtils.*;

public class MyBiddingStrategy implements BiddingStrategy {

    private static final int MINIMUM_ROUNDS = 1;
    private static final int TWO_ROUNDS = 2;
    private static final int ZERO_CASH = 0;

    BidAnalyzer bidAnalyzer = new BidAnalyzer();

    @Override
    public int makeBid(BidderImpl bidder) {
        //How many rounds left - initial value
        int totalRounds = bidder.getQuantity() / 2;

        //Number of Quantity units we need to gain for a victory
        int quantityUnitsToWin = calculateQuantityUnitsToWin(bidder);

        //if we have only 2 QU's in auction - we put all our cash to win
        if (MINIMUM_ROUNDS == totalRounds) {
            return makeBidForOnlyOneRound(bidder);
        }

        // if auction consists of two rounds we making 2 equal bids
        else if (TWO_ROUNDS == totalRounds) {
            return makeEqualBidsForTwoRoundAuction(bidder);
        } else if (totalRounds > 2) {
            //Let's skip the first round and make initial bid equal zero
            int currentRound = bidder.getCurrentRound();
            if (0 == bidder.getCurrentRound()) {
                bidder.setCurrentRound(++currentRound);
                return makeZeroBid(bidder);
            }

            bidder.setCurrentRound(++currentRound);
            //if we already have more than a half of QU's to win auction or we are out of cash - we skip round
            if (bidder.getGainedQuantityUnits() >= quantityUnitsToWin || 0 == bidder.getCashLeft()) {
                return makeZeroBid(bidder);
            } else if (ZERO_CASH == bidAnalyzer.getOpponentCashLeft(bidder) && bidder.getCashLeft() >= 1) {
                return makeMinimalBid(bidder);
            }    //if this is last round and we need <=2 QU's to win bid all left cash to win it
            else if (bidder.getCurrentRound() == totalRounds && quantityUnitsToWin - bidder.getGainedQuantityUnits() <= 2) {
                return bidder.getCashLeft();
            } else {
                return makeBidsAfterFirstRound(bidder, totalRounds, currentRound);
            }
        }
        return 0;
    }

    private int makeBidsAfterFirstRound(BidderImpl bidder, int totalRounds, int currentRound) {
        //initially we plan to make equal bids after skipping first round, so this bid is initial after first round skipped
        int bid = bidder.getInitialCash() / (totalRounds - 1);

        List<Pair<Integer, Integer>> bidHistory = bidder.getBidHistory();
        int lastOpponentBid = bidAnalyzer.getLastOpponentBid(bidHistory);
        int lastOwnBid = bidAnalyzer.getLastOwnBid(bidHistory);

        if (currentRound >= 2) {
            //analyzing the current position, if our strategy is okay, then we keep using it
            if ((bidAnalyzer.isOwnQuantityBigger(bidder) || lastOwnBid > lastOpponentBid) && bid < bidder.getCashLeft()) {
                // if several opponent bids are same - we can just increment their bid - it'll save us some cash
                if (bidAnalyzer.lastOpponentBidsAreSameInLastRounds(bidder.getBidHistory())) {
                    bid = ++lastOpponentBid;
                    return bid;
                } else if (lastOwnBid > lastOpponentBid) {
                    bid = lastOwnBid;
                } else {
                    bid = ++lastOwnBid;
                }
//                //checks that our bid will not make as bankrupt - addition in BidderImpl
                if (!bankrupcyDanger(bid, bidder)) {
                    return bid;
                }
            }

            //if opponent always raising bid we will try to overbid him(if he plays incrementing - so we add lastOpponentBid + 2)
            if (bidAnalyzer.opponentBidsAlwaysRaised(bidHistory) && isOpponentsBidBigger(lastOpponentBid, lastOwnBid)) {
                bid = lastOpponentBid + 2;
                if (bid <= bidder.getCashLeft()) {
                    return bid;
                }
            }
            // if we are not winning and opponent's last bid was higher - than we trying to increment last opponent bid
            bid = !bidAnalyzer.isOwnQuantityBigger(bidder) && isOpponentsBidBigger(lastOpponentBid, lastOwnBid) && bidder.getCashLeft() >= ++lastOpponentBid ? lastOpponentBid : lastOwnBid;

        }
        return bid;
    }

    /**
     * Returns a zero bid
     *
     * @param bidder - instance of bidder
     * @return 0
     */
    private int makeZeroBid(BidderImpl bidder) {
        return 0;
    }

    /**
     * Returns a bid equal to 1 MU
     *
     * @param bidder - instance of bidder
     * @return 1
     */
    private int makeMinimalBid(BidderImpl bidder) {
        return 1;
    }

    /**
     * Method that separates initial cash in 2 equal parts for case when we have 2 rounds
     *
     * @param bidder - bidder instance
     * @return bid value
     */
    private int makeEqualBidsForTwoRoundAuction(BidderImpl bidder) {
        return bidder.getInitialCash() / 2;
    }

    /**
     * Method that bids all our cash for case when we have 1 round
     *
     * @param bidder - instance of bidder
     * @return bid value
     */
    private int makeBidForOnlyOneRound(BidderImpl bidder) {
        return bidder.getInitialCash();
    }

}
