package com.carlosdev.DemandManager.service;

import com.carlosdev.DemandManager.config.TokenConfig;
import com.carlosdev.DemandManager.dto.request.LoginRequestDTO;
import com.carlosdev.DemandManager.dto.request.RegisterRequestDTO;
import com.carlosdev.DemandManager.dto.response.LoginResponseDTO;
import com.carlosdev.DemandManager.dto.response.RegisterResponseDTO;
import com.carlosdev.DemandManager.exception.UserEmailExistsException;
import com.carlosdev.DemandManager.mapper.UserRegisterAuthMapper;
import com.carlosdev.DemandManager.model.UserAuth;
import com.carlosdev.DemandManager.model.VerificationToken;
import com.carlosdev.DemandManager.repository.UserAuthRepository;
import com.carlosdev.DemandManager.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserAuthRepository userAuthRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserAuthRepository userAuthRepository, VerificationTokenRepository verificationTokenRepository, TokenConfig tokenConfig,
                       AuthenticationManager authenticationManager, PasswordEncoder
                               passwordEncoder) {
        this.userAuthRepository = userAuthRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.tokenConfig = tokenConfig;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {

        if (userAuthRepository.existsByEmail(registerRequestDTO.email())) {
            throw new UserEmailExistsException("E-mail já existente: " + registerRequestDTO.email());
        }

        UserAuth userAuth = UserRegisterAuthMapper.userAuth(registerRequestDTO);

        String encodedPassword = passwordEncoder.encode(registerRequestDTO.password_hash());
        userAuth.setPassword_hash(encodedPassword);
        userAuth.setIs_verified(false);

        userAuthRepository.save(userAuth);

        VerificationToken verificationToken = new VerificationToken(userAuth);
        verificationTokenRepository.save(verificationToken);

        String verificationLink =
                "http://localhost:4000/auth/verify-account?token=" + verificationToken.getToken();
        log.info("Simulação envio de e-mail para:{}", userAuth.getEmail());
        log.info("Link de verificação:{}", verificationLink);

        return UserRegisterAuthMapper.toResponse(userAuth);

    }

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {

        UsernamePasswordAuthenticationToken userAndPAss =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());


        Authentication authentication = authenticationManager.authenticate(userAndPAss);

        UserAuth userAuth = (UserAuth) authentication.getPrincipal();

        userAuth.setLast_login_at(Instant.now());
        userAuthRepository.save(userAuth);
        String token = tokenConfig.generateToken(userAuth);
        return new LoginResponseDTO(userAuth.getUsername(), token);
    }

    @Transactional
    public void verifyAccount(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token de verificação invalida"));

        if(verificationToken.isExpired()) {

            verificationTokenRepository.delete(verificationToken);
            throw new RuntimeException("Token de verificação expirada.");
        }

        UserAuth user = verificationToken.getUser();

        user.setIs_verified(true);
        userAuthRepository.save(user);

        verificationTokenRepository.delete(verificationToken);
    }
}
