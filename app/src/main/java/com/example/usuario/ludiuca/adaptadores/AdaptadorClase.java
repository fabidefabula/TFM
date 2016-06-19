package com.example.usuario.ludiuca.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.usuario.ludiuca.fragmentos.FragmentoClase;
import com.example.usuario.ludiuca.fragmentos.FragmentoClases;
import com.example.usuario.ludiuca.fragmentos.FragmentoLogin;
import com.example.usuario.ludiuca.fragmentos.FragmentoNotificaciones;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfil;
import com.example.usuario.ludiuca.fragmentos.FragmentoTareas;

/**
 * Created by fabio on 11/04/2016.
 */
public class AdaptadorClase extends FragmentPagerAdapter {
    Context c;

    public AdaptadorClase(FragmentManager fm, Context ctx) {
        super(fm);
        c = ctx;

    }
    public Fragment get(int p) {
        return getItem(p);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new FragmentoTareas();
            case 1:
                return new FragmentoNotificaciones();
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tareas";
            case 1:
                return "Notificaciones";
        }
        return "";
    }
    @Override
    public int getCount() {
        return 2;
    }
}
