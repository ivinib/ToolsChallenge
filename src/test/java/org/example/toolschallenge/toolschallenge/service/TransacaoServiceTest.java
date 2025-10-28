package org.example.toolschallenge.toolschallenge.service;

import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {
    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    private Transacao transacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transacao = new Transacao();
        transacao.setIdTransacao(1L);
        transacao.setCartao("1234567890123");
    }

    @Test
    void testSalvarTransacao() {
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        ResponseEntity<Transacao> response = transacaoService.salvaTransacao(transacao);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(transacao, response.getBody());
        verify(transacaoRepository).save(transacao);
    }

    @Test
    void testListarTodasTransacoes() {
        List<Transacao> lista = List.of(transacao);
        when(transacaoRepository.findAll()).thenReturn(lista);

        ResponseEntity<List<Transacao>> response = transacaoService.listarTodasTransacoes();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testListarTransacoesVazia() {
        when(transacaoRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Transacao>> response = transacaoService.listarTodasTransacoes();

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testBuscaTransacaoInexistente() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Transacao> response = transacaoService.buscaTransacao(1L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testAtualizaTransacao() {
        Transacao atualizada = new Transacao();
        atualizada.setCartao("9999999999999");

        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        ResponseEntity<Transacao> response = transacaoService.atualizaTransacao(1L, atualizada);

        assertEquals(200, response.getStatusCode().value());
        verify(transacaoRepository).save(transacao);
        assertEquals("9999999999999", transacao.getCartao());
    }

    @Test
    void testDeleteTransacaoExistente() {
        when(transacaoRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = transacaoService.deleteTransacao(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(transacaoRepository).deleteById(1L);
    }

    @Test
    void testDeleteTransacaoInexistente() {
        when(transacaoRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = transacaoService.deleteTransacao(1L);

        assertEquals(404, response.getStatusCode().value());
    }

}