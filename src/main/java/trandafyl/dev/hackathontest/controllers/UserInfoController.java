package trandafyl.dev.hackathontest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-info")
@RequiredArgsConstructor
@Tag(name = "User Info")
public class UserInfoController {

    @PostMapping("/info")
    @PreAuthorize("#email.equals(principal.getAttribute('email'))")
    public String userInfo(String email) {
        return "User info";
    }
}
