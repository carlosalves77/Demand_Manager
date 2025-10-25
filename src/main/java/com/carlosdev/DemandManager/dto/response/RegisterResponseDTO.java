package com.carlosdev.DemandManager.dto.response;

import com.carlosdev.DemandManager.util.RoleType;

import java.time.Instant;

public record RegisterResponseDTO(
        String id,
        String email,
        String username,
        Instant created_at,
        RoleType role
) {
}
