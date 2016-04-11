package com.example.usuario.ludiuca.fragmentos;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Webservice;

import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Usuario on 17/02/2016.
 */
public class FragmentoLogin extends Fragment{
    View rootView;
    String username,password;
    Profesor profesor;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.content_main, container, false);
        Button botonLogin = (Button)rootView.findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse
                EditText cUser = (EditText)rootView.findViewById(R.id.editUser);
                EditText cPass = (EditText)rootView.findViewById(R.id.editPass);
                (new RealizarLogin(cUser.getText().toString(), cPass.getText().toString())).execute();


            }
        });

        return rootView;
    }



    class RealizarLogin extends AsyncTask<Void, Void, Void>{
        private String user, pass, response;
        private HashMap<String, String> requestBody;

        public RealizarLogin(String u, String p){
            user = u;
            pass = p;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            requestBody = new HashMap<>();
            requestBody.put("username", user);
            requestBody.put("password", pass);
            requestBody.put("operacion", "login");
        }
        @Override
        protected Void doInBackground(Void... params ){
            response = Webservice.getInstancia().operacionPost(requestBody);
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            DatosUsuario profeSingle = DatosUsuario.getInstance();
            ArrayList<Clase> clasesProfe = new ArrayList<>();
            Clase clase1;

            if(response != null){
                System.out.println(response);
                try {
                    JSONObject json = new JSONObject(response);
                    boolean exito = json.getBoolean("Exito");
                    String mensaje = json.getString("Mensaje");
                    if(exito) {
                        JSONObject respuesta = json.getJSONObject("Respuesta");

                        JSONArray clases = respuesta.getJSONArray("Clases");

                        for (int i = 0; i < clases.length(); i++) {
                            JSONObject clase = clases.getJSONObject(i);
                            clase1 = new Clase(clase.getInt("Asignatura"), clase.getInt("Curso"), clase.getString("Imagen"));
                            clasesProfe.add(clase1);
                        }

                        profesor = new Profesor(respuesta.getString("Nombre"), respuesta.getString("Apellidos"));
                        profesor.setClasesProfe(clasesProfe);
                        profesor.setNickName(respuesta.getString("Nic"));
                        profesor.setExp(respuesta.getInt("Experiencia"));
                        profesor.setLevel(respuesta.getInt("Nivel"));
                        profesor.setFotoPerfil(respuesta.getString("Foto"));

                        //Change the string value and launch intent to ActivityB
                        DatosUsuario.getInstance().setProfesor(profesor);
                        Intent intent = new Intent(getContext(), FragmentoPerfil.class);
                        Intent intent2 = new Intent(getContext(), FragmentoClases.class);

                        getActivity().startActivity(new Intent(rootView.getContext(), PrincipalActivity.class));


                    }
                    else{
                        Toast.makeText(rootView.getContext(), mensaje, Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
            else{
                Toast.makeText(rootView.getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }



    }






}
