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
import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Grupo;
import com.example.usuario.ludiuca.clases.Profesor;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class FragmentoGrupos extends Fragment {
    View rootView;
    Clase claseSeleccionada;
    Profesor profe;
    ListView lvGrupos;
    ArrayList<Grupo> grupos;
    LinearLayout grupo;
    public FragmentoGrupos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_grupos, container, false);

        profe = DatosUsuario.getInstance().getProfesor();
        claseSeleccionada = DatosUsuario.getInstance().getClase();
        grupos = claseSeleccionada.getGruposClase();

        AdaptadorGrupo adaptador = new AdaptadorGrupo(getActivity(), grupos);
        lvGrupos = (ListView) rootView.findViewById(R.id.lvGrupos);
        lvGrupos.setAdapter(adaptador);

        return rootView;
    }


    class AdaptadorGrupo extends ArrayAdapter<Grupo> {
        ArrayList<Grupo> grupos;
        public AdaptadorGrupo(Context context, ArrayList<Grupo> grupos) {
            super(context, R.layout.lisitem_grupos, grupos);
            this.grupos = grupos;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.lisitem_grupos, null);
            TextView lblNombre = (TextView) item.findViewById(R.id.LblNombre);
            lblNombre.setText(grupos.get(position).getNombre());
            ImageView imagenGrupo = (ImageView) item.findViewById(R.id.imageGrupo);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(grupos.get(position).getFotoGrupo(), imagenGrupo);
            grupo = (LinearLayout)item.findViewById(R.id.clickGrupo);

            grupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   DatosUsuario.getInstance().setGrupoSeleccionado(grupos.get(pos));
                    FragmentoGrupos.this.getActivity().startActivity(new Intent(rootView.getContext(), AlumnosGrupoActivity.class));

                }

            });
//            alumnosGrupoArray = grupos.get(position).getAlumnosGrupo();
//            AdaptadorAlumnosGrupo adaptadorAlumnos = new AdaptadorAlumnosGrupo(getContext(), alumnosGrupoArray);
//            ListView alumnoGrupo = (ListView) item.findViewById(R.id.lvAlumnoGrupo);
//            alumnoGrupo.setAdapter(adaptadorAlumnos);
//            alumnosGrupoArray=null;
            return(item);
            }
        }

    }



