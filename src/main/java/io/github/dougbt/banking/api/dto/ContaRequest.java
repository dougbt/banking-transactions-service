package io.github.dougbt.banking.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ContaRequest(
        @NotNull(message = "Número da conta é obrigatório")
        Integer numeroConta,

        @NotNull(message = "Saldo é obrigatório")
        @PositiveOrZero(message = "Saldo não pode ser negativo")
        BigDecimal saldo
) {
}
