package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Privilegio;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


/**
 * Created by fabio on 22/03/2016.
 */
public class FragmentoPerfilAlumno extends Fragment {

    View rootView;
    //Profesor profe;
    Alumno alumno;
    GridView lvMedallas;
    ListView lvlPrivilegios;
    int numNiveles = 20;
    int expPerLevel = 1000;
    ArrayList<Medalla> medallas;
    ArrayList<Privilegio> privilegios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_perfil_alumno, container, false);
        alumno = DatosUsuario.getInstance().getAlumno();

        Button botonChange = (Button)rootView.findViewById(R.id.b_change);
        TextView name = (TextView)rootView.findViewById(R.id.tv_nombre);
        TextView level = (TextView)rootView.findViewById(R.id.tv_level);
        TextView exp= (TextView)rootView.findViewById(R.id.tv_exp);
        TextView nick = (TextView)rootView.findViewById(R.id.tv_nick);
        ImageView imagenPerfil = (ImageView)rootView.findViewById(R.id.img_perfil);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(alumno.getFotoPerfil(), imagenPerfil);
        ProgressBar barraNivel = (ProgressBar)rootView.findViewById(R.id.progressBar);
        barraNivel.setProgress(alumno.getLevel() * 100 / numNiveles);
        barraNivel.setSecondaryProgress((alumno.getExp()) * 100 / (alumno.getLevel() * expPerLevel));
        name.setText(name.getText() + alumno.getNombre());
        level.setText(level.getText() + String.valueOf(alumno.getLevel()));
        exp.setText(exp.getText() + String.valueOf(alumno.getExp()));
        nick.setText(nick.getText() + String.valueOf(alumno.getNickName()));


        medallas  = alumno.getMedallasAlumno();
        privilegios = alumno.getPrivilegiosAlumno();

        AdaptadorMedallas adaptadorM = new AdaptadorMedallas(getActivity(), medallas);
        lvMedallas = (GridView)rootView.findViewById(R.id.gvMedallas);
        lvMedallas.setAdapter(adaptadorM);

        AdaptadorPrivilegios adaptadorP =  new AdaptadorPrivilegios(getActivity(), privilegios);
        lvlPrivilegios = (ListView)rootView.findViewById(R.id.lvPrivilegios);
        lvlPrivilegios.setAdapter(adaptadorP);

        botonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse


            }
        });

        return rootView;
    }

    class AdaptadorMedallas extends ArrayAdapter<Medalla> {
        ArrayList<Medalla> medallas;

        public  AdaptadorMedallas(Context context, ArrayList<Medalla> medallas){
            super(context, R.layout.listitem_medallas, medallas);
            this.medallas = medallas;

        }
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_medallas, null);

            TextView lblMedalla = (TextView)item.findViewById(R.id.LblNombre);
            lblMedalla.setText(String.valueOf((medallas.get(position).getNombre())));
            ImageView imagenMedalla = (ImageView)item.findViewById(R.id.imageMedalla);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(medallas.get(position).getImagenMedalla(), imagenMedalla);

            return(item);
        }

    }



    class AdaptadorPrivilegios extends ArrayAdapter<Privilegio> {
        ArrayList<Privilegio> privilegios;

        public  AdaptadorPrivilegios(Context context, ArrayList<Privilegio> privilegios){
            super(context, R.layout.listitem_privilegios, privilegios);
            this.privilegios = privilegios;

        }
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_privilegios, null);

            TextView lblNombrePrivilegio= (TextView)item.findViewById(R.id.LblNombre);
            lblNombrePrivilegio.setText(String.valueOf((privilegios.get(position).getNombre())));
            TextView lblTextoPrivilegio= (TextView)item.findViewById(R.id.LblTexto);
            lblTextoPrivilegio.setText(String.valueOf((privilegios.get(position).getTexto())));

            return(item);
        }

    }

}







