package trandafyl.dev.hackathontest.dto;

import lombok.Builder;
import lombok.Data;
import trandafyl.dev.hackathontest.models.User;

import java.time.LocalDateTime;

@Data
@Builder
public class AuctionBidResponse {
    private Long id;
    private Double price;
    private UserPartialResponse user;
    private LocalDateTime bidAt;
}
