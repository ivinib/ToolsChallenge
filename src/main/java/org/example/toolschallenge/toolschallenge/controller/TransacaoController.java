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

    //Endpoint de POST que chama o metodo da service para validar e salvar uma transação
    @PostMapping
    public ResponseEntity<Transacao> salvaTransacao(@Valid @RequestBody Transacao transacao) {
        return transacaoService.salvaTransacao(transacao);
    }

    //Endpoint de GET que chama o metodo da service para buscar todas as transações salvas no banco de dados
    @GetMapping
    public ResponseEntity<List<Transacao>> listarTodasTransacoes() {
        return transacaoService.listarTodasTransacoes();
    }

    //Endpoint de GET que chama o metodo da service para buscar a transação pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscaTransacao(@PathVariable Long id) {
        return transacaoService.buscaTransacao(id);
    }

    //Endpoint de PUT que chama o metodo da service para validar e atualizar uma transação
    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizaTransacao(@PathVariable Long id, @Valid @RequestBody Transacao transacaoAtualizada) {
        return transacaoService.atualizaTransacao(id, transacaoAtualizada);
    }

    //Endpoint de PUT que chama o metodo da service para processar o estorno pelo id
    @PutMapping("/estorno/{id}")
    public ResponseEntity<Transacao> processaEstorno(@PathVariable Long id) {
        return transacaoService.processaEstorno(id);
    }

    //Endpoint de DELETE que chama o metodo da service deletar uma transação pelo id
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
