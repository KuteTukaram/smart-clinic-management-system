package com.smartclinic.clinic.repository;

import com.smartclinic.clinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity
 * Provides database access methods for User operations
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email
     * @param email the user's email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists by email
     * @param email the user's email
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
}

