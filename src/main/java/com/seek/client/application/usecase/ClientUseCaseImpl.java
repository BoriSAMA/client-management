package com.seek.client.application.usecase;

import com.seek.client.adapter.in.rest.dto.response.ClientMetricsResponse;
import com.seek.client.domain.model.Client;
import com.seek.client.domain.port.input.ClientUseCase;
import com.seek.client.domain.port.output.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ClientUseCaseImpl implements ClientUseCase {

    private static final String CUSTOMER_PREFIX_TEXT = "CUST-";
    private final ClientRepository clientRepository;

    @Override
    public void registerClient(Client client) {
        String clientCode = generateClientCode();
        client.setClientCode(clientCode);
        clientRepository.save(client);
    }

    private String generateClientCode() {
        int random = new Random().nextInt(900000) + 100000;
        return CUSTOMER_PREFIX_TEXT + random;
    }

    @Override
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.getClientsByPage(pageable);
    }

    @Override
    public ClientMetricsResponse getClientMetrics() {
        List<Client> clients = clientRepository.findAll();

        double average = clients.stream()
                .mapToInt(Client::getAge)
                .average()
                .orElse(0.0);

        double standardDev = Math.sqrt(
                clients.stream()
                        .mapToDouble(c -> Math.pow(c.getAge() - average, 2))
                        .average()
                        .orElse(0.0)
        );

        return ClientMetricsResponse.builder().averageAge(average).ageStandardDeviation(standardDev).build();
    }
}
