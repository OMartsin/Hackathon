package trandafyl.dev.hackathontest.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trandafyl.dev.hackathontest.models.AuctionCategory;
import trandafyl.dev.hackathontest.models.AuctionLot;

import java.util.List;

@Repository
public interface AuctionLotRepository extends JpaRepository<AuctionLot, Long> {
    List<AuctionLot> findAllByCreatorId(Long id);

    @Query("SELECT a FROM AuctionLot a JOIN a.categories c " + "WHERE c IN :categories AND " + "(COALESCE((SELECT MAX(b.price) FROM AuctionBid b WHERE b.auctionLot = a), a.startPrice) BETWEEN :minValue AND :maxValue)")
    Page<AuctionLot> findByCategoriesAndPriceRange(@Param("categories") List<AuctionCategory> categories,
                                                   @Param("minValue") Double minValue,
                                                   @Param("maxValue") Double maxValue, Pageable pageable);
}
