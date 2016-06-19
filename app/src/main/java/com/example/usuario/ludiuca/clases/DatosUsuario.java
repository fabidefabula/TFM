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
    private ArrayList<Medalla> listaMedallas;
    private DatosUsuario(){}
    private Grupo grupoSeleccionado;


    public Profesor getProfesor() {return profesor;}
    public void setProfesor(Profesor profesor) {this.profesor = profesor;}
    public Clase getClase(){return claseSeleccionada;}
    public void setClase(Clase clase){claseSeleccionada = clase;}
    public void setAlumno(Alumno alumno){alumnoSeleccionado =alumno;}
    public void setListaMedallas(ArrayList<Medalla> listaMedallas){this.listaMedallas = listaMedallas;}

    public ArrayList<Medalla> getListaMedallas() {
        return listaMedallas;
    }

    public Alumno getAlumno(){return alumnoSeleccionado;}
    public static DatosUsuario getInstance(){
        if(mInstance == null)
        {
            mInstance = new DatosUsuario();
        }
        return mInstance;
    }

    public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public Grupo getGrupoSeleccionado() {
        return grupoSeleccionado;
    }
}


