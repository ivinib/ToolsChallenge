package org.example.toolschallenge.toolschallenge.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.toolschallenge.toolschallenge.exception.CampoInvalidoException;
import org.example.toolschallenge.toolschallenge.model.Descricao;
import org.example.toolschallenge.toolschallenge.model.FormaPagamento;
import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.util.Status;
import org.example.toolschallenge.toolschallenge.util.TipoPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestValidacoes {
    private Validator validator;

    private Transacao transacao;
    private Descricao descricao;
    private FormaPagamento formaPagamento;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        transacao = new Transacao();
        descricao = new Descricao();
        formaPagamento = new FormaPagamento();

        transacao.setCartao("12456789012432");

        descricao.setStatus(Status.AUTORIZADO.name());
        descricao.setValor("123.45");
        descricao.setDataHora(LocalDateTime.now());
        descricao.setEstabelecimento("Mercado Compra Certa");
        transacao.setDescricao(descricao);

        formaPagamento.setTipo(TipoPagamento.AVISTA.name());
        formaPagamento.setParcelas("1");
        transacao.setFormaPagamento(formaPagamento);
    }

    @Test
    void testCartaoValido() {
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertTrue(violacoes.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "123456789012", "12345678901234567890"})
    void testCartapTamanhoInvalido(String cartao){
        transacao.setCartao(cartao);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("Cartão deve conter entre 13 e 19 digitos"));
    }

    @Test
    void testCartaoNulo() {
        transacao.setCartao(null);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("O número do cartão deve ser preenchido"));
    }

    @Test
    void testDescricaoNula(){
        transacao.setDescricao(null);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("A descrição é obrigatoria"));
    }

    @Test
    void testFormaPagamentoNula(){
        transacao.setFormaPagamento(null);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("A forma de pagamento deve ser preenchida"));
    }

    @Test
    void testValorNulo(){
        transacao.getDescricao().setValor(null);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("O valor deve ser preencido"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.", "f.q", "1.t2", "e.32", "3.456", "-123.45"})
    void testValorInvalido(String valor){
        transacao.getDescricao().setValor(valor);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("Valor invalido"));
    }

    @Test
    void testDataNula(){
        transacao.getDescricao().setDataHora(null);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("Data deve ser preenchida"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "12/10/2025 5:07:06",
            "12/10/2025 05:07:0",
            "12/10/2025 05:7:06",
            "2/10/2025 05:07:06",
            "12/9/2025 05:07:06",
            "12/10/25 05:07:06",
            "12.10.2025 05:07:06",
            "12/10 05.07.06",
            "12/10/2025 05:07"

    })
    void testDataInvalida(String data){
        Exception ex = assertThrows(CampoInvalidoException.class, () -> Descricao.convertDate(data));

        assertNotNull(ex);
        assertEquals("Formato inválido, use 'dd/mm/aaaa hh:mm:ss", ex.getMessage());
    }

    @Test
    void testTipoPagamentoNulo(){
        transacao.getFormaPagamento().setTipo(null);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("O tipo do pagamento deve ser preenchido"));
    }

    @Test
    void testParcelasNulo(){
        transacao.getFormaPagamento().setParcelas(null);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("O número de parcelas deve ser preenchido"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "0", "2f", "qw"})
    void testParcelaInvalida(String parcelas){
        transacao.getFormaPagamento().setTipo(TipoPagamento.PARCELADO_LOJA.name());
        transacao.getFormaPagamento().setParcelas(parcelas);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("O campo parcelas deve ser um número maior que 0 de no maximo 2 digitos"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"VISTA", "PARCELADO", "PRESTACAO"})
    void testTipoPagamentoInvalido(String tipo){
        transacao.getFormaPagamento().setTipo(tipo);
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("O tipo do pagamento é inválido"));
    }

    @Test
    void testTipoAVistaComParcelaMaiorQueUm(){
        transacao.getFormaPagamento().setTipo(TipoPagamento.AVISTA.name());
        transacao.getFormaPagamento().setParcelas("2");
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("O número de parcelas deve ser 1 quando o tipo de pagamento é AVISTA"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PARCELADO_EMISSOR", "PARCELADO_LOJA"})
    void testTipoParceladoComParcelaUm(String tipo){
        transacao.getFormaPagamento().setTipo(tipo);
        transacao.getFormaPagamento().setParcelas("1");
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertFalse(violacoes.isEmpty());
        assertEquals(1, violacoes.size());
        assertTrue(violacoes.iterator().next().getMessage().contains("O número de parcelas não pode ser 1 quando o tipo de pagamento é PARCELADO"));
    }

}
