package com.loqah.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final UserRepository users;
    public ProfileController(UserRepository users) { this.users=users; }
    @GetMapping("/me")
    public Map<String,Object> me(Authentication auth) {
        User u=users.findByEmailIgnoreCase(auth.getName()).orElseThrow();
        return Map.of("id",u.getId(),"email",u.getEmail(),"displayName",u.getDisplayName(),
                "role",u.getRole(),"joinedAt",u.getCreatedAt());
    }
}