package com.example.usuario.ludiuca.clases;

/**
 * Created by fabio on 21/04/2016.
 */
public class Privilegio {

    String nombre, texto, idPrivilegio;

    public Privilegio(String nombre, String texto) {
        this.nombre = nombre;
        this.texto = texto;
    }

    public String getNombre() {return nombre;}
    public void setIdPrivilegio(String idPrivilegio) {this.idPrivilegio = idPrivilegio;}
    public String getTexto() {
        return texto;
    }
}
