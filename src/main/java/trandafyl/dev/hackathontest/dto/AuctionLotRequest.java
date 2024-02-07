package trandafyl.dev.hackathontest.dto;

import lombok.Builder;
import lombok.Data;
import trandafyl.dev.hackathontest.models.AuctionCategory;

import java.util.List;

@Builder
@Data
public class AuctionLotRequest {
    private String name;
    private String description;
    private Double startPrice;
    private Double minIncrease;
    private final List<String> imagesLinks;
    private final List<AuctionCategory> categories;
}
