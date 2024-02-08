package trandafyl.dev.hackathontest.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import trandafyl.dev.hackathontest.dto.AuctionLotEditRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.models.AuctionBid;
import trandafyl.dev.hackathontest.models.AuctionLot;
import trandafyl.dev.hackathontest.repositories.AuctionLotRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Transactional
public class AuctionLotService {

    private final AuctionLotRepository auctionRepository;
    private final UserService userService;
    private final S3Service s3Service;

    public List<AuctionLotResponse> getAuctions() {
        var auctions = auctionRepository.findAll();

        return auctions.stream().map(this::mapToDTO).toList();
    }

    public Optional<AuctionLotResponse> getAuction(long id) {
        var auction = auctionRepository.findById(id);
        return auction.map(this::mapToDTO);
    }

    public AuctionLotResponse addAuction(AuctionLotRequest newAuction, List<MultipartFile> files) {
        var images = files.stream().map(s3Service::uploadFile).toList();
        var mappedAuction = mapFromDTO(newAuction, images);
        var savedAuction = auctionRepository.save(mappedAuction);

        return mapToDTO(savedAuction);
    }

    public AuctionLotResponse editAuction(long auctionId, AuctionLotEditRequest editedAuction, List<MultipartFile> files) {
        var auction = auctionRepository.findById(auctionId).orElseThrow();

        var images = Stream.concat(files.stream().map(s3Service::uploadFile), auction.getImageNames().stream()).toList();

        var mappedAuction = mapFromEditRequestDTO(editedAuction, auction, images);

        var savedAuction = auctionRepository.save(mappedAuction);

        return mapToDTO(savedAuction);
    }

    public void deleteAuction(long auctionId) {
        var auction = auctionRepository.findById(auctionId).orElseThrow();
        auction.getImageNames().forEach(s3Service::deleteFile);
        auctionRepository.deleteById(auctionId);
    }


    public AuctionLot mapFromDTO(AuctionLotRequest auction, List<String> images) {
        return AuctionLot
                .builder()
                .auctionBids(new ArrayList<>())
                .categories(auction.getCategories())
                .creator(userService.getUser(auction.getCreatorId()).orElseThrow())
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
                .creator(auctionLot.getCreator())
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
                .categories(auctionLot.getCategories())
                .description(auctionLot.getDescription())
                .endDateTime(auctionLot.getEndDateTime())
                .imageNames(auctionLot.getImageNames())
                .minIncrease(auctionLot.getMinIncrease())
                .name(auctionLot.getName())
                .startPrice(auctionLot.getStartPrice())
                .build();
    }
}
