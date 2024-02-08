package trandafyl.dev.hackathontest.dto;

import lombok.Data;

@Data
public class AuctionBidRequest {
    private Long userId;
    private Double price;
}
