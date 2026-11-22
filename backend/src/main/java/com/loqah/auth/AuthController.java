package com.loqah.auth;

import com.loqah.security.JwtService;
import com.loqah.user.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users=users; this.encoder=encoder; this.jwt=jwt;
    }
    public record RegisterRequest(@NotBlank @Email String email,
                                  @NotBlank @Size(min=8,max=72) String password,
                                  @NotBlank @Size(min=2,max=80) String displayName) {}
    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (users.existsByEmailIgnoreCase(req.email()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","Email is already registered"));
        User user = users.save(new User(req.email(), encoder.encode(req.password()), req.displayName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "token",jwt.generate(user.getEmail()), "tokenType","Bearer",
                "user", Map.of("id",user.getId(),"email",user.getEmail(),"displayName",user.getDisplayName(),"role",user.getRole())));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        var user = users.findByEmailIgnoreCase(req.email()).orElse(null);
        if (user == null || !encoder.matches(req.password(), user.getPasswordHash()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid email or password"));
        return ResponseEntity.ok(Map.of("token",jwt.generate(user.getEmail()),"tokenType","Bearer",
                "user",Map.of("id",user.getId(),"email",user.getEmail(),"displayName",user.getDisplayName(),"role",user.getRole())));
    }
}