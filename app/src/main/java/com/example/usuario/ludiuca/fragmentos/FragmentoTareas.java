package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.usuario.ludiuca.AltaTareaActivity;
import com.example.usuario.ludiuca.ClaseActivity;
import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.TareasActivity;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Tarea;
import com.example.usuario.ludiuca.clases.Webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by fabio on 30/03/2016.
 */
public class FragmentoTareas extends Fragment {

    View rootView;
    ListView lvTareas;
    ArrayList<Tarea> tareasClase;
    Clase claseSeleccionada;



    public FragmentoTareas() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_tareas, container, false);

        claseSeleccionada = DatosUsuario.getInstance().getClase();
        tareasClase = claseSeleccionada.getTareasClase();
        AdaptadorTareas adaptador = new AdaptadorTareas(getActivity(), tareasClase);
        lvTareas = (ListView) rootView.findViewById(R.id.lvTareas);
        lvTareas.setAdapter(adaptador);
        FloatingActionButton inserta = (FloatingActionButton) rootView.findViewById(R.id.bInsertarTarea);

        inserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoTareas.this.getActivity().startActivity(new Intent(rootView.getContext(), AltaTareaActivity.class));
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {

        tareasClase = claseSeleccionada.getTareasClase();
        AdaptadorTareas adaptador = new AdaptadorTareas(getActivity(), tareasClase);
        lvTareas = (ListView) rootView.findViewById(R.id.lvTareas);
        lvTareas.setAdapter(adaptador);

        super.onStart();
    }

    class AdaptadorTareas extends ArrayAdapter<Tarea> {
        ArrayList<Tarea> tareas;

        public AdaptadorTareas(Context context, ArrayList<Tarea> tareas) {
            super(context, R.layout.listitem_tareas, tareas);
            this.tareas = tareas;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_tareas, null);

            TextView lblDescription = (TextView) item.findViewById(R.id.LblDescription);
            lblDescription.setText(tareasClase.get(pos).getDescription());
            TextView lblFechaCreacion = (TextView) item.findViewById(R.id.LblFechaCreacion);
            TextView lblFechaEntrega = (TextView) item.findViewById(R.id.LblFechaEntrega);
            //arreglar
            lblFechaCreacion.setText("Fecha creación: " + tareasClase.get(pos).getFechaCreacionString());
            lblFechaEntrega.setText("Fecha entrega: " + tareasClase.get(pos).getFechaEntregaString());
            ImageView botonBorrarTarea = (ImageView) item.findViewById(R.id.ivBorrarTarea);
            botonBorrarTarea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final int id = tareasClase.get(pos).getIdTarea();
                    (new Thread() {
                        public void run() {
                            String response = null;
                            try {
                                HashMap<String, String> requestBody;
                                requestBody = new HashMap<>();
                                requestBody.put("operacion", "borrarTarea");
                                requestBody.put("idTarea", "" + id);
                                response = Webservice.getInstancia().operacionPost(requestBody);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (response == null) {
                                    //Toast.makeText(rootView.getContext(), "Error al borrar", Toast.LENGTH_LONG).show();
                                } else {
                                    //Toast.makeText(rootView.getContext(), "Tarea borrada", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }).start();
                    tareasClase.remove(pos);
                    notifyDataSetChanged();

                }
            });
            SwipeLayout swipeLayout = (SwipeLayout) item.findViewById(R.id.swipe);

//set show mode.
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, item.findViewById(R.id.bottom_wrapper));

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onClose(SwipeLayout layout) {
                    //when the SurfaceView totally cover the BottomView.
                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                    //you are swiping.
                }

                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    //when the BottomView totally show.
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    //when user's hand released.
                }
            });

            return (item);
        }

    }


}


