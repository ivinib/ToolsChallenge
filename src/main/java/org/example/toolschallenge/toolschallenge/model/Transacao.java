package org.example.toolschallenge.toolschallenge.model;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "tb_transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long cartao;

    @OneToOne
    private Descricao descricao;

    @OneToOne
    private FormaPagamento formaPagamento;

}
