package trandafyl.dev.hackathontest.dto;

import java.io.Serializable;

/**
 * DTO for {@link trandafyl.dev.hackathontest.events.models.BidUpdateEvent}
 */
public record BidUpdateWebsocketResponse(Long auctionId, UserWebsocketResponse user,
                                         Double newBid) implements Serializable {
}