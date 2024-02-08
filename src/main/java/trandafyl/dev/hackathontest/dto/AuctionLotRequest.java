package trandafyl.dev.hackathontest.dto;

import lombok.Builder;
import lombok.Data;
import trandafyl.dev.hackathontest.models.AuctionCategory;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class AuctionLotRequest {
    private String name;
    private String description;
    private Double startPrice;
    private Double minIncrease;
    private LocalDateTime endDateTime;
    private final List<AuctionCategory> categories;
}
