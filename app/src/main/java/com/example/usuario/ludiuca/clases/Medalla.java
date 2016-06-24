package com.example.usuario.ludiuca.clases;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Medalla {
    private String nombre;
    private String descripcion, idAsignatura;
    private String imagenMedalla;
    private int opacidad;
    private boolean estado;
    private boolean flag = false;
    private int idMedalla;

    boolean seleccionada;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public boolean isSeleccionada() {
        return seleccionada;
    }
    public boolean isFlag() {return flag;}
    public void setFlag(boolean flag) {this.flag = flag;}
    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public int getIdMedalla() {
        return idMedalla;
    }

    public Medalla(int idMedalla, String nombre, String imagenMedalla) {
        this.nombre = nombre;
        this.idMedalla = idMedalla;
        this.imagenMedalla = imagenMedalla;
        this.seleccionada = false;
    }

    public void setIdMedalla(int idMedalla) {this.idMedalla = idMedalla;}
    public void setIdAsignatura(String idAsignatura) {this.idAsignatura = idAsignatura;}
    public String getNombre() {
        return nombre;
    }
    public String getImagenMedalla() {
        return imagenMedalla;
    }

    public int getOpacidad() {
        return opacidad;
    }
    public boolean isEstado() {
        return estado;
    }
}
