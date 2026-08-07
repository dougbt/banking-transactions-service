package io.github.dougbt.banking.domain.model;

import java.math.BigDecimal;

public enum FormaDePagamento {
    CREDITO("CREDITO", new BigDecimal("0.05")),
    DEBITO("DEBITO", new BigDecimal("0.03")),
    PIX("PIX", BigDecimal.ZERO);

    private final String codigo;
    private final BigDecimal taxa;

    FormaDePagamento(String codigo, BigDecimal taxa) {
        this.codigo = codigo;
        this.taxa = taxa;
    }

    public String getCodigo() {
        return codigo;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public static FormaDePagamento fromCodigo(String codigo) {
        for (FormaDePagamento forma : values()) {
            if (forma.codigo.equalsIgnoreCase(codigo)) {
                return forma;
            }
        }
        throw new IllegalArgumentException("Código de forma de pagamento inválido: " + codigo);
    }
}
