package com.example.usuario.ludiuca.fragmentos;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.ludiuca.AltaActitudActivity;
import com.example.usuario.ludiuca.AlumnosActitudesActivity;
import com.example.usuario.ludiuca.CambiarAvatarProfeActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Actitud;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Webservice;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fabio on 11/04/2016.
 */
public class FragmentoActitudes extends Fragment {
    View rootView;
    Button bValorar;
    // ListView gvMedallas;
    Alumno alumno;
    Actitud elegida;
    ArrayList<Actitud> actitudesBuenas;
    ArrayList<Actitud> actitudesMalas;

    ListView lvBuenas, lvMalas;
    int posicionElegido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_actitudes, container, false);
        bValorar = (Button) rootView.findViewById(R.id.bValorar);
        actitudesBuenas = DatosUsuario.getInstance().getActitudesBuenas();
        actitudesMalas = DatosUsuario.getInstance().getActitudesMalas();
        elegida =actitudesBuenas.get(0);
        DatosUsuario.getInstance().setActitudElegida(actitudesBuenas.get(0));
        AdaptadorActitudes adaptadorBuenas = new AdaptadorActitudes(getActivity(), actitudesBuenas);
        AdaptadorActitudes adaptadorMalas = new AdaptadorActitudes(getActivity(), actitudesMalas);
        FloatingActionButton bAltaActitud = (FloatingActionButton)rootView.findViewById(R.id.fab);
        bAltaActitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoActitudes.this.getActivity().startActivity(new Intent(rootView.getContext(), AltaActitudActivity.class));

            }
        });

        lvBuenas = (ListView) rootView.findViewById(R.id.lvActitudesBuenas);
        lvMalas = (ListView) rootView.findViewById(R.id.lvActitudesMalas);
        lvBuenas.setAdapter(adaptadorBuenas);
        lvMalas.setAdapter(adaptadorMalas);

        bValorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(rootView.getContext(), AlumnosActitudesActivity.class));


            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        actitudesBuenas = DatosUsuario.getInstance().getActitudesBuenas();
        actitudesMalas = DatosUsuario.getInstance().getActitudesMalas();
        AdaptadorActitudes adaptadorBuenas = new AdaptadorActitudes(getActivity(), actitudesBuenas);
        AdaptadorActitudes adaptadorMalas = new AdaptadorActitudes(getActivity(), actitudesMalas);
        lvBuenas.setAdapter(adaptadorBuenas);
        lvMalas.setAdapter(adaptadorMalas);

        super.onStart();
    }

    class AdaptadorActitudes extends ArrayAdapter<Actitud> {
        ArrayList<Actitud> actitudes;
        public  AdaptadorActitudes(Context context, ArrayList<Actitud> actitudes){
            super(context, R.layout.lisitem_actitudes, actitudes);
            this.actitudes = actitudes;

        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.lisitem_actitudes, null);
            TextView nombre = (TextView)item.findViewById(R.id.LblNombre);
            TextView exp = (TextView)item.findViewById(R.id.LblExp);
            nombre.setText(actitudes.get(position).getNombre());
            if(actitudes.get(position).getTipo()==1){
                exp.setText(exp.getText() +" "+ actitudes.get(position).getPuntos());
            }else{
                exp.setText(exp.getText() +" -"+ actitudes.get(position).getPuntos());
            }


            if(position != posicionElegido && actitudes.get(position).getTipo()==1){
                item.setBackgroundColor(0xcafbc8);
            } if(position != posicionElegido && actitudes.get(position).getTipo()==2) {
                item.setBackgroundColor(0xfbc2c2);
            }if(position==posicionElegido && elegida.getTipo()==actitudes.get(position).getTipo()){
                item.setBackgroundColor(Color.GRAY);
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < actitudes.size(); i++) {
                        if (actitudes.get(i).isFlag() == false && i == position) {
                            actitudes.get(position).setFlag(true);
                            posicionElegido = i;
                        } else {
                            actitudes.get(i).setFlag(false);

                        }
                    }
                    System.out.println("click en la posición" + " " + position);
                    elegida = actitudes.get(position);
                    DatosUsuario.getInstance().setActitudElegida(actitudes.get(position));
                    v.setBackgroundColor(Color.GRAY);
                    ((BaseAdapter) lvBuenas.getAdapter()).notifyDataSetChanged();
                    ((BaseAdapter) lvMalas.getAdapter()).notifyDataSetChanged();


                }
            });


            return(item);
        }

    }

}
