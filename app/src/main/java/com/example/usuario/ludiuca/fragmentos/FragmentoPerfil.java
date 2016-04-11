package com.example.usuario.ludiuca.fragmentos;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.DatosUsuario;


/**
 * Created by fabio on 22/03/2016.
 */
public class FragmentoPerfil extends Fragment {

   View rootView;
   Profesor profe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_perfil, container, false);
        profe = DatosUsuario.getInstance().getProfesor();
        Button botonChange = (Button)rootView.findViewById(R.id.b_change);
        TextView name = (TextView)rootView.findViewById(R.id.tv_nombre);
        TextView level = (TextView)rootView.findViewById(R.id.tv_level);
        TextView exp= (TextView)rootView.findViewById(R.id.tv_exp);
        TextView nick = (TextView)rootView.findViewById(R.id.tv_nick);

        name.setText(profe.getNombre());
        level.setText(String.valueOf(profe.getLevel()));
        exp.setText(String.valueOf(profe.getExp()));
        nick.setText(String.valueOf(profe.getNickName()));


        botonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse


            }
        });

        return rootView;
    }



    }













