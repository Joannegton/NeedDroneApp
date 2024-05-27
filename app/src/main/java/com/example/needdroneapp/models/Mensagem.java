package com.example.needdroneapp.models;

public class Mensagem {
    private Integer id;
    private Integer remetenteId;
    private Integer destinatarioId;
    private String texto;
    private String dataHora;

    public boolean isEnviada() {
        return enviada;
    }

    public void setEnviada(boolean enviada) {
        this.enviada = enviada;
    }

    private boolean enviada;


    // Getters
    public Integer getId() {
        return id;
    }

    public Integer getRemetenteId() {
        return remetenteId;
    }

    public Integer getDestinatarioId() {
        return destinatarioId;
    }

    public String getTexto() {
        return texto;
    }

    public String getDataHora() {
        return dataHora;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setRemetenteId(Integer remetenteId) {
        this.remetenteId = remetenteId;
    }

    public void setDestinatarioId(Integer destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}