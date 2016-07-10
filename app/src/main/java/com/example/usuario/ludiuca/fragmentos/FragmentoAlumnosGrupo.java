package com.example.usuario.ludiuca.fragmentos;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.usuario.ludiuca.AlumnosGrupoActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Grupo;
import com.example.usuario.ludiuca.clases.Profesor;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by fabio on 11/04/2016.
 */
public class FragmentoAlumnosGrupo extends Fragment {
    View rootView;
    Grupo grupoSeleccionado;
    ListView lvAlumnos;
    ArrayList<Alumno> alumnosGrupoSeleccionado;
    Alumno alumnoSeleccionado;
    LinearLayout alumno;

    public FragmentoAlumnosGrupo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_alumnos_grupo, container, false);
        grupoSeleccionado = DatosUsuario.getInstance().getGrupoSeleccionado();
        alumnosGrupoSeleccionado = grupoSeleccionado.getAlumnosGrupo();
        AdaptadorAlumnosGrupo adaptador = new AdaptadorAlumnosGrupo(getActivity(), alumnosGrupoSeleccionado);
        lvAlumnos = (ListView)rootView.findViewById(R.id.lvAlumnosGrupo);
        lvAlumnos.setAdapter(adaptador);
        //return super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    class AdaptadorAlumnosGrupo extends ArrayAdapter<Alumno> {
        ArrayList<Alumno> alumnos;
        public  AdaptadorAlumnosGrupo(Context context, ArrayList<Alumno> a){
            super(context, R.layout.lisitem_alumnos_grupo, a);
            this.alumnos = a;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.lisitem_alumnos_grupo, null);
            TextView lblNombre = (TextView)item.findViewById(R.id.LblNombre);
            lblNombre.setText(alumnos.get(position).getNombre());
            //System.out.println(alumnos.get(position).getNombre());
            TextView lblNick = (TextView)item.findViewById(R.id.LblNick);
            lblNick.setText(alumnos.get(position).getNickName());
            ImageView imagenAlumno = (ImageView)item.findViewById(R.id.imageAlumno);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(alumnos.get(position).getFotoPerfil(), imagenAlumno);
            alumno = (LinearLayout)item.findViewById(R.id.clickAlumnoGrupo);
            alumno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alumnoSeleccionado = alumnos.get(pos);
                    DatosUsuario.getInstance().setAlumno(alumnoSeleccionado);
                    FragmentoAlumnosGrupo.this.getActivity().startActivity(new Intent(rootView.getContext(), AlumnoActivity.class));

                }

            });
            return(item);
        }
    }
}
