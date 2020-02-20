package com.example.pap;

public class infopojo {


    String cantidad, circuitos,descargo,fecha,poste;


    public infopojo() {
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCircuitos() {
        return circuitos;
    }

    public void setCircuitos(String circuitos) {
        this.circuitos = circuitos;
    }

    public String getDescargo() {
        return descargo;
    }

    public void setDescargo(String descargo) {
        this.descargo = descargo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    @Override
    public String toString() {
        return cantidad +
                circuitos +
               descargo +
               fecha +
               poste;
    }
}

