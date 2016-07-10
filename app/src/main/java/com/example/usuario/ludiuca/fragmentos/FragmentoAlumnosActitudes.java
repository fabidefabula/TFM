package com.example.usuario.ludiuca.fragmentos;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Actitud;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Avatar;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Webservice;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Usuario on 17/02/2016.
 */
public class FragmentoAlumnosActitudes extends Fragment {
    View rootView;
    Profesor profesor;
    ListView alumnoslv;
    ArrayList<Alumno> alumnosSeleccionados = new ArrayList<>();
    ArrayList<Alumno> alumnos;
    boolean bool = false;
    Actitud elegida;
    int puntos;
    int tipo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_alumnos_actitudes, container, false);
        Button botonDarActitudes = (Button) rootView.findViewById(R.id.bDarActitudes);
        alumnos = DatosUsuario.getInstance().getClase().getAlumnosClase();
        AdaptadorElegirAlumnoActitud adaptador = new AdaptadorElegirAlumnoActitud(getActivity(), alumnos );
        alumnos.get(0).setFlag(true);
        alumnos.get(0).setYaEstaba(true);
        alumnosSeleccionados.add(alumnos.get(0));
        elegida = DatosUsuario.getInstance().getActitudElegida();
        tipo = elegida.getTipo();
        puntos = elegida.getPuntos();
        alumnoslv = (ListView) rootView.findViewById(R.id.lvAlumnosActitudes);
        alumnoslv.setAdapter(adaptador);
        botonDarActitudes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<alumnos.size(); i++){
                    if(alumnos.get(i).isFlag()){
                        alumnosSeleccionados.add(alumnos.get(i));
                    }
                }
                for(int i=0; i<alumnosSeleccionados.size(); i++){
                    new updatePuntosAlumnos(alumnosSeleccionados.get(i), puntos, tipo).execute();
                }
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }

    class AdaptadorElegirAlumnoActitud extends ArrayAdapter<Alumno> {
        ArrayList<Alumno> alumnos;
        public  AdaptadorElegirAlumnoActitud(Context context, ArrayList<Alumno> alumnos){
            super(context, R.layout.lisitem_alumnos_actitudes, alumnos);
            this.alumnos = alumnos;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.lisitem_alumnos_actitudes, null);
            TextView nombre = (TextView)item.findViewById(R.id.LblNombre);
            TextView exp = (TextView)item.findViewById(R.id.LblExp);
            nombre.setText(alumnos.get(position).getNombre());
            exp.setText(exp.getText()+" "+alumnos.get(position).getExp());

            final ImageView avatarAlumno = (ImageView)item.findViewById(R.id.imageAlumno);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(alumnos.get(position).getFotoPerfil(), avatarAlumno);

            if(alumnos.get(position).isFlag()){
                item.setBackgroundColor(Color.GRAY);
            } else{
                item.setBackgroundColor(Color.WHITE);
            }

            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (alumnos.get(position).isFlag() == true && alumnos.get(position).isYaEstaba() == true) {
                        alumnos.get(position).setFlag(false);
                        alumnosSeleccionados.remove(alumnos.get(position));
                        alumnos.get(position).setYaEstaba(false);

                    } else {
                        alumnos.get(position).setFlag(true);
                        alumnosSeleccionados.add(alumnos.get(position));
                        alumnos.get(position).setYaEstaba(true);
                    }


                    v.setBackgroundColor(Color.GRAY);
                    ((BaseAdapter) alumnoslv.getAdapter()).notifyDataSetChanged();

                }
            });


            return(item);
        }

    }
    class updatePuntosAlumnos extends AsyncTask<Void, Void, Void> {
        private String response;
        private Alumno alumno;
        private int puntos, tipo;
        private HashMap<String, String> requestBody;

        public updatePuntosAlumnos(Alumno a, int p, int t) {
            alumno = a;
            puntos = p;
            tipo = t;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(tipo ==1){
                alumno.setExp(alumno.getExp()+puntos );
            }else {
                if (alumno.getExp() - puntos > 0) {
                    alumno.setExp(alumno.getExp() - puntos);
                }
            }

            requestBody = new HashMap<>();
            requestBody.put("operacion", "updatePuntosAlumno");
            requestBody.put("Puntos", ""+alumno.getExp());
            requestBody.put("idAlumno", "" + alumno.getIdAlumno());

        }

        @Override
        protected Void doInBackground(Void... params) {
            response = Webservice.getInstancia().operacionPost(requestBody);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (response != null) {
                System.out.println(response);
                try {
                    JSONObject json = new JSONObject(response);
                    boolean exito = json.getBoolean("Exito");
                    String mensaje = json.getString("Mensaje");
                    if (exito) {
                        Toast.makeText(rootView.getContext(), mensaje, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(rootView.getContext(), mensaje, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
    }


}
