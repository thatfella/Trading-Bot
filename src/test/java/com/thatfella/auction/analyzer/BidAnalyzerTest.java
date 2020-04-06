package com.thatfella.auction.analyzer;

import com.thatfella.auction.bidder.BidderImpl;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BidAnalyzerTest {

    private BidAnalyzer bidAnalyzer;
    private BidderImpl ownBidder;
    private BidderImpl opponentBidder;

    @BeforeEach
    public void setUp() {
        bidAnalyzer = new BidAnalyzer();
        ownBidder = new BidderImpl();
        List<Pair<Integer, Integer>> ownHistory = new ArrayList<>();
        ownHistory.add(new Pair<>(0, 100));
        ownBidder.setBidHistory(ownHistory);
        ownBidder.setGainedQuantityUnits(0);
        ownBidder.setInitialCash(1000);

        opponentBidder = new BidderImpl();
        List<Pair<Integer, Integer>> opponentHistory = new ArrayList<>();
        opponentHistory.add(new Pair<>(100, 0));
        opponentBidder.setBidHistory(opponentHistory);
        opponentBidder.setGainedQuantityUnits(2);
        opponentBidder.setInitialCash(1000);

    }

    @Test
    void isOwnQuantityBigger() {
        assertFalse(bidAnalyzer.isOwnQuantityBigger(ownBidder));
        assertTrue(bidAnalyzer.isOwnQuantityBigger(opponentBidder));
    }

    @Test
    void getOpponentCashLeft() {
        assertEquals(900, bidAnalyzer.getOpponentCashLeft(ownBidder));
        assertEquals(1000, bidAnalyzer.getOpponentCashLeft(opponentBidder));
    }

    @Test
    void lastOpponentBidsAreSameInLastRounds() {
        ownBidder.setBidHistory(new ArrayList<>());
        assertFalse(bidAnalyzer.lastOpponentBidsAreSameInLastRounds(ownBidder.getBidHistory()));
        ownBidder.setBidHistory(new ArrayList<Pair<Integer, Integer>>() {{
            add(new Pair<>(1000, 1000));
            add(new Pair<>(1000, 1000));
        }});
        assertTrue(bidAnalyzer.lastOpponentBidsAreSameInLastRounds(ownBidder.getBidHistory()));
        ownBidder.getBidHistory().add(new Pair<>(1000, 10));
        assertFalse(bidAnalyzer.lastOpponentBidsAreSameInLastRounds(ownBidder.getBidHistory()));
    }

    @Test
    void getLastOwnBid() {
        assertEquals(0, bidAnalyzer.getLastOwnBid(ownBidder.getBidHistory()));
    }

    @Test
    void getLastOpponentBid() {
        assertEquals(100, bidAnalyzer.getLastOpponentBid(ownBidder.getBidHistory()));
    }

    @Test
    void opponentBidsAlwaysRaised() {
        ownBidder.setBidHistory(new ArrayList<Pair<Integer, Integer>>() {{
            add(new Pair<>(1000, 1000));
            add(new Pair<>(1000, 1200));
            add(new Pair<>(1000, 1222));
        }});
        assertTrue(bidAnalyzer.opponentBidsAlwaysRaised(ownBidder.getBidHistory()));

        ownBidder.getBidHistory().add(new Pair<>(1000, 1222));
        assertFalse(bidAnalyzer.opponentBidsAlwaysRaised(ownBidder.getBidHistory()));
    }

    @Test
    void getLastWinnerBid() {
        assertEquals(100, bidAnalyzer.getLastWinnerBid(ownBidder.getBidHistory()));
    }
}