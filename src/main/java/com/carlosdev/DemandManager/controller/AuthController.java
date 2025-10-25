package com.carlosdev.DemandManager.controller;

import com.carlosdev.DemandManager.dto.request.LoginRequestDTO;
import com.carlosdev.DemandManager.dto.request.RegisterRequestDTO;
import com.carlosdev.DemandManager.dto.response.LoginResponseDTO;
import com.carlosdev.DemandManager.dto.response.RegisterResponseDTO;
import com.carlosdev.DemandManager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(
            @Valid @RequestBody
            RegisterRequestDTO registerRequestDTO) {

        RegisterResponseDTO responseDTO = authService.registerUser(registerRequestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(
            @Valid
            @RequestBody
            LoginRequestDTO loginRequestDTO) {

        LoginResponseDTO loginResponseDTO = authService.loginUser(loginRequestDTO);

        return ResponseEntity.ok().body(loginResponseDTO);
    }

    @GetMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        try {
            authService.verifyAccount(token);

            return ResponseEntity.ok("Conta verificada com sucesso! Você já pode fazer o login");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
