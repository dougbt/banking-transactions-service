package io.github.dougbt.banking.domain.service;

import io.github.dougbt.banking.api.dto.ContaRequest;
import io.github.dougbt.banking.api.dto.ContaResponse;

public interface ContaService {

    ContaResponse criarConta(ContaRequest request);

    ContaResponse buscarConta(Integer numeroConta);

    boolean contaExiste(Integer numeroConta);
}
