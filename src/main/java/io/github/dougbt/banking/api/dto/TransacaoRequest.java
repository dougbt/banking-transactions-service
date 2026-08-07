package io.github.dougbt.banking.api.dto;

import io.github.dougbt.banking.domain.model.FormaDePagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransacaoRequest(
        @NotNull FormaDePagamento formaPagamento,
        @NotNull Integer numeroConta,
        @NotNull @Positive BigDecimal valor
) {
}
