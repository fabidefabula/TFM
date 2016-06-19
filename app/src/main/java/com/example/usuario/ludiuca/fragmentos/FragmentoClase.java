package com.example.usuario.ludiuca.fragmentos;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usuario.ludiuca.AlumnoActivity;
import com.example.usuario.ludiuca.ClaseActivity;
import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.TareasActivity;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Profesor;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabio on 11/04/2016.
 */
public class FragmentoClase extends Fragment {
    View rootView;
    Clase claseSeleccionada;
    Profesor profe;
    ListView lvAlumnos;
    ArrayList<Alumno> alumnos;
    Alumno alumnoSeleccionado;
    LinearLayout alumno;
    public FragmentoClase() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_clase, container, false);
        //ClaseActivity actividad = (ClaseActivity)getActivity();
        profe = DatosUsuario.getInstance().getProfesor();
        claseSeleccionada = DatosUsuario.getInstance().getClase();
        alumnos =  claseSeleccionada.getAlumnosClase();
        AdaptadorClase adaptador = new AdaptadorClase(getActivity(), alumnos);
        lvAlumnos = (ListView)rootView.findViewById(R.id.lvAlumnos);
        lvAlumnos.setAdapter(adaptador);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    class AdaptadorClase extends ArrayAdapter<Alumno> {
        ArrayList<Alumno> alumnos;
        public  AdaptadorClase(Context context, ArrayList<Alumno> alumnos){
            super(context, R.layout.lisitem_alumnos, alumnos);
            this.alumnos = alumnos;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.lisitem_alumnos, null);

            TextView lblNombre = (TextView)item.findViewById(R.id.LblNombre);
            lblNombre.setText(alumnos.get(position).getNombre());
            //System.out.println(alumnos.get(position).getNombre());
            TextView lblNick = (TextView)item.findViewById(R.id.LblNick);
            lblNick.setText(alumnos.get(position).getNickName());
            ImageView imagenAlumno = (ImageView)item.findViewById(R.id.imageAlumno);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(alumnos.get(position).getFotoPerfil(), imagenAlumno);
            alumno = (LinearLayout)item.findViewById(R.id.clickAlumno);
            alumno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alumnoSeleccionado = alumnos.get(pos);
                    DatosUsuario.getInstance().setAlumno(alumnoSeleccionado);

                    getActivity().startActivity(new Intent(rootView.getContext(), AlumnoActivity.class));

                }

            });
            return(item);
        }
    }
}
