package trandafyl.dev.hackathontest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuctionLotChatMessageRequest {
    private String message;
    private Long auctionLotId;
}
