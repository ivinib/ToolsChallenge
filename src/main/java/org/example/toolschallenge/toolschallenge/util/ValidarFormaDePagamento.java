package org.example.toolschallenge.toolschallenge.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.toolschallenge.toolschallenge.model.FormaPagamento;

public class ValidarFormaDePagamento implements ConstraintValidator<FormaPagamentoValida, FormaPagamento> {

    @Override
    public boolean isValid(FormaPagamento pagamento, ConstraintValidatorContext constraintValidatorContext) {
        if (pagamento == null){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("A forma de pagamento deve ser preenchida")
                    .addPropertyNode("formaPagamento")
                    .addConstraintViolation();
            return false;
        }

        if (pagamento.getTipo() == null || pagamento.getTipo().isEmpty()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O tipo do pagamento deve ser preenchido")
                    .addPropertyNode("tipo")
                    .addConstraintViolation();
            return false;
        }

        if (pagamento.getParcelas() == null || pagamento.getParcelas().isEmpty()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O número de parcelas deve ser preenchido")
                    .addPropertyNode("parcelas")
                    .addConstraintViolation();
            return false;
        }

        if (!pagamento.getTipo().toUpperCase().equals(TipoPagamento.AVISTA.name()) &&
            !pagamento.getTipo().toUpperCase().equals(TipoPagamento.PARCELADO_EMISSOR.name())  &&
            !pagamento.getTipo().toUpperCase().equals(TipoPagamento.PARCELADO_LOJA.name()) ){

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O tipo do pagamento é inválido")
                    .addPropertyNode("tipo")
                    .addConstraintViolation();
            return false;
        }

        if (!pagamento.getParcelas().matches("^[1-9]\\d?$")){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O campo parcelas deve ser um número maior que 0 de no maximo 2 digitos")
                    .addPropertyNode("parcelas")
                    .addConstraintViolation();
            return false;
        }

        if (pagamento.getTipo().toUpperCase().equals(TipoPagamento.AVISTA.name()) && !pagamento.getParcelas().equals("1")){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O número de parcelas deve ser 1 quando o tipo de pagamento é AVISTA")
                    .addPropertyNode("parcelas")
                    .addConstraintViolation();
            return false;
        }

        if ((pagamento.getTipo().toUpperCase().equals(TipoPagamento.PARCELADO_LOJA.name()) || pagamento.getTipo().toUpperCase().equals(TipoPagamento.PARCELADO_EMISSOR.name()) ) && pagamento.getParcelas().equals("1")){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("O número de parcelas não pode ser 1 quando o tipo de pagamento é PARCELADO")
                    .addPropertyNode("parcelas")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
