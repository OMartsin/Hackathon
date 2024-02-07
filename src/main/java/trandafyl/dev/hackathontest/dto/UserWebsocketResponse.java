package trandafyl.dev.hackathontest.dto;

import java.io.Serializable;

/**
 * DTO for {@link trandafyl.dev.hackathontest.models.User}
 */
public record UserWebsocketResponse(Long id, String email, String username) implements Serializable {
}