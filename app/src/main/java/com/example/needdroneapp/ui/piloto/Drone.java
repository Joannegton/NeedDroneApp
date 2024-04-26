package com.example.needdroneapp.ui.piloto;

public class Drone {
    private String nome;
    private int autonomia;
    private int areaCobertura;
    private String tipoDrone;
    private String imgQualidade;
    private String status;
    private int imgSobreposicao;
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

    public int getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(int autonomia) {
        this.autonomia = autonomia;
    }

    public int getAreaCobertura() {
        return areaCobertura;
    }

    public void setAreaCobertura(int areaCobertura) {
        this.areaCobertura = areaCobertura;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImgSobreposicao() {
        return imgSobreposicao;
    }

    public void setImgSobreposicao(int imgSobreposicao) {
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
