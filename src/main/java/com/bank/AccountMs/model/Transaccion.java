package com.bank.AccountMs.model;


import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {
    @Min(value = 0, message = "El monto debe ser mayor a 0")
    private Double monto;
}