package trandafyl.dev.hackathontest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import trandafyl.dev.hackathontest.dto.*;
import trandafyl.dev.hackathontest.services.AuctionLotService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auction-lots/")
@Tag(name = "Lots")
public class AuctionLotController {

    private final AuctionLotService auctionLotService;

    @GetMapping
    public ResponseEntity<PageResponse<Page<AuctionLotResponse>>> getAuctions(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
                @RequestParam(required = false, defaultValue = "100") int pageSize) {

        var auctions = auctionLotService.getAuctions(pageNumber, pageSize);

        return ResponseEntity.ok(auctions);
    }

    @GetMapping("{id}/")
    public ResponseEntity<AuctionLotResponse> getAuction(@PathVariable long id) {
        var auction = auctionLotService.getAuction(id);

        return auction
                .map(ResponseEntity::ok)
                .orElseThrow();
    }

    @PostMapping
    public ResponseEntity<AuctionLotResponse> addAuction(@RequestPart("files") List<MultipartFile> files, @ModelAttribute AuctionLotRequest newAuction) {
        var auction = auctionLotService.addAuction(newAuction, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(auction);
    }

    @PutMapping("{id}/")
    public ResponseEntity<AuctionLotResponse> editAuction(@PathVariable long id, @ModelAttribute AuctionLotEditRequest editedAuction, @RequestPart("files") List<MultipartFile> files) {
        var auction = auctionLotService.editAuction(id, editedAuction, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(auction);
    }

    @DeleteMapping("{id}/")
    public ResponseEntity<String> deleteAuction(@PathVariable long id) {
        auctionLotService.deleteAuction(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("users/{id}")
    public ResponseEntity<AuctionLotListResponse> getUsersLots(@PathVariable long id){
        var user = auctionLotService.getUserLots(id);

        return ResponseEntity.ok(user);
    }
}
