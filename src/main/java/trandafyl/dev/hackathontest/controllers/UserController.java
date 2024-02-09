package trandafyl.dev.hackathontest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trandafyl.dev.hackathontest.dto.UserListResponse;
import trandafyl.dev.hackathontest.dto.UserResponse;
import trandafyl.dev.hackathontest.services.UserService;

@RestController
@AllArgsConstructor
@Tag(name = "Users")
public class UserController {
    private final UserService userService;


    @GetMapping("auction-lots/{lotId}/bidders/")
    public ResponseEntity<UserListResponse> getBidders(@PathVariable long lotId){
        var bidders = userService.getBidders(lotId);

        return ResponseEntity.ok(bidders);
    }

    @GetMapping("users/{id}/")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id){
        var user = userService.getUser(id).orElseThrow();

        return ResponseEntity.ok(user);
    }
}
