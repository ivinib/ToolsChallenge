package org.example.toolschallenge.toolschallenge.controller;

import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TraansacaoController {

    private final TransacaoRepository transacaoRepository;

    public TraansacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @PostMapping
    public ResponseEntity<Transacao> salvaTransacao(@RequestBody Transacao transacao) {
        Transacao transacaoSalva = transacaoRepository.save(transacao);
        return ResponseEntity.ok(transacaoSalva);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTodasTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscaTransacao(@PathVariable Long id) {
        return transacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizaTransacao(@PathVariable Long id, @RequestBody Transacao transacaoAtualizada) {
        return transacaoRepository.findById(id)
                .map(transacao -> {
                    transacao.setCartao(transacaoAtualizada.getCartao());
                    transacao.setDescricao(transacaoAtualizada.getDescricao());
                    transacao.setFormaPagamento(transacaoAtualizada.getFormaPagamento());
                    Transacao salvaTransacao = transacaoRepository.save(transacao);
                    return ResponseEntity.ok(salvaTransacao);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id) {
        if(transacaoRepository.existsById(id)) {
            transacaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
