package com.carlosdev.DemandManager.dto.request;

import com.carlosdev.DemandManager.util.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record RegisterRequestDTO(

        @Email()
        @NotBlank(message = "Informe um e-mail valido")
        @Column(unique = true)
        String email,

        @NotNull(message = "Informe a senha")
        String password_hash,

        @NotEmpty(message = "Informe o nome do usuário")
        String username,

        @NotNull(message = "Informe o tipo de usuário")
        @Enumerated(EnumType.STRING)
        RoleType role
) {
}
