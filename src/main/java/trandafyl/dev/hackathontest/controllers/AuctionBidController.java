package trandafyl.dev.hackathontest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trandafyl.dev.hackathontest.dto.AuctionBidRequest;
import trandafyl.dev.hackathontest.dto.AuctionBidResponse;
import trandafyl.dev.hackathontest.services.AuctionBidService;

import java.util.List;

@RestController
@RequestMapping("/auction-lots/")
@AllArgsConstructor
public class AuctionBidController {

    private final AuctionBidService auctionBidService;

    @GetMapping("{lot_id}/bids/")
    public ResponseEntity<PageResponse<Page<AuctionBidResponse>>> getBids(@PathVariable long lot_id,
                                                                          @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                          @RequestParam(required = false, defaultValue = "100") int pageSize) {
        var bids = auctionBidService.getBids(lot_id, pageNumber, pageSize);

        return ResponseEntity.ok(bids);
    }
    @PostMapping("{lot_id}/bids/")
    public ResponseEntity<AuctionBidResponse> addBid(@PathVariable long lot_id, @RequestBody AuctionBidRequest newBid) {
        var bid = auctionBidService.addBid(lot_id, newBid);

        return ResponseEntity.status(HttpStatus.CREATED).body(bid.orElseThrow());
    }

    @PutMapping("{lot_id}/bids/{bid_id}/")
    public ResponseEntity<AuctionBidResponse> editBid(@PathVariable long lot_id, @PathVariable long bid_id, @RequestBody AuctionBidRequest editedBid) {
        var optionalBid = auctionBidService.editBid(lot_id, bid_id, editedBid);

        return ResponseEntity.status(HttpStatus.CREATED).body(optionalBid.orElseThrow());
    }

//    @GetMapping("{lot_id}/bids/bidders")
//    public AuctionBidResponse getBidders(@PathVariable long lot_id) {
//
//    }
//

    @GetMapping("bids/{bid_id}/")
    public ResponseEntity<AuctionBidResponse> getBid(@PathVariable long bid_id) {
        var optionalBid = auctionBidService.getBid(bid_id);

        return ResponseEntity.ok(optionalBid.orElseThrow());
    }

    @DeleteMapping("bids/{bid_id}/")
    public ResponseEntity<String> deleteBid(@PathVariable long bid_id) {
        auctionBidService.deleteBid(bid_id);

        return ResponseEntity.noContent().build();
    }
}
