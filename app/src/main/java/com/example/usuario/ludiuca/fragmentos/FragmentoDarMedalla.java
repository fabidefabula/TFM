package com.example.usuario.ludiuca.fragmentos;


import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Medalla;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by fabio on 11/04/2016.
 */
public class FragmentoDarMedalla extends Fragment {
    View rootView;
    Button buttonOk;
   // ListView gvMedallas;
    RecyclerView rvMedallas;
    Alumno alumno;
    ArrayList<Medalla> medallas;
    public FragmentoDarMedalla() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_dar_medalla, container, false);
        //ClaseActivity actividad = (ClaseActivity)getActivity();
        alumno = DatosUsuario.getInstance().getAlumno();
        //Profesor profe = DatosUsuario.getInstance().getProfesor();
        medallas = DatosUsuario.getInstance().getListaMedallas();
        final AdaptadorMedallas2 adaptadorDarMedallas = new AdaptadorMedallas2( medallas);

        //final AdaptadorMedallas adaptadorDarMedallas = new AdaptadorMedallas(getActivity(), medallas);
        rvMedallas = (RecyclerView)rootView.findViewById(R.id.rvDarMedallas);
        rvMedallas.setHasFixedSize(true);

        //gvMedallas.setAdapter(adaptadorDarMedallas);
        // gvMedallas.setAdapter(adaptadorDarMedallas);

        rvMedallas.setAdapter(adaptadorDarMedallas);
        rvMedallas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        buttonOk = (Button) rootView.findViewById(R.id.bOk);

       // gvMedallas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        gvMedallas.setFocusable(false);
//        gvMedallas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //tendria que haber un super catalogo de medallas y coger de aqui la posicion para saber en cual se ha clicado
//                //Medalla medallaSeleccionada;
//
//                for (Medalla m : medallas) {
//                    m.setSeleccionada(false);
//                }
//                Medalla temp = medallas.get(position);
//                temp.setSeleccionada(true);
//
//                adaptadorDarMedallas.notifyDataSetChanged();
//
//            }
//        });


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coger la medalla que ha cogido y asignarsela al alumno
                //medallas.add(medalla)
                return;
            }
        });
        return rootView;
    }

//    class AdaptadorMedallas extends ArrayAdapter<Medalla> {
//        ArrayList<Medalla> medallas;
//
//        public AdaptadorMedallas(Context context, ArrayList<Medalla> medallas) {
//            super(context, R.layout.listitem_dar_medallas, medallas);
//            this.medallas = medallas;
//
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            final int pos = position;
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//           View item = inflater.inflate(R.layout.listitem_dar_medallas, null);
//
//
//
//
//            TextView lblMedalla = (TextView) item.findViewById(R.id.LblNombre);
//            lblMedalla.setText(String.valueOf((medallas.get(position).getNombre())));
//            ImageView imagenMedalla = (ImageView) item.findViewById(R.id.imageMedalla);
//            ImageLoader imageLoader = ImageLoader.getInstance();
//            imageLoader.displayImage(medallas.get(position).getImagenMedalla(), imagenMedalla);
//
//            if (medallas.get(pos).isSeleccionada()){
//                item.setBackgroundColor(Color.CYAN);
//            }else{
//                item.setBackgroundColor(Color.WHITE);
//            }
//
//            return (item);
//        }
//
//    }




    public class AdaptadorMedallas2 extends RecyclerView.Adapter<AdaptadorMedallas2.MedallasViewHolder>{
        private ArrayList<Medalla> datos;

        public AdaptadorMedallas2(ArrayList<Medalla> datos) {
            this.datos = datos;
        }



        public class MedallasViewHolder extends RecyclerView.ViewHolder{
            private TextView nombreMedalla;
            private ImageView imagenMedalla;


            public MedallasViewHolder(View itemView) {
                super(itemView);
                nombreMedalla = (TextView)itemView.findViewById(R.id.LblNombre);
                imagenMedalla = (ImageView)itemView.findViewById(R.id.imageMedalla);

            }
            public void bindMedalla(Medalla m) {
                nombreMedalla.setText(m.getNombre());
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(m.getImagenMedalla(), imagenMedalla);
            }
        }

        @Override
        public int getItemCount() {
            return datos.size();
        }

        @Override
        public void onBindViewHolder(MedallasViewHolder holder, int position) {
                Medalla item = datos.get(position);
                holder.bindMedalla(item);
        }

        @Override
        public MedallasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_dar_medallas, parent, false);

            MedallasViewHolder tvh = new MedallasViewHolder(itemView);

            return tvh;
        }
    }



}