package trandafyl.dev.hackathontest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trandafyl.dev.hackathontest.dto.AuctionCategoriesResponse;
import trandafyl.dev.hackathontest.services.AuctionCategoryService;

@RestController
@AllArgsConstructor
@RequestMapping("/categories/")
@Tag(name = "Category")
public class AuctionCategoryController {
    private final AuctionCategoryService categoryService;

    @GetMapping
    public ResponseEntity<AuctionCategoriesResponse> getCategories(){
        var categories = categoryService.getCategories();

        return ResponseEntity.ok(categories);
    }
}
