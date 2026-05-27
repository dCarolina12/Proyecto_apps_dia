package com.docufacil.docufacil.controller;

import com.docufacil.docufacil.model.User;
import com.docufacil.docufacil.repository.UserRepository;
import com.docufacil.docufacil.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new ResponseEntity<>(Map.of("error", "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED);
            }

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getOrganization().getId());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", user.getEmail(),
                    "role", user.getRole(),
                    "organizationId", user.getOrganization().getId()
            ));

        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}