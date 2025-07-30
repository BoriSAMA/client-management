package com.seek.client.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a client in the system, extending the base {@link Person} class
 * with an additional client-specific code identifier.
 * This class is part of the domain model and is used to transfer
 * client information across different layers of the application.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Client extends Person {

    private String clientCode;
}
