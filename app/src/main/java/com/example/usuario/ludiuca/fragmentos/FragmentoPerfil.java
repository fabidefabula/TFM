package com.example.usuario.ludiuca.fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by fabio on 22/03/2016.
 */
public class FragmentoPerfil extends Fragment {

   View rootView;
   Profesor profe;
    int numNiveles = 20;
    int expPerLevel = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_perfil, container, false);
        profe = DatosUsuario.getInstance().getProfesor();
        Button botonChange = (Button)rootView.findViewById(R.id.b_change);
        TextView name = (TextView)rootView.findViewById(R.id.tv_nombre);
        TextView level = (TextView)rootView.findViewById(R.id.tv_level);
        TextView exp= (TextView)rootView.findViewById(R.id.tv_exp);
        TextView nick = (TextView)rootView.findViewById(R.id.tv_nick);


//        Intent data = new Intent();
//        data.setType("image/*");
//        data.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(data, "Select Picture"), R.drawable.ic_add_circle_outline_black_24dp);
//        Uri Selected_Image_Uri=data.getData();
//        ImageView imageView= (ImageView) rootView.findViewById(R.id.img_perfil);
//        imageView.setImageURI(Selected_Image_Uri);

        ImageView imagenPerfil = (ImageView)rootView.findViewById(R.id.img_perfil);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(profe.getFotoPerfil(), imagenPerfil);
        ProgressBar barraNivel = (ProgressBar)rootView.findViewById(R.id.progressBar);
        barraNivel.setProgress(profe.getLevel() * 100 / numNiveles);
        barraNivel.setSecondaryProgress((profe.getExp())*100/(profe.getLevel()*expPerLevel));

        name.setText(name.getText() + profe.getNombre());
        level.setText(level.getText() + String.valueOf(profe.getLevel()));
        exp.setText(exp.getText() + String.valueOf(profe.getExp()));
        nick.setText(nick.getText() + String.valueOf(profe.getNickName()));


        botonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lo que haga cuando pulse


            }
        });

        return rootView;
    }



    }













