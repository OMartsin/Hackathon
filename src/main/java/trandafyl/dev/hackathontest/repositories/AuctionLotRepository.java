package trandafyl.dev.hackathontest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trandafyl.dev.hackathontest.models.AuctionLot;

import java.util.List;

@Repository
public interface AuctionLotRepository extends JpaRepository<AuctionLot, Long> {
    List<AuctionLot> findAllByCreatorId(Long id);
}
