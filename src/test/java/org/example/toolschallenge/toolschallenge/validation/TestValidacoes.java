package org.example.toolschallenge.toolschallenge.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.toolschallenge.toolschallenge.model.FormaPagamento;
import org.example.toolschallenge.toolschallenge.model.Transacao;
import org.example.toolschallenge.toolschallenge.util.TipoPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestValidacoes {
    private Validator validator;

    private FormaPagamento formaPagamento;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        formaPagamento = new FormaPagamento();
        formaPagamento.setTipo(TipoPagamento.AVISTA.name());
        formaPagamento.setParcelas("1");
    }

    @Test
    void testCartaoValido() {
        Transacao transacao = new Transacao();
        transacao.setCartao("1234567890123");
        transacao.setFormaPagamento(formaPagamento);

        Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCartaoCurto() {
        Transacao transacao = new Transacao();
        transacao.setCartao("123456");
        transacao.setFormaPagamento(formaPagamento);

        Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("entre 13 e 19")));
    }
    @Test
    void testFormaPagamentoInvalida() {
        Transacao transacao = new Transacao();
        transacao.setCartao("1234567890123");
        formaPagamento.setTipo("PARCELA");
        formaPagamento.setParcelas("2");
        transacao.setFormaPagamento(formaPagamento);

        Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("O tipo do pagamento é inválido")));
    }

    @Test
    void testFormaPagamentoValida() {
        Transacao transacao = new Transacao();
        transacao.setCartao("1234567890123");

        formaPagamento.setTipo(TipoPagamento.PARCELADO_EMISSOR.name()); // valid
        transacao.setFormaPagamento(formaPagamento);

        Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao);
        assertTrue(violations.isEmpty());
    }


}
