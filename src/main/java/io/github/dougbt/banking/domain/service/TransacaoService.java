package io.github.dougbt.banking.domain.service;

import io.github.dougbt.banking.api.dto.TransacaoRequest;
import io.github.dougbt.banking.api.dto.TransacaoResponse;

public interface TransacaoService {

    TransacaoResponse processarTransacao(TransacaoRequest request);
}
