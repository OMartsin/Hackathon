package trandafyl.dev.hackathontest.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import trandafyl.dev.hackathontest.models.UserRole;

import java.util.List;

@Getter
@Builder
public class UserListResponse {
    private List<PartialUserResponse> response;
    private long recordsCount;

    @Builder
    @Data
    public static class PartialUserResponse {
        private Long id;
        private String email;
        private String username;
        private UserRole role;
    }
}
