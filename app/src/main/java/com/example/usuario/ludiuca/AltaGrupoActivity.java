package com.example.usuario.ludiuca;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.example.usuario.ludiuca.adaptadores.AdaptadorAltaGrupo;
import com.example.usuario.ludiuca.adaptadores.AdaptadorAltaTarea;
import com.example.usuario.ludiuca.clases.SamplePagerItem;
import com.example.usuario.ludiuca.widgets.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fabio on 12/04/2016.
 */
public class AltaGrupoActivity extends ActionBarActivity{
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragmento_tabs);
            final List<SamplePagerItem> tabs = new ArrayList<SamplePagerItem>();
            tabs.add(new SamplePagerItem("Tareas", Color.WHITE, Color.WHITE));
            tabs.add(new SamplePagerItem("Notificaciones", Color.WHITE, Color.WHITE));
            ViewPager contenedorTabs = (ViewPager)findViewById(R.id.pager);
            AdaptadorAltaGrupo adaptadoraltagrupo = new AdaptadorAltaGrupo(getSupportFragmentManager(), getApplicationContext());
            contenedorTabs.setAdapter(adaptadoraltagrupo);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            final SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidings_tabs);
            mSlidingTabLayout.setViewPager(contenedorTabs);
            mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return tabs.get(position).getIndicatorColor();
                }

                @Override
                public int getDividerColor(int position) {
                    return tabs.get(position).getDividerColor();
                }
            });

        }

}
