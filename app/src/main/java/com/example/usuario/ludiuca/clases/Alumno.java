package com.example.usuario.ludiuca.clases;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Alumno extends Entidad {
    private String nombre, idAlumno, apellidos, nickName, fotoPerfil;
    private int exp;
    private int level;
    private int monedas;
    private ArrayList<Medalla> medallasAlumno = new ArrayList<>();
    private ArrayList<Clase> clasesAlumno = new ArrayList<>();
    private ArrayList<Grupo> gruposAlumno = new ArrayList<>();
    private ArrayList<Privilegio> privilegiosAlumno = new ArrayList<>();


    public Alumno (String nombre, String apellidos){
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    public String getNombre() {
        return nombre;
    }



    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNickName() {
        return nickName;
    }
    public String getFotoPerfil(){ return fotoPerfil;}

    public String getApellidos() {
        return apellidos;
    }

    public int getExp() {
        return exp;
    }

    public int getMonedas() {
        return monedas;
    }

    public int getLevel() {
        return level;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Medalla> getMedallasAlumno() {
        return medallasAlumno;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof Alumno) {
            Alumno p = (Alumno)o;
            return this.idAlumno.equals(p.idAlumno);
        } else {
            return false;
        }
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void setMedallasAlumno(ArrayList<Medalla> medallasAlumno) {
        this.medallasAlumno = medallasAlumno;
    }

    public ArrayList<Privilegio> getPrivilegiosAlumno() {
        return privilegiosAlumno;
    }

    public void setPrivilegiosAlumno(ArrayList<Privilegio> privilegiosAlumno) {
        this.privilegiosAlumno = privilegiosAlumno;
    }
}
