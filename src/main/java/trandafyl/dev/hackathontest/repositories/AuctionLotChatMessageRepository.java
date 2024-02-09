package trandafyl.dev.hackathontest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import trandafyl.dev.hackathontest.models.AuctionLotChatMessage;

public interface AuctionLotChatMessageRepository extends JpaRepository<AuctionLotChatMessage, Long> {

}
