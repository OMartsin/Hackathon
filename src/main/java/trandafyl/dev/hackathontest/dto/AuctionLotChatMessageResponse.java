package trandafyl.dev.hackathontest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class AuctionLotChatMessageResponse {
    private Long id;
    private String message;
    private UserListResponse.PartialUserResponse user;
    private LocalDateTime createdAt;
    private Long auctionLotId;
}
