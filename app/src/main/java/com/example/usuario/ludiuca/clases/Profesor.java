package com.example.usuario.ludiuca.clases;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/02/2016.
 */

public class Profesor extends Entidad{
    private String nombre;
    private String apellidos;
    private String nickName;
    private int exp;
    private int level;
    private String fotoPerfil;
    private ArrayList<Clase> clasesProfe = new ArrayList<>();
    final ArrayList<Medalla> medallasProfe = new ArrayList<>();

    public Profesor (String n, String a){
        nombre = n;
        apellidos = a;
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

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void setClasesProfe(ArrayList<Clase> clasesProfe) {
        this.clasesProfe = clasesProfe;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNickName() {
        return nickName;
    }

    public ArrayList<Clase> getClasesProfe() {
        return clasesProfe;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public ArrayList<Medalla> getMedallasProfe() {
        return medallasProfe;
    }
}
