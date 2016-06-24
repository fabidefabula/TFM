package com.example.usuario.ludiuca.fragmentos;


import android.content.Intent;
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

import com.example.usuario.ludiuca.AltaTareaActivity;
import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.TareasActivity;
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
import java.util.Calendar;
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
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();

    ArrayList<Tarea> tareas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_altatarea, container, false);
        Button botonAlta = (Button) rootView.findViewById(R.id.bAlta);

        final EditText fechaEntrega = (EditText) rootView.findViewById(R.id.etFechaEntrega);
        fechaEntrega.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    fechaEntrega.setText(current);
                    fechaEntrega.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        botonAlta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse
                EditText descripcion = (EditText) rootView.findViewById(R.id.etDescripcion);
                if(TextUtils.isEmpty(descripcion.getText())&& TextUtils.isEmpty(fechaEntrega.getText()) ){
                    Toast.makeText(rootView.getContext(), "Campos vacíos", Toast.LENGTH_LONG).show();
                }else{
                    (new AltaTarea(descripcion.getText().toString(), fechaEntrega.getText().toString())).execute();
                }

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
                        JSONArray respuesta = json.getJSONArray("Respuesta");
                        tareas = new ArrayList<>();
                        for(int i=0; i<respuesta.length(); i++){
                            JSONObject jsonTarea = respuesta.getJSONObject(i);
                            Tarea tarea = new Tarea(jsonTarea.getString("Description"), jsonTarea.getString("Createdate"), jsonTarea.getString("Finishdate"));

                            tarea.setIdTarea(jsonTarea.getInt("idTarea"));
                            tareas.add(tarea);
                        }
                        DatosUsuario.getInstance().getClase().setTareasClase(tareas);
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
