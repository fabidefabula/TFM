package com.example.usuario.ludiuca.clases;

/**
 * Created by fabio on 21/04/2016.
 */
public class Privilegio {

    private  String nombre, texto;
    private int idPrivilegio;
    private  boolean flag = false;

    public Privilegio(String nombre, String texto, int id) {
        this.nombre = nombre;
        this.texto = texto;
        this.idPrivilegio = id;
    }
    public Privilegio (){}

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getNombre() {return nombre;}
    public void setIdPrivilegio(int idPrivilegio) {this.idPrivilegio = idPrivilegio;}
    public String getTexto() {
        return texto;
    }

    public int getIdPrivilegio() {
        return idPrivilegio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
