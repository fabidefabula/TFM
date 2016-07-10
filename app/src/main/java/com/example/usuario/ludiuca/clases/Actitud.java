package com.example.usuario.ludiuca.clases;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Actitud {
    private String nombre;
    private String descripcion;
    private String imagenActitud;
    private int puntos;
    private boolean flag;
    private int tipo, idActitud;

    public Actitud(String nombre, int puntos){
        this.nombre = nombre;
        this.puntos = puntos;

    }
    public Actitud(){}

    public boolean isFlag() {
        return flag;
    }

    public int getIdActitud() {
        return idActitud;
    }

    public void setIdActitud(int idActitud) {
        this.idActitud = idActitud;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNombre() {
        return nombre;
    }
}
