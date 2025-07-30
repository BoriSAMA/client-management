package com.seek.client.adapter.out.persistence.repository;

import com.seek.client.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing {@link UserEntity} data from the database.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query methods
 * for the {@code UserEntity}.
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Retrieves a {@link UserEntity} by its username.
     *
     * @param username the username of the user to be retrieved
     * @return an {@link Optional} containing the {@link UserEntity} if found, or empty if not found
     */
    Optional<UserEntity> findByUsername(String username);
}
