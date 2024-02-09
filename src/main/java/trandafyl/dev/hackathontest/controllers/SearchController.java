package trandafyl.dev.hackathontest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.dto.PageResponse;
import trandafyl.dev.hackathontest.services.SearchService;

@RestController
@AllArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search/")
    public ResponseEntity<PageResponse<Streamable<AuctionLotResponse>>/*ListResponse*/> search (
            @RequestParam String q,
                @RequestParam(required = false, defaultValue = "0") long pageNumber,
                    @RequestParam(required = false, defaultValue = "5") long pageSize) {
        return ResponseEntity.ok(searchService.searchWithFuzzy(q, pageNumber, pageSize));
    }
}
