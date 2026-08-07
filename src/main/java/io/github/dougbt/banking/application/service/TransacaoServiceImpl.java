package io.github.dougbt.banking.application.service;

import io.github.dougbt.banking.api.dto.TransacaoRequest;
import io.github.dougbt.banking.api.dto.TransacaoResponse;
import io.github.dougbt.banking.domain.model.Conta;
import io.github.dougbt.banking.domain.model.FormaDePagamento;
import io.github.dougbt.banking.domain.service.TransacaoService;
import io.github.dougbt.banking.domain.service.TransacaoStrategy;
import io.github.dougbt.banking.infrastructure.repository.ContaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final ContaRepository contaRepository;
    private final Map<FormaDePagamento, TransacaoStrategy> strategies;

    public TransacaoServiceImpl(
            ContaRepository contaRepository,
            Map<FormaDePagamento, TransacaoStrategy> strategies) {
        this.contaRepository = contaRepository;
        this.strategies = strategies;
    }

    @Override
    public TransacaoResponse processarTransacao(TransacaoRequest request) {
        Conta conta = contaRepository.findByNumeroConta(request.numeroConta())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Conta não encontrada"));

        TransacaoStrategy strategy = strategies.get(request.formaPagamento());
        if (strategy == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Forma de pagamento inválida. Use PIX, CREDITO ou DEBITO");
        }

        BigDecimal valorFinal = strategy.calcularValorFinal(request.valor());

        if (conta.getSaldo().compareTo(valorFinal) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Saldo insuficiente para realizar a transação");
        }

        BigDecimal novoSaldo = conta.getSaldo().subtract(valorFinal);
        conta.setSaldo(novoSaldo);
        contaRepository.save(conta);

        return new TransacaoResponse(
                conta.getNumeroConta(),
                conta.getSaldo());
    }
}
