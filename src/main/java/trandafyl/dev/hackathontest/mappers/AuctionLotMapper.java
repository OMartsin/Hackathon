package trandafyl.dev.hackathontest.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import trandafyl.dev.hackathontest.dto.AuctionLotEditRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotListResponse;
import trandafyl.dev.hackathontest.dto.AuctionLotRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.models.AuctionBid;
import trandafyl.dev.hackathontest.models.AuctionLot;
import trandafyl.dev.hackathontest.services.AuthService;
import trandafyl.dev.hackathontest.services.S3Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class AuctionLotMapper {
    private final S3Service s3Service;
    private final UserMapper userMapper;
    private final AuthService authService;

    public AuctionLot mapFromDTO(AuctionLotRequest auction, List<String> images) {
        return AuctionLot
                .builder()
                .auctionBids(new ArrayList<>())
                .categories(auction.getCategories())
                .creator(userMapper.toUser(authService.getCurrentUser().orElseThrow()))
                .description(auction.getDescription())
                .endDateTime(auction.getEndDateTime())
                .imageNames(images)
                .minIncrease(auction.getMinIncrease())
                .name(auction.getName())
                .startDateTime(LocalDateTime.now())
                .startPrice(auction.getStartPrice())
                .build();
    }

    public AuctionLotResponse mapToDTO(AuctionLot auctionLot) {
        return AuctionLotResponse
                .builder()
                .auctionBids(auctionLot.getAuctionBids())
                .categories(auctionLot.getCategories())
                .creator(userMapper.toUserPartialResponse(auctionLot.getCreator()))
                .currentBid(auctionLot.getAuctionBids().stream().max(Comparator.comparing(AuctionBid::getPrice)).orElse(null))
                .description(auctionLot.getDescription())
                .endDateTime(auctionLot.getEndDateTime())
                .id(auctionLot.getId())
                .imageNames(auctionLot.getImageNames().stream().map(s3Service::createURL).toList())
                .minIncrease(auctionLot.getMinIncrease())
                .name(auctionLot.getName())
                .startDateTime(auctionLot.getStartDateTime())
                .startPrice(auctionLot.getStartPrice())
                .build();
    }


    public AuctionLotListResponse mapToListDTO(List<AuctionLot> auctionLot){
        var partialList = auctionLot.stream().map(this::mapToPartialResponseDTO).toList();
        return AuctionLotListResponse
                .builder()
                .response(partialList)
                .recordsCount(partialList.size())
                .build();
    }


    public AuctionLotListResponse.AuctionLotPartialResponse mapToPartialResponseDTO(AuctionLot auctionLot){
        return AuctionLotListResponse.AuctionLotPartialResponse
                .builder()
                .categories(auctionLot.getCategories())
                .creator(userMapper.toUserPartialResponse(auctionLot.getCreator()))
                .currentBid(auctionLot.getAuctionBids().stream().max(Comparator.comparing(AuctionBid::getPrice)).orElse(null))
                .description(auctionLot.getDescription())
                .endDateTime(auctionLot.getEndDateTime())
                .id(auctionLot.getId())
                .imageNames(auctionLot.getImageNames().stream().map(s3Service::createURL).toList())
                .minIncrease(auctionLot.getMinIncrease())
                .name(auctionLot.getName())
                .startDateTime(auctionLot.getStartDateTime())
                .startPrice(auctionLot.getStartPrice())
                .bidsCount(auctionLot.getAuctionBids().size())
                .build();
    }

    public AuctionLot mapFromResponseDTO(AuctionLotResponse auctionLot) {
        return AuctionLot
                .builder()
                .auctionBids(auctionLot.getAuctionBids())
                .categories(auctionLot.getCategories())
                .description(auctionLot.getDescription())
                .endDateTime(auctionLot.getEndDateTime())
                .id(auctionLot.getId())
                .imageNames(auctionLot.getImageNames())
                .minIncrease(auctionLot.getMinIncrease())
                .name(auctionLot.getName())
                .startDateTime(auctionLot.getStartDateTime())
                .startPrice(auctionLot.getStartPrice())
                .build();
    }

    public AuctionLot mapFromEditRequestDTO(AuctionLotEditRequest auctionLot, AuctionLot auction, List<String> fileNames) {
        return AuctionLot
                .builder()
                .id(auction.getId())
                .startDateTime(auction.getStartDateTime())
                .auctionBids(auction.getAuctionBids())
                .creator(userMapper.toUser(authService.getCurrentUser().orElseThrow()))
                .categories(auctionLot.getCategories())
                .description(auctionLot.getDescription())
                .endDateTime(auctionLot.getEndDateTime())
                .imageNames(Stream.of(auctionLot.getImageNames(), fileNames).flatMap(List::stream).toList())
                .minIncrease(auctionLot.getMinIncrease())
                .name(auctionLot.getName())
                .startPrice(auctionLot.getStartPrice())
                .build();
    }
}
