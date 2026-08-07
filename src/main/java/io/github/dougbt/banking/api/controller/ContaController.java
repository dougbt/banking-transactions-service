package io.github.dougbt.banking.api.controller;

import io.github.dougbt.banking.api.dto.ContaRequest;
import io.github.dougbt.banking.api.dto.ContaResponse;
import io.github.dougbt.banking.domain.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaResponse> criarConta(@Valid @RequestBody ContaRequest request) {
        ContaResponse response = contaService.criarConta(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{numeroConta}")
                .buildAndExpand(response.numeroConta())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<ContaResponse> buscarConta(
            @RequestParam("numero_conta") Integer numeroConta) {

        ContaResponse response = contaService.buscarConta(numeroConta);
        return ResponseEntity.ok(response);
    }
}
