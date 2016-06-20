package com.example.usuario.ludiuca.fragmentos;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Fecha;
import com.example.usuario.ludiuca.clases.Grupo;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Notificacion;
import com.example.usuario.ludiuca.clases.Privilegio;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Tarea;
import com.example.usuario.ludiuca.clases.Webservice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Usuario on 17/02/2016.
 */
public class FragmentoAltaTarea extends Fragment {
    View rootView;
    String username, password;
    Profesor profesor;
    ArrayList<Alumno> alumnos;
    ArrayList<Medalla> listaMedallas = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_altatarea, container, false);
        Button botonAlta = (Button) rootView.findViewById(R.id.bAlta);

        botonAlta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse
                EditText descripcion = (EditText) rootView.findViewById(R.id.etDescripcion);
                EditText fechaEntrega = (EditText) rootView.findViewById(R.id.etFechaEntrega);
                (new AltaTarea(descripcion.getText().toString(), fechaEntrega.getText().toString())).execute();


            }
        });

        return rootView;
    }


    class AltaTarea extends AsyncTask<Void, Void, Void> {
        private String descripcion, fechaEntrega, response;
        private HashMap<String, String> requestBody;

        public AltaTarea(String d, String f) {
            descripcion = d;
            fechaEntrega = f;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestBody = new HashMap<>();
            requestBody.put("operacion", "insertaTarea");
            requestBody.put("idAsignatura", "" + DatosUsuario.getInstance().getClase().getIdAsignatura());
            requestBody.put("idClase", "" + DatosUsuario.getInstance().getClase().getIdClase());
            requestBody.put("idCurso", "" + DatosUsuario.getInstance().getClase().getIdCurso());
            requestBody.put("idProfesor", "" + DatosUsuario.getInstance().getProfesor().getIdProfesor());
            requestBody.put("description", descripcion);
            requestBody.put("finishDate", fechaEntrega);
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
