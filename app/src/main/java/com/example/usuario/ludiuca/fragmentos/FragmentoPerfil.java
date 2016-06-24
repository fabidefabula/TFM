package com.example.usuario.ludiuca.fragmentos;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.ludiuca.AltaTareaActivity;
import com.example.usuario.ludiuca.CambiarAvatarProfeActivity;
import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Avatar;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Tarea;
import com.example.usuario.ludiuca.clases.Webservice;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by fabio on 22/03/2016.
 */
public class FragmentoPerfil extends Fragment {

    View rootView;
    Profesor profe;
    int numNiveles = 20;
    int expPerLevel = 1000;
    ArrayList<Medalla> medallasProfe;
    GridView lvMedallas;

    @Override
    public void onStart() {
        ImageView imagenPerfil = (ImageView) rootView.findViewById(R.id.img_perfil);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(profe.getFotoPerfil(), imagenPerfil);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_perfil, container, false);
        profe = DatosUsuario.getInstance().getProfesor();
        Button botonChange = (Button) rootView.findViewById(R.id.b_change);
        TextView name = (TextView) rootView.findViewById(R.id.tv_nombre);
        TextView level = (TextView) rootView.findViewById(R.id.tv_level);
        TextView exp = (TextView) rootView.findViewById(R.id.tv_exp);
        TextView nick = (TextView) rootView.findViewById(R.id.tv_nick);
        medallasProfe = profe.getMedallasProfe();

        AdaptadorMedallasProfe adaptador = new AdaptadorMedallasProfe(getActivity(), medallasProfe);
        lvMedallas = (GridView) rootView.findViewById(R.id.gvMedallasProfe);
        lvMedallas.setAdapter(adaptador);

        ImageView imagenPerfil = (ImageView) rootView.findViewById(R.id.img_perfil);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(profe.getFotoPerfil(), imagenPerfil);
        ProgressBar barraNivel = (ProgressBar) rootView.findViewById(R.id.progressBar);
        barraNivel.setProgress(profe.getLevel() * 100 / numNiveles);
        barraNivel.setSecondaryProgress((profe.getExp()) * 100 / (profe.getLevel() * expPerLevel));

        name.setText(name.getText() + profe.getNombre());
        level.setText(level.getText() + String.valueOf(profe.getLevel()));
        exp.setText(exp.getText() + String.valueOf(profe.getExp()));
        nick.setText(nick.getText() + String.valueOf(profe.getNickName()));


        botonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoPerfil.this.getActivity().startActivity(new Intent(rootView.getContext(), CambiarAvatarProfeActivity.class));

            }
        });

        return rootView;
    }


    class AdaptadorMedallasProfe extends ArrayAdapter<Medalla> {
        ArrayList<Medalla> medallas;

        public AdaptadorMedallasProfe(Context context, ArrayList<Medalla> medallas) {
            super(context, R.layout.listitem_medallas_profe, medallas);
            this.medallas = medallas;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_medallas_profe, null);

            TextView lblMedalla = (TextView) item.findViewById(R.id.LblNombremp);
            lblMedalla.setText(String.valueOf((medallas.get(position).getNombre())));
            ImageView imagenMedalla = (ImageView) item.findViewById(R.id.imageMedallap);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(medallas.get(pos).getImagenMedalla(), imagenMedalla);

            return (item);
        }


    }


}












