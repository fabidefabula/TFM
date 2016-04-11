package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.DatosUsuario;

import java.util.ArrayList;

/**
 * Created by fabio on 30/03/2016.
 */
public class FragmentoClases extends Fragment {

    View rootView;
    Profesor profe;
    ListView lvClases;
    int[] nombres;
    ArrayList<Clase> clases;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_clases, container, false);
        profe = DatosUsuario.getInstance().getProfesor();
        clases = DatosUsuario.getInstance().getProfesor().getClasesProfe();

        PrincipalActivity actividad = (PrincipalActivity)getActivity();
        //actividad.. pa regenerar la activity
       // listaClases= (ListView)rootView.findViewById(R.id.lvClases);
        //listaClases.setAdapter(new AdaptadorClase());



        lvClases = (ListView)rootView.findViewById(R.id.lvClases);
        AdaptadorClase adaptador = new AdaptadorClase(actividad, clases);
        lvClases.setAdapter(adaptador);

        return rootView;
    }

    class AdaptadorClase extends BaseAdapter{
        private Context mContext;
        AdaptadorClase(Context context, ArrayList<Clase> clases){
            super();
        }

        @Override
        public int getCount() {
            return profe.getClasesProfe().size();
        }

        @Override
        public Object getItem(int position) {
            return profe.getClasesProfe().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView viewClase;
            if (convertView == null) {

                viewClase = new TextView(mContext);

            } else {
                viewClase = (TextView) convertView;
            }
            viewClase.setText(clases.get(position).getAsignatura());

            return viewClase;
        }
    }


}


