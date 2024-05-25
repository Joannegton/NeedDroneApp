package com.example.needdroneapp.models;

public class Avaliacao {
    private Integer id;
    private Integer avaliadorId;
    private Integer avaliadoId;
    private String comentario;
    private String dataAvaliacao;
    private Float avaliacao;



    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public Integer getAvaliadorId() {
        return avaliadorId;
    }

    public void setAvaliadorId(Integer pilotoId) {
        this.avaliadorId = pilotoId;
    }

    public Integer getAvaliadoId() {
        return avaliadoId;
    }

    public void setAvaliadoId(Integer clienteId) {
        this.avaliadoId = clienteId;
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
