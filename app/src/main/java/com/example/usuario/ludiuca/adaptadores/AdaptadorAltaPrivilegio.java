package com.example.usuario.ludiuca.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.usuario.ludiuca.fragmentos.FragmentoAltaPrivilegio;
import com.example.usuario.ludiuca.fragmentos.FragmentoAltaTarea;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfilAlumno;

/**
 * Created by fabio on 11/04/2016.
 */
public class AdaptadorAltaPrivilegio extends FragmentPagerAdapter {
    Context c;

    public AdaptadorAltaPrivilegio(FragmentManager fm, Context ctx) {
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
                return new FragmentoAltaPrivilegio();

        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Insertar Privilegio";
        }
        return "";
    }
    @Override
    public int getCount() {
        return 1;
    }
}
