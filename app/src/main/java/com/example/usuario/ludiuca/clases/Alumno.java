package com.example.usuario.ludiuca.clases;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Alumno extends Entidad {
    private String nombre;
    private String apellidos;
    private String nickName;
    private int exp;
    private int level;
    private int monedas;
    private String fotoPerfil;
    final ArrayList<Medalla> medallasAlumno = new ArrayList<>();
    final ArrayList<Clase> clasesAlumno = new ArrayList<>();
    final ArrayList<Grupo> grupossAlumno = new ArrayList<>();


}
