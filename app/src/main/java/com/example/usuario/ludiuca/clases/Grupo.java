package com.example.usuario.ludiuca.clases;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Grupo {

    private String nombre;
    private Clase clase;
    private String fotoGrupo;
     ArrayList<Alumno> alumnosGrupo = new ArrayList<>();
    final ArrayList<Tarea> tareasGrupo= new ArrayList<>();

    public Grupo(Clase clase, String nombre, String fotoGrupo) {
        this.clase = clase;
        this.nombre = nombre;
        this.fotoGrupo = fotoGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFotoGrupo() {
        return fotoGrupo;
    }

    public void setAlumnosGrupo(ArrayList<Alumno> alumnosGrupo) {
        this.alumnosGrupo = alumnosGrupo;
    }

    public ArrayList<Alumno> getAlumnosGrupo() {
        return alumnosGrupo;
    }
}
