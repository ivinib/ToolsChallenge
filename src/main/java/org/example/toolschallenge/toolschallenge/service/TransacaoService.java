package org.example.toolschallenge.toolschallenge.service;

import jakarta.validation.Valid;
import org.example.toolschallenge.toolschallenge.exception.CampoInvalidoException;
import org.example.toolschallenge.toolschallenge.model.Descricao;
import org.example.toolschallenge.toolschallenge.model.FormaPagamento;
import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.repository.TransacaoRepository;
import org.example.toolschallenge.toolschallenge.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Random;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    //Metodo que valida e salva a transação no banco de dados
    public ResponseEntity<Transacao> salvaTransacao(@Valid @RequestBody Transacao transacao) {

        processaPagamento(transacao);
        try{
            Transacao transacaoSalva = transacaoRepository.save(transacao);
            log.info("transacao salva com sucesso");
            return ResponseEntity.ok(transacaoSalva);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar a transacao. Erro:" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    //Metodo que busca todas as transações salvas no banco de dados
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

    //Metodo que busca uma transação especifica no banco pelo id
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

    //Metodo que atualiza uma transação e salva no banco de dados
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

    //Metodo que processa o estorno de uma transação
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

    //Metodo que deleta uma transação do bando buscando pelo id
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id) {
        if(transacaoRepository.existsById(id)) {
            transacaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Metodo que é chamado antes de salvar a transação para validar e gerar as informações
    private void processaPagamento(Transacao transacao) {
        Descricao descricao = transacao.getDescricao();
        descricao.setCodigoAutorizacao(geraCodigo());
        descricao.setNsu(geraCodigo());

        if (null == transacao.getDescricao().getEstabelecimento()){
            descricao.setStatus(Status.NEGADO.name());
        }else {
            descricao.setStatus(Status.AUTORIZADO.name());
        }
        transacao.setCartao(mascaraNumeroCartao(transacao.getCartao()));
    }

    //Metodo processa o estorno
    private void processaEstorno(Transacao transacao) {
        transacao.getDescricao().setStatus(Status.CANCELADO.name());
    }

    //Metodo que é chamado no processaPagamento() e gera os codigos de autorização e nsu
    private String geraCodigo(){
        Random random = new Random();
        long codigoAutorizacao = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return String.valueOf(codigoAutorizacao);
    }

    //Metodo auxiliar para atualizar os campos da descrição no processo de atualizar uma transação
    private void atualizaDescricao(Descricao descricaoExistente, Descricao descricaoAtualizada) {
        descricaoExistente.setValor(descricaoAtualizada.getValor());
        descricaoExistente.setDataHora(descricaoAtualizada.getDataHora());
        descricaoExistente.setEstabelecimento(descricaoAtualizada.getEstabelecimento());
        descricaoExistente.setNsu(descricaoAtualizada.getNsu());
        descricaoExistente.setCodigoAutorizacao(descricaoAtualizada.getCodigoAutorizacao());
        descricaoExistente.setStatus(descricaoAtualizada.getStatus());
    }

    //Metodo auxiliar para atualizar os campos da forma de pagamento no processo de atualizar uma transação
    private void atualizaFormaPagamento(FormaPagamento formaExistente, FormaPagamento formaAtualizada) {
        formaExistente.setTipo(formaAtualizada.getTipo());
        formaExistente.setParcelas(formaAtualizada.getParcelas());
    }

    //Metodo que mascara o número do cartão antes de salvar no banco de dados
    private static String mascaraNumeroCartao(String cartao) {
        if (cartao == null || cartao.length() < 13){
            throw new CampoInvalidoException("cartao", "O número do cartão deve conter no minimo 13 digitos");
        }
        String primeirosDigitos = cartao.substring(0, 4);
        String ultomosDigitos = cartao.substring(cartao.length() - 4);
        String meioMascarado = "*".repeat(cartao.length() - 8);
        return primeirosDigitos + meioMascarado + ultomosDigitos;
    }
}
