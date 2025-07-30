package com.seek.client.adapter.in.rest.controller;

import com.seek.client.adapter.in.rest.dto.request.ClientRequest;
import com.seek.client.adapter.in.rest.dto.response.ClientMetricsResponse;
import com.seek.client.adapter.in.rest.dto.response.ClientResponse;
import com.seek.client.adapter.mapper.ClientRestMapper;
import com.seek.client.domain.model.Client;
import com.seek.client.domain.port.input.ClientUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    private ClientUseCase clientUseCase;
    private ClientRestMapper clientRestMapper;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        clientUseCase = mock(ClientUseCase.class);
        clientRestMapper = mock(ClientRestMapper.class);
        clientController = new ClientController(clientUseCase, clientRestMapper);
    }

    @Test
    void shouldCreateClientSuccessfully() {
        // Arrange
        ClientRequest request = ClientRequest.builder().firstName("John").lastName("Doe").birthDate(LocalDate.of(1995, 5, 20)).build();
        Client client = new Client();
        when(clientRestMapper.toDomain(request)).thenReturn(client);

        // Act
        ResponseEntity<Void> response = clientController.createClient(request);

        // Assert
        assertEquals(201, response.getStatusCode().value());
        verify(clientUseCase, times(1)).registerClient(client);
    }

    @Test
    void shouldReturnAllClients() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Client client = new Client();
        ClientResponse response = ClientResponse.builder().firstName("John").lastName("Doe").age(30).birthDate(LocalDate.of(1995, 5, 20)).estimatedDeathDate(LocalDate.of(2075, 5, 20)).build();
        Page<Client> clientPage = new PageImpl<>(List.of(client));
        Page<ClientResponse> responsePage = new PageImpl<>(List.of(response));

        when(clientUseCase.getAllClients(pageable)).thenReturn(clientPage);
        when(clientRestMapper.toResponse(client)).thenReturn(response);

        // Act
        ResponseEntity<Page<ClientResponse>> result = clientController.getAllClients(pageable);

        // Assert
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, result.getBody().getTotalElements());
        assertEquals("John", result.getBody().getContent().getFirst().getFirstName());
    }

    @Test
    void shouldReturnClientMetrics() {
        // Arrange
        ClientMetricsResponse metrics = new ClientMetricsResponse(35.5, 4.2);
        when(clientUseCase.getClientMetrics()).thenReturn(metrics);

        // Act
        ResponseEntity<ClientMetricsResponse> response = clientController.getClientMetrics();

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(35.5, response.getBody().getAverageAge());
        assertEquals(4.2, response.getBody().getAgeStandardDeviation());
    }
}
