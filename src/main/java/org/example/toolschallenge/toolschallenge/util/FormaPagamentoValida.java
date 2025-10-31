package org.example.toolschallenge.toolschallenge.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = ValidarFormaDePagamento.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormaPagamentoValida {
    String message() default "Forma de pagamento invalida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
