package trandafyl.dev.hackathontest.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import trandafyl.dev.hackathontest.config.AuthorizationValidator;
import trandafyl.dev.hackathontest.config.BidPlacingException;
import trandafyl.dev.hackathontest.dto.PageResponse;
import trandafyl.dev.hackathontest.dto.AuctionBidRequest;
import trandafyl.dev.hackathontest.dto.AuctionBidResponse;
import trandafyl.dev.hackathontest.dto.AuctionLotResponse;
import trandafyl.dev.hackathontest.mappers.UserMapper;
import trandafyl.dev.hackathontest.models.AuctionBid;
import trandafyl.dev.hackathontest.repositories.AuctionBidRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Log4j2
public class AuctionBidService {
    private final AuctionBidRepository auctionBidRepository;
    private final AuctionLotService auctionLotService;
    private final AuthService authService;
    private final AuthorizationValidator authValidator;
    private final UserMapper userMapper;

    public PageResponse<Page<AuctionBidResponse>> getBids(long lot_id, int pageNumber, int pageSize) {
        var bids = auctionBidRepository.findByAuctionLotId(PageRequest.of(pageNumber, pageSize), lot_id);

        return new PageResponse<>(bids.map(this::mapToDTO), auctionBidRepository.count());
    }

    public Optional<AuctionBidResponse> addBid(long lot_id, AuctionBidRequest newBid) {
        var optionalLot = auctionLotService.getAuction(lot_id);

        if(optionalLot.isPresent()) {
            var lot = optionalLot.get();
            var bid = mapFromDTO(lot, newBid);
            if(!isValidBid(lot, bid)){
                throw new BidPlacingException("The bid is not valid!");
            }
            var result = auctionBidRepository.save(bid);
            return Optional.of(mapToDTO(result));
        }
        return Optional.empty();
    }

    public Optional<AuctionBidResponse> editBid(long lot_id, long bid_id, AuctionBidRequest newBidRequest){
        var optionalLot = auctionLotService.getAuction(lot_id);

        if(optionalLot.isEmpty()) {
            return Optional.empty();
        }
        var lot = optionalLot.get();

        authValidator.checkEditAuthority(userMapper.toUser(lot.getCreator()));

        var optionalBid = auctionBidRepository.findById(bid_id);
        if(optionalBid.isEmpty()) {
            return Optional.empty();
        }
        var bid = optionalBid.get();

        if(!isValidBid(lot, bid)) {
            throw new BidPlacingException("The bid is not valid!");
        }

        var newBid = mapFromDTO(lot, newBidRequest);
        newBid.setId(bid.getId());

        var result = auctionBidRepository.save(bid);
        return Optional.of(mapToDTO(result));
    }

    public Optional<AuctionBidResponse> getBid(long bidId) {
        var optionalBid = auctionBidRepository.findById(bidId);

        return optionalBid.map(this::mapToDTO);
    }

    public void deleteBid(long bidId) {
        var bidder = auctionBidRepository.findById(bidId).orElseThrow().getUser();

        authValidator.checkEditAuthority(bidder);

        auctionBidRepository.deleteById(bidId);
    }

    private boolean isValidBid(AuctionLotResponse lot, AuctionBid bid){
        return LocalDateTime.now().isBefore(lot.getEndDateTime()) &&
                !lot.getCreator().equals(bid.getUser()) &&
                ((lot.getCurrentBid() != null && bid.getPrice() - lot.getCurrentBid().getPrice() >= lot.getMinIncrease()) ||
                (lot.getCurrentBid() == null && bid.getPrice() >= lot.getStartPrice()));
    }

    public AuctionBid mapFromDTO(AuctionLotResponse lot, AuctionBidRequest bid) {
        return AuctionBid
                .builder()
                .bidAt(LocalDateTime.now())
                .auctionLot(auctionLotService.mapFromResponseDTO(lot))
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
}
