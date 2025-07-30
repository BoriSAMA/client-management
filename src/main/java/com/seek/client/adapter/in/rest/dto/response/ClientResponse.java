package com.seek.client.adapter.in.rest.dto.response;

import lombok.*;
import java.time.LocalDate;

/**
 * Represents the response containing detailed information about a client.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {

    private Long id;
    private String clientCode;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private int age;
    private LocalDate estimatedDeathDate;
}

