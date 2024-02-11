package trandafyl.dev.hackathontest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuctionLotChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User sender;
    @Column(nullable = false, length = 256)
    @NotBlank(message = "The value of 'content' must not be blank")
    private String content;
    @NotNull
    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private AuctionLot auctionLot;

}
