package org.example.toolschallenge.toolschallenge.service;

import org.example.toolschallenge.toolschallenge.model.Descricao;
import org.example.toolschallenge.toolschallenge.model.FormaPagamento;
import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.repository.TransacaoRepository;
import org.example.toolschallenge.toolschallenge.util.Status;
import org.example.toolschallenge.toolschallenge.util.TipoPagamento;
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

    private static final String MENSAGEM_ERRO = "Ocorreu um erro ao tentar salvar a transacao. Erro:";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transacao = new Transacao();
        transacao.setIdTransacao(1L);
        transacao.setCartao("1234567890123");

        Descricao descricao = new Descricao();
        descricao.setEstabelecimento("Mercado");
        descricao.setValor("50.70");
        transacao.setDescricao(descricao);

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setTipo(TipoPagamento.PARCELADO_LOJA.name());
        formaPagamento.setParcelas("4");
        transacao.setFormaPagamento(formaPagamento);
    }

    @Test
    void testSalvarTransacao() {
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        ResponseEntity<Transacao> response = transacaoService.salvaTransacao(transacao);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(transacao, response.getBody());
        verify(transacaoRepository).save(transacao);
        assertEquals(Status.AUTORIZADO.name(), response.getBody().getDescricao().getStatus());
        assertEquals("1234*****0123", transacao.getCartao());
        assertNotNull(response.getBody().getDescricao().getNsu());
        assertNotNull(response.getBody().getDescricao().getCodigoAutorizacao());
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

        Descricao descricaoAtualizada = new Descricao();
        descricaoAtualizada.setEstabelecimento("Mercado");
        descricaoAtualizada.setValor("54.78");
        descricaoAtualizada.setStatus(Status.AUTORIZADO.name());
        atualizada.setDescricao(descricaoAtualizada);

        FormaPagamento formaPagamentoAtualizada = new FormaPagamento();
        formaPagamentoAtualizada.setTipo(TipoPagamento.PARCELADO_LOJA.name());
        formaPagamentoAtualizada.setParcelas("4");
        atualizada.setFormaPagamento(formaPagamentoAtualizada);

        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        ResponseEntity<Transacao> response = transacaoService.atualizaTransacao(1L, atualizada);

        assertEquals(200, response.getStatusCode().value());
        verify(transacaoRepository).save(transacao);
        assertEquals("9999*****9999", transacao.getCartao());
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

    @Test
    void testProcessaTransacaoSemEstabelecimento() {
        transacao.getDescricao().setEstabelecimento(null);
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        ResponseEntity<Transacao> response = transacaoService.salvaTransacao(transacao);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(transacao, response.getBody());
        verify(transacaoRepository).save(transacao);
        assertEquals(Status.NEGADO.name(), response.getBody().getDescricao().getStatus());
        assertNotNull(response.getBody().getDescricao().getNsu());
        assertNotNull(response.getBody().getDescricao().getCodigoAutorizacao());
    }

    @Test
    void testProcessaEstorno() {

        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        ResponseEntity<Transacao> response = transacaoService.processaEstorno(1L);

        assertEquals(200, response.getStatusCode().value());
        verify(transacaoRepository).save(transacao);
        assertEquals(Status.CANCELADO.name(), response.getBody().getDescricao().getStatus());
    }

}