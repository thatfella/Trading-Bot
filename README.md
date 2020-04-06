# Trading-Bot
Code is documented and covered with tests (for coverage used plugin Jacoco - current coverage ratio is 0.88)

# Key Classes
<b>Bidder</b> - interface which describes a bidder;<br>
<b>BidderImpl</b> - implementation of Bidder interface;<br>
<b>BiddingStrategy</b> - an interface, which describes bidding strategy;<br>
<b>MyBiddingStrategy</b> - my implementation of BiddingStrategy;<br>
<b>AggressiveStrategy</b> - implementation of BiddingStrategy which is trying to win auction as soon as possible;<br>
<b>AlwaysIncrementWinnerBidStrategy</b> - implementation of BiddingStrategy which increments the last winner's bid;<br>
<b>EqualBidsStrategy</b> - implementation of BiddingStrategy which makes equal bids every turn;<br>
<b>EqualBidsWithSkippingFirstTurn</b> - implementation of BiddingStrategy which skips first turn and makes equal bids the next turns;<br>
<b>RandomBidStrategy</b> - implementation of BiddingStrategy which makes a random bid every turn;<br>
<b>BidAnalyzer</b> - provides some methods for bid analysis<br>
Also there are several utility classes.


# Task
A product x QU (quantity units) will be auctioned under 2 parties. The parties have each y MU (monetary units) for auction. They offer then simultaneously an arbitrary number of its MU on the first 2 QU of the product. After that, the bids will be visible to both. The 2 QU of the product is awarded to who has offered the most MU; if both bid the same, then both get 1 QU. Both bidders must pay their amount - including the defeated. A bid of 0 MU is allowed. Bidding on each 2 QU is repeated until the supply of x QU is fully auctioned. Each bidder aims to get a larger amount than its competitor.
In an auction wins the program that is able to get more QU than the other. With a tie, the program that retains more MU wins. Write a program that can participate in such an auction and competes with one of our programs. Please explain its strategy.

The bidder interface:

<code>
package auction;


/**
 * Represents a bidder for the action.
 */
public interface Bidder {

	/**
	 * Initializes the bidder with the production quantity and the allowed cash limit.
	 * 
	 * @param quantity
	 *            the quantity
	 * @param cash
	 *            the cash limit
	 */
	void init(int quantity, int cash);

	/**
	 * Retrieves the next bid for the product, which may be zero.
	 * 
	 * @return the next bid
	 */
	int placeBid();

	/**
	 * Shows the bids of the two bidders.
	 * 
	 * @param own
	 *            the bid of this bidder
	 * @param other
	 *            the bid of the other bidder
	 */
	void bids(int own, int other);
}
</code>
