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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Actitud;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Medalla;
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
public class FragmentoAltaActitud extends Fragment {
    View rootView;
    String username, password;
    Profesor profesor;
    ArrayList<Alumno> alumnos;
    int tipoActitud;

    ArrayList<Actitud> actitudes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_alta_actitud, container, false);
        Button botonAlta = (Button) rootView.findViewById(R.id.bAltaA);
        CheckBox tipo = (CheckBox) rootView.findViewById(R.id.cbTipoActitud);
        if(tipo.isChecked()){
            tipoActitud=1;
        }else{
            tipoActitud=2;
        }

        botonAlta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse
                EditText experiencia = (EditText) rootView.findViewById(R.id.etExperiencia);
                EditText descripcion = (EditText) rootView.findViewById(R.id.etDescripcionA);

                if(TextUtils.isEmpty(descripcion.getText()) && TextUtils.isEmpty(experiencia.getText()) ){
                    Toast.makeText(rootView.getContext(), "Campos vacíos", Toast.LENGTH_LONG).show();
                }else{
                    (new AltaActitud(descripcion.getText().toString(), experiencia.getText().toString())).execute();
                }

            }
        });

        return rootView;
    }


    class AltaActitud extends AsyncTask<Void, Void, Void> {
        private String descripcion, exp, response;
        private HashMap<String, String> requestBody;

        public AltaActitud(String d, String e) {
            descripcion = d;
            exp = e;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestBody = new HashMap<>();
            requestBody.put("operacion", "insertaActitud");
            requestBody.put("Name",  descripcion);
            requestBody.put("Type", "" + tipoActitud);
            requestBody.put("Experience", "" + exp);

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
                        actitudes = new ArrayList<>();
                        for(int i=0; i<respuesta.length(); i++){
                            JSONObject jsonActitud = respuesta.getJSONObject(i);
                            Actitud actitud= new Actitud(jsonActitud.getString("Name"), jsonActitud.getInt("Experience"));

                            actitud.setIdActitud(jsonActitud.getInt("idActitud"));
                            actitud.setTipo(jsonActitud.getInt("Type"));
                            actitudes.add(actitud);
                        }
                        DatosUsuario.getInstance().getClase().setActitudesClase(actitudes);
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
