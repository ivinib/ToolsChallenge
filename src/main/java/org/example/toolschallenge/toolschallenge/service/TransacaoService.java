package org.example.toolschallenge.toolschallenge.service;

import jakarta.validation.Valid;
import org.example.toolschallenge.toolschallenge.model.Descricao;
import org.example.toolschallenge.toolschallenge.model.FormaPagamento;
import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.repository.TransacaoRepository;
import org.example.toolschallenge.toolschallenge.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public ResponseEntity<Transacao> salvaTransacao(@Valid @RequestBody Transacao transacao) {

        processaPagamento(transacao);
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

    public ResponseEntity<Transacao> atualizaTransacao(@PathVariable Long id, @Valid @RequestBody Transacao transacaoAtualizada) {
        try{
            log.info("Buscando transacao para ser atualizada");
            Transacao transacao = transacaoRepository.findById(id).orElse(null);
            if (transacao == null) {
                return ResponseEntity.notFound().build();
            }
            atualizaDescricao(transacao.getDescricao(), transacaoAtualizada.getDescricao());
            atualizaFormaPagamento(transacao.getFormaPagamento(), transacaoAtualizada.getFormaPagamento());

            transacao.setCartao(mascaraNumeroCartao(transacaoAtualizada.getCartao()));
            transacaoRepository.save(transacao);

            log.info("transacao atualizada com sucesso");
            return ResponseEntity.ok(transacao);

        } catch (RuntimeException e) {
            log.error("Ocorreu um erro ao tentar atualizar transacao. Erro:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Transacao> processaEstorno(@PathVariable Long id){
        try{
            log.info("Buscando transacao para estorno");
            Transacao transacao = transacaoRepository.findById(id).orElse(null);
            if (transacao == null) {
                log.info("Transacao nao encontrada");
                return ResponseEntity.notFound().build();
            }else {
                processaEstorno(transacao);
                transacaoRepository.save(transacao);
                log.info("Estorno processado com sucesso");
                return ResponseEntity.ok(transacao);
            }
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar processar estorno. Erro:" + e.getMessage());
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

    private void processaPagamento(Transacao transacao) {
        Descricao descricao = transacao.getDescricao();
        descricao.setCodigoAutorizacao(geraCodigoAutorizacao());
        descricao.setNsu(geraCodigoNsu());

        if (null == transacao.getDescricao().getEstabelecimento()){
            descricao.setStatus(Status.NEGADO.name());
        }else {
            descricao.setStatus(Status.AUTORIZADO.name());
        }
        transacao.setCartao(mascaraNumeroCartao(transacao.getCartao()));
    }

    private void processaEstorno(Transacao transacao) {
        transacao.getDescricao().setStatus(Status.CANCELADO.name());
    }

    private String geraCodigoAutorizacao(){
        Random random = new Random();
        long codigoAutorizacao = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return String.valueOf(codigoAutorizacao);
    }

    private String geraCodigoNsu(){
        Random random = new Random();
        long codigoAutorizacao = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return String.valueOf(codigoAutorizacao);
    }

    private void atualizaDescricao(Descricao descricaoExistente, Descricao descricaoAtualizada) {
        descricaoExistente.setValor(descricaoAtualizada.getValor());
        descricaoExistente.setDataHora(descricaoAtualizada.getDataHora());
        descricaoExistente.setEstabelecimento(descricaoAtualizada.getEstabelecimento());
        descricaoExistente.setNsu(descricaoAtualizada.getNsu());
        descricaoExistente.setCodigoAutorizacao(descricaoAtualizada.getCodigoAutorizacao());
        descricaoExistente.setStatus(descricaoAtualizada.getStatus());
    }

    private void atualizaFormaPagamento(FormaPagamento formaExistente, FormaPagamento formaAtualizada) {
        formaExistente.setTipo(formaAtualizada.getTipo());
        formaExistente.setParcelas(formaAtualizada.getParcelas());
    }

    public static String mascaraNumeroCartao(String cartao) {
        String primeirosDigitos = cartao.substring(0, 4);
        String ultomosDigitos = cartao.substring(cartao.length() - 4);
        String meioMascarado = "*".repeat(cartao.length() - 8);
        return primeirosDigitos + meioMascarado + ultomosDigitos;
    }
}
