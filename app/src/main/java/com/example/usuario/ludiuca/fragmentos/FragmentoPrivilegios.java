package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.usuario.ludiuca.AltaPrivilegioActivity;
import com.example.usuario.ludiuca.AltaTareaActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Privilegio;
import com.example.usuario.ludiuca.clases.Tarea;
import com.example.usuario.ludiuca.clases.Webservice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fabio on 30/03/2016.
 */
public class FragmentoPrivilegios extends Fragment {

    View rootView;
    ListView lvPrivilegios;
    ArrayList<Privilegio> privilegiosClase;
    ArrayList<Privilegio> privilegios;
    Clase claseSeleccionada;
    Privilegio elegido;
    int posicionElegido = 0;
    Alumno alumno;
    AdaptadorPrivilegios adaptador;

    public FragmentoPrivilegios() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_privilegios, container, false);

        claseSeleccionada = DatosUsuario.getInstance().getClase();
        privilegiosClase = DatosUsuario.getInstance().getPrivilegios();
        alumno = DatosUsuario.getInstance().getAlumno();
        adaptador = new AdaptadorPrivilegios(getActivity(), privilegiosClase);
        lvPrivilegios = (ListView) rootView.findViewById(R.id.lvDarPrivilegios);
        lvPrivilegios.setAdapter(adaptador);
        elegido = privilegiosClase.get(0);
        FloatingActionButton inserta = (FloatingActionButton) rootView.findViewById(R.id.bInsertarPrivilegio);
        Button dar = (Button) rootView.findViewById(R.id.bDarPrivilegio);


        lvPrivilegios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Privilegio p : privilegiosClase) {
                    p.setFlag(false);
                }
                privilegiosClase.get(position).setFlag(true);
                adaptador = new AdaptadorPrivilegios(getActivity(), privilegiosClase);
                lvPrivilegios.setAdapter(adaptador);
            }
        });

        dar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DarPrivilegio(alumno.getIdAlumno(), claseSeleccionada.getIdAsignatura(), elegido.getIdPrivilegio()).execute();

            }
        });

        inserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoPrivilegios.this.getActivity().startActivity(new Intent(rootView.getContext(), AltaPrivilegioActivity.class));
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        privilegiosClase = DatosUsuario.getInstance().getPrivilegios();
        AdaptadorPrivilegios adaptador = new AdaptadorPrivilegios(getActivity(), privilegiosClase);
        lvPrivilegios = (ListView) rootView.findViewById(R.id.lvDarPrivilegios);
        lvPrivilegios.setAdapter(adaptador);

        super.onStart();
    }

    class AdaptadorPrivilegios extends ArrayAdapter<Privilegio> {
        ArrayList<Privilegio> privilegios;

        public AdaptadorPrivilegios(Context context, ArrayList<Privilegio> privilegios) {
            super(context, R.layout.listitem_privilegios, privilegiosClase);
            this.privilegios = privilegios;

        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_privilegios, null);

            LinearLayout ll = (LinearLayout) item.findViewById(R.id.listItemPrivilegio);

            TextView lblName = (TextView) item.findViewById(R.id.LblName);
            TextView lblDescription = (TextView) item.findViewById(R.id.LblDescription);
            lblName.setText(privilegios.get(pos).getNombre());
            lblDescription.setText(privilegios.get(pos).getTexto());

            if (!privilegios.get(pos).isFlag()) {
                ll.setBackgroundColor(Color.WHITE);
            } else {
                ll.setBackgroundColor(Color.GRAY);
            }
            return (item);
        }
    }

    class DarPrivilegio extends AsyncTask<Void, Void, Void> {
        private String response;
        private int idAlumno, idAsignatura, idPrivilegio;
        private HashMap<String, String> requestBody;

        public DarPrivilegio(int idAlumno, int idAsignatura, int idPrivilegio) {
            this.idAlumno = idAlumno;
            this.idAsignatura = idAsignatura;
            this.idPrivilegio = idPrivilegio;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestBody = new HashMap<>();
            requestBody.put("operacion", "darPrivilegioAlumno");
            requestBody.put("idAlumno", "" + this.idAlumno);
            requestBody.put("idAsignatura", "" + this.idAsignatura);
            requestBody.put("idPrivilegio", "" + this.idPrivilegio);
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
                        privilegios = new ArrayList<>();
                        for (int i = 0; i < respuesta.length(); i++) {
                            JSONObject jsonPrivilegio = respuesta.getJSONObject(i);
                            Privilegio privilegio = new Privilegio(jsonPrivilegio.getString("Name"), jsonPrivilegio.getString("Description"), jsonPrivilegio.getInt("idPrivilegio"));
                            privilegios.add(privilegio);
                        }
                        alumno.setPrivilegiosAlumno(privilegios);
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
}

//ImageView botonBorrarPrivilegio = (ImageView) item.findViewById(R.id.ivBorrarPrivilegio);
//            botonBorrarPrivilegio.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    final int id = privilegios.get(pos).getIdPrivilegio();
//                    (new Thread() {
//                        public void run() {
//                            String response = null;
//                            try {
//                                HashMap<String, String> requestBody;
//                                requestBody = new HashMap<>();
//                                requestBody.put("operacion", "borrarPrivilegio");
//                                requestBody.put("idTarea", "" + id);
//                                response = Webservice.getInstancia().operacionPost(requestBody);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            } finally {
//                                if (response == null) {
//                                    //Toast.makeText(rootView.getContext(), "Error al borrar", Toast.LENGTH_LONG).show();
//                                } else {
//                                    //Toast.makeText(rootView.getContext(), "Tarea borrada", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        }
//                    }).start();
//                    privilegiosClase.remove(pos);
//                    notifyDataSetChanged();
//
//                }
//            });






