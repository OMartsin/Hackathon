package trandafyl.dev.hackathontest.models;

import jakarta.persistence.*;
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

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    private Double startPrice;

    @Basic(optional = false)
    @Column(nullable = false)
    private Double currentPrice;

    @Basic(optional = false)
    @Column(nullable = false)
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
