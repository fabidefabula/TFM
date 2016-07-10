package com.example.usuario.ludiuca.fragmentos;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Privilegio;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Tarea;
import com.example.usuario.ludiuca.clases.Webservice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Usuario on 17/02/2016.
 */
public class FragmentoAltaPrivilegio extends Fragment {
    View rootView;
    Profesor profesor;


    ArrayList<Privilegio> privilegios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_alta_privilegio, container, false);
        Button botonAlta = (Button) rootView.findViewById(R.id.bAltaP);
        final EditText nombre = (EditText) rootView.findViewById(R.id.etNombreP);
        final EditText descripcion = (EditText) rootView.findViewById(R.id.etTextoP);

        botonAlta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse
                if(TextUtils.isEmpty(descripcion.getText())|| TextUtils.isEmpty(nombre.getText()) ){
                    Toast.makeText(rootView.getContext(), "Campos vacíos", Toast.LENGTH_LONG).show();
                }else{
                    new AltaPrivilegio(nombre.getText().toString(),descripcion.getText().toString()).execute();
                }

            }
        });

        return rootView;
    }

    class AltaPrivilegio extends AsyncTask<Void, Void, Void> {
        private String descripcion, nombre, response;
        private HashMap<String, String> requestBody;

        public AltaPrivilegio(String n, String d) {
            nombre = n;
            descripcion = d;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestBody = new HashMap<>();
            requestBody.put("operacion", "insertPrivilegio");
            requestBody.put("Name", nombre);
            requestBody.put("Description", descripcion);
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
                        for(int i=0; i<respuesta.length(); i++){
                            JSONObject jsonPrivilegio = respuesta.getJSONObject(i);
                            Privilegio privilegio = new Privilegio(jsonPrivilegio.getString("Name"),jsonPrivilegio.getString("Description"), jsonPrivilegio.getInt("idPrivilegio"));
                            privilegios.add(privilegio);
                        }
                        DatosUsuario.getInstance().setPrivilegios(privilegios);
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
