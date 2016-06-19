package com.example.usuario.ludiuca.clases;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Tarea {
    private Date fechaEntrega;
    private Date fechaCreacion;
    private String fechaEntregaString;
    private String fechaCreacionString;
    private String description;

    public String getDescription() {
        return description;
    }
    public Date getFechaEntrega() {
        return fechaEntrega;
    }
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    public String getFechaEntregaString() {return fechaEntregaString;}
    public String getFechaCreacionString() {return fechaCreacionString;}

    public Tarea(String description, String fechaCreacion, String fechaEntrega){
        this.fechaCreacionString = fechaCreacion;
        this.fechaEntregaString = fechaEntrega;
        this.description = description;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateCreation = format.parse(fechaCreacion);
            this.fechaCreacion = dateCreation;
            Date dateFinish = format.parse(fechaEntrega);
            this.fechaEntrega = dateFinish;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
