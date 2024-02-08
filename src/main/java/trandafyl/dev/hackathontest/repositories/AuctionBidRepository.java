package trandafyl.dev.hackathontest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import trandafyl.dev.hackathontest.models.AuctionBid;

import java.util.List;

public interface AuctionBidRepository extends JpaRepository<AuctionBid, Long> {
    List<AuctionBid> findByAuctionLotId(Long lotId);
}
