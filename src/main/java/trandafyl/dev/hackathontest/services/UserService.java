package trandafyl.dev.hackathontest.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import trandafyl.dev.hackathontest.models.User;
import trandafyl.dev.hackathontest.repositories.UserRepository;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> getUser(long userId){
        return userRepository.findById(userId);
    }

    public Optional<User> getUser(String email){
        return userRepository.findByEmail(email);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }
}
