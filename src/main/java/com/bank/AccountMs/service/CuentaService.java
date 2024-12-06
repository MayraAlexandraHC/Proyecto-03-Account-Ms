package com.bank.AccountMs.service;


import com.bank.AccountMs.model.Cuenta;
import java.util.List;

public interface CuentaService {
    /**
     * Crea una nueva cuenta
     * @param cuenta Objeto cuenta con tipoCuenta y clienteId
     * @return La cuenta creada con todos sus datos
     */
    Cuenta crearCuenta(Cuenta cuenta);

    /**
     * Lista todas las cuentas
     * @return Lista de cuentas
     */
    List<Cuenta> listarCuentas();

    /**
     * Obtiene una cuenta por su ID
     * @param id ID de la cuenta
     * @return Cuenta encontrada
     */
    Cuenta obtenerCuentaPorId(String id);

    /**
     * Realiza un depósito en la cuenta
     * @param cuentaId ID de la cuenta
     * @param monto Monto a depositar
     * @return Cuenta actualizada
     */
    Cuenta depositarDinero(String cuentaId, Double monto);

    /**
     * Realiza un retiro de la cuenta
     * @param cuentaId ID de la cuenta
     * @param monto Monto a retirar
     * @return Cuenta actualizada
     */
    Cuenta retirarDinero(String cuentaId, Double monto);

    /**
     * Elimina una cuenta
     * @param id ID de la cuenta
     */
    void eliminarCuenta(String id);

    /**
     * Lista todas las cuentas de un cliente específico
     * @param clienteId ID del cliente
     * @return Lista de cuentas del cliente
     */
    List<Cuenta> listarCuentasPorClienteId(String clienteId);
}