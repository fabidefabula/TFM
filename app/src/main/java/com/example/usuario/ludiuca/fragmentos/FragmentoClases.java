package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;

import com.example.usuario.ludiuca.ClaseActivity;
import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.TareasActivity;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by fabio on 30/03/2016.
 */
public class FragmentoClases extends Fragment {

    View rootView;

    Profesor profe;
    ListView lvClases;
    View clase;
    View opciones;

    ArrayList<Clase> clases;
    Clase claseSeleccionada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_clases, container, false);

        profe = DatosUsuario.getInstance().getProfesor();
        clases = DatosUsuario.getInstance().getProfesor().getClasesProfe();
        PrincipalActivity actividad = (PrincipalActivity)getActivity();

        AdaptadorClases adaptador = new AdaptadorClases(actividad, clases);

        lvClases = (ListView)rootView.findViewById(R.id.lvClases);
        lvClases.setAdapter(adaptador);


        return rootView;
    }

    class AdaptadorClases extends ArrayAdapter<Clase>{
        ArrayList<Clase> clases;
     public  AdaptadorClases(Context context, ArrayList<Clase> clases){
         super(context, R.layout.listitem_clases, clases);
         this.clases = clases;

     }
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_clases, null);

            TextView lblClase = (TextView)item.findViewById(R.id.LblClase);
            lblClase.setText(String.valueOf((clases.get(position).getAsignatura())));
            TextView lblCurso = (TextView)item.findViewById(R.id.LblCurso);
            lblCurso.setText(String.valueOf(clases.get(position).getCurso()));
            ImageView claseim = (ImageView)item.findViewById(R.id.iClase);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(clases.get(position).getImagenCurso(), claseim);


            clase = (LinearLayout)item.findViewById(R.id.clickClase);
            clase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    claseSeleccionada = clases.get(pos);
                    DatosUsuario.getInstance().setClase(claseSeleccionada);
                    FragmentoClases.this.getActivity().startActivity(new Intent(rootView.getContext(), TareasActivity.class));

                }

            });
            opciones = (LinearLayout)item.findViewById(R.id.clickOptions);
            opciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    claseSeleccionada = clases.get(pos);
                    DatosUsuario.getInstance().setClase(claseSeleccionada);
                    showPopup(v, claseSeleccionada);
                }
            });

           // ImageView imagenClase = (ImageView)item.findViewById(R.id.imageClass);
            //imagenClase.setImageResource();

            return(item);
        }

    }

    public void showPopup(View v, final Clase clase) {
        PopupMenu popup = new PopupMenu(rootView.getContext(), v);
        popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem arg0) {
                int id = arg0.getItemId();

                if (id == R.id.ver_alumnos) {
                    getActivity().startActivity(new Intent(rootView.getContext(), ClaseActivity.class));
                }

                return true;
            }
        });
        popup.inflate(R.menu.menu_clase);
        popup.show();
    }

}


