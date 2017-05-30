package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.usuario.ludiuca.ActivityCamarakk;
import com.example.usuario.ludiuca.MainActivityCamara;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Imagen;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentoGaleria extends Fragment {

    View rootView;

    Clase claseSeleccionada;
    Alumno alumno;
    private GridView gvGaleria;
    private ArrayList<Imagen> imagenes = new ArrayList<>();

    public FragmentoGaleria() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.kk, container, false);
        //LinearLayout layoutCamara = (LinearLayout) rootView.findViewById(R.id.boton_imagen);
        claseSeleccionada = DatosUsuario.getInstance().getClase();
        Imagen venecia = new Imagen(R.drawable.star, "star");
        Imagen egipto = new Imagen(R.drawable.kk, "serie");

        imagenes.add(venecia);
        imagenes.add(egipto);


        //create new adapter
        AdaptadorGaleria adaptador = new AdaptadorGaleria(getActivity(), imagenes);
        //initialize RecyclerView
        gvGaleria = (GridView) rootView.findViewById(R.id.gvGaleria);
        //add adapter
        gvGaleria.setAdapter(adaptador);

        ImageButton boton = (ImageButton) rootView.findViewById(R.id.photoButton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentoGaleria.this.getActivity().startActivity(new Intent(rootView.getContext(), ActivityCamarakk.class));


            }

        });


        return rootView;

    }


    class AdaptadorGaleria extends ArrayAdapter<Imagen> {

        private final ArrayList<Imagen> imagenes;

        public AdaptadorGaleria(Context context, ArrayList<Imagen> imagenes) {
            super(context, R.layout.listitem_galeria, imagenes);
            this.imagenes = imagenes;

        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;


            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_galeria, null);
            final ImageView imagen = (ImageView) item.findViewById(R.id.imagenGaleria);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(Integer.toString(imagenes.get(position).getImage()), imagen);

            return (item);
        }

    }

}








