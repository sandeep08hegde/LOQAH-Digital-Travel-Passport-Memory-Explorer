package com.loqah;

import com.loqah.security.JwtService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoqahApplicationTests {
    @Test
    void jwtTokenContainsTheIssuedEmail() {
        String secret = "local-test-secret-must-be-at-least-32-bytes-long";
        JwtService jwt = new JwtService(secret, 3600);
        String token = jwt.generate("explorer@example.com");
        assertEquals("explorer@example.com", jwt.subject(token));
    }
}