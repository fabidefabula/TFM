package com.example.usuario.ludiuca.fragmentos;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Avatar;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Profesor;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabio on 11/04/2016.
 */
public class FragmentoDarMedalla extends Fragment {
    View rootView;
    Button buttonOk;
    // ListView gvMedallas;
    Alumno alumno;
    ArrayList<Medalla> medallas;
    Medalla elegida;
    ListView lvMedallas;
    int posicionElegido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_dar_medalla, container, false);
        buttonOk = (Button) rootView.findViewById(R.id.bOk);
        medallas = DatosUsuario.getInstance().getListaMedallasAlumnos();
        AdaptadorDarMedallaAlumno adaptador = new AdaptadorDarMedallaAlumno(getActivity(), medallas );

        lvMedallas = (ListView) rootView.findViewById(R.id.lvDarMedallas);
        lvMedallas.setAdapter(adaptador);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });

        return rootView;
    }

    class AdaptadorDarMedallaAlumno extends ArrayAdapter<Medalla> {
        ArrayList<Medalla> medallas;
        public  AdaptadorDarMedallaAlumno(Context context, ArrayList<Medalla> medallas){
            super(context, R.layout.listitem_dar_medallas, medallas);
            this.medallas = medallas;

        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_dar_medallas, null);
            TextView nombre = (TextView)item.findViewById(R.id.LblNombreMedalla);
            nombre.setText(medallas.get(position).getNombre());
            final ImageView medalla = (ImageView)item.findViewById(R.id.imageMedalla);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(medallas.get(position).getImagenMedalla(), medalla);
            if(position != posicionElegido){
                item.setBackgroundColor(Color.WHITE);
            }else{
                item.setBackgroundColor(Color.GRAY);
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < medallas.size(); i++){
                        if(medallas.get(i).isFlag()==false && i == position){
                            medallas.get(position).setFlag(true);
                            posicionElegido = i;
                        }else{
                            medallas.get(i).setFlag(false);

                        }
                    }
                    System.out.println("click en la posición" + " " + position);
                    elegida = new Medalla(medallas.get(position).getIdMedalla(), medallas.get(position).getNombre(), medallas.get(position).getImagenMedalla());
                    v.setBackgroundColor(Color.GRAY);
                    ((BaseAdapter) lvMedallas.getAdapter()).notifyDataSetChanged();

                }
            });


            return(item);
        }

    }



}
