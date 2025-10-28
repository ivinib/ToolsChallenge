package org.example.toolschallenge.toolschallenge.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "tb_descricao")
public class Descricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descricao")
    private Long idDescricao;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "data_hora")
    private Date dataHora;

    @Column(name = "estabelecimento")
    private String estabelecimento;

    @Column(name = "nsu")
    private Long nsu;

    @Column(name = "codigo_autorizacao")
    private String codigoAutorizacao;

    @Column(name = "status")
    private String status;

    public Long getIdDescricao() {
        return idDescricao;
    }

    public void setIdDescricao(Long idDescricao) {
        this.idDescricao = idDescricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Long getNsu() {
        return nsu;
    }

    public void setNsu(Long nsu) {
        this.nsu = nsu;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
