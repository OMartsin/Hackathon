package trandafyl.dev.hackatontest.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
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

    @OneToMany(mappedBy = "auction")
    private List<AuctionBid> auctionBids = new ArrayList<>();

}
