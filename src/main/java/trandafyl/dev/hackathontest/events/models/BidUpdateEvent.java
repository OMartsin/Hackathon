package trandafyl.dev.hackathontest.events.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import trandafyl.dev.hackathontest.models.User;

@AllArgsConstructor
@Getter
@Setter
public class BidUpdateEvent implements UpdateEvent{
    private final Long auctionId;
    private final User user;
    private final Double newBid;

    @Override
    public String toString() {
        return "BidUpdateEvent{" +
                "auctionId=" + auctionId +
                ", user=" + user +
                ", newBid=" + newBid +
                '}';
    }

    @Override
    public String toJson() {
        return "{" +
                "\"auctionId\":" + auctionId +
                ", \"user\":" + user +
                ", \"newBid\":" + newBid +
                '}';
    }
}
