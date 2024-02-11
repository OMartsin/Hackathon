package trandafyl.dev.hackathontest.dto;

import java.util.List;

import lombok.Data;
import trandafyl.dev.hackathontest.models.AuctionCategory;

@Data
public class AuctionCategoriesResponse {
    private List<AuctionCategory> categories;
    private int recordsCount;

    public AuctionCategoriesResponse(List<AuctionCategory> categories) {
        this.categories = categories;
        recordsCount = categories.size();
    }
}
