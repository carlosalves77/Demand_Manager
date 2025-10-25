package com.carlosdev.DemandManager;

import com.carlosdev.DemandManager.dto.request.RegisterRequestDTO;
import com.carlosdev.DemandManager.dto.response.RegisterResponseDTO;
import com.carlosdev.DemandManager.mapper.UserRegisterAuthMapper;
import com.carlosdev.DemandManager.model.UserAuth;
import com.carlosdev.DemandManager.repository.UserAuthRepository;
import com.carlosdev.DemandManager.repository.VerificationTokenRepository;
import com.carlosdev.DemandManager.service.AuthService;
import com.carlosdev.DemandManager.util.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserAuthRepository userAuthRepository;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private RegisterRequestDTO requestDTO;
    private UserAuth userAuth;
    private RegisterResponseDTO responseDTO;

    @BeforeEach
    void setUp() {

        requestDTO = new RegisterRequestDTO(
               "test@example.com",
               "password123",
               "carlos",
               RoleType.GUEST
        );

        UUID id = UUID.randomUUID();

        userAuth = new UserAuth();
        userAuth.setUserId(id);
        userAuth.setUsername("carlos");
        userAuth.setEmail("test@exmaple.com");

        responseDTO = new RegisterResponseDTO(
                id.toString(),
                "test@example.com",
                "carlos",
                Instant.now(),
                RoleType.GUEST
        );
    }

//    @Test
//    void registerUser_ShouldReturnSuccessRegister() {
//
//        try (MockedStatic<UserRegisterAuthMapper> mapper = Mockito.mockStatic(UserRegisterAuthMapper.class)) {
//            mapper.when(() -> UserRegisterAuthMapper.userAuth(requestDTO))
//                    .thenReturn(userAuth);
//
//            mapper.when(() -> UserRegisterAuthMapper.toResponse(userAuth)).thenReturn(responseDTO);
//
//            when(userAuthRepository.existsByEmail(requestDTO.email()))
//                    .thenReturn(false);
//
//            when(passwordEncoder.encode(requestDTO.password_hash()))
//                    .thenReturn("encodedPassword");
//
//            when(userAuthRepository.save(any(UserAuth.class))).thenReturn(userAuth);
//
//            when(userAuthRepository.save(any(UserAuth.class))).thenReturn(userAuth);
//
//
//        }
//    }
}
