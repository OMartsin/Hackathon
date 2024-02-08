package trandafyl.dev.hackathontest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import trandafyl.dev.hackathontest.dto.AuctionLotEditRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotPartialResponse;
import trandafyl.dev.hackathontest.dto.AuctionLotRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.services.AuctionLotService;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuctionLotController {

    private final AuctionLotService auctionLotService;

    @GetMapping("/auction-lots/")
    public ResponseEntity<List<AuctionLotResponse>> getAuctions() {
        var auctions = auctionLotService.getAuctions();

        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/auction-lots/{id}/")
    public ResponseEntity<AuctionLotResponse> getAuction(@PathVariable long id) {
        var auction = auctionLotService.getAuction(id);

        return auction
                .map(ResponseEntity::ok)
                .orElseThrow();
    }

    @PostMapping("/auction-lots/")
    public ResponseEntity<AuctionLotResponse> addAuction(@RequestPart("files") List<MultipartFile> files, @ModelAttribute AuctionLotRequest newAuction) {
        var auction = auctionLotService.addAuction(newAuction, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(auction);
    }

    @PutMapping("/auction-lots/{id}/")
    public ResponseEntity<AuctionLotResponse> editAuction(@PathVariable long id, @ModelAttribute AuctionLotEditRequest editedAuction, @RequestPart("files") List<MultipartFile> files) {
        var auction = auctionLotService.editAuction(id, editedAuction, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(auction);
    }

    @DeleteMapping("/auction-lots/{id}/")
    public ResponseEntity<String> deleteAuction(@PathVariable long id) {
        auctionLotService.deleteAuction(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("users/{id}/auction-lots/")
    public ResponseEntity<List<AuctionLotPartialResponse>> getUsersLots(@PathVariable long id){
        var user = auctionLotService.getUserLots(id);

        return ResponseEntity.ok(user);
    }
}
