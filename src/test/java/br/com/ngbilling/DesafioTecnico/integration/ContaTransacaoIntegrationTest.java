package br.com.ngbilling.DesafioTecnico.integration;

import br.com.ngbilling.DesafioTecnico.domain.model.Conta;
import br.com.ngbilling.DesafioTecnico.infrastructure.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ContaTransacaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContaRepository contaRepository;

    @BeforeEach
    void setup() {
        contaRepository.deleteAll();
        contaRepository.save(new Conta(234, new BigDecimal("180.37")));
    }

    @Test
    void deveCriarContaComSaldoInicial() throws Exception {
        mockMvc.perform(post("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroConta\":999,\"saldo\":100.00}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroConta", is(999)))
                .andExpect(jsonPath("$.saldo", is(100.00)));
    }

    @Test
    void deveBuscarContaExistente() throws Exception {
        mockMvc.perform(get("/conta")
                        .param("numero_conta", "234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroConta", is(234)))
                .andExpect(jsonPath("$.saldo", is(180.37)));
    }

    @Test
    void deveRetornar404ParaContaInexistente() throws Exception {
        mockMvc.perform(get("/conta")
                        .param("numero_conta", "9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRealizarTransacaoDebitoComTaxa() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"formaPagamento\":\"DEBITO\",\"numeroConta\":234,\"valor\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroConta", is(234)))
                .andExpect(jsonPath("$.saldo", is(closeTo(170.07, 0.01))));
    }

    @Test
    void deveRetornar422ParaSaldoInsuficiente() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"formaPagamento\":\"CREDITO\",\"numeroConta\":234,\"valor\":1000}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deveRealizarTransacaoPixSemTaxa() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"formaPagamento\":\"PIX\",\"numeroConta\":234,\"valor\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroConta", is(234)))
                .andExpect(jsonPath("$.saldo", is(closeTo(170.37, 0.01))));
    }
}
