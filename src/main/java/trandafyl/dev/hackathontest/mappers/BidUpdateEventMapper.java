package trandafyl.dev.hackathontest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import trandafyl.dev.hackathontest.dto.BidUpdateWebsocketResponse;
import trandafyl.dev.hackathontest.events.models.BidUpdateEvent;

@Mapper(componentModel = "spring")
public interface BidUpdateEventMapper {
    BidUpdateEventMapper INSTANCE = Mappers.getMapper(BidUpdateEventMapper.class);

    BidUpdateWebsocketResponse toDto(BidUpdateEvent event);
}
