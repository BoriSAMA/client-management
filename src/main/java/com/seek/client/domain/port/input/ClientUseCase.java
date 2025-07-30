package com.seek.client.domain.port.input;

import com.seek.client.adapter.in.rest.dto.response.ClientMetricsResponse;
import com.seek.client.domain.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Input port that defines use cases related to client operations.
 * This interface exposes the application's business capabilities
 * that can be invoked by external adapters.
 */
public interface ClientUseCase {

    /**
     * Registers a new client in the system.
     * @param client the client entity to be registered
     */
    void registerClient(Client client);

    /**
     * Retrieves a paginated list of all clients.
     * @param pageable the pagination and sorting information
     * @return a page containing clients
     */
    Page<Client> getAllClients(Pageable pageable);

    /**
     * Calculates and retrieves statistical metrics for the registered clients.
     * @return a response containing metrics such as average age and standard deviation
     */
    ClientMetricsResponse getClientMetrics();
}