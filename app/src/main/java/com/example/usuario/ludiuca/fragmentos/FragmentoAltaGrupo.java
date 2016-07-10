package com.example.usuario.ludiuca.fragmentos;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.ludiuca.AlumnoActivity;
import com.example.usuario.ludiuca.AlumnosGrupoActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.adaptadores.AdaptadorAltaActitud;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Avatar;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Grupo;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Webservice;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Usuario on 17/02/2016.
 */
public class FragmentoAltaGrupo extends Fragment {
    View rootView;
    Profesor profesor;
    GridView listaAvatares;
    ArrayList<Alumno> alumnos;
    ArrayList<Avatar> aGrupos;
    ArrayList<Alumno> alumnosSeleccionados = new ArrayList<>();
    Avatar elegido;
    Grupo grupo;
    int tipo = 1;
    int posElegido = 0;
    ListView lvAlumnos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_alta_grupo, container, false);
        Button botonAlta = (Button) rootView.findViewById(R.id.bAltaG);
        listaAvatares = (GridView) rootView.findViewById(R.id.gvAvataresGrupo);
        aGrupos = DatosUsuario.getInstance().getAvataresGrupos();
        alumnos = DatosUsuario.getInstance().getClase().getAlumnosClase();
        lvAlumnos = (ListView) rootView.findViewById(R.id.lvAlumnosAltaGrupo);
        AdaptadorAlumnosAltaGrupo adaptadorA = new AdaptadorAlumnosAltaGrupo(getActivity(), alumnos);
        lvAlumnos.setAdapter(adaptadorA);
        elegido = aGrupos.get(0);
        AdaptadorAvataresGrupo adaptador = new AdaptadorAvataresGrupo(getActivity(), aGrupos);
        listaAvatares.setAdapter(adaptador);

        botonAlta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse
                EditText nombre = (EditText) rootView.findViewById(R.id.etNombreG);
                if (TextUtils.isEmpty(nombre.getText())) {
                    Toast.makeText(rootView.getContext(), "Campo vacío", Toast.LENGTH_LONG).show();
                } else {
                    grupo = new Grupo(DatosUsuario.getInstance().getClase(), nombre.getText().toString(), elegido.getPhoto());
                    new altaGrupo(alumnosSeleccionados, nombre.getText().toString()).execute();
                    getActivity().onBackPressed();
                }

            }
        });

        return rootView;
    }

    class AdaptadorAlumnosAltaGrupo extends ArrayAdapter<Alumno> {
        ArrayList<Alumno> alumnos;

        public AdaptadorAlumnosAltaGrupo(Context context, ArrayList<Alumno> alumnos) {
            super(context, R.layout.lisitem_alumnos_alta_grupo, alumnos);
            this.alumnos = alumnos;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.lisitem_alumnos_alta_grupo, null);
            TextView lblNombre = (TextView) item.findViewById(R.id.LblNombreAG);
            lblNombre.setText(alumnos.get(position).getNombre());
            TextView lblNick = (TextView) item.findViewById(R.id.LblNickAG);
            lblNick.setText(alumnos.get(position).getNickName());
            ImageView imagenAlumno = (ImageView) item.findViewById(R.id.imageAlumnoAG);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(alumnos.get(position).getFotoPerfil(), imagenAlumno);

            if (alumnos.get(position).isFlag()) {
                item.setBackgroundColor(Color.GRAY);
            } else {
                item.setBackgroundColor(Color.WHITE);
            }
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (alumnos.get(pos).isFlag() == true && alumnos.get(pos).isYaEstaba() == true) {
                        alumnos.get(pos).setFlag(false);
                        alumnosSeleccionados.remove(alumnos.get(pos));
                        alumnos.get(pos).setYaEstaba(false);

                    } else {
                        alumnos.get(pos).setFlag(true);
                        alumnosSeleccionados.add(alumnos.get(pos));
                        alumnos.get(pos).setYaEstaba(true);
                    }


                    v.setBackgroundColor(Color.GRAY);
                    ((BaseAdapter) lvAlumnos.getAdapter()).notifyDataSetChanged();
                }
            });
            return (item);
        }
    }

    class AdaptadorAvataresGrupo extends ArrayAdapter<Avatar> {
        ArrayList<Avatar> avataresGrupos;

        public AdaptadorAvataresGrupo(Context context, ArrayList<Avatar> grupos) {
            super(context, R.layout.listitem_avatares_grupos, grupos);
            this.avataresGrupos = grupos;

        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_avatares_grupos, null);

            ImageView imagenGrupo = (ImageView) item.findViewById(R.id.avatarGrupo);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(avataresGrupos.get(position).getPhoto(), imagenGrupo);

            if (position != posElegido) {
                item.setBackgroundColor(Color.WHITE);
            } else {
                item.setBackgroundColor(Color.GRAY);
            }
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < avataresGrupos.size(); i++) {
                        if (avataresGrupos.get(i).isFlag() == false && i == position) {
                            avataresGrupos.get(position).setFlag(true);
                            posElegido = i;
                        } else {
                            avataresGrupos.get(i).setFlag(false);

                        }
                    }
                    elegido = avataresGrupos.get(position);
                    v.setBackgroundColor(Color.GRAY);
                    ((BaseAdapter) listaAvatares.getAdapter()).notifyDataSetChanged();
                }
            });


            return (item);
        }
    }

    class altaGrupo extends AsyncTask<Void, Void, Void> {
        private String response;
        private String nombre;
        private ArrayList<Alumno> alumno;

        private HashMap<String, String> requestBody;

        public altaGrupo(ArrayList<Alumno> a, String n) {
            nombre = n;
            alumno = a;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            requestBody = new HashMap<>();
            requestBody.put("operacion", "insertaGrupo");
            requestBody.put("idAsignatura", "" + DatosUsuario.getInstance().getClase().getIdAsignatura());
            requestBody.put("idProfesor", "" + DatosUsuario.getInstance().getProfesor().getIdProfesor());
            requestBody.put("Name", nombre);
            requestBody.put("Photo", elegido.getPhoto());

            JSONArray alumnosJSON = new JSONArray();
            try {
                for (Alumno al : this.alumno) {
                    JSONObject joAlumno = new JSONObject();
                    joAlumno.put("idAlumno", "" + al.getIdAlumno());
                    joAlumno.put("idClase", "" + DatosUsuario.getInstance().getClase().getIdClase());
                    joAlumno.put("idAsignatura", "" + DatosUsuario.getInstance().getClase().getIdAsignatura());
                    alumnosJSON.put(joAlumno);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            requestBody.put("Alumnos", Base64.encodeToString(alumnosJSON.toString().getBytes(), Base64.DEFAULT));

            System.out.println( Base64.encodeToString(alumnosJSON.toString().getBytes(), Base64.DEFAULT));

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
                        ArrayList<Grupo> gruposClase = new ArrayList<>();
                        JSONArray respuesta = json.getJSONArray("Grupos");

                        for (int w = 0; w < respuesta.length(); w++) {
                            JSONObject grupoJson = respuesta.getJSONObject(w);
                            Grupo grupo1 = new Grupo(DatosUsuario.getInstance().getClase(), grupoJson.getString("Name"), grupoJson.getString("Photo"));
                            grupo1.setIdGrupo(grupoJson.getInt("idGrupo"));
                            JSONArray alumnosArray = grupoJson.getJSONArray("Alumnos");
                            ArrayList<Alumno> alumnosGrupo = new ArrayList<>();
                            for (int a = 0; a < alumnosArray.length(); a++) {
                                JSONObject alumnoJson = alumnosArray.getJSONObject(a);
                                Alumno alumno1 = new Alumno(alumnoJson.getString("Name"), alumnoJson.getString("Surname"));
                                alumnosGrupo.add(alumno1);
                            }
                            grupo1.setAlumnosGrupo(alumnosGrupo);
                            gruposClase.add(grupo1);
                        }
                        DatosUsuario.getInstance().getClase().setGruposClase(gruposClase);


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

