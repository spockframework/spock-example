package E01_specification_by_example

import spock.lang.Specification

/**
 * @author varsum
 */
class AuctionBiddingSpec_Solution extends Specification {
    def "User bids in auction and it succeed."() {
        given: "an identity of an user and an identity of an auction"
        Integer userId = 100_000
        Integer auctionId = 12_787

        and: "a bid value"
        BigDecimal bid = 3_150

        and: "bidding facade"
        def biddingFacade = new AuctionBiddingFacade()

        when: "user bids on an auction"
        def userBid = biddingFacade.bid(auctionId, userId, bid)

        then: "last bid is from that user"
        userId == userBid.userId

        then: "last bid is of price given by that user"
        bid == userBid.bid
    }

    def "User bids in auction and it's bid is too low."() {
        given: "an identity of an user and an identity of an auction"
        Integer userId = 100_001
        Integer auctionId = 12_787

        and: "a bid value"
        BigDecimal bid = 3_150

        and: "bidding facade"
        def biddingFacade = new AuctionBiddingFacade()

        when: "user bids on an auction"
        biddingFacade.bid(auctionId, userId, bid)

        then: "Actual price of auction product is not changed"
        AuctionBiddingOperationException exception = thrown()
        exception.message == 'Your bid has been rejected due to too low bid value'

        and: "User bid is too low"
        exception.reason == AuctionBiddingOperationException.Reason.BID_TOO_LOW


    }
}

