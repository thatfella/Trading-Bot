package com.thatfella.auction.strategy;

import com.thatfella.auction.bidder.BidderImpl;

import java.util.Scanner;

import static com.thatfella.auction.utils.BiddingUtils.bankrupcyDanger;

/**
 * Strategy that accepts the keyboard input;
 */
public class KeyBoardStrategy implements BiddingStrategy {

    Scanner scanner = new Scanner(System.in);

    @Override
    public int makeBid(BidderImpl bidder) {
        int currentRound = bidder.getCurrentRound();
        int bid = scanner.nextInt();

        while (bankrupcyDanger(bid, bidder)) {
            System.out.println("Wrong amount of money units  try again");
            bid = scanner.nextInt();
        }

        bidder.setCurrentRound(++currentRound);
        System.out.println("Opponent's keyboard bid " + bid);
        return bid;
    }
}
