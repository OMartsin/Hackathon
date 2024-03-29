package trandafyl.dev.hackathontest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auction_bid")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @PositiveOrZero(message = "The value of 'startPrice' must be positive or zero")
    private Double price;

    @NotNull
    @PastOrPresent
    private LocalDateTime bidAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private AuctionLot auctionLot;

    @JsonBackReference
    public AuctionLot getAuctionLot() {
        return auctionLot;
    }

    @JsonBackReference
    public User getUser() {
        return user;
    }
}
