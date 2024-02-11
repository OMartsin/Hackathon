package trandafyl.dev.hackathontest.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import trandafyl.dev.hackathontest.dto.AuctionBidRequest;
import trandafyl.dev.hackathontest.dto.AuctionBidResponse;
import trandafyl.dev.hackathontest.dto.AuctionBidToUserResponse;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.models.AuctionBid;
import trandafyl.dev.hackathontest.services.AuthService;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class AuctionBidMapper {
    private final AuctionLotMapper lotMapper;
    private final UserMapper userMapper;
    private final AuthService authService;

    public AuctionBid mapFromDTO(AuctionLotResponse lot, AuctionBidRequest bid) {
        return AuctionBid
                .builder()
                .bidAt(LocalDateTime.now())
                .auctionLot(lotMapper.mapFromResponseDTO(lot))
                .price(bid.getPrice())
                .user(userMapper.toUser(authService.getCurrentUser().orElseThrow()))
                .build();
    }

    public AuctionBidResponse mapToDTO(AuctionBid bid) {
        return AuctionBidResponse
                .builder()
                .id(bid.getId())
                .bidAt(bid.getBidAt())
                .price(bid.getPrice())
                .user(userMapper.toUserPartialResponse(bid.getUser()))
                .build();
    }

    public AuctionBidToUserResponse mapToUserResponseDTO(AuctionBid bid) {
        return AuctionBidToUserResponse
                .builder()
                .id(bid.getId())
                .bidAt(bid.getBidAt())
                .price(bid.getPrice())
                .user(userMapper.toUserPartialResponse(bid.getUser()))
                .lotId(bid.getAuctionLot().getId())
                .lotName(bid.getAuctionLot().getName())
                .biggestBid(bid.getAuctionLot().getAuctionBids().stream().mapToDouble(AuctionBid::getPrice)
                        .max().orElse(0))
                .build();
    }
}
