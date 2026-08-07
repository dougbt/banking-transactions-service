package io.github.dougbt.banking.domain.service;

import java.math.BigDecimal;

public interface TransacaoStrategy {

    BigDecimal calcularValorFinal(BigDecimal valorOriginal);
}
