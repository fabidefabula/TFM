package com.example.usuario.ludiuca.clases;

import android.nfc.FormatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Fecha {
    private String fechaString;
    private Date fecha;

    public Fecha(String fecha) {
        this.fechaString= fecha;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date= format.parse(fecha);
            this.fecha = date;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Fecha(Date fecha){
        this.fecha=fecha;
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
            fecha.setMonth(fecha.getMonth()+1);
            String convertido = fechaHora.format(fecha);
            convertido = convertido.substring(0,5)+"0"+convertido.substring(5,8);
            System.out.println(convertido);
        this.fechaString = convertido;
    }

    public String getFechaString() {return fechaString;}

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }

    public Date getFecha() {
        return fecha;
    }
}
