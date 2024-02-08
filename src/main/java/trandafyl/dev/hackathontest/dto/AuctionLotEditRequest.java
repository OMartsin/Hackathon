package trandafyl.dev.hackathontest.dto;

import lombok.Builder;
import lombok.Data;
import trandafyl.dev.hackathontest.models.AuctionCategory;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AuctionLotEditRequest {
    private String name;
    private String description;
    private Double startPrice;
    private Double minIncrease;
    private LocalDateTime endDateTime;
    private long creatorId;
    private List<String> imageNames;
    private List<AuctionCategory> categories;
}
