package trandafyl.dev.hackathontest.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trandafyl.dev.hackathontest.models.AuctionBid;

import java.util.List;

@Repository
public interface AuctionBidRepository extends JpaRepository<AuctionBid, Long> {
    List<AuctionBid> findByAuctionLotId(Long lotId);
    Page<AuctionBid> findByAuctionLotId(Pageable pageable, Long lotId);
    Page<AuctionBid> findByUserId(Pageable pageable, Long userId);
}
