package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Fecha;
import com.example.usuario.ludiuca.clases.Notificacion;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fabio on 26/03/2016.
 */
public class FragmentoNotificaciones extends Fragment{

    View rootView;
    ArrayList<Notificacion> notificaciones = new ArrayList<>();
    HashMap<Fecha, Notificacion> notificacionesHash;
    ListView lvNotificaciones;
    TextView fecha;

    public FragmentoNotificaciones(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_notificaciones, container, false);
        final Clase clase = DatosUsuario.getInstance().getClase();

        lvNotificaciones =(ListView)rootView.findViewById(R.id.lvNot);
        fecha = (TextView)rootView.findViewById(R.id.tv_date);
        notificaciones = clase.getNotificacionArray();
        notificacionesHash = clase.getNotificacionesClase();

        CalendarView calendario = (CalendarView)rootView.findViewById(R.id.calendar);
        //AdaptadorNotificaciones adaptador = new AdaptadorNotificaciones(getActivity(), notificaciones);
      //  lvNotificaciones.setAdapter(adaptador);

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
        @Override
         public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
             //coge la notificacion que corresponde de esa fecha del profesor
              ArrayList<Notificacion> notificacionesFecha;
             month++;
            Fecha fechaSeleccionada = new Fecha(year +"-"+ month + "-" + dayOfMonth);

            String mesBueno = (("" + month).toString().length() > 1) ? "-" + month : ("-0" + month);
            String diaBueno = (("" + dayOfMonth).toString().length() > 1) ? "-" + dayOfMonth : ("-0" + dayOfMonth);
            fechaSeleccionada.setFechaString(""+year + mesBueno + diaBueno);

             System.out.println(fechaSeleccionada.getFechaString());
            try{
                clase.getNotificationsByDate(fechaSeleccionada);

            }catch (Exception e){
                e.printStackTrace();
            }

            fecha.setText(fechaSeleccionada.getFechaString());
            AdaptadorNotificaciones adaptador1 = new AdaptadorNotificaciones(getActivity(), clase.getArrayNotificacionesByDate());
            lvNotificaciones.setAdapter(adaptador1);
         }
        });

        return rootView;
        }
    class AdaptadorNotificaciones extends ArrayAdapter<Notificacion> {
        ArrayList<Notificacion> notificaciones;
        public  AdaptadorNotificaciones(Context context, ArrayList<Notificacion> notificaciones){
            super(context, R.layout.listitem_notificaciones, notificaciones);
            this.notificaciones = notificaciones;

        }
        public View getView(int position, View convertView, ViewGroup parent) {
            int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_notificaciones, null);

            TextView lblDescription = (TextView)item.findViewById(R.id.LblDescription);
            lblDescription.setText(notificaciones.get(pos).getDescription());
            ImageView emoji = (ImageView)item.findViewById(R.id.notificacionEmoji);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(notificaciones.get(pos).getEmoji(), emoji);

            return(item);
        }

    }



}
