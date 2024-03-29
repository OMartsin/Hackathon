package trandafyl.dev.hackathontest.dto;

import lombok.Builder;
import lombok.Data;
import trandafyl.dev.hackathontest.models.AuctionBid;
import trandafyl.dev.hackathontest.models.AuctionCategory;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class AuctionLotResponse {
    private Long id;
    private String name;
    private UserListResponse.PartialUserResponse creator;
    private Double startPrice;
    private Double minIncrease;
    private String description;
    private AuctionBid currentBid;
    private LocalDateTime endDateTime;
    private LocalDateTime startDateTime;
    private final List<String> imageNames;
    private final List<AuctionBid> auctionBids;
    private final List<AuctionCategory> categories;
}
