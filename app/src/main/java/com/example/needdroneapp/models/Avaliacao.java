package com.example.needdroneapp.models;

public class Avaliacao {
    private Integer pilotoId;
    private Integer clienteId;
    private String comentario;
    private String dataAvaliacao;
    private Float avaliacao;



    public Integer getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(Integer pilotoId) {
        this.pilotoId = pilotoId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(String dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Float avaliacao) {
        this.avaliacao = avaliacao;
    }
}
