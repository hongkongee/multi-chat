package com.example.auth_module.api;

import com.example.auth_module.service.auth.AuthService;
import com.example.auth_module.service.auth.dto.LoginRequest;
import com.example.auth_module.service.auth.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/multichat/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String result = authService.login(request.getUserId(), request.getPassword());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.register(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다!");
    }

}
