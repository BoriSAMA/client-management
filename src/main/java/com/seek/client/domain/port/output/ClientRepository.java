package com.seek.client.domain.port.output;

import com.seek.client.domain.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Output port for client persistence operations.
 * Defines the contract that must be implemented by an adapter responsible for
 * interacting with the underlying data source or persistence mechanism.
 */
public interface ClientRepository {

    /**
     * Persists the given client in the data source.
     * @param client the client entity to be saved
     */
    void save(Client client);

    /**
     * Retrieves all clients from the data source.
     * @return a list containing all clients
     */
    List<Client> findAll();

    /**
     * Retrieves a paginated list of clients based on the given pagination parameters.
     * @param pageable the pagination and sorting information
     * @return a page of clients
     */
    Page<Client> getClientsByPage(Pageable pageable);
}
