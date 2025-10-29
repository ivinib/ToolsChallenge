package org.example.toolschallenge.toolschallenge.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/test")
public class TesteController {

    @GetMapping("/violacao")
    public void disparaViolacao() {
        Set<ConstraintViolation<?>> violacoes = new HashSet<>();

        ConstraintViolation<?> violacao = new ConstraintViolation<Object>() {
            @Override
            public String getMessage() {
                return "Campo invalido";
            }

            @Override
            public Path getPropertyPath() {
                return PathImpl.createPathFromString("campo");
            }

            @Override
            public String getMessageTemplate() {
                return "";
            }

            @Override
            public Object getRootBean() {
                return null;
            }

            @Override
            public Class<Object> getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Object[] getExecutableParameters() {
                return new Object[0];
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public <U> U unwrap(Class<U> aClass) {
                return null;
            }
        };
        violacoes.add(violacao);
        throw new ConstraintViolationException(violacoes);
    }
}
