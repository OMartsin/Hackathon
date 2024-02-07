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
    @Column(length = 32)
    private String username;

    @NotNull
    @Column(length = 64)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<AuctionBid> auctionBids = new ArrayList<>();

    @JsonManagedReference
    public List<AuctionBid> getAuctionBids() {
        return auctionBids;
    }
}
