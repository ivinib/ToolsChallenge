package org.example.toolschallenge.toolschallenge.model;

import jakarta.persistence.Id;

import java.util.Date;

public class Descricao {
    @Id
    private long id;
    private double valor;
    private Date dataHora;
    private String estabelecimento;
    private long nsu;
    private String codigoAutorizacao;
    private String status;
}
