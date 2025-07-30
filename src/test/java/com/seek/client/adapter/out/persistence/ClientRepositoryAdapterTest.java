package com.seek.client.adapter.out.persistence;

import com.seek.client.adapter.mapper.ClientMapper;
import com.seek.client.adapter.out.persistence.entity.ClientEntity;
import com.seek.client.adapter.out.persistence.repository.ClientJpaRepository;
import com.seek.client.domain.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientRepositoryAdapterTest {

    private ClientJpaRepository clientJpaRepository;
    private ClientMapper clientMapper;
    private ClientRepositoryAdapter clientRepositoryAdapter;

    @BeforeEach
    void setUp() {
        clientJpaRepository = mock(ClientJpaRepository.class);
        clientMapper = mock(ClientMapper.class);
        clientRepositoryAdapter = new ClientRepositoryAdapter(clientJpaRepository, clientMapper);
    }

    @Test
    void shouldSaveClient() {
        // Arrange
        Client client = Client.builder()
                .clientCode("CUST001")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        ClientEntity entity = ClientEntity.builder()
                .clientCode("CUST001")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        when(clientMapper.toEntity(client)).thenReturn(entity);

        // Act
        clientRepositoryAdapter.save(client);

        // Assert
        verify(clientMapper).toEntity(client);
        verify(clientJpaRepository).save(entity);
    }

    @Test
    void shouldFindAllClients() {
        // Arrange
        List<ClientEntity> entities = List.of(
                ClientEntity.builder().clientCode("C1").firstName("A").birthDate(LocalDate.of(1990, 1, 1)).build()
        );
        List<Client> models = List.of(
                Client.builder().clientCode("C1").firstName("A").birthDate(LocalDate.of(1990, 1, 1)).build()
        );

        when(clientJpaRepository.findAll()).thenReturn(entities);
        when(clientMapper.toDomain(entities.get(0))).thenReturn(models.get(0));

        // Act
        List<Client> result = clientRepositoryAdapter.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("C1", result.get(0).getClientCode());
        verify(clientJpaRepository).findAll();
        verify(clientMapper).toDomain(entities.get(0));
    }

    @Test
    void shouldReturnPaginatedClients() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 2);
        ClientEntity entity = ClientEntity.builder().clientCode("C1").firstName("A").birthDate(LocalDate.of(1990, 1, 1)).build();
        Client model = Client.builder().clientCode("C1").firstName("A").birthDate(LocalDate.of(1990, 1, 1)).build();

        Page<ClientEntity> pageEntities = new PageImpl<>(List.of(entity));
        when(clientJpaRepository.findAll(pageable)).thenReturn(pageEntities);
        when(clientMapper.toDomain(entity)).thenReturn(model);

        // Act
        Page<Client> result = clientRepositoryAdapter.getClientsByPage(pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("C1", result.getContent().get(0).getClientCode());
        verify(clientJpaRepository).findAll(pageable);
        verify(clientMapper).toDomain(entity);
    }
}
