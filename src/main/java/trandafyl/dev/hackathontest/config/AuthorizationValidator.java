package trandafyl.dev.hackathontest.config;

import com.amazonaws.services.pi.model.NotAuthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import trandafyl.dev.hackathontest.models.User;
import trandafyl.dev.hackathontest.services.AuthService;

@Configuration
@AllArgsConstructor
public class AuthorizationValidator {

    private final AuthService authService;

    public void checkEditAuthority(User user){
        if(!authService.isActionAuthorized(user)){
            throw new NotAuthorizedException("You are not authorized to perform this action");
        }
    }
}
