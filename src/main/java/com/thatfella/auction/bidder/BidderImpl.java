package com.thatfella.auction.bidder;

import com.thatfella.auction.strategy.BiddingStrategy;
import com.thatfella.auction.strategy.MyBiddingStrategy;
import javafx.util.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
public class BidderImpl implements Bidder {

    //initial quantity of QU's
    private int quantity;

    //initial cash
    private int initialCash;

    //cash left after made bid
    private int cashLeft;

    //number of QU's the bidder won;
    private int gainedQuantityUnits;

    //current round
    private int currentRound;

    // Bidding Strategy
    private BiddingStrategy biddingStrategy;

    // Bidding History
    private List<Pair<Integer, Integer>> bidHistory = new ArrayList<>();

    /**
     * Initializes the bidder with the production quantity and the allowed cash limit. My strategy is set by default but could be changed if needed
     *
     * @param quantity the quantity
     * @param cash     the cash limit
     */
    @Override
    public void init(int quantity, int cash) {
        Assert.isTrue(quantity > 0 && quantity % 2 == 0, "Quantity should be positive and even");
        Assert.isTrue(cash > 0, "Cash should be positive number");
        this.quantity = quantity;
        this.initialCash = cash;
        this.biddingStrategy = new MyBiddingStrategy();
        this.setCashLeft(cash);
        this.setCurrentRound(0);
    }

    /**
     * Retrieves the next bid for the product, which may be zero.
     *
     * @return the next bid
     */
    @Override
    public int placeBid() {
        int bid = biddingStrategy.makeBid(this);
        //if the bid suites conditions - than all ok
        if (bid >= 0 && bid <= initialCash && bid <= cashLeft) {
            cashLeft = getCurrentRound() == 0 ? initialCash - bid : cashLeft - bid;
            return bid;
        }
        if (0 == cashLeft) {
            return 0;
        }
        // if left cash not 0 - and bid is more than cash left try to make a random bid in bounds of cash left
        bid = new Random().nextInt(cashLeft);
        cashLeft -= bid;
        return bid;
    }

    /**
     * @param own   the bid of this bidder
     * @param other the bid of the other bidder
     */
    @Override
    public void bids(int own, int other) {
        saveBidsToHistory(own, other);
    }

    /**
     * Save the bids to the history. History is used to make the bidding decisions based on the previous results;
     *
     * @param own   the bid of this bidder
     * @param other the bid of the other bidder
     */
    private void saveBidsToHistory(int own, int other) {
        bidHistory.add(new Pair<>(own, other));
        if (own > other) {
            this.gainedQuantityUnits += 2;
        } else if (own == other) {
            this.gainedQuantityUnits += 1;
        }
    }

}
