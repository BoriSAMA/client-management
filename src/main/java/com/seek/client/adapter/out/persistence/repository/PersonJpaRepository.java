package com.seek.client.adapter.out.persistence.repository;

import com.seek.client.adapter.out.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing {@link PersonEntity} data from the database.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query methods
 * for the {@code PersonEntity}.
 */
public interface PersonJpaRepository extends JpaRepository<PersonEntity, Long> {
}
