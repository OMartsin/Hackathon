package trandafyl.dev.hackathontest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trandafyl.dev.hackathontest.dto.AuctionLotRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.services.AuctionLotService;

import java.util.List;

@RestController
@RequestMapping("/auction-lots/")
@AllArgsConstructor
public class AuctionLotController {

    private final AuctionLotService auctionLotService;

    @GetMapping
    public ResponseEntity<List<AuctionLotResponse>> getAuctions() {
        var auctions = auctionLotService.getAuctions();

        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<AuctionLotResponse> getAuction(@PathVariable long id) {
        var auction = auctionLotService.getAuction(id);

        return auction
                .map(ResponseEntity::ok)
                .orElseThrow();
    }

    @PostMapping
    public ResponseEntity<AuctionLotResponse> addAuction(@RequestBody AuctionLotRequest newAuction) {
        var auction = auctionLotService.addAuction(newAuction);

        return ResponseEntity.status(HttpStatus.CREATED).body(auction);
    }

    @PutMapping("/{id}/")
    public ResponseEntity<AuctionLotResponse> editAuction(@PathVariable long id, @RequestBody AuctionLotRequest editedAuction) {
        var auction = auctionLotService.editAuction(id, editedAuction);

        return ResponseEntity.status(HttpStatus.CREATED).body(auction);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<String> deleteAuction(@PathVariable long id) {
        auctionLotService.deleteAuction(id);

        return ResponseEntity.noContent().build();
    }
}
