package com.example.usuario.ludiuca.clases;

import android.app.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Usuario on 17/02/2016.
 */

public class Profesor extends Entidad {
    private String nombre;
    private String apellidos;
    private String nickName;
    private int exp;
    private int level;
    private int id;
    private String fotoPerfil, idProfesor;
    private ArrayList<Clase> clasesProfe = new ArrayList<>();
    private ArrayList<Medalla> medallasProfe = new ArrayList<>();

    public Profesor(String n, String a) {
        nombre = n;
        apellidos = a;
    }

    public Profesor() {
    }

    public void setId(int id) {this.id = id;}

    public int getId() {return id;}

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setExp(int exp) {this.exp = exp;}
    public String getIdProfesor() {return idProfesor;}
    public void setIdProfesor(String idProfesor) {this.idProfesor = idProfesor;}
    public void setLevel(int level) {this.level = level;}
    public void setMedallasProfe(ArrayList<Medalla> medallas){medallasProfe = medallas;}
    public void setFotoPerfil(String fotoPerfil) {this.fotoPerfil = fotoPerfil;}
    public void setClasesProfe(ArrayList<Clase> clasesProfe) {
        this.clasesProfe = clasesProfe;
    }
    public String getNombre() {return nombre;}
    public String getApellidos() {return apellidos;}
    public String getNickName() {return nickName;}
    public ArrayList<Clase> getClasesProfe() {return clasesProfe;}
    public Clase getClaseProfe(int position) {return clasesProfe.get(position);}
    public int getExp() {return exp;}
    public int getLevel() {return level;}
    public String getFotoPerfil() {
        return fotoPerfil;
    }
    public ArrayList<Medalla> getMedallasProfe() {return medallasProfe;}

}

