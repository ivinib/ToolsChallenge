package org.example.toolschallenge.toolschallenge.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.toolschallenge.toolschallenge.model.FormaPagamento;

public class ValidacaoDaFormaDePagamento implements ConstraintValidator<FormaPagamentoValida, FormaPagamento> {

    @Override
    public boolean isValid(FormaPagamento pagamento, ConstraintValidatorContext constraintValidatorContext) {
        if (pagamento == null) return false;


        if (pagamento.getTipo().isEmpty() || pagamento.getParcelas().isEmpty()) return false;

        if (!pagamento.getTipo().equals(TipoPagamento.AVISTA.name()) &&
            !pagamento.getTipo().equals(TipoPagamento.PARCELADO_EMISSOR.name())  &&
            !pagamento.getTipo().equals(TipoPagamento.PARCELADO_LOJA.name()) ){

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O tipo do pagamento é inválido")
                    .addPropertyNode("tipo")
                    .addConstraintViolation();
            return false;
        }

        if (pagamento.getParcelas().matches("d{1,2}")){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O campo parcelas deve ser um número de no maximo 2 digitos")
                    .addPropertyNode("parcelas")
                    .addConstraintViolation();
            return false;
        }

        if (pagamento.getTipo().equals(TipoPagamento.AVISTA.name()) && !pagamento.getParcelas().equals("1")){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O número de parcelas deve ser 1 quando o tipo de pagamento é AVISTA")
                    .addPropertyNode("parcelas")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
