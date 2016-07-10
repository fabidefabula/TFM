package com.example.usuario.ludiuca.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.usuario.ludiuca.fragmentos.FragmentoAlumnosActitudes;
import com.example.usuario.ludiuca.fragmentos.FragmentoCambiarAvatar;

public class AdaptadorAlumnosActitudes extends FragmentPagerAdapter {
    Context c;

    public AdaptadorAlumnosActitudes(FragmentManager fm, Context ctx) {
        super(fm);
        c = ctx;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new FragmentoAlumnosActitudes();

        }
        return null;
    }

    public Fragment get(int p) {
        return getItem(p);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Seleccionar alumnos";

        }
        return "";
    }

    @Override
    public int getCount() {
        return 1;
    }

}