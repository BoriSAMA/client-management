package com.seek.client.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.seek.client.domain.model.Client;
import com.seek.client.domain.port.output.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;

class ClientUseCaseImplTest {

    private ClientUseCaseImpl clientUseCase;
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientUseCase = new ClientUseCaseImpl(clientRepository);
    }

    @Test
    void shouldRegisterClient() {
        // Arrange
        Client client = Client.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        // Act
        clientUseCase.registerClient(client);

        // Assert
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void shouldReturnPaginatedClients() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 2, Sort.by("firstName"));
        List<Client> clients = List.of(
                Client.builder().firstName("Ana").lastName("Torres").birthDate(LocalDate.of(1985, 5, 5)).build(),
                Client.builder().firstName("Luis").lastName("Perez").birthDate(LocalDate.of(1992, 3, 3)).build()
        );
        Page<Client> expectedPage = new PageImpl<>(clients, pageable, 2);

        when(clientRepository.getClientsByPage(pageable)).thenReturn(expectedPage);

        // Act
        Page<Client> result = clientUseCase.getAllClients(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Ana", result.getContent().getFirst().getFirstName());
        verify(clientRepository, times(1)).getClientsByPage(pageable);
    }

    @Test
    void shouldCalculateClientMetrics() {
        // Arrange
        List<Client> clients = List.of(
                Client.builder().birthDate(LocalDate.now().minusYears(30)).build(),
                Client.builder().birthDate(LocalDate.now().minusYears(40)).build()
        );

        when(clientRepository.findAll()).thenReturn(clients);

        // Act
        var metrics = clientUseCase.getClientMetrics();

        // Assert
        assertEquals(35.0, metrics.getAverageAge());
        assertEquals(5.0, metrics.getAgeStandardDeviation());
    }
}
