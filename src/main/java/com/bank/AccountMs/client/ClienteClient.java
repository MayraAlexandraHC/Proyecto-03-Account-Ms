package com.bank.AccountMs.client;

import com.bank.AccountMs.exception.ClienteNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteClient {
    private final RestTemplate restTemplate;

    public boolean existeCliente(String clienteId) {
        try {
            // Llamada al microservicio de clientes
            restTemplate.getForObject("/clientes/{id}", String.class, clienteId);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            log.error("Error al verificar cliente: {}", e.getMessage());
            throw new ClienteNotFoundException("Error al verificar la existencia del cliente");
        }
    }
}