package trandafyl.dev.hackathontest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionLot {
    @Id
    @Column(name = "lot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotBlank(message = "The value of 'name' must not be blank")
    private String name;

    @NotBlank(message = "The value of 'description' must not be blank")
    private String description;

    @NotNull
    @PositiveOrZero(message = "The value of 'startPrice' must be positive or zero")
    private Double startPrice;

    @NotNull
    @PositiveOrZero(message = "The value of 'minIncrease' must be positive or zero")
    private Double minIncrease;

    @ElementCollection
    @Column(name = "images_links", nullable = false)
    private List<String> imagesLinks = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<AuctionCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "auctionLot")
    private List<AuctionBid> auctionBids = new ArrayList<>();
}
