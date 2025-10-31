package org.example.toolschallenge.toolschallenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.example.toolschallenge.toolschallenge.exception.CampoInvalidoException;
import org.example.toolschallenge.toolschallenge.util.ValorValido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @PastOrPresent(message = "Não pode estar do futuro")
    @NotNull(message = "Data deve ser preenchida")
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

    //Conversor de data que recebe o valor em String e devolve em LocalDateTime
    public static LocalDateTime convertDate(String date){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return LocalDateTime.parse(date, formatter);
        }catch (DateTimeParseException ex){
            throw new CampoInvalidoException("dataHora", "Formato inválido, use 'dd/mm/aaaa hh:mm:ss");
        }
    }
}
