package trandafyl.dev.hackathontest.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 64, unique = true)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String username;

    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<AuctionBid> auctionBids = new ArrayList<>();

    @JsonManagedReference
    public List<AuctionBid> getAuctionBids() {
        return auctionBids;
    }

    @OneToMany(mappedBy = "sender")
    private List<AuctionLotChatMessage> sentMessages = new ArrayList<>();
}
