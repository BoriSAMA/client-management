package com.seek.client.adapter.in.rest.controller;

import com.seek.client.adapter.in.rest.dto.request.ClientRequest;
import com.seek.client.adapter.in.rest.dto.response.ClientMetricsResponse;
import com.seek.client.adapter.in.rest.dto.response.ClientResponse;
import com.seek.client.adapter.mapper.ClientRestMapper;
import com.seek.client.domain.model.Client;
import com.seek.client.domain.port.input.ClientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "Client Management", description = "Operations for managing clients")
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientUseCase clientUseCase;
    private final ClientRestMapper mapper;

    @Operation(
            summary = "Create a new client",
            description = "Registers a new client in the system with their basic information"
    )
    @ApiResponse(responseCode = "201", description = "Client created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    public ResponseEntity<Void> createClient(@Valid @RequestBody ClientRequest request) {
        Client client = mapper.toDomain(request);
        clientUseCase.registerClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "List all clients",
            description = "Retrieves all clients with full details including age and estimated death date"
    )
    @ApiResponse(responseCode = "200", description = "List of clients retrieved successfully")
    @GetMapping
    public ResponseEntity<Page<ClientResponse>> getAllClients(@ParameterObject Pageable pageable) {
        Page<Client> clients = clientUseCase.getAllClients(pageable);
        Page<ClientResponse> response = clients.map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get client metrics",
            description = "Returns statistics like average age and standard deviation among all clients"
    )
    @ApiResponse(responseCode = "200", description = "Client metrics retrieved successfully")
    @GetMapping("/metrics")
    public ResponseEntity<ClientMetricsResponse> getClientMetrics() {
        return ResponseEntity.ok(clientUseCase.getClientMetrics());
    }
}
