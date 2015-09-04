package E01_specification_by_example

/**
 * @author varsum
 */
class AuctionBiddingOperationException extends Exception{
    private Reason reason

    AuctionBiddingOperationException(String message, Reason reason) {
        super(message)
        this.reason = reason
    }

    Reason getReason() {
        return reason
    }

    public enum Reason {
        BID_TOO_LOW, AUCTION_FINISHED
    }

}
