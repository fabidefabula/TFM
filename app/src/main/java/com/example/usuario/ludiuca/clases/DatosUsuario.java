package com.example.usuario.ludiuca.clases;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by fabio on 30/03/2016.
 */
public class DatosUsuario {

    private static DatosUsuario mInstance = null;

   private Profesor profesor;
    private Clase claseSeleccionada;
    private Alumno alumnoSeleccionado;
    private ArrayList<Medalla> listaMedallasAlumnos;
    private ArrayList<Medalla> listaMedallasProfesor;
    private DatosUsuario(){}
    private Grupo grupoSeleccionado;
    private ArrayList<Avatar> avataresProfe;
    private ArrayList<Avatar> avataresAlumnos;


    public ArrayList<Avatar> getAvataresProfe() {return avataresProfe;}
    public ArrayList<Avatar> getAvataresAlumnos() {return avataresAlumnos;}
    public Profesor getProfesor() {return profesor;}
    public void setProfesor(Profesor profesor) {this.profesor = profesor;}
    public Clase getClase(){return claseSeleccionada;}
    public void setClase(Clase clase){claseSeleccionada = clase;}
    public void setAlumno(Alumno alumno){alumnoSeleccionado =alumno;}
    public void setListaMedallasAlumnos(ArrayList<Medalla> listaMedallasAlumnos){this.listaMedallasAlumnos = listaMedallasAlumnos;}
    public void setAvataresAlumnos(ArrayList<Avatar> avataresAlumnos) {this.avataresAlumnos = avataresAlumnos;}
    public ArrayList<Medalla> getListaMedallasAlumnos() {
        return listaMedallasAlumnos;
    }
    public ArrayList<Medalla> getListaMedallasProfesor() {return listaMedallasProfesor;}
    public void setListaMedallasProfesor(ArrayList<Medalla> listaMedallasProfesor) {this.listaMedallasProfesor = listaMedallasProfesor;}
    public Alumno getAlumno(){return alumnoSeleccionado;}
    public static DatosUsuario getInstance(){
        if(mInstance == null)
        {
            mInstance = new DatosUsuario();
        }
        return mInstance;
    }

    public void setGrupoSeleccionado(Grupo grupoSeleccionado) {this.grupoSeleccionado = grupoSeleccionado;}
    public void setAvataresProfe(ArrayList<Avatar> avataresProfe) {this.avataresProfe = avataresProfe;}
    public Grupo getGrupoSeleccionado() {
        return grupoSeleccionado;
    }
}


