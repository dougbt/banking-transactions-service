package io.github.dougbt.banking.domain.service.strategy;

import io.github.dougbt.banking.domain.service.TransacaoStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PixStrategy implements TransacaoStrategy {

    @Override
    public BigDecimal calcularValorFinal(BigDecimal valorOriginal) {
        return valorOriginal;
    }
}
