package com.example.usuario.ludiuca.clases;

/**
 * Created by User on 07/03/2017.
 */

public class Imagen {
    int image;
    String name;
    //Alumno alumno;


    public Imagen (int i, String n){
        this.image = i;
        this.name = n;
       // this.alumno = a;
    }

    public Imagen(){
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

