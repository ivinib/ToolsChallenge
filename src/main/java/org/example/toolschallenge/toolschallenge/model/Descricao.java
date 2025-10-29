package org.example.toolschallenge.toolschallenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.example.toolschallenge.toolschallenge.util.ValorValido;

import java.time.LocalDateTime;

@Entity(name = "tb_descricao")
public class Descricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descricao")
    private Long idDescricao;

    @Column(name = "valor")
    @ValorValido
    private String valor;

    @Column(name = "data_hora")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy H:m:s")
    private LocalDateTime dataHora;

    @Column(name = "estabelecimento")
    private String estabelecimento;

    @Column(name = "nsu")
    private String nsu;

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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getNsu() {
        return nsu;
    }

    public void setNsu(String nsu) {
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
