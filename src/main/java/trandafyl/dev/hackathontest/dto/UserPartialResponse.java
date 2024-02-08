package trandafyl.dev.hackathontest.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trandafyl.dev.hackathontest.models.UserRole;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPartialResponse {
    private Long id;
    private String email;
    private String username;
    private UserRole role;
}
