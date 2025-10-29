package org.example.toolschallenge.toolschallenge.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidarValor.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValorValido {
    String message() default "Valor da compra inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
