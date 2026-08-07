package br.com.ngbilling.DesafioTecnico.domain.service.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransacaoStrategyTest {

    @Test
    void deveAplicarTaxaDeCincoPorCentoNoCredito() {
        BigDecimal resultado = new CreditoStrategy().calcularValorFinal(new BigDecimal("100.00"));

        assertEquals(0, new BigDecimal("105.0000").compareTo(resultado));
    }

    @Test
    void deveAplicarTaxaDeTresPorCentoNoDebito() {
        BigDecimal resultado = new DebitoStrategy().calcularValorFinal(new BigDecimal("100.00"));

        assertEquals(0, new BigDecimal("103.0000").compareTo(resultado));
    }

    @Test
    void naoDeveAplicarTaxaNoPix() {
        BigDecimal resultado = new PixStrategy().calcularValorFinal(new BigDecimal("100.00"));

        assertEquals(0, new BigDecimal("100.00").compareTo(resultado));
    }
}
