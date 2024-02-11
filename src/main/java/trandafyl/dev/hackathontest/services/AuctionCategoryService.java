package trandafyl.dev.hackathontest.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import trandafyl.dev.hackathontest.models.AuctionCategory;
import trandafyl.dev.hackathontest.dto.*;
import trandafyl.dev.hackathontest.mappers.AuctionCategoryMapper;

@Service
@AllArgsConstructor
public class AuctionCategoryService {
    private final AuctionCategoryMapper categoryMapper;

    public AuctionCategoriesResponse getCategories() {
        var categories = AuctionCategory.values();

        return categoryMapper.mapToDTO(categories);
    }

}
