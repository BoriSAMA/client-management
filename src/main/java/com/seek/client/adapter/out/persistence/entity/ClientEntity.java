package com.seek.client.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * JPA entity that represents a client record in the database.
 * Inherits common person attributes from {@link PersonEntity} and includes
 * a client-specific code used as a unique identifier.
 */
@Entity
@Table(name = "TABLE_CLIENTS")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClientEntity extends PersonEntity {

    @Column(name = "CLIENT_CODE", nullable = false, unique = true)
    private String clientCode;
}
