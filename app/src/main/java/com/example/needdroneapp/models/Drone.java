package com.example.needdroneapp.models;

public class Drone {
    private String nome;
    private String tipoDrone;
    private String imgQualidade;
    private String autonomia;
    private String areaCobertura;
    private String status;
    private String imgSobreposicao;
    private String foto;
    private String pilotoId;


    // Add other drone properties...

    // Getters and setters


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoDrone() {
        return tipoDrone;
    }

    public void setTipoDrone(String tipoDrone) {
        this.tipoDrone = tipoDrone;
    }

    public String getImgQualidade() {
        return imgQualidade;
    }

    public void setImgQualidade(String imgQualidade) {
        this.imgQualidade = imgQualidade;
    }

    public String getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(String autonomia) {
        this.autonomia = autonomia;
    }

    public String getAreaCobertura() {
        return areaCobertura;
    }

    public void setAreaCobertura(String areaCobertura) {
        this.areaCobertura = areaCobertura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgSobreposicao() {
        return imgSobreposicao;
    }

    public void setImgSobreposicao(String imgSobreposicao) {
        this.imgSobreposicao = imgSobreposicao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(String pilotoId) {
        this.pilotoId = pilotoId;
    }
}