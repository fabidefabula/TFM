package com.example.usuario.ludiuca;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.example.usuario.ludiuca.adaptadores.AdaptadorPrincipal;
import com.example.usuario.ludiuca.clases.SamplePagerItem;
import com.example.usuario.ludiuca.fragmentos.FragmentoCambiarAvatar;;
import com.example.usuario.ludiuca.widgets.SlidingTabLayout;

import java.util.List;
import java.util.ArrayList;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by fabio on 15/03/2016.
 */
public class PrincipalActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

            setContentView(R.layout.fragmento_tabs);
            final List<SamplePagerItem> tabs = new ArrayList<SamplePagerItem>();
            tabs.add(new SamplePagerItem("Perfil", Color.WHITE, Color.WHITE));
            tabs.add(new SamplePagerItem("Clases", Color.WHITE, Color.WHITE));
            ViewPager contenedorTabs = (ViewPager) findViewById(R.id.pager);

            AdaptadorPrincipal adaptador = new AdaptadorPrincipal(getSupportFragmentManager(), getApplicationContext());
            contenedorTabs.setAdapter(adaptador);
            //crear adaptadores para otras vistas para reutilizar la activity
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





