package com.carlosdev.DemandManager.repository;

import com.carlosdev.DemandManager.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthRepository extends JpaRepository<UserAuth, UUID> {

    Optional<UserDetails> findUserByEmail(String username);

    boolean existsByEmail(String email);
}
