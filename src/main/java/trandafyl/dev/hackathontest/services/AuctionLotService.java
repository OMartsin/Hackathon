package trandafyl.dev.hackathontest.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import trandafyl.dev.hackathontest.dto.AuctionLotRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.models.AuctionBid;
import trandafyl.dev.hackathontest.models.AuctionLot;
import trandafyl.dev.hackathontest.repositories.AuctionRepository;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class AuctionLotService {

    AuctionRepository auctionRepository;

    public List<AuctionLotResponse> getAuctions() {
        var auctions = auctionRepository.findAll();

        return auctions.stream().map(this::mapToDTO).toList();
    }

    public Optional<AuctionLotResponse> getAuction(long id) {
        var auction = auctionRepository.findById(id);
        return auction.map(this::mapToDTO);
    }

    public AuctionLotResponse addAuction(AuctionLotRequest newAuction) {
        var mappedAuction = mapFromDTO(newAuction);

        var savedAuction = auctionRepository.save(mappedAuction);

        return mapToDTO(savedAuction);
    }

    public AuctionLotResponse editAuction(long auctionId, AuctionLotRequest editedAuction) {
        var mappedAuction = mapFromDTO(editedAuction);
        mappedAuction.setId(auctionId);

        var savedAuction = auctionRepository.save(mappedAuction);

        return mapToDTO(savedAuction);
    }

    public void deleteAuction(long auctionId) {
        auctionRepository.deleteById(auctionId);
    }


    private AuctionLot mapFromDTO(AuctionLotRequest auction) {
        return AuctionLot
                .builder()
                .auctionBids(new ArrayList<>())
                .categories(auction.getCategories())
                .description(auction.getDescription())
                .imagesLinks(auction.getImagesLinks())
                .minIncrease(auction.getMinIncrease())
                .name(auction.getName())
                .startPrice(auction.getStartPrice())
                .build();
    }

    private AuctionLotResponse mapToDTO(AuctionLot auctionLot) {
        return AuctionLotResponse
                .builder()
                .auctionBids(auctionLot.getAuctionBids())
                .categories(auctionLot.getCategories())
                .currentBid(auctionLot.getAuctionBids().stream().max(Comparator.comparing(AuctionBid::getPrice)).orElse(null))
                .description(auctionLot.getDescription())
                .id(auctionLot.getId())
                .imagesLinks(auctionLot.getImagesLinks())
                .minIncrease(auctionLot.getMinIncrease())
                .name(auctionLot.getName())
                .startPrice(auctionLot.getStartPrice())
                .build();
    }
}
