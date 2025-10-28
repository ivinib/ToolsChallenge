package org.example.toolschallenge.toolschallenge.controller;

import jakarta.validation.Valid;
import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Transacao> salvaTransacao(@Valid @RequestBody Transacao transacao) {
        return transacaoService.salvaTransacao(transacao);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTodasTransacoes() {
        return transacaoService.listarTodasTransacoes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscaTransacao(@PathVariable Long id) {
        return transacaoService.buscaTransacao(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizaTransacao(@PathVariable Long id, @Valid @RequestBody Transacao transacaoAtualizada) {
        return transacaoService.atualizaTransacao(id, transacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id) {
        try{
            transacaoService.deleteTransacao(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
