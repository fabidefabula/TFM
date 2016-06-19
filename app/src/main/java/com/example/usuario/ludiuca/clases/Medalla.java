package com.example.usuario.ludiuca.clases;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Medalla {
    private String nombre;
    private String descripcion, idMedalla, idAsignatura;
    private String imagenMedalla;
    private int opacidad;
    private boolean estado;

    boolean seleccionada;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public Medalla(String nombre, String imagenMedalla) {
        this.nombre = nombre;

        this.imagenMedalla = imagenMedalla;
        this.seleccionada = false;
    }

    public void setIdMedalla(String idMedalla) {this.idMedalla = idMedalla;}
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
