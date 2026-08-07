package io.github.dougbt.banking.api.dto;

import java.math.BigDecimal;

public record ContaResponse(Integer numeroConta, BigDecimal saldo) {
}
