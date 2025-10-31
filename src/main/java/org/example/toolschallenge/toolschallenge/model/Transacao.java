package org.example.toolschallenge.toolschallenge.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.toolschallenge.toolschallenge.util.FormaPagamentoValida;

@Entity(name = "tb_transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transacao")
    private Long idTransacao;

    @Column(name = "cartao")
    @Size(min = 13, max = 19, message = "Cartão deve conter entre 13 e 19 digitos")
    @NotNull(message = "O número do cartão deve ser preenchido")
    private String cartao;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_descricao", referencedColumnName = "id_descricao")
    @NotNull(message = "A descrição é obrigatoria")
    private Descricao descricao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_forma_pagamento", referencedColumnName = "id_forma_pagamento")
    @FormaPagamentoValida
    private FormaPagamento formaPagamento;

    public Long getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Long idTransacao) {
        this.idTransacao = idTransacao;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
