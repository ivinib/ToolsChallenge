package org.example.toolschallenge.toolschallenge.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ValidarValor implements ConstraintValidator<ValorValido, String> {
    @Override
    public boolean isValid(String valor, ConstraintValidatorContext constraintValidatorContext) {
        if (valor == null) return false;

        if (!valor.matches("\\d+(\\.\\d{1,2})?")){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Valor invalido")
                    .addPropertyNode("valor")
                    .addConstraintViolation();
            return false;
        }

        try {
            BigDecimal valorDecimal = new BigDecimal(valor);
            if (valorDecimal.compareTo(BigDecimal.ZERO) <= 0 ) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("O valor deve ser positivo")
                        .addPropertyNode("valor")
                        .addConstraintViolation();
                return false;
            }
        }catch (NumberFormatException e){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Valor invalido")
                    .addPropertyNode("valor")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
