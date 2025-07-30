package com.seek.client.adapter.in.rest.dto.response;

import lombok.*;

/**
 * Represents the response containing statistical metrics about clients.
 * Includes the average age and the standard deviation of age.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientMetricsResponse {

    private double averageAge;
    private double ageStandardDeviation;
}

