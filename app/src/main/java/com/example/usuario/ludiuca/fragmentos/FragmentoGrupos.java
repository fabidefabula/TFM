package com.example.usuario.ludiuca.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.usuario.ludiuca.AltaGrupoActivity;
import com.example.usuario.ludiuca.AltaTareaActivity;
import com.example.usuario.ludiuca.AlumnoActivity;
import com.example.usuario.ludiuca.AlumnosGrupoActivity;
import com.example.usuario.ludiuca.PrincipalActivity;
import com.example.usuario.ludiuca.R;
import com.example.usuario.ludiuca.clases.Alumno;
import com.example.usuario.ludiuca.clases.Clase;
import com.example.usuario.ludiuca.clases.DatosUsuario;
import com.example.usuario.ludiuca.clases.Grupo;
import com.example.usuario.ludiuca.clases.Profesor;
import com.example.usuario.ludiuca.clases.Webservice;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.PendingIntent.getActivity;

public class FragmentoGrupos extends Fragment {
    View rootView;
    Clase claseSeleccionada;
    Profesor profe;
    ListView lvGrupos;
    ArrayList<Grupo> grupos;
    LinearLayout grupo;
    public FragmentoGrupos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragmento_grupos, container, false);

        profe = DatosUsuario.getInstance().getProfesor();
        claseSeleccionada = DatosUsuario.getInstance().getClase();
        grupos = claseSeleccionada.getGruposClase();
        FloatingActionButton bCrear = (FloatingActionButton)rootView.findViewById(R.id.bCrearGrupo);
       bCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoGrupos.this.getActivity().startActivity(new Intent(rootView.getContext(), AltaGrupoActivity.class));

            }
        });
        AdaptadorGrupo adaptador = new AdaptadorGrupo(getActivity(), grupos);
        lvGrupos = (ListView) rootView.findViewById(R.id.lvGrupos);
        lvGrupos.setAdapter(adaptador);

        return rootView;
    }

    @Override
    public void onStart() {
        grupos = claseSeleccionada.getGruposClase();
        AdaptadorGrupo adaptador = new AdaptadorGrupo(getActivity(), grupos);
        lvGrupos = (ListView) rootView.findViewById(R.id.lvGrupos);
        lvGrupos.setAdapter(adaptador);
        super.onStart();
    }

    class AdaptadorGrupo extends ArrayAdapter<Grupo> {
        ArrayList<Grupo> grupos;
        public AdaptadorGrupo(Context context, ArrayList<Grupo> grupos) {
            super(context, R.layout.lisitem_grupos, grupos);
            this.grupos = grupos;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.lisitem_grupos, null);
            TextView lblNombre = (TextView) item.findViewById(R.id.LblNombre);
            lblNombre.setText(grupos.get(position).getNombre());
            ImageView imagenGrupo = (ImageView) item.findViewById(R.id.imageGrupo);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(grupos.get(position).getFotoGrupo(), imagenGrupo);
            grupo = (LinearLayout)item.findViewById(R.id.clickGrupo);
            ImageView botonBorrarGrupo= (ImageView) item.findViewById(R.id.ivBorrarGrupo);
            botonBorrarGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int id = grupos.get(pos).getIdGrupo();
                    (new Thread() {
                        public void run() {
                            String response = null;
                            try {
                                HashMap<String, String> requestBody;
                                requestBody = new HashMap<>();
                                requestBody.put("operacion", "borrarGrupo");
                                requestBody.put("idGrupo", "" + id);
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
                    grupos.remove(pos);
                    notifyDataSetChanged();
                }
            });
            grupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatosUsuario.getInstance().setGrupoSeleccionado(grupos.get(pos));
                    FragmentoGrupos.this.getActivity().startActivity(new Intent(rootView.getContext(), AlumnosGrupoActivity.class));

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
            return(item);
            }
        }

    }



