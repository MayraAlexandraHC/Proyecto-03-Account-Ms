package com.bank.AccountMs.repository;

import com.bank.AccountMs.model.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, String> {
    List<Cuenta> findByClienteId(String clienteId);
    boolean existsByClienteId(String clienteId);
    boolean existsByNumeroCuenta(String numeroCuenta);
}