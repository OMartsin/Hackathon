package trandafyl.dev.hackathontest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import trandafyl.dev.hackathontest.dto.AuctionLotChatMessageRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotChatMessageResponse;
import trandafyl.dev.hackathontest.services.AuctionLotChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction-lots/{id}/chat/")
public class AuctionChatController {
    private final AuctionLotChatService auctionLotChatService;

    @GetMapping(path = "subscribe/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToChat(@PathVariable("id") Long auctionLotId) {
        return auctionLotChatService.createEmitterForAuctionLot(auctionLotId);
    }

    @PostMapping("send-message/")
    public ResponseEntity<AuctionLotChatMessageResponse> sendMessage(@PathVariable("id") Long auctionLotId, @RequestBody String message) {
        return ResponseEntity.ok(auctionLotChatService.addMessage
                (new AuctionLotChatMessageRequest(message, auctionLotId)).orElseThrow());
    }
}
