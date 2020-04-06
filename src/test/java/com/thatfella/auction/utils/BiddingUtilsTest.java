package com.thatfella.auction.utils;

import com.thatfella.auction.bidder.BidderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BiddingUtilsTest {

    private BidderImpl bidder;

    @BeforeEach
    private void setUp() {
        bidder = new BidderImpl();
        bidder.setQuantity(20);
        bidder.setCashLeft(100);

    }

    @Test
    void bankrupcyDanger() {
        assertTrue(BiddingUtils.bankrupcyDanger(150, bidder));
        assertFalse(BiddingUtils.bankrupcyDanger(33, bidder));
    }

    @Test
    void isOpponentsBidBigger() {
        assertFalse(BiddingUtils.isOpponentsBidBigger(10,33));
        assertTrue(BiddingUtils.isOpponentsBidBigger(150,33));
    }

    @Test
    void calculateQuantityUnitsToWin() {
        assertEquals(11, BiddingUtils.calculateQuantityUnitsToWin(bidder));
    }
}