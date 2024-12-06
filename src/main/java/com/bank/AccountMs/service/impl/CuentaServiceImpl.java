package com.bank.AccountMs.service.impl;

import com.bank.AccountMs.client.ClienteClient;
import com.bank.AccountMs.exception.CuentaException;
import com.bank.AccountMs.model.Cuenta;
import com.bank.AccountMs.model.TipoCuenta;
import com.bank.AccountMs.repository.CuentaRepository;
import com.bank.AccountMs.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {
    private final CuentaRepository cuentaRepository;
    private final ClienteClient clienteClient;

    @Override
    @Transactional
    public Cuenta crearCuenta(Cuenta cuenta) {
        validarDatosCreacion(cuenta);

        if (!clienteClient.existeCliente(cuenta.getClienteId())) {
            throw new CuentaException("El cliente no existe");
        }

        Cuenta nuevaCuenta = Cuenta.builder()
                .tipoCuenta(cuenta.getTipoCuenta())
                .clienteId(cuenta.getClienteId())
                .numeroCuenta(generarNumeroCuenta())
                .saldo(0.0)
                .build();

        return cuentaRepository.save(nuevaCuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta obtenerCuentaPorId(String id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaException("Cuenta no encontrada"));
    }

    @Override
    @Transactional
    public Cuenta depositarDinero(String cuentaId, Double monto) {
        if (monto <= 0) {
            throw new CuentaException("El monto debe ser mayor a 0");
        }

        Cuenta cuenta = obtenerCuentaPorId(cuentaId);
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public Cuenta retirarDinero(String cuentaId, Double monto) {
        if (monto <= 0) {
            throw new CuentaException("El monto debe ser mayor a 0");
        }

        Cuenta cuenta = obtenerCuentaPorId(cuentaId);
        double nuevoSaldo = cuenta.getSaldo() - monto;

        if (cuenta.getTipoCuenta() == TipoCuenta.AHORROS && nuevoSaldo < 0) {
            throw new CuentaException("Saldo insuficiente en cuenta de ahorros");
        }

        if (cuenta.getTipoCuenta() == TipoCuenta.CORRIENTE && nuevoSaldo < -500) {
            throw new CuentaException("Excede el límite de sobregiro permitido (-500)");
        }

        cuenta.setSaldo(nuevoSaldo);
        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public void eliminarCuenta(String id) {
        Cuenta cuenta = obtenerCuentaPorId(id);
        cuentaRepository.delete(cuenta);
    }

    private void validarDatosCreacion(Cuenta cuenta) {
        if (cuenta.getTipoCuenta() == null) {
            throw new CuentaException("El tipo de cuenta es obligatorio");
        }
        if (cuenta.getClienteId() == null || cuenta.getClienteId().trim().isEmpty()) {
            throw new CuentaException("El ID del cliente es obligatorio");
        }
    }

    private String generarNumeroCuenta() {
        return String.format("%010d",
                Long.parseLong(UUID.randomUUID().toString().replace("-", "").substring(0, 10), 16) % 10000000000L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listarCuentasPorClienteId(String clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }
}