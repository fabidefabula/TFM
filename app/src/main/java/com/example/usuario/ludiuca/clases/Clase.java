package com.example.usuario.ludiuca.clases;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Clase {
    int asignatura;
    int curso;
    String imagenCurso;
    final ArrayList<Alumno> alumnosClase = new ArrayList<>();
    final ArrayList<Tarea> tareasClase= new ArrayList<>();
    final ArrayList<Actitud> actitudesClase= new ArrayList<>();

    public Clase(int asignatura,  int curso, String imagenCurso) {
        this.asignatura = asignatura;
        this.curso = curso;
        this.imagenCurso = imagenCurso;
    }

    public int getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(int asignatura) {
        this.asignatura = asignatura;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }
}
