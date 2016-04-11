package com.example.usuario.ludiuca.clases;

/**
 * Created by fabio on 30/03/2016.
 */
public class DatosUsuario {

    private static DatosUsuario mInstance = null;

   private Profesor profesor;

    private DatosUsuario(){


    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public static DatosUsuario getInstance(){
        if(mInstance == null)
        {

            mInstance = new DatosUsuario();
        }
        return mInstance;
    }


}


