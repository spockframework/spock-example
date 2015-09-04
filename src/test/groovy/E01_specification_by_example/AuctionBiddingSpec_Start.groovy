package E01_specification_by_example

/**
 * @author varsum
 */
class AuctionBiddingSpec_Start extends spock.lang.Specification {

    def "User bids in auction and it succeed"() {
        given: "an identity of an user and an identity of an auction"

        and: "a bid value"

        when: "user bids on an auction"

        then: "last bid is from that user"

        then: "last bid is of price given by that user"
    }

    def "User bids in auction and it's bid is too low"() {
        given: "an identity of an user and an identity of an auction"

        and: "a bid value"

        and: "bidding facade"

        when: "user bids on an auction"

        then: "actual price of auction product is not changed"

        and: "user bid is too low"
    }
}

