package io.github.dougbt.banking.application.service;

import io.github.dougbt.banking.api.dto.ContaRequest;
import io.github.dougbt.banking.api.dto.ContaResponse;
import io.github.dougbt.banking.domain.model.Conta;
import io.github.dougbt.banking.domain.service.ContaService;
import io.github.dougbt.banking.infrastructure.repository.ContaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    public ContaServiceImpl(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public ContaResponse criarConta(ContaRequest request) {
        if (request.saldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo não pode ser negativo");
        }

        if (contaRepository.existsByNumeroConta(request.numeroConta())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conta já existente");
        }

        Conta novaConta = new Conta(request.numeroConta(), request.saldo());
        Conta contaSalva = contaRepository.save(novaConta);

        return new ContaResponse(contaSalva.getNumeroConta(), contaSalva.getSaldo());
    }

    @Override
    public ContaResponse buscarConta(Integer numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
                .map(conta -> new ContaResponse(conta.getNumeroConta(), conta.getSaldo()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
    }

    @Override
    public boolean contaExiste(Integer numeroConta) {
        return contaRepository.existsByNumeroConta(numeroConta);
    }
}
