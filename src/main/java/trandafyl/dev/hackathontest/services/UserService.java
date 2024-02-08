package trandafyl.dev.hackathontest.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import trandafyl.dev.hackathontest.dto.UserPartialResponse;
import trandafyl.dev.hackathontest.dto.UserResponse;
import trandafyl.dev.hackathontest.mappers.UserMapper;
import trandafyl.dev.hackathontest.models.User;
import trandafyl.dev.hackathontest.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<UserResponse> getUser(long userId){
        return userRepository.findById(userId).map(userMapper::toUserResponse);
    }

    public Optional<UserResponse> getUser(String email){
        return userRepository.findByEmail(email).map(userMapper::toUserResponse);
    }

    public UserResponse addUser(User newUser){
        var user = userRepository.save(newUser);

        return userMapper.toUserResponse(user);
    }

    public List<UserPartialResponse> getBidders(long lotId) {
        return userRepository
                .findAll()
                .stream()
                .filter(user -> user.getAuctionBids().stream().anyMatch(bid -> bid.getAuctionLot().getId() == lotId))
                .map(userMapper::toUserPartialResponse)
                .toList();
    }
}
