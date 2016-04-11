package com.example.usuario.ludiuca.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.usuario.ludiuca.fragmentos.FragmentoClases;
import com.example.usuario.ludiuca.fragmentos.FragmentoLogin;
import com.example.usuario.ludiuca.fragmentos.FragmentoPerfil;

/**
 * Created by fabio on 11/04/2016.
 */
public class AdaptadorClase extends FragmentPagerAdapter {
    Context c;

    public AdaptadorClase(FragmentManager fm, Context ctx) {
        super(fm);
        c = ctx;

    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new FragmentoPerfil();
            case 1:
                return new FragmentoClases();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
