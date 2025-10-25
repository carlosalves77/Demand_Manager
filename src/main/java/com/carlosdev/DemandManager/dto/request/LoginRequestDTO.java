package com.carlosdev.DemandManager.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDTO(
        @NotEmpty(message = "E-mail obrigatório")
        String email,
        @NotEmpty(message = "Senha obrigatória")
        String password
) {
}
