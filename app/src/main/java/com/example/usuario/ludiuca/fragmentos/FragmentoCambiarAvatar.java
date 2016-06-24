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

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.TareasActivity;
import com.example.usuario.ludiuca.adaptadores.AdaptadorCambiarAvatarProfe;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Avatar;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Medalla;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Tarea;
import com.example.usuario.ludiuca.clases.Webservice;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Usuario on 17/02/2016.
 */
public class FragmentoCambiarAvatar extends Fragment {
    View rootView;
    Profesor profesor;
    GridView cambiarAvatar;
    ArrayList<Avatar> avatares;
    Avatar elegido;
    int posicionElegido;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_cambiar_avatar_profe, container, false);
        Button botonAlta = (Button) rootView.findViewById(R.id.bAlta);
        avatares = DatosUsuario.getInstance().getAvataresProfe();
        elegido = avatares.get(1);
        AdaptadorCambiarAvatarProfeView adaptador = new AdaptadorCambiarAvatarProfeView(getActivity(), avatares );

        cambiarAvatar = (GridView) rootView.findViewById(R.id.gvCambiarAvatar);
        cambiarAvatar.setAdapter(adaptador);
        botonAlta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new actualizarAvatarProfe(elegido).execute();
            }
        });

        return rootView;
    }

    class AdaptadorCambiarAvatarProfeView extends ArrayAdapter<Avatar> {
        ArrayList<Avatar> avatares;
        public  AdaptadorCambiarAvatarProfeView(Context context, ArrayList<Avatar> avatares){
            super(context, R.layout.listitem_avatares_profe, avatares);
            this.avatares = avatares;

        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;


            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_avatares_profe, null);
            final ImageView avatar = (ImageView)item.findViewById(R.id.avatarProfe);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(avatares.get(position).getPhoto(), avatar);
            if(position != posicionElegido){
                item.setBackgroundColor(Color.WHITE);
            }else{
                item.setBackgroundColor(Color.GRAY);
            }

            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < avatares.size(); i++){
                        if(avatares.get(i).getFlag()==false && i == position){
                            avatares.get(position).setFlag(true);
                            posicionElegido = i;
                        }else{
                            avatares.get(i).setFlag(false);

                        }
                    }
                    System.out.println("click en la posición" + " " + position);
                    elegido = new Avatar(avatares.get(position).getIdAvatar(), avatares.get(position).getPhoto());
                    v.setBackgroundColor(Color.GRAY);
                    ((BaseAdapter) cambiarAvatar.getAdapter()).notifyDataSetChanged();

                }
            });


            return(item);
        }

    }
    class actualizarAvatarProfe extends AsyncTask<Void, Void, Void> {
        private String descripcion, fechaEntrega, response;
        private Avatar avatar;
        private HashMap<String, String> requestBody;

        public actualizarAvatarProfe(Avatar a) {
            avatar = a;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestBody = new HashMap<>();
            requestBody.put("operacion", "actualizarAvatarProfe");
            requestBody.put("idProfesor", "" + DatosUsuario.getInstance().getProfesor().getIdProfesor());
            requestBody.put("Photo", avatar.getPhoto());
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
                        DatosUsuario.getInstance().getProfesor().setFotoPerfil(avatar.getPhoto());

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
