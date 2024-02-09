package trandafyl.dev.hackathontest.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuctionBidResponse {
    private Long id;
    private Double price;
    private UserListResponse.PartialUserResponse user;
    private LocalDateTime bidAt;
}
