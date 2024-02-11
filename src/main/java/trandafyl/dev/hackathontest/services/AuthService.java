package trandafyl.dev.hackathontest.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import trandafyl.dev.hackathontest.dto.UserResponse;
import trandafyl.dev.hackathontest.models.User;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;

    public boolean isUserAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public boolean isActionAuthorized(User desiredUser) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return isCurrentUserAdmin() || (
                auth.isAuthenticated() &&
                        auth.getName().equals(desiredUser.getEmail())
        );
    }

    private boolean isCurrentUserAdmin(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.isAuthenticated() &&
                auth.getAuthorities()
                        .stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    public Optional<UserResponse> getCurrentUser(){
        String email = getCurrentUsersEmail();
        return userService.getUser(email);
    }


    public User getCurrentUserEntity(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserEntity(email);
    }

    public String getCurrentUsersEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
