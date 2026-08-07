package io.github.dougbt.banking.api.controller;

import io.github.dougbt.banking.api.dto.TransacaoRequest;
import io.github.dougbt.banking.api.dto.TransacaoResponse;
import io.github.dougbt.banking.domain.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> realizarTransacao(
            @Valid @RequestBody TransacaoRequest request) {

        TransacaoResponse response = transacaoService.processarTransacao(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
