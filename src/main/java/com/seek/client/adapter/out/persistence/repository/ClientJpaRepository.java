package com.seek.client.adapter.out.persistence.repository;

import com.seek.client.adapter.out.persistence.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing {@link ClientEntity} data from the database.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query methods
 * for the {@code ClientEntity}.
 */
public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {

    Page<ClientEntity> findAll(Pageable pageable);
}
