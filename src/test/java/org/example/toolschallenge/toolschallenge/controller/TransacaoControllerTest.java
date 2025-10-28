package org.example.toolschallenge.toolschallenge.controller;

import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransacaoService transacaoService;

    @Test
    void testSalvarTransacaoComSucesso() throws Exception {
        String json = """
        {
            "cartao": "1234567890123",
            "descricao": { "valor": 100.0 },
            "formaPagamento": { "tipo": "AVISTA", "parcelas": 1 }
        }
        """;

        Transacao transacao = new Transacao();
        transacao.setCartao("1234567890123");

        Mockito.when(transacaoService.salvaTransacao(Mockito.any())).thenReturn(ResponseEntity.ok(transacao));

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartao").value("1234567890123"));
    }

    @Test
    void testListarTodasTransacoes() throws Exception {
        Mockito.when(transacaoService.listarTodasTransacoes())
                .thenReturn(ResponseEntity.ok(List.of(new Transacao())));

        mockMvc.perform(get("/transacao"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscaTransacaoNaoEncontrada() throws Exception {
        Mockito.when(transacaoService.buscaTransacao(99L))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/transacao/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscaTransacaoExistente() throws Exception {
        String json = """
        {
            "cartao": "1234567890123",
            "descricao": { "valor": 100.0 },
            "formaPagamento": { "tipo": "AVISTA", "parcelas": 1 }
        }
        """;

        Transacao transacao = new Transacao();
        transacao.setCartao("1234567890123");

        Mockito.when(transacaoService.salvaTransacao(Mockito.any())).thenReturn(ResponseEntity.ok(transacao));

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        Mockito.when(transacaoService.buscaTransacao(1L))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/transacao/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTransacao() throws Exception {
        Mockito.when(transacaoService.deleteTransacao(1L))
                .thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/transacao/1"))
                .andExpect(status().isNoContent());
    }

}