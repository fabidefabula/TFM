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
    private int idGrupoInsertado;
    private ArrayList<Avatar> avataresProfe;
    private ArrayList<Avatar> avataresAlumnos;
    private ArrayList<Avatar> avataresGrupos;
    private ArrayList<Actitud> actitudesBuenas = new ArrayList<>();
    private ArrayList<Actitud> actitudesMalas = new ArrayList<>();
    private ArrayList<Privilegio> privilegios = new ArrayList<>();
    private Actitud actitudElegida = new Actitud();

    //actitudes
    public ArrayList<Actitud> getActitudesBuenas() {return actitudesBuenas;}
    public ArrayList<Actitud> getActitudesMalas() {return actitudesMalas;}
    public void setActitudesMalas(ArrayList<Actitud> actitudesMalas) {this.actitudesMalas = actitudesMalas;}
    public void setActitudesBuenas(ArrayList<Actitud> actitudesBuenas) {this.actitudesBuenas = actitudesBuenas;}

    public int getIdGrupoInsertado() {
        return idGrupoInsertado;
    }

    public void setIdGrupoInsertado(int idGrupoInsertado) {
        this.idGrupoInsertado = idGrupoInsertado;
    }

    public Actitud getActitudElegida() {return actitudElegida;}
    public void setActitudElegida(Actitud actitudElegida) {this.actitudElegida = actitudElegida;}


    public ArrayList<Avatar> getAvataresGrupos() {
        return avataresGrupos;
    }

    public void setAvataresGrupos(ArrayList<Avatar> avataresGrupos) {
        this.avataresGrupos = avataresGrupos;
    }

    public ArrayList<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(ArrayList<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }

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


