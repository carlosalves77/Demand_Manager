package com.carlosdev.DemandManager.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "verification_token")
public class VerificationToken {

    private static final int EXPIRATION_HOURS = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAuth user;

    @Column(nullable = false)
    private Instant expiryDate;

    public UserAuth getUser() {
        return user;
    }

    public VerificationToken() {}

    public VerificationToken(UserAuth user) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.expiryDate = Instant.now().plus(EXPIRATION_HOURS, ChronoUnit.HOURS);
    }

    public UUID getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryDate);
    }
}
