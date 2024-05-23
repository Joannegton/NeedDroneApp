package com.example.needdroneapp.models;

public class Proposta {
    private Integer id;
    private Integer projetoId;
    private Integer clienteId;
    private Integer pilotoId;
    private Float ofertaInicial;
    private String detalhesProposta;
    private String status;
    private Integer droneId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Integer projetoId) {
        this.projetoId = projetoId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(Integer pilotoId) {
        this.pilotoId = pilotoId;
    }

    public Float getOfertaInicial() {
        return ofertaInicial;
    }

    public void setOfertaInicial(Float ofertaInicial) {
        this.ofertaInicial = ofertaInicial;
    }

    public String getDetalhesProposta() {
        return detalhesProposta;
    }

    public void setDetalhesProposta(String detalhesProposta) {
        this.detalhesProposta = detalhesProposta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDroneId() {
        return droneId;
    }

    public void setDroneId(Integer droneId) {
        this.droneId = droneId;
    }



}
