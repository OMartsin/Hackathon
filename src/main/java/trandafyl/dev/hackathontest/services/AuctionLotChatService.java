package trandafyl.dev.hackathontest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import trandafyl.dev.hackathontest.dto.AuctionLotChatMessageRequest;
import trandafyl.dev.hackathontest.dto.AuctionLotChatMessageResponse;
import trandafyl.dev.hackathontest.mappers.UserMapper;
import trandafyl.dev.hackathontest.models.AuctionLotChatMessage;
import trandafyl.dev.hackathontest.repositories.AuctionLotChatMessageRepository;
import trandafyl.dev.hackathontest.repositories.AuctionLotRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class AuctionLotChatService {
    private final AuctionLotChatMessageRepository auctionLotChatMessageRepository;
    private final AuthService authService;
    private final AuctionLotRepository auctionLotRepository;
    private final UserMapper userMapper;
    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public Optional<AuctionLotChatMessageResponse> addMessage(AuctionLotChatMessageRequest request) {
        AuctionLotChatMessage message = AuctionLotChatMessage.builder()
                .content(request.getMessage())
                .auctionLot(auctionLotRepository.findById(request.getAuctionLotId()).orElseThrow(
                        () -> new IllegalArgumentException
                                ("Auction lot with id " + request.getAuctionLotId() + " not found")))
                .createdAt(LocalDateTime.now())
                .sender(authService.getCurrentUserEntity())
                .build();
        var response = Optional.of(AuctionLotChatMessageResponse.builder()
                .id(auctionLotChatMessageRepository.save(message).getId())
                .message(message.getContent())
                .auctionLotId(message.getAuctionLot().getId())
                .createdAt(message.getCreatedAt())
                .user(userMapper.toUserPartialResponse(message.getSender()))
                .build());
        distributeMessage(response.get().getAuctionLotId(), response.get());
        return response;
    }

    public SseEmitter createEmitterForAuctionLot(Long auctionLotId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        this.emitters.computeIfAbsent(auctionLotId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(auctionLotId, emitter));
        emitter.onTimeout(() -> removeEmitter(auctionLotId, emitter));
        emitter.onError((e) -> removeEmitter(auctionLotId, emitter));

        try {
            emitter.send(SseEmitter.event().name("INIT")
                    .data("Connection established for auction lot " + auctionLotId));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    private void removeEmitter(Long auctionLotId, SseEmitter emitter) {
        List<SseEmitter> emittersList = this.emitters.get(auctionLotId);
        if (emittersList != null) {
            emittersList.remove(emitter);
            if (emittersList.isEmpty()) {
                this.emitters.remove(auctionLotId);
            }
        }
    }

    private void distributeMessage(Long auctionLotId, AuctionLotChatMessageResponse message) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        List<SseEmitter> emittersForLot = this.emitters.getOrDefault(auctionLotId, Collections.emptyList());

        emittersForLot.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("message").data(message));
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });

        emittersForLot.removeAll(deadEmitters);
    }
}
