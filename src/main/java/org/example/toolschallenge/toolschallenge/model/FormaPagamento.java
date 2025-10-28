package org.example.toolschallenge.toolschallenge.model;

import jakarta.persistence.*;

@Entity(name = "tb_forma_pagamento")
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_forma_pagamento")
    private Long idPagamento;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "parcelas")
    private String parcelas;

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getParcelas() {
        return parcelas;
    }

    public void setParcelas(String parcelas) {
        this.parcelas = parcelas;
    }
}
