package com.seek.client.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA entity representing an application user.
 * This entity is mapped to the TABLE_USERS table
 * and stores authentication and authorization data.
 */
@Entity
@Table(name = "TABLE_USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Column(name = "ENABLED")
    private boolean isEnabled;
}
