package com.thatfella.auction.utils;

import com.thatfella.auction.bidder.BidderImpl;
import com.thatfella.auction.enums.AuctionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuctionUtilsTest {

    private BidderImpl own;
    private BidderImpl opponent;

    @BeforeEach
    private void setUp() {
        own = new BidderImpl();
        own.setGainedQuantityUnits(8);
        own.setCashLeft(100);
        opponent = new BidderImpl();
        opponent.setGainedQuantityUnits(4);
        opponent.setCashLeft(100);
    }

    @Test
    void determineWinner() {
        assertEquals(AuctionResult.MY_STRATEGY_WON,AuctionUtils.determineWinner(own,opponent));
        opponent.setGainedQuantityUnits(8);
        assertEquals(AuctionResult.DRAW,AuctionUtils.determineWinner(own,opponent));
        opponent.setCashLeft(1000);
        assertEquals(AuctionResult.OPPONENT_WON, AuctionUtils.determineWinner(own,opponent));
    }
}