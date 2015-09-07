package E01_specification_by_example

import java.sql.Timestamp

/**
 * @author varsum
 */
class AuctionBid {
    private final Integer auctionId;
    private final Integer userId;
    private final BigDecimal bid;
    private final Timestamp createTs;

    private AuctionBid(Integer auctionId, Integer userId, BigDecimal bid, Timestamp createTs) {
        this.auctionId = auctionId
        this.userId = userId
        this.bid = bid
        this.createTs = createTs
    }

    static AuctionBid of(Integer auctionId, Integer userId, BigDecimal bid, Timestamp createTs) {
        return new AuctionBid(auctionId, userId, bid, createTs);
    }

 }
