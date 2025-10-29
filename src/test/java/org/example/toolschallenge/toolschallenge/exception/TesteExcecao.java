package org.example.toolschallenge.toolschallenge.exception;

import org.example.toolschallenge.toolschallenge.controller.TesteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TesteController.class)
public class TesteExcecao {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testeConstraintViolationException() throws Exception {
        mockMvc.perform(get("/test/violacao"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campo").value("Campo invalido"));
    }
}
