package trandafyl.dev.hackathontest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trandafyl.dev.hackathontest.models.AuctionBid;
import trandafyl.dev.hackathontest.models.UserRole;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private UserRole role;
    private List<AuctionBid> auctionBids;
}
