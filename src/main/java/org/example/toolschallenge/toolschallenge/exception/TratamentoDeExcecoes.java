package org.example.toolschallenge.toolschallenge.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratamentoDeExcecoes {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> trataConstraintValidationException(ConstraintViolationException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getConstraintViolations().forEach(violacao -> {
            String campo = violacao.getPropertyPath().toString();
            String mensagem = violacao.getMessage();
            erros.put(campo, mensagem);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> trataMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(violacao -> {
            erros.put(violacao.getField(), violacao.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(CampoInvalidoException.class)
    public ResponseEntity<Map<String, String>> trataCartaoInvalidoException(CampoInvalidoException ex) {
        Map<String, String> erros = new HashMap<>();
        erros.put(ex.getCampo(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> trataHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> erros = new HashMap<>();
        if (ex.getMessage().contains("LocalDateTime")){
            erros.put("dataHora", "Formato de data inválido. Use o formato dd/mm/aaaa hh:mm:ss ");
        }else {
            erros.put("erro", "Ocorreu um erro ao tentar processar a requisição. Erro: "+ ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}
