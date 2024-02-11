package trandafyl.dev.hackathontest.mappers;

import org.springframework.stereotype.Component;
import trandafyl.dev.hackathontest.dto.AuctionCategoriesResponse;
import trandafyl.dev.hackathontest.models.AuctionCategory;

import java.util.List;

@Component
public class AuctionCategoryMapper {
    public AuctionCategoriesResponse mapToDTO(AuctionCategory[] auctionCategories){
        return new AuctionCategoriesResponse(List.of(auctionCategories));
    }
    public AuctionCategoriesResponse mapToDTO(List<AuctionCategory> auctionCategories){
        return new AuctionCategoriesResponse(auctionCategories);
    }
}
