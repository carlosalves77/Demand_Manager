package com.carlosdev.DemandManager.mapper;

import com.carlosdev.DemandManager.dto.request.RegisterRequestDTO;
import com.carlosdev.DemandManager.dto.response.RegisterResponseDTO;
import com.carlosdev.DemandManager.model.UserAuth;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UserRegisterAuthMapper {

    public static RegisterResponseDTO toResponse(UserAuth userAuth) {
        if (userAuth == null) {
            return  null;
        }

        return new RegisterResponseDTO(
                userAuth.getUserId().toString(),
                userAuth.getEmail(),
                userAuth.getUsername(),
                userAuth.getCreated_at(),
                userAuth.getRole()
        );
    }

    public static UserAuth userAuth(RegisterRequestDTO registerRequestDTO) {
        if (registerRequestDTO == null) {
            return  null;
        }

        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(registerRequestDTO.email());
        userAuth.setPassword_hash(registerRequestDTO.password_hash());
        userAuth.setUsername(registerRequestDTO.username());
        userAuth.setCreated_at(Instant.now());
        userAuth.setRole(registerRequestDTO.role());

        return  userAuth;
    }
}
