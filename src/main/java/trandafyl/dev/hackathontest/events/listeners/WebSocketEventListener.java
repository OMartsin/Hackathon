package trandafyl.dev.hackathontest.events.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import trandafyl.dev.hackathontest.events.models.BidUpdateEvent;
import trandafyl.dev.hackathontest.events.models.UpdateEvent;
import trandafyl.dev.hackathontest.mappers.BidUpdateEventMapper;


@Component
@RequiredArgsConstructor
public class WebSocketEventListener implements UpdateEventListener{
    private final SimpMessagingTemplate messagingTemplate;
    private final BidUpdateEventMapper bidUpdateEventMapper;

    @Override
    @EventListener
    public void update(UpdateEvent event) {
        if (event instanceof BidUpdateEvent) {
            handleBidUpdateEvent((BidUpdateEvent) event);
        }  else {
            throw new IllegalArgumentException("Unsupported UpdateEvent type");
        }
    }

    private void handleBidUpdateEvent(BidUpdateEvent event) {
        String destination = "/topic/bidUpdate";
        var dto = bidUpdateEventMapper.toDto(event);
        messagingTemplate.convertAndSend(destination, dto);
    }

}
