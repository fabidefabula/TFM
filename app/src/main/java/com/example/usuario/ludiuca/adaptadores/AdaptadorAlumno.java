package com.example.usuario.ludiuca.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.usuario.ludiuca.MainActivityCamara;
import com.example.usuario.ludiuca.fragmentos.FragmentoClase;
import com.example.usuario.ludiuca.fragmentos.FragmentoDarMedalla;
import com.example.usuario.ludiuca.fragmentos.FragmentoGaleria;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfil;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfilAlumno;
import com.example.usuario.ludiuca.fragmentos.FragmentoPrivilegios;

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
                return new FragmentoPrivilegios();
           case 3:
                return new FragmentoGaleria();

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
                return "Privilegios";
            case 3:
                return "Galería";
        }
        return "";
    }
    @Override
    public int getCount() {
        return 4;
    }
}
