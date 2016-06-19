package com.example.usuario.ludiuca.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.usuario.ludiuca.fragmentos.FragmentoClase;
import com.example.usuario.ludiuca.fragmentos.FragmentoDarMedalla;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfil;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfilAlumno;

/**
 * Created by fabio on 11/04/2016.
 */
public class AdaptadorAlumno extends FragmentPagerAdapter {
    Context c;

    public AdaptadorAlumno(FragmentManager fm, Context ctx) {
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
                return new FragmentoPerfilAlumno();
            case 1:
                return new FragmentoDarMedalla();
            case 2:
                return new FragmentoPerfil();
            case 3:
                return new FragmentoPerfilAlumno();
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Alumno";
            case 1:
                return "Dar Medalla";
            case 2:
                return "Actitudes";
            case 3:
                return "Privilegios";
        }
        return "";
    }
    @Override
    public int getCount() {
        return 4;
    }
}
