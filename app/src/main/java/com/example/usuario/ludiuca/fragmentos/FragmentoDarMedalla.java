package com.example.usuario.ludiuca.fragmentos;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Avatar;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Tarea;
import com.example.usuario.ludiuca.clases.Webservice;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.HashMap;
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
    ArrayList<Medalla> medallasAlumno;
    Medalla elegida;
    ListView lvMedallas;
    int posicionElegido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_dar_medalla, container, false);
        buttonOk = (Button) rootView.findViewById(R.id.bOk);
        medallas = DatosUsuario.getInstance().getListaMedallasAlumnos();
        medallasAlumno = DatosUsuario.getInstance().getAlumno().getMedallasAlumno();
        ArrayList<Medalla> medallasFiltradas = filtrarMedallasAlumno();
        AdaptadorDarMedallaAlumno adaptador = new AdaptadorDarMedallaAlumno(getActivity(), medallasFiltradas);
        elegida = medallasFiltradas.get(0);
        lvMedallas = (ListView) rootView.findViewById(R.id.lvDarMedallas);
        lvMedallas.setAdapter(adaptador);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new darMedallaAlumno(elegida).execute();

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        medallas = DatosUsuario.getInstance().getListaMedallasAlumnos();
        medallasAlumno = DatosUsuario.getInstance().getAlumno().getMedallasAlumno();
        super.onStart();
    }

    class AdaptadorDarMedallaAlumno extends ArrayAdapter<Medalla> {
        ArrayList<Medalla> medallas;

        public AdaptadorDarMedallaAlumno(Context context, ArrayList<Medalla> medallas) {
            super(context, R.layout.listitem_dar_medallas, medallas);
            this.medallas = medallas;

        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_dar_medallas, null);
            TextView nombre = (TextView) item.findViewById(R.id.LblNombreMedalla);
            nombre.setText(medallas.get(position).getNombre());
            final ImageView medalla = (ImageView) item.findViewById(R.id.imageMedalla);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(medallas.get(position).getImagenMedalla(), medalla);
            if (position != posicionElegido) {
                item.setBackgroundColor(Color.WHITE);
            } else {
                item.setBackgroundColor(Color.GRAY);
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < medallas.size(); i++) {
                        if (medallas.get(i).isFlag() == false && i == position) {
                            medallas.get(position).setFlag(true);
                            posicionElegido = i;
                        } else {
                            medallas.get(i).setFlag(false);

                        }
                    }
                    System.out.println("click en la posición" + " " + position);
                    elegida = new Medalla(medallas.get(position).getIdMedalla(), medallas.get(position).getNombre(), medallas.get(position).getImagenMedalla());
                    v.setBackgroundColor(Color.GRAY);
                    ((BaseAdapter) lvMedallas.getAdapter()).notifyDataSetChanged();

                }
            });


            return (item);
        }

    }

    class darMedallaAlumno extends AsyncTask<Void, Void, Void> {
        private String response;
        private Medalla medalla;
        private HashMap<String, String> requestBody;

        public darMedallaAlumno(Medalla m) {
            medalla = m;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestBody = new HashMap<>();
            requestBody.put("operacion", "darMedallaAlumno");
            requestBody.put("idAlumno", "" + DatosUsuario.getInstance().getAlumno().getIdAlumno());
            requestBody.put("idAsignatura", "" + DatosUsuario.getInstance().getClase().getIdAsignatura());
            requestBody.put("idMedallaAlumno", "" + elegida.getIdMedalla());
            requestBody.put("idProfesor", "" + DatosUsuario.getInstance().getProfesor().getIdProfesor());

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
                        //Cambiar fragmento
                        JSONArray respuesta = json.getJSONArray("Respuesta");
                        medallas = new ArrayList<>();
                        for (int i = 0; i < respuesta.length(); i++) {
                            JSONObject jsonMedalla = respuesta.getJSONObject(i);
                            Medalla medalla = new Medalla(jsonMedalla.getInt("idMedallaAlumno"), jsonMedalla.getString("Name"), jsonMedalla.getString("Photo"));
                            medalla.setIdAsignatura(jsonMedalla.getString("idAsignatura"));
                            medalla.setDescripcion(jsonMedalla.getString("Description"));
                            medallas.add(medalla);
                        }
                        DatosUsuario.getInstance().getAlumno().setMedallasAlumno(medallas);
                        getActivity().onBackPressed();

                    } else {
                        Toast.makeText(rootView.getContext(), mensaje, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }


    }

    public ArrayList<Medalla> filtrarMedallasAlumno() {
        ArrayList<Medalla> listaNueva = new ArrayList<Medalla>();
        for (int i = 0; i < medallas.size(); i++) {
            boolean encontrada = false;
            for (int j = 0; j < medallasAlumno.size(); j++) {
                if ((medallas.get(i).getIdMedalla() + "").equals("" + medallasAlumno.get(j).getIdMedalla())) {
                    encontrada = true;
                    break;
                }
            }

            if (!encontrada) {
                listaNueva.add(medallas.get(i));
            }
        }
        return listaNueva;
    }

}
