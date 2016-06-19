package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usuario.ludiuca.ClaseActivity;
import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.TareasActivity;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Tarea;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fabio on 30/03/2016.
 */
public class FragmentoTareas extends Fragment {

    View rootView;
    ListView lvTareas;
    ArrayList<Tarea> tareasClase;
    Clase claseSeleccionada;
    StringBuffer fechaCreacion = new StringBuffer();


public FragmentoTareas(){

}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_tareas, container, false);

        claseSeleccionada = DatosUsuario.getInstance().getClase();
        tareasClase = claseSeleccionada.getTareasClase();
        //System.out.println(tareasClase.get(0).getDescripcion());
      //  TareasActivity actividad = (TareasActivity)getActivity();
        AdaptadorTareas adaptador = new AdaptadorTareas(getActivity(), tareasClase);
       lvTareas = (ListView)rootView.findViewById(R.id.lvTareas);

       lvTareas.setAdapter(adaptador);

        return rootView;
    }

    class AdaptadorTareas extends ArrayAdapter<Tarea>{
        ArrayList<Tarea> tareas;
     public  AdaptadorTareas(Context context, ArrayList<Tarea> tareas){
         super(context, R.layout.listitem_tareas, tareas);
         this.tareas = tareas;

     }
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_tareas, null);

           TextView lblDescription = (TextView)item.findViewById(R.id.LblDescription);
           lblDescription.setText(tareasClase.get(pos).getDescription());
            TextView lblFechaCreacion = (TextView)item.findViewById(R.id.LblFechaCreacion);
            TextView lblFechaEntrega = (TextView)item.findViewById(R.id.LblFechaEntrega);
            //arreglar
            lblFechaCreacion.setText("Fecha creación: " + tareasClase.get(pos).getFechaCreacionString());
            lblFechaEntrega.setText("Fecha entrega: " + tareasClase.get(pos).getFechaEntregaString());
//
//            TextView lblCurso = (TextView)item.findViewById(R.id.LblCurso);
//            lblCurso.setText(String.valueOf(clases.get(position).getCurso()));

//            tarea = (LinearLayout)item.findViewById(R.id.clickTarea);
//            tarea.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    claseSeleccionada = clases.get(pos);
//                    DatosUsuario.getInstance().setClase(claseSeleccionada);
//                    FragmentoTareas.this.getActivity().startActivity(new Intent(rootView.getContext(), TareasActivity.class));
//
//                }
//
//            });
//            opciones = (LinearLayout)item.findViewById(R.id.clickOptions);
//            opciones.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    claseSeleccionada = clases.get(pos);
//                    DatosUsuario.getInstance().setClase(claseSeleccionada);
//                    showPopup(v, claseSeleccionada);
//                }
//            });

           // ImageView imagenClase = (ImageView)item.findViewById(R.id.imageClass);
            //imagenClase.setImageResource();

            return(item);
        }

    }

//    public void showPopup(View v, final Clase clase) {
//        PopupMenu popup = new PopupMenu(rootView.getContext(), v);
//        popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem arg0) {
//                int id = arg0.getItemId();
//
//                if (id == R.id.ver_alumnos) {
//                    getActivity().startActivity(new Intent(rootView.getContext(), ClaseActivity.class));
//                }
//
//                return true;
//            }
//        });
//        popup.inflate(R.menu.menu_clase);
//        popup.show();
//    }

}


