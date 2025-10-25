package com.carlosdev.DemandManager.config;


import java.util.UUID;


public record JwTUserData(
        UUID userId, String email
) {

}
