package com.example.usuario.ludiuca.clases;


import android.app.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Usuario on 17/02/2016.
 */


public class Notificacion {
    private String nombre;
    private String emoji;  //quiza mejor un número
    private Fecha fecha;

    private String description;
    private int id;

    public Notificacion(String description, String fecha, int idNotificacion) {
        this.fecha = new Fecha(fecha);
        this.description = description;
        id = idNotificacion;

    }
    public Notificacion(){}

    public int getId() {return id;}
    public String getDescription() {return description;}
    public Fecha getFecha() {return fecha;}

}
