package com.seek.client.adapter.out.persistence;

import com.seek.client.adapter.mapper.ClientMapper;
import com.seek.client.adapter.out.persistence.repository.ClientJpaRepository;
import com.seek.client.domain.model.Client;
import com.seek.client.domain.port.output.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Adapter that implements the {@link ClientRepository} output port using JPA.
 * Delegates persistence operations to {@link ClientJpaRepository} and handles
 * conversion between domain and entity models through {@link ClientMapper}.
 */
@Repository
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientMapper clientMapper;

    /**
     * Saves the given domain client by mapping it to a JPA entity and persisting it.
     * @param client the domain client to be saved
     */
    @Override
    public void save(Client client) {
        clientJpaRepository.save(clientMapper.toEntity(client));
    }

    /**
     * Retrieves all clients from the database and maps them to domain models.
     * @return a list of domain clients
     */
    @Override
    public List<Client> findAll() {
        return clientJpaRepository.findAll()
                .stream()
                .map(clientMapper::toDomain)
                .toList();
    }

    /**
     * Retrieves a paginated list of clients from the database and maps them to domain models.
     * @param pageable the pagination and sorting information
     * @return a page of domain clients
     */
    @Override
    public Page<Client> getClientsByPage(Pageable pageable) {
        return clientJpaRepository.findAll(pageable)
                .map(clientMapper::toDomain);
    }
}
