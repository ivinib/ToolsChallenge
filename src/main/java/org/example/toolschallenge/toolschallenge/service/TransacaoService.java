package org.example.toolschallenge.toolschallenge.service;

import jakarta.validation.Valid;
import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public ResponseEntity<Transacao> salvaTransacao(@Valid @RequestBody Transacao transacao) {
        try{
            Transacao transacaoSalva = transacaoRepository.save(transacao);
            log.info("transacao salva com sucesso");
            return ResponseEntity.ok(transacaoSalva);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar a transacao. Erro:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<Transacao>> listarTodasTransacoes() {
        try{
            log.info("Buscando todas as transacoes");
            List<Transacao> transacoes = transacaoRepository.findAll();

            if (transacoes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(transacoes);

        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar buscar todas as transacoes. Erro:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Transacao> buscaTransacao(@PathVariable Long id) {
        try{
            log.info("Buscando transacao com id {}", id);
            Transacao transacao = transacaoRepository.findById(id).orElse(null);
            if (transacao == null) {
                return ResponseEntity.notFound().build();
            }else {
                return ResponseEntity.ok(transacao);
            }
        } catch (RuntimeException e) {
            log.error("Ocorreu um erro ao tentar buscar transacao. Erro:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Transacao> atualizaTransacao(@PathVariable Long id, @RequestBody Transacao transacaoAtualizada) {
        try{
            log.info("Buscando transacao para ser atualizada");
            Transacao transacao = transacaoRepository.findById(id).orElse(null);
            if (transacao == null) {
                return ResponseEntity.notFound().build();
            }
            transacao.setCartao(transacaoAtualizada.getCartao());
            transacao.setDescricao(transacaoAtualizada.getDescricao());
            transacao.setFormaPagamento(transacaoAtualizada.getFormaPagamento());
            transacaoRepository.save(transacao);

            log.info("transacao atualizada com sucesso");
            return ResponseEntity.ok(transacao);

        } catch (RuntimeException e) {
            log.error("Ocorreu um erro ao tentar atualizar transacao. Erro:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id) {
        if(transacaoRepository.existsById(id)) {
            transacaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
