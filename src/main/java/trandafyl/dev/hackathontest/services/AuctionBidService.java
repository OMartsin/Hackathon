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
import trandafyl.dev.hackathontest.mappers.AuctionBidMapper;
import trandafyl.dev.hackathontest.mappers.UserMapper;
import trandafyl.dev.hackathontest.models.AuctionBid;
import trandafyl.dev.hackathontest.repositories.AuctionBidRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Log4j2
public class AuctionBidService {
    private final AuctionBidRepository auctionBidRepository;
    private final AuctionLotService auctionLotService;
    private final AuthorizationValidator authValidator;
    private final UserMapper userMapper;
    private final AuctionBidMapper bidMapper;

    public PageResponse<Page<AuctionBidResponse>> getBids(long lot_id, int pageNumber, int pageSize) {
        var bids = auctionBidRepository.findByAuctionLotId(PageRequest.of(pageNumber, pageSize), lot_id);

        return new PageResponse<>(bids.map(bidMapper::mapToDTO), auctionBidRepository.count());
    }

    public Optional<AuctionBidResponse> addBid(long lot_id, AuctionBidRequest newBid) {
        var optionalLot = auctionLotService.getAuction(lot_id);

        if(optionalLot.isPresent()) {
            var lot = optionalLot.get();
            var bid = bidMapper.mapFromDTO(lot, newBid);
            if(!isValidBid(lot, bid)){
                throw new BidPlacingException("The bid is not valid!");
            }
            var result = auctionBidRepository.save(bid);
            return Optional.of(bidMapper.mapToDTO(result));
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

        var newBid = bidMapper.mapFromDTO(lot, newBidRequest);
        newBid.setId(bid.getId());

        var result = auctionBidRepository.save(bid);
        return Optional.of(bidMapper.mapToDTO(result));
    }

    public Optional<AuctionBidResponse> getBid(long bidId) {
        var optionalBid = auctionBidRepository.findById(bidId);

        return optionalBid.map(bidMapper::mapToDTO);
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
}
