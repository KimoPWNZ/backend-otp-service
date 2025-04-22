package com.promoit.otp.controller;

import com.promoit.otp.model.User;
import com.promoit.otp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        log.info("Registration attempt for user: {}", user.getLogin());
        boolean ok = userService.register(user);
        if (ok) {
            log.info("User registered: {}", user.getLogin());
            return ResponseEntity.ok(new StatusResponse("Registered"));
        } else {
            log.warn("Registration failed for user: {}", user.getLogin());
            return ResponseEntity.badRequest().body(new StatusResponse("Registration failed"));
        }
    }

    static class StatusResponse {
        private String status;
        public StatusResponse(String status) { this.status = status; }
        public String getStatus() { return status; }
    }
}