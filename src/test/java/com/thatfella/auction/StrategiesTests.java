package com.thatfella.auction;

import com.thatfella.auction.bidder.BidderImpl;
import com.thatfella.auction.enums.AuctionResult;
import com.thatfella.auction.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.thatfella.auction.utils.AuctionUtils.determineWinner;

@Slf4j
public class StrategiesTests {

    private List<Integer> numberOfQUs = Arrays.asList(2, 4, 6, 8, 10, 100);
    private List<Integer> initialCashAmount = Arrays.asList(1000, 2000, 4000, 8000, 10000, 100000);

    @Test
    public void myVsEqualBids() {
        competeBidders(new EqualBidsStrategy());
    }

    @Test
    public void myVsAgressiveStrategy() {
        competeBidders(new AggressiveStrategy());
    }

    @Test
    public void myVsEqualBidsWithSkippingTurn() {
        competeBidders(new EqualBidsWithSkippingFirstTurn());
    }

    @Test
    public void myVsRandomBids() {
        competeBidders(new RandomBidStrategy());
    }

    @Test
    public void myVsAlwaysIncrement() {
        competeBidders(new AlwaysIncrementWinnerBidStrategy());
    }


    public void competeBidders(BiddingStrategy opponent) {
        int victoryCounter = 0;
        int drawCounter = 0;
        for (Integer units : numberOfQUs) {
            for (Integer cash : initialCashAmount) {
                log.info("#####################AUCTION STARTED########################");
                BidderImpl mybidder = new BidderImpl();
                mybidder.init(units, cash);
                BidderImpl opponentBidder = new BidderImpl();
                opponentBidder.init(units, cash);
                opponentBidder.setBiddingStrategy(opponent);

                int rounds = mybidder.getQuantity() / 2;
                int currentRound = 0;

                while (currentRound < rounds) {
                    int myBid = mybidder.placeBid();
                    int oppBid = opponentBidder.placeBid();
                    mybidder.bids(myBid, oppBid);
                    opponentBidder.bids(oppBid, myBid);
                    currentRound++;
                }

                log.info(" bid history       " + mybidder.getBidHistory());
                log.info("My gained quantity " + mybidder.getGainedQuantityUnits());
                log.info("My bidder cash left " + mybidder.getCashLeft());
                log.info("Opponent cash left " + opponentBidder.getCashLeft());
                AuctionResult winner = determineWinner(mybidder, opponentBidder);
                if (winner == AuctionResult.MY_STRATEGY_WON) {
                    victoryCounter += 1;
                } else if (winner == AuctionResult.DRAW) {
                    drawCounter += 1;
                }
                    log.info(winner.name());
            }
        }
        log.info("Victories : " + victoryCounter + " from 36 attempts");
        log.info("Draws : " + drawCounter + " from 36 attempts");
    }

}
