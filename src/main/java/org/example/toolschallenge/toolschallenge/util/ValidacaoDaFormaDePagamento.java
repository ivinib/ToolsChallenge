package org.example.toolschallenge.toolschallenge.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.toolschallenge.toolschallenge.model.FormaPagamento;

public class ValidacaoDaFormaDePagamento implements ConstraintValidator<FormaPagamentoValida, FormaPagamento> {

    @Override
    public boolean isValid(FormaPagamento pagamento, ConstraintValidatorContext constraintValidatorContext) {
        if (pagamento == null) return false;

        boolean valido = true;

        if (pagamento.getTipo().equals(TipoPagamento.AVISTA.name()) && pagamento.getParcelas() > 1){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O número de parcelas deve ser 1 quando o tipo de pagamento é AVISTA")
                    .addPropertyNode("parcelas")
                    .addConstraintViolation();
            valido = false;
        }
        return valido;
    }
}
