package com.example.needdroneapp.models;

public class Projeto {

    Integer projetoId;
    String titulo;
    String descricao;
    String dataEvento;
    String rua;
    String cidadeEstado;
    String status;
    Integer clienteId;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidadeEstado() {
        return cidadeEstado;
    }

    public void setCidadeEstado(String cidadeEstado) {
        this.cidadeEstado = cidadeEstado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public Integer getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Integer projetoId) {
        this.projetoId = projetoId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
}
