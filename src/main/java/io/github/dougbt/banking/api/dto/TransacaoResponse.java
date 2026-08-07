package io.github.dougbt.banking.api.dto;

import java.math.BigDecimal;

public record TransacaoResponse(Integer numeroConta, BigDecimal saldo) {
}
