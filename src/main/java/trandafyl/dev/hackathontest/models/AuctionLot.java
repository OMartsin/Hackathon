package trandafyl.dev.hackathontest.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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

    @NotNull
    @PastOrPresent
    private LocalDateTime startDateTime = LocalDateTime.now();

    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime endDateTime;

    @ElementCollection
    @Column(name = "image_names", nullable = false)
    private List<String> imageNames = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<AuctionCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "auctionLot")
    private List<AuctionBid> auctionBids = new ArrayList<>();

    @OneToMany(mappedBy = "auctionLot")
    private List<AuctionLotChatMessage> auctionLotChatMessages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @JsonManagedReference
    public List<AuctionBid> getAuctionBids() {
        return auctionBids;
    }
}
