package com.example.usuario.ludiuca.fragmentos;


import android.app.Notification;
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
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.Fecha;
import com.example.usuario.ludiuca.clases.Grupo;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Notificacion;
import com.example.usuario.ludiuca.clases.Privilegio;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Tarea;
import com.example.usuario.ludiuca.clases.Webservice;

import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Usuario on 17/02/2016.
 */
public class FragmentoLogin extends Fragment {
    View rootView;
    String username, password;
    Profesor profesor;
    ArrayList<Alumno> alumnos;
    ArrayList<Medalla> listaMedallas = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.content_main, container, false);
        Button botonLogin = (Button) rootView.findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse
                EditText cUser = (EditText) rootView.findViewById(R.id.editUser);
                EditText cPass = (EditText) rootView.findViewById(R.id.editPass);
                (new RealizarLogin(cUser.getText().toString(), cPass.getText().toString())).execute();


            }
        });

        return rootView;
    }


    class RealizarLogin extends AsyncTask<Void, Void, Void> {
        private String user, pass, response;
        private HashMap<String, String> requestBody;

        public RealizarLogin(String u, String p) {
            user = u;
            pass = p;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestBody = new HashMap<>();
            requestBody.put("username", user);
            String password = md5(pass);
            requestBody.put("password", password);
            requestBody.put("operacion", "login");
        }

        @Override
        protected Void doInBackground(Void... params) {
            response = Webservice.getInstancia().operacionPost(requestBody);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ArrayList<Clase> clasesProfe = new ArrayList<>();
            Clase clase1;

            if (response != null) {
                System.out.println(response);
                try {
                    JSONObject json = new JSONObject(response);
                    boolean exito = json.getBoolean("Exito");
                    String mensaje = json.getString("Mensaje");
                    if (exito) {
                        JSONObject respuesta = json.getJSONObject("Respuesta");
                        JSONArray clases = respuesta.getJSONArray("Clases");
                        //Obtengo la lista completa de medallas disponibles
                        JSONArray listaMedallasJSON = respuesta.getJSONArray("Medallas");
                        for(int t=0 ; t < listaMedallasJSON.length(); t++){
                            JSONObject medallaJSON = listaMedallasJSON.getJSONObject(t);
                            Medalla medalla1 = new Medalla(medallaJSON.getString("Name"), medallaJSON.getString("Photo"));
                            medalla1.setIdMedalla(medallaJSON.getString("idMedalla"));
                            medalla1.setDescripcion(medallaJSON.getString("Description"));
                            listaMedallas.add(medalla1);
                        }
                        DatosUsuario.getInstance().setListaMedallas(listaMedallas);
                        //Obtengo las clases del profesor
                        for (int i = 0; i < clases.length(); i++) {
                            JSONObject clase = clases.getJSONObject(i);
                            ArrayList<Tarea> tareasClase = new ArrayList<>();
                            ArrayList<Alumno> alumnosClase = new ArrayList<>();
                            clase1 = new Clase(clase.getString("SubjectName"), clase.getString("CourseName"), clase.getString("SubjectPicture"), clase.getString("Group"));
                            clase1.setIdAsignatura(clase.getInt("idAsignatura"));
                            clase1.setIdClase(clase.getInt("idClase"));
                            clase1.setIdCurso(clase.getInt("idCurso"));
                            JSONArray tareas = clase.getJSONArray("Tareas");
                            clase1.setTareasClase(tareasClase);
                            JSONArray alumnos = clase.getJSONArray("Alumnos");
                            clase1.setAlumnosClase(alumnosClase);
                            //De cada clase tomamos las tareas puestas por el profesor
                            for(int r=0; r < tareas.length(); r++){
                                JSONObject tarea = tareas.getJSONObject(r);
                                Tarea tarea2;
                                tarea2 = new Tarea(tarea.getString("Description"), tarea.getString("Createdate"), tarea.getString("Finishdate"));
                                tarea2.setIdTarea(tarea.getInt("idTarea"));
                                tareasClase.add(tarea2);
                            }
                            //Tomamos las notificaciones de cada clase
                            HashMap<Fecha, Notificacion> notificacionesHash = new HashMap<>();

                            JSONArray notificaciones = clase.getJSONArray("Notificaciones");
                            ArrayList<Notificacion> notificacionArray = new ArrayList<>();
                            clase1.setNotificacionesClase(notificacionesHash);
                            for(int q = 0; q < notificaciones.length(); q++){
                                JSONObject notificacion = notificaciones.getJSONObject(q);
                                Notificacion notificacion1 = new Notificacion(notificacion.getString("Description"), notificacion.getString("Date"), notificacion.getInt("idNotification"));
                                notificacionesHash.put(notificacion1.getFecha(), notificacion1);
                                notificacionArray.add(notificacion1);
                            }
                            clase1.setNotificacionArray(notificacionArray);
                            clase1.setNotificacionesClase(notificacionesHash);
                            //Tomamos los alumnos
                           HashMap<String,Alumno> hmClase = clase1.getHashAlumnoId();
                            for (int j = 0; j < alumnos.length(); j++) {
                                JSONObject alumno = alumnos.getJSONObject(j);
                                Alumno alumno2;
                                alumno2 = new Alumno(alumno.getString("Name"), alumno.getString("Surname"));
                                clase1.getAlumnosClase().add(j, alumno2);
                                alumno2.setFotoPerfil(alumno.getString("Photo"));
                                alumno2.setNickName(alumno.getString("Nickname"));
                                alumno2.setLevel(alumno.getInt("Level"));
                                alumno2.setExp(alumno.getInt("Experience"));
                                alumno2.setMonedas(alumno.getInt("Coins"));
                                alumno2.setIdAlumno(alumno.getString("idAlumno"));
                                hmClase.put(alumno.getString("idAlumno"), alumno2);

                                JSONArray medallas = alumno.getJSONArray("Medallas");
                                ArrayList<Medalla> arrayMedallas = new ArrayList<>();
                                //De cada alumno tomamos las medallas que tiene
                                for (int k = 0; k < medallas.length(); k++) {
                                    JSONObject medallaJson = medallas.getJSONObject(k);
                                    Medalla medallica = new Medalla(medallaJson.getString("Name"), medallaJson.getString("Photo"));
                                    medallica.setIdMedalla(medallaJson.getString("idMedalla"));
                                    medallica.setIdAsignatura(medallaJson.getString("idAsignatura"));
                                    medallica.setDescripcion(medallaJson.getString("Description"));
                                    arrayMedallas.add(medallica);
                                }
                                alumno2.setMedallasAlumno(arrayMedallas);

                                //De cada alumno tomamos los privilegios que tiene
                                JSONArray privilegios = alumno.getJSONArray("Privilegios");
                                ArrayList<Privilegio> arrayPrivilegios = new ArrayList<>();
                                for (int m = 0; m < privilegios.length(); m++) {
                                    JSONObject privilegioJson = privilegios.getJSONObject(m);
                                    Privilegio privilegio = new Privilegio(privilegioJson.getString("Name"), privilegioJson.getString("Description"));
                                    privilegio.setIdPrivilegio(privilegioJson.getString("idPrivilegio"));
                                    arrayPrivilegios.add(privilegio);
                                }
                                alumno2.setPrivilegiosAlumno(arrayPrivilegios);
                            }
                            //De cada clase tomamos los grupos de alumnos
                            ArrayList<Grupo> gruposClase = new ArrayList<>();
                            ArrayList<Alumno> alumnosGrupo = new ArrayList<>();
                            JSONArray gruposJson = clase.getJSONArray("Grupos");

                            for(int w = 0; w<gruposJson.length(); w++) {
                                JSONObject grupoJson = gruposJson.getJSONObject(w);
                                Grupo grupo1 = new Grupo(clase1, grupoJson.getString("Name"), grupoJson.getString("Photo"));
                                gruposClase.add(grupo1);
                                grupo1.setAlumnosGrupo(alumnosGrupo);
                                JSONArray alumnosGrupoJson = grupoJson.getJSONArray("Alumnos");
                                for(int a=0; a<alumnosGrupoJson.length(); a++){
                                    JSONObject alumnoJson = alumnosGrupoJson.getJSONObject(a);
                                   //Alumno alumno1;
                                    alumnosGrupo.add(hmClase.get(alumnoJson.getString("idAlumno")));
                                    //alumno1.setIdAlumno(alumnoJson.getString("idAlumno"));
                                }
                                grupo1.setAlumnosGrupo(alumnosGrupo);
                                clase1.setGruposClase(gruposClase);
                            }
                            clasesProfe.add(clase1);
                        }
                        //Tomamos los datos del profesor para llenar el objeto
                        profesor = new Profesor(respuesta.getString("Name"), respuesta.getString("Surname"));
                        profesor.setClasesProfe(clasesProfe);
                        profesor.setNickName(respuesta.getString("Nickname"));
                        profesor.setExp(respuesta.getInt("Experience"));
                        profesor.setLevel(respuesta.getInt("Level"));
                        profesor.setFotoPerfil(respuesta.getString("Photo"));
                        profesor.setIdProfesor(respuesta.getString("idProfesor"));

                        //Change the string value and launch intent to ActivityB
                        DatosUsuario.getInstance().setProfesor(profesor);
                        Intent intent = new Intent(getContext(), FragmentoPerfil.class);
                        Intent intent2 = new Intent(getContext(), FragmentoClases.class);
                        Intent intent3 = new Intent(getContext(), FragmentoClase.class);
                        getActivity().startActivity(new Intent(rootView.getContext(), PrincipalActivity.class));


                    } else {
                        Toast.makeText(rootView.getContext(), mensaje, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
